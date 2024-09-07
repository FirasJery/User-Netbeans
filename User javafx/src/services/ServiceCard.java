/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Card;
import entities.Utilisateur;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.DataSource;

/**
 *
 * @author Ghass
 */
public class ServiceCard implements IService<Card> {
    
    
    Connection cnx = DataSource.getInstance().getCnx();
     @Override
     public void ajouter(Card c) {
        try {
            String req = "INSERT INTO credit_card (nom, prenom,date_expiration,cvc,zipcode,ville,num,id_user) VALUES ('" + c.getNom() + "', '" + c.getPrenom() + "','" + c.getDate() + "','" + c.getCvc() + "','" + c.getZipcode() + "','" + c.getVille() + "','" + c.getNum() + "','"+c.getUser().getId()+"')";
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("card created !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
     
     @Override
    public void supprimer(int id) {
        try {
            String req = "DELETE FROM credit_card WHERE id = " + id;
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("card deleted !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    @Override
    public void modifier(Card c) {
        try {
            String req = "UPDATE credit_card SET nom = '" + c.getNom() + "', prenom = '" + c.getPrenom() + "', date_expiration = '" + c.getDate() + "',cvc = '" + c.getCvc() + "',zipcode = '" + c.getZipcode() + "',ville = '" + c.getVille() + "',num = '" + c.getNum() + "',id_user='"+c.getUser().getId()+"' WHERE credit_card.id = " + c.getId();
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("card updated !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
      @Override
    public List<Card> getAll() {
        List<Card> list = new ArrayList<>();
        try {
            String req = "Select * from credit_card";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Utilisateur u;
                ServiceUser su = new ServiceUser();
                u= su.getOneById(rs.getInt("id_user"));
                Card c = new Card(rs.getInt(1), rs.getString("nom"), rs.getString(3),rs.getString("date_expiration"),rs.getInt("cvc"),rs.getInt("zipcode"),rs.getString("ville"),rs.getString("num"),u);
                list.add(c);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }
    
    @Override
    public Card getOneById(int id) {
        Card c = null;
        try {
            String req = "Select * from credit_card WHERE credit_card.id='"+id+"'";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                 Utilisateur u;
                ServiceUser su = new ServiceUser();
                u= su.getOneById(rs.getInt("id_user"));
                 c = new Card(rs.getInt(1), rs.getString("nom"), rs.getString(3),rs.getString("date_expiration"),rs.getInt("cvc"),rs.getInt("zipcode"),rs.getString("ville"),rs.getString("num"),u);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return c;
    }
     
    public ObservableList<Card> find() {
        ObservableList<Card> l = FXCollections.observableArrayList(); 
        ServiceCard us = new ServiceCard();
        try {
       String query2="SELECT * FROM credit_card ";
                PreparedStatement smt = cnx.prepareStatement(query2);
                // smt.setInt(1, s);
                Card c;
                ResultSet rs= smt.executeQuery(query2);
                while(rs.next()){
                    Utilisateur u;
                ServiceUser su = new ServiceUser();
                u= su.getOneById(rs.getInt("id_user"));
                    
                  c=new Card(rs.getString("nom"),rs.getString("prenom"),rs.getString("Date_expiration"),rs.getInt("cvc"),rs.getInt("zipcode"),rs.getString("ville"),rs.getString("num"),u);
                   l.add(c);
                }
                System.out.println(l);
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
    }
         return l;
}
    
    public List<Card> getMyCards(int id) {
        List<Card> list = new ArrayList<>();
        try {
            String req = "Select * from credit_card WHERE credit_card.id_user='"+id+"' ";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Utilisateur u;
                ServiceUser su = new ServiceUser();
                u= su.getOneById(id);
                Card c = new Card(rs.getInt(1), rs.getString("nom"), rs.getString(3),rs.getString("date_expiration"),rs.getInt("cvc"),rs.getInt("zipcode"),rs.getString("ville"),rs.getString("num"),u);
                list.add(c);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }
    public ObservableList<String> getMyCardW(int id) {
        ObservableList<String> list = FXCollections.observableArrayList();
        String num;
        try {
            String req = "Select num from credit_card WHERE credit_card.id_user='"+id+"' ";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                num=rs.getString("num");
                list.add(num);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }
    public Boolean Checksolde(int num,float somme)
    {
        int a=0;
        try {
            Card c;
            
            String req = "Select * from credit_card WHERE credit_card.num='"+String.valueOf(num)+"' ";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Utilisateur u;
                ServiceUser su = new ServiceUser();
                u= su.getOneById(rs.getInt("id_user"));
                 c = new Card(rs.getInt(1), rs.getString("nom"), rs.getString(3),rs.getString("date_expiration"),rs.getInt("cvc"),rs.getInt("zipcode"),rs.getString("ville"),rs.getString("num"),u);
                 a = c.getCvc();
            }
            } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        if(a>somme)
        { 
            return true;
        }
        else 
            return false;
    }
    public void Soustraire(Card c , int somme)
    {
        
    int a = c.getCvc();
    int b = a-somme;
    c.setCvc(b);
    modifier(c);
    
    
    }
    
    
    public Card getOneByNum(int num) {
        Card c = null;
        try {
            String req = "Select * from credit_card WHERE credit_card.num='"+String.valueOf(num)+"'";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                 Utilisateur u;
                ServiceUser su = new ServiceUser();
                u= su.getOneById(rs.getInt("id_user"));
                 c = new Card(rs.getInt(1), rs.getString("nom"), rs.getString(3),rs.getString("date_expiration"),rs.getInt("cvc"),rs.getInt("zipcode"),rs.getString("ville"),rs.getString("num"),u);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return c;
    }
    
}

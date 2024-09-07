/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Certif;
import entities.Test;
import java.sql.Connection;
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
public class ServiceTest implements IService <Test>{
    
    Connection cnx = DataSource.getInstance().getCnx();
     @Override
    public void ajouter(Test c) {
        try {
            String req = "INSERT INTO test (titre,categorie,description,idcertif,prix) VALUES ('" + c.getTitre() + "', '" + c.getCategorie()+ "','" + c.getDesc() + "','" + c.getIdcertif().getId() + "','"+c.getPrix()+"')";
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Test created !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    
    @Override
    public void supprimer(int id) {
        try {
            String req = "DELETE FROM test WHERE id = " + id;
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Test deleted !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    @Override
    public void modifier(Test c) {
        try {
            String req = "UPDATE test SET titre = '" + c.getTitre() + "', categorie = '" + c.getCategorie() + "', description = '" + c.getDesc() + "',idcertif = '" + c.getIdcertif().getId() + "',prix='"+c.getPrix()+"'  WHERE test.id = " + c.getId();
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("card updated !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    @Override
    public List<Test> getAll() {
        List<Test> list = new ArrayList<>();
        try {
            String req = "Select * from test";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                ServiceCertif sw = new ServiceCertif();
                Certif u;
                
                u=sw.getOneById(rs.getInt("idcertif"));
                Test c = new Test(rs.getInt(1), rs.getString("titre"), rs.getString(3),rs.getString("description"),u,rs.getFloat("prix"));
                list.add(c);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }
    public List<Test> getByIdCertif(int id) {
        List<Test> list = new ArrayList<>();
        try {
            String req = "Select * from test WHERE test.idcertif ='"+id+"'";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                ServiceCertif sw = new ServiceCertif();
                Certif u;
                
                u=sw.getOneById(rs.getInt("idcertif"));
                Test c = new Test(rs.getInt(1), rs.getString("titre"), rs.getString(3),rs.getString("description"),u,rs.getFloat("prix"));
                list.add(c);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }
    
    public ObservableList<String> getall() {
        ObservableList<String> posts = FXCollections.observableArrayList();
        try {
            String req = "select distinct nom from categorie";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);

            while (rs.next()) {
               String d = rs.getString("nom");
                
               posts.add(d);
            }
            

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return posts;
    }
    
    @Override
    public Test getOneById(int id) {
        Test c = null;
        try {
            String req = "Select * from test WHERE test.id ='"+id+"'";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                 ServiceCertif sw = new ServiceCertif();
                Certif u;
                
                u=sw.getOneById(rs.getInt("idcertif"));
                c = new Test(rs.getInt(1), rs.getString("titre"), rs.getString(3),rs.getString("description"),u,rs.getFloat("prix"));
              
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return c;
    }
      public List<Test> getSuccess(int iduser,int idcertif) {
          
        List<Test> list = new ArrayList<>();
        try {
            String req = "Select * from test join passage on test.id=passage.idtest WHERE passage.iduser='"+iduser+"' AND passage.etat=1 AND test.idcertif='"+idcertif+"'";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                ServiceCertif sw = new ServiceCertif();
                Certif u;
                
                u=sw.getOneById(rs.getInt("idcertif"));
                Test c = new Test(rs.getInt(1), rs.getString("titre"), rs.getString(3),rs.getString("description"),u,rs.getFloat("prix"));
                list.add(c);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }
}

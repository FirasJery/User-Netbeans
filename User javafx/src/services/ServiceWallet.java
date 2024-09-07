/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Utilisateur;
import entities.Wallet;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import utils.DataSource;

/**
 *
 * @author Ghass
 */
public class ServiceWallet implements IService<Wallet> {
    
    Connection cnx = DataSource.getInstance().getCnx();
     @Override
    public void ajouter(Wallet w) {
        try {
            String req = "INSERT INTO wallet (Nom_wallet, solde,bonus,tel,id_user,cle) VALUES ('" + w.getNum_carte() + "', '" + w.getSolde() + "','" + w.getBonus() + "','"+w.getTel()+"','" +w.getIduser().getId()+"','"+w.getCle()+"')";
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Wallet created !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    @Override
    public void supprimer(int id) {
        try {
            String req = "DELETE FROM wallet  WHERE id = " + id;
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Wallet deleted !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    @Override
    public void modifier(Wallet w) {
        try {
            String req = "UPDATE wallet SET Nom_wallet = '" + w.getNum_carte() + "', solde = '" + w.getSolde() + "',bonus = '" + w.getBonus() + "',tel = '"+w.getTel()+"',id_user ='"+w.getIduser().getId()+"',cle='"+w.getCle()+"' WHERE wallet.id = " + w.getId();
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Wallet updated !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
        public void modifierSolde(Wallet w, float solde) {
             float bonus = (float) (solde * 0.2);
             bonus +=w.getBonus();
        
        try {
            String req = "UPDATE wallet SET  solde = '" +solde + "',bonus = '" + bonus + "' WHERE wallet.id = " + w.getId();
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Wallet updated !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
        public void modifierSoldeT(Wallet w, float solde) {
             float bonus = (float) (solde * 0.2);
             bonus +=w.getBonus();
        
        try {
            String req = "UPDATE wallet SET  solde = '" +solde + "',bonus = '" + bonus + "' WHERE wallet.cle = " + w.getCle();
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Wallet updated !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    @Override
    public List<Wallet> getAll() {
        List<Wallet> list = new ArrayList<>();
        try {
            String req = "Select * from wallet";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Utilisateur u;
                ServiceUser ss = new ServiceUser();
                u = ss.getOneById(rs.getInt(6));
                Wallet w = new Wallet(rs.getInt(1), rs.getString(2), rs.getFloat(3),rs.getFloat(4),rs.getString("tel"),u,rs.getString("cle"));
                list.add(w);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }
    @Override
    public Wallet getOneById(int id) {
        Wallet w = null;
        try {
            String req = "Select * from wallet WHERE wallet.id = "+id;
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Utilisateur u;
                ServiceUser ss = new ServiceUser();
                u = ss.getOneById(rs.getInt(6));
                 w = new Wallet(rs.getInt(1), rs.getString(2), rs.getFloat(3),rs.getFloat(4),rs.getString("tel"),u,rs.getString("cle"));
                 System.out.println(rs.getString("tel"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return w;
    }
    public Wallet getOneByKey(String id) {
        Wallet w = null;
        try {
            String req = "Select * from wallet WHERE wallet.cle = '"+id+"'";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Utilisateur u;
                ServiceUser ss = new ServiceUser();
                u = ss.getOneById(rs.getInt(6));
                 w = new Wallet(rs.getInt(1), rs.getString(2), rs.getFloat(3),rs.getFloat(4),rs.getString("tel"),u,rs.getString("cle"));
                 System.out.println(rs.getString("tel"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return w;
    }
    public Wallet getOneByUserId(int id) {
        Wallet w = null;
        try {
            String req = "Select * from wallet WHERE wallet.id_user = "+id;
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Utilisateur u;
                ServiceUser ss = new ServiceUser();
                u = ss.getOneById(id);
                 w = new Wallet(rs.getInt(1), rs.getString("Nom_wallet"), rs.getFloat(3),rs.getFloat(4),rs.getString("tel"),u,rs.getString("cle"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return w;
    }
    public Wallet getOneByNum(String id) {
        Wallet w = null;
        try {
            String req = "Select * from wallet WHERE wallet.nom_wallet = "+id;
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Utilisateur u;
                ServiceUser ss = new ServiceUser();
                u = ss.getOneById(rs.getInt("id_user"));
                 w = new Wallet(rs.getInt(1), rs.getString("Nom_wallet"), rs.getFloat(3),rs.getFloat(4),rs.getString("tel"),u,rs.getString("cle"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return w;
    }
    
    public void AddSolde(Wallet c , float somme)
    {
     float a =c.getSolde();
     float b = a+somme;
    c.setSolde(b);
    modifierSolde(c,b);
    
    
    }
    
     public void AddSoldeT(Wallet c , int somme)
    {
     float a =c.getSolde();
     float b = a+somme;
    c.setSolde(b);
    modifierSoldeT(c,b);
    
    
    }
    
    
    public Boolean Checksolde(int id,float somme)
    {
        float a=0;
        try {
            Wallet c;
            
            String req = "Select * from wallet WHERE wallet.id='"+id+"' ";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Utilisateur u;
                ServiceUser su = new ServiceUser();
                u= su.getOneById(rs.getInt("id_user"));
                 c = new Wallet(rs.getInt(1), rs.getString("nom_wallet"), rs.getFloat(3),rs.getFloat(4),rs.getString("tel"),u,rs.getString("cle"));
                 a = c.getSolde();
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
    

    public void LowerSolde(Wallet c , int somme)
    {
     float a =c.getSolde();
     float b = a-somme;
    c.setSolde(b);
    modifierSolde(c,b);
    
    
    }
    
     public int ChercherW(int id) {

        try {
            String req = "SELECT * from wallet WHERE wallet.id_user ='" + id + "'  ";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                if (rs.getInt("id_user")==id) {
                    System.out.println("Wallet trouvé ! ");
                    return 1;
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return -1;
    }
    
    
    public String generateRandomString(int length) {
        
        String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder builder = new StringBuilder();
        
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(CHARACTERS.length());
            builder.append(CHARACTERS.charAt(index));
        }
        
        return builder.toString();
    }
}








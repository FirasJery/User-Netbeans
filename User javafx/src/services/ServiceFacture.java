/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Facture;
import entities.Transaction;
import entities.Utilisateur;
import entities.Wallet;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import utils.DataSource;

/**
 *
 * @author Ghass
 */
public class ServiceFacture implements IService<Facture> {
    
    Connection cnx = DataSource.getInstance().getCnx();
     @Override
    public void ajouter(Facture c) {
        try {
            String req = "INSERT INTO facture (montant_paye, wallet_s,certif,id_user) VALUES ('" + c.getMontant() + "', '" + c.getWallet_s().getId() + "','" + c.getNom_certif() + "','" + c.getUser().getId() + "')";
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
            String req = "DELETE FROM facture WHERE id = " + id;
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Transaction deleted !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    @Override
    public void modifier(Facture c) {
        try {
            String req = "UPDATE facture SET montant_paye = '" + c.getMontant() + "', wallet_s = '" + c.getWallet_s().getId() + "', certif = '" + c.getNom_certif() + "',id_user = '" + c.getUser().getId() + "'  WHERE facture.id = " + c.getId();
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("card updated !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    @Override
    public List<Facture> getAll() {
        List<Facture> list = new ArrayList<>();
        try {
            String req = "Select * from facture";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                ServiceUser su = new ServiceUser();
                ServiceWallet sw = new ServiceWallet();
                Wallet s;
                Utilisateur u;
                s=sw.getOneById(rs.getInt("wallet_s"));
                u=su.getOneById(rs.getInt("id_user"));
               
                Facture c = new Facture(rs.getInt(1), rs.getFloat("montant_paye"),s,rs.getString("certif"),u);
                list.add(c);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }
    
    public Facture getOneById(int id) {
        Facture c = null;
        try {
            String req = "Select * from facture WHERE facture.id ='"+id+"'";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                ServiceUser su = new ServiceUser();
                ServiceWallet sw = new ServiceWallet();
                Wallet s;
                Utilisateur u;
                s=sw.getOneById(rs.getInt("wallet_s"));
                u=su.getOneById(rs.getInt("id_user"));
               
                c = new Facture(rs.getInt(1), rs.getFloat("montant_paye"),s,rs.getString("certif"),u);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return c;
    }
    
    public List<Facture> getMyFacts(int id) {
        List<Facture> list = new ArrayList<>();
        try {
            String req = "Select * from facture WHERE facture.id_user='"+id+"' ";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                ServiceUser su = new ServiceUser();
                ServiceWallet sw = new ServiceWallet();
                Wallet s;
                Utilisateur u;
                s=sw.getOneById(rs.getInt("wallet_s"));
                u=su.getOneById(rs.getInt("id_user"));
               
                Facture c = new Facture(rs.getInt(1), rs.getFloat("montant_paye"),s,rs.getString("certif"),u);
                list.add(c);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }
    
}

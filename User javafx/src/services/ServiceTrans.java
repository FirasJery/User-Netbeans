/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Card;
import entities.Data;
import entities.Transaction;
import entities.Utilisateur;
import entities.Wallet;
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
public class ServiceTrans implements IService<Transaction> {
    
    Connection cnx = DataSource.getInstance().getCnx();
     @Override
    public void ajouter(Transaction c) {
        try {
            String req = "INSERT INTO transaction (date, montant,sending_wallet,rec_wallet,etat) VALUES ('" + c.getDate() + "', '" + c.getMontant() + "','" + c.getS_wallet().getId() + "','" + c.getR_wallet().getId() + "','" + c.isEtat() + "')";
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
            String req = "DELETE FROM transaction WHERE id = " + id;
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Transaction deleted !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    @Override
    public void modifier(Transaction c) {
        try {
            String req = "UPDATE transaction SET date = '" + c.getDate() + "', montant = '" + c.getMontant() + "', sending_wallet = '" + c.getS_wallet().getId() + "',rec_wallet = '" + c.getR_wallet().getId() + "',etat = '" + c.isEtat() + "'  WHERE transaction.id = " + c.getId();
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("card updated !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    @Override
    public List<Transaction> getAll() {
        List<Transaction> list = new ArrayList<>();
        try {
            String req = "Select * from transaction";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                ServiceWallet sw = new ServiceWallet();
                Wallet s,r;
                s=sw.getOneById(rs.getInt("sending_wallet"));
                r=sw.getOneById(rs.getInt("rec_wallet"));
                Transaction c = new Transaction(rs.getInt(1), rs.getDate("date"), rs.getFloat(3),s,r,rs.getInt("etat"));
                list.add(c);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }
    
      @Override
    public Transaction getOneById(int id) {
        Transaction c = null;
        try {
            String req = "Select * from transaction WHERE transaction.id ='"+id+"'";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                 ServiceWallet sw = new ServiceWallet();
                Wallet s,r;
                s=sw.getOneById(rs.getInt("sending_wallet"));
                r=sw.getOneById(rs.getInt("rec_wallet"));
                c = new Transaction(rs.getInt(1), rs.getDate("date"), rs.getFloat(3),s,r,rs.getInt("etat"));
              
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return c;
    }
    
    public List<Transaction> getMyStrans(int id) {
        List<Transaction> list = new ArrayList<>();
        try {
            String req = "Select * from transaction WHERE transaction.sending_wallet='"+id+"' ";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                ServiceWallet sw = new ServiceWallet();
                Wallet s,r;
                s=sw.getOneById(id);
                r=sw.getOneById(rs.getInt("rec_wallet"));
                Transaction c = new Transaction(rs.getInt(1), rs.getDate("date"), rs.getFloat(3),s,r,rs.getInt("etat"));
                list.add(c);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }
    
     public ObservableList<Data> getData(int id) throws SQLException {
       
        String sql = "SELECT date, COUNT(*) AS count FROM transaction WHERE sending_wallet='"+id+"' GROUP BY date";
        Statement stmt = cnx.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        // Create an ObservableList of data to use in the chart
        ObservableList<Data> data = FXCollections.observableArrayList();
        while (rs.next()) {
            String date = rs.getString("date");
            int count = rs.getInt(2);
            data.add(new Data(date, count));
        }
        
        return data;
    }
    
    public List<Transaction> getMyRtrans(int id) {
        List<Transaction> list = new ArrayList<>();
        try {
            String req = "Select * from transaction WHERE transaction.rec_wallet='"+id+"' ";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                ServiceWallet sw = new ServiceWallet();
                Wallet s,r;
                s=sw.getOneById(rs.getInt("sending_wallet"));
                r=sw.getOneById(id);
                Transaction c = new Transaction(rs.getInt(1), rs.getDate("date"), rs.getFloat(3),s,r,rs.getInt("etat"));
                list.add(c);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }
    
}

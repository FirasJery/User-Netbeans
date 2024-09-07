/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Certif;
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
public class ServiceCertif implements IService<Certif> {
    
    Connection cnx = DataSource.getInstance().getCnx();
     @Override
    public void ajouter(Certif c) {
        try {
            String req = "INSERT INTO certif (nom,description,badge) VALUES ('" + c.getNom() + "', '" + c.getDesc()+ "','" + c.getBadge() + "')";
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Certif created !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    
    @Override
    public void supprimer(int id) {
        try {
            String req = "DELETE FROM certif WHERE id = " + id;
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Certif deleted !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    @Override
    public void modifier(Certif c) {
        try {
            String req = "UPDATE certif SET nom = '" + c.getNom() + "', description = '" + c.getDesc() + "', badge = '" + c.getBadge() + "'  WHERE reclamation.id = " + c.getId();
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Certif updated !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    @Override
    public List<Certif> getAll() {
        List<Certif> list = new ArrayList<>();
        try {
            String req = "Select * from certif";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                
                Certif c = new Certif(rs.getInt(1), rs.getString("nom"), rs.getString(3),rs.getString("badge"));
                list.add(c);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }
    
    @Override
    public Certif getOneById(int id) {
        Certif c = null;
        try {
            String req = "Select * from certif WHERE certif.id ='"+id+"'";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                 c = new Certif(rs.getInt(1), rs.getString("nom"), rs.getString(3),rs.getString("badge"));
              
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return c;
    }
     public Certif getOneByName(String id){
        Certif c = null;
        try {
            String req = "Select * from certif WHERE certif.nom ='"+id+"'";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                 c = new Certif(rs.getInt(1), rs.getString("nom"), rs.getString(3),rs.getString("badge"));
              
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return c;
    }
    
}

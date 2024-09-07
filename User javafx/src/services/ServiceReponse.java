/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Reclamation;
import entities.Reponse;
import entities.Utilisateur;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import utils.DataSource;

/**
 *
 * @author Firas
 */
public class ServiceReponse implements IService<Reponse> {
    
    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouter(Reponse c) {
        try {
            String req = "INSERT INTO reponse (objet,contenu,idrec) VALUES ('" + c.getObjet() + "', '"
                    + c.getContenu() + "','" + c.getIdrec().getId() + "')";
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Reponse created !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void supprimer(int id) {
        try {
            String req = "DELETE FROM reponse WHERE id = " + id;
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Reponse deleted !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(Reponse c) {
        try {
            String req = "UPDATE reponse SET objet = '" + c.getObjet() + "', contenu = '" + c.getContenu() + "', idrec = '" + c.getIdrec().getId() + "'  WHERE reponse.id = " + c.getId();
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("card updated !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Reponse> getAll() {
        List<Reponse> list = new ArrayList<>();
        try {
            String req = "Select * from reponse";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                ServiceRec sr = new ServiceRec();
                Reclamation r;

                r = sr.getOneById(rs.getInt("idrec"));
                Reponse c = new Reponse(rs.getInt(1), rs.getString("objet"), rs.getString(3), r);
                list.add(c);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }

    @Override
    public Reponse getOneById(int id) {
        Reponse c = null;
        try {
            String req = "Select * from reponse WHERE reponse.id ='" + id + "'";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                ServiceRec sr = new ServiceRec();
                Reclamation r;

                r = sr.getOneById(rs.getInt("idrec"));
                 c = new Reponse(rs.getInt(1), rs.getString("objet"), rs.getString(3), r);

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return c;
    }
    public Reponse getOneByIdRec(int id) {
        Reponse c = null;
        try {
            String req = "Select * from reponse WHERE reponse.idrec ='" + id + "'";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                ServiceRec sr = new ServiceRec();
                Reclamation r;

                r = sr.getOneById(rs.getInt("idrec"));
                 c = new Reponse(rs.getInt(1), rs.getString("objet"), rs.getString(3), r);

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return c;
    }
    
}

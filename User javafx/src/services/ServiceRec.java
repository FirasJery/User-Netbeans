/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Reclamation;
import entities.Utilisateur;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import utils.DataSource;


/**
 *
 * @author Ghass
 */
public class ServiceRec implements IService<Reclamation> {

    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouter(Reclamation c) {
        try {
            Date currentDate = new Date();
SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
String formattedDate = formatter.format(currentDate);
            String req = "INSERT INTO reclamation (description,type,etat,id_user,date) VALUES ('" + c.getDesc() + "', '"
                    + c.getType() + "','" + c.isEtat() + "','" + c.getId_user().getId() + "','"+formattedDate+"')";
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
            String req = "DELETE FROM reclamation WHERE id = " + id;
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Transaction deleted !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(Reclamation c) {
        try {
            String req = "UPDATE reclamation SET description = '" + c.getDesc() + "', type = '" + c.getType() + "', etat = '" + c.isEtat() + "',id_user = '" + c.getId_user().getId() + "'  WHERE reclamation.id = " + c.getId();
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("card updated !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Reclamation> getAll() {
        List<Reclamation> list = new ArrayList<>();
        try {
            String req = "Select * from reclamation";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                ServiceUser sw = new ServiceUser();
                Utilisateur u;

                u = sw.getOneById(rs.getInt("id_user"));
                Reclamation c = new Reclamation(rs.getInt(1), rs.getString("description"), rs.getString(3), rs.getInt("etat"), u);
                list.add(c);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }

    @Override
    public Reclamation getOneById(int id) {
        Reclamation c = null;
        try {
            String req = "Select * from reclamation WHERE reclamation.id ='" + id + "'";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                ServiceUser sw = new ServiceUser();
                Utilisateur u;

                u = sw.getOneById(rs.getInt("id_user"));
                c = new Reclamation(rs.getInt(1), rs.getString("description"), rs.getString(3), rs.getInt("etat"), u);

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return c;
    }

    public List<Reclamation> getMyRtrans(int id) {
        List<Reclamation> list = new ArrayList<>();
        try {
            String req = "Select * from reclamation WHERE reclamation.id_user='" + id + "' ";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                ServiceUser sw = new ServiceUser();
                Utilisateur u;

                u = sw.getOneById(id);
                Reclamation c = new Reclamation(rs.getInt(1), rs.getString("description"), rs.getString(3), rs.getInt("etat"), u);
                list.add(c);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }

}

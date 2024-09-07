/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Freelancer;
import entities.Postulation;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
public class ServicePostulation implements IService<Postulation> {

    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouter(Postulation p) {

        try {

            String query = "INSERT INTO postulation ( isAccepted, id_offre, id_freelancer) VALUES (?, ?, ?)";
            PreparedStatement statement = cnx.prepareStatement(query);
            statement.setInt(1, 0);
            statement.setInt(2, p.getO().getId_offre());
            statement.setInt(3, p.getF().getId());

            statement.executeUpdate();
            System.out.println("Postulation ajouté !");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void supprimer(int id) {
        try {
            String req = "DELETE FROM postulation WHERE id_postulation = " + id;
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Postulation supprimé !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(Postulation p) { // modifie automatiquement la postulation a acceptée
        try {

            String req = "UPDATE postulation SET  isAccepted = ?  WHERE id_postulation = ?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, 1);
            ps.setInt(2, p.getId_postulation());
            ps.executeUpdate();

            System.out.println("Postulation updated !");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Postulation> getAll() {
        List<Postulation> list = new ArrayList<>();
        ServiceUser su = new ServiceUser();
        ServiceOffre so = new ServiceOffre();
        try {
            String req = "Select * from postulation";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {

                Postulation p;

                p = new Postulation(rs.getInt("id_postulation"), so.getOneById(rs.getInt("id_offre")), (Freelancer)su.getOneById(rs.getInt("id_freelancer")), rs.getInt("isAccepted"));
                list.add(p);

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }
     public List<Postulation> getParOffre(int id_offre) {
        List<Postulation> list = new ArrayList<>();
        ServiceUser su = new ServiceUser();
        ServiceOffre so = new ServiceOffre();
        try {
            String req = "Select * from postulation where ( isAccepted = 0 && id_offre = '"+ id_offre+"')";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {

                Postulation p;

                p = new Postulation(rs.getInt("id_postulation"), so.getOneById(rs.getInt("id_offre")), (Freelancer)su.getOneById(rs.getInt("id_freelancer")), rs.getInt("isAccepted"));
                list.add(p);

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }

    @Override
    public Postulation getOneById(int id) {
        Postulation p = new Postulation();
        ServiceUser su = new ServiceUser();
        ServiceOffre so = new ServiceOffre();
        try {
            String req = "Select * from postulation where id_postulation = '" + id + "'";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {

                p = new Postulation(rs.getInt("id_postulation"), so.getOneById(rs.getInt("id_offre")), (Freelancer)su.getOneById(rs.getInt("id_freelancer")), rs.getInt("isAccepted"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return p;
    }
public Postulation getOneByOffre(int id_offre) {
        Postulation p = new Postulation();
        ServiceUser su = new ServiceUser();
        ServiceOffre so = new ServiceOffre();
        try {
            String req = "Select * from postulation where ( isAccepted = 1 && id_offre = '" + id_offre + "')";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {

                p = new Postulation(rs.getInt("id_postulation"), so.getOneById(rs.getInt("id_offre")), (Freelancer)su.getOneById(rs.getInt("id_freelancer")), rs.getInt("isAccepted"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return p;
    }

    /*public List<Postulation> getPostulationParFreelancer(int id_freelancer) {
        List<Postulation> list = new ArrayList<>();
        ServiceUser su = new ServiceUser();
        ServiceOffre so = new ServiceOffre();
        try {
            String req = "Select * from postulation where ( isAccepted = 1 && id_freelancer = '"+ id_freelancer+"')";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {

                Postulation p;

                p = new Postulation(rs.getInt("id_postulation"), so.getOneById(rs.getInt("id_offre")), (Freelancer)su.getOneById(rs.getInt("id_freelancer")), rs.getInt("isAccepted"));
                list.add(p);

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }*/
    
    
    /*public List<Postulation> getPostulationParFreelancerNotAccepted(int id_freelancer) {
        List<Postulation> list = new ArrayList<>();
        ServiceUser su = new ServiceUser();
        ServiceOffre so = new ServiceOffre();
        try {
            String req = "Select * from postulation where ( isAccepted = 0 && id_freelancer = '"+ id_freelancer+"')";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {

                Postulation p;

                p = new Postulation(rs.getInt("id_postulation"), so.getOneById(rs.getInt("id_offre")), (Freelancer)su.getOneById(rs.getInt("id_freelancer")), rs.getInt("isAccepted"));
                list.add(p);

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }*/
}

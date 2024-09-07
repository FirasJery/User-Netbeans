/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.sql.Connection;
import entities.Entreprise;
import entities.Offre;
import entities.Postulation;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.DataSource;

/**
 *
 * @author ASUS
 */
public class ServiceOffre implements IService<Offre> {

    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouter(Offre o) {

        try {

            String query = "INSERT INTO offre ( title, description, category,type,duree,isAccepted,isFinished,id_entreprise,date_debut,date_fin) VALUES ( ?, ?, ?, ?, ?, ?,?,?,?,?)";
            PreparedStatement statement = cnx.prepareStatement(query);

            statement.setString(1, o.getTitle());
            statement.setString(2, o.getDescription());
            statement.setString(3, o.getCategory());
            statement.setString(4, o.getType());
            statement.setInt(5, o.getDuree());
            statement.setInt(6, 0);
            statement.setInt(7, 0);
            statement.setInt(8, o.getE().getId());
            statement.setObject(9, java.sql.Date.valueOf(o.getDate_debut()));
            statement.setObject(10, java.sql.Date.valueOf(o.getDate_fin()));

            statement.executeUpdate();
            System.out.println("Offre ajouté !");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void supprimer(int id) {

        try {
            String req = "DELETE FROM offre WHERE id_offre = " + id;
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Offre supprimé !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(Offre p) {
        try {

            String req = "UPDATE offre SET  title = ? , description = ? , category = ? ,type = ? ,duree = ? , date_debut = ? ,date_fin = ?  WHERE id_offre = ?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, p.getTitle());
            ps.setString(2, p.getDescription());
            ps.setString(3, p.getCategory());
            ps.setString(4, p.getType());
            ps.setInt(5, p.getDuree());
            ps.setObject(6, java.sql.Date.valueOf(p.getDate_debut()));
            ps.setObject(7, java.sql.Date.valueOf(p.getDate_fin()));
            ps.setInt(8, p.getId_offre());
            ps.executeUpdate();

            System.out.println("Offre updated !");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Offre> getAll() {
        List<Offre> list = new ArrayList<>();
        ServiceUser su = new ServiceUser();
        try {
            String req = "Select * from offre";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {

                Offre o;

                o = new Offre(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), (Entreprise) su.getOneById(rs.getInt(9)), rs.getDate(10).toLocalDate(), rs.getDate(11).toLocalDate());
                list.add(o);

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }

    public List<Offre> getAllNotAccepted() {
        List<Offre> list = new ArrayList<>();
        ServiceUser su = new ServiceUser();
        try {
            String req = "Select * from offre where isAccepted = 0 ";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {

                Offre o;

                o = new Offre(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), (Entreprise) su.getOneById(rs.getInt(9)), rs.getDate(10).toLocalDate(), rs.getDate(11).toLocalDate());
                list.add(o);

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }

    public void accepter(Offre o) {
        try {
            LocalDate ld = LocalDate.now();
            LocalDate ldf = ld.plusMonths(o.getDuree());
            String req = "UPDATE offre SET  isAccepted = ? , date_debut = ? ,date_fin = ?  WHERE id_offre = ?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, 1);
            ps.setObject(2, java.sql.Date.valueOf(ld));
            ps.setObject(3, java.sql.Date.valueOf(ldf));
            ps.setInt(4, o.getId_offre());
            ps.executeUpdate();

            System.out.println("Offre updated !");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    public void terminer(Offre o) {
        try {
            LocalDate ld = LocalDate.now();
            int duree = o.getDate_debut().getMonthValue() - ld.getMonthValue();
            String req = "UPDATE offre SET  isFinished = ? , duree = ? ,date_fin = ?  WHERE id_offre = ?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, 1);
            ps.setInt(2, duree);
            ps.setObject(3, java.sql.Date.valueOf(ld));
            ps.setInt(4, o.getId_offre());
            ps.executeUpdate();

            System.out.println("Offre updated !");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public Offre getOneById(int id) {
        Offre o = new Offre();
        ServiceUser su = new ServiceUser();
        try {
            String req = "Select * from offre where id_offre = '" + id + "'";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {

                o = new Offre(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), (Entreprise) su.getOneById(rs.getInt(9)), rs.getDate(10).toLocalDate(), rs.getDate(11).toLocalDate());

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return o;
    }

    ///////////////////////////////////////////////////////////////////////// Entreprise
    public List<Offre> getOffresNotAcceptedParEntreprise(int id_entreprise) {
        List<Offre> list = new ArrayList<>();
        ServiceUser su = new ServiceUser();
        System.out.println(id_entreprise);
        try {
            String req = "Select * from offre where ( isAccepted = 0 && id_entreprise = '" + id_entreprise + "') ";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {

                Offre o;

                o = new Offre(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), (Entreprise) su.getOneById(rs.getInt(9)), rs.getDate(10).toLocalDate(), rs.getDate(11).toLocalDate());
                //System.out.println(o.toString());
                list.add(o);

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }
     public List<Offre> getOffresAcceptedParEntreprise(int id_entreprise) {
        List<Offre> list = new ArrayList<>();
        ServiceUser su = new ServiceUser();
        System.out.println(id_entreprise);
        try {
            String req = "Select * from offre where ( isAccepted = 1 && isFinished = 0 && id_entreprise = '" + id_entreprise + "') ";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {

                Offre o;

                o = new Offre(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), (Entreprise) su.getOneById(rs.getInt(9)), rs.getDate(10).toLocalDate(), rs.getDate(11).toLocalDate());
                //System.out.println(o.toString());
                list.add(o);

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }
     
              public List<Offre> getOffresTerminesParEntreprise(int id_entreprise) {
        List<Offre> list = new ArrayList<>();
        ServiceUser su = new ServiceUser();
        System.out.println(id_entreprise);
        try {
            String req = "Select * from offre where ( isFinished = 1 && id_entreprise = '" + id_entreprise + "') ";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {

                Offre o;

                o = new Offre(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), (Entreprise) su.getOneById(rs.getInt(9)), rs.getDate(10).toLocalDate(), rs.getDate(11).toLocalDate());
                //System.out.println(o.toString());
                list.add(o);

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }

    ///////////////////////////////////////////////////// Freelancer
    public List<Offre> getOffresPostules(int id_freelancer) {
        /*ServicePostulation sp = new ServicePostulation();
        List<Postulation> lp = sp.getPostulationParFreelancerNotAccepted(id_freelancer);
        
        
        for (Postulation p : lp) {*/
        ServiceUser su = new ServiceUser();
        List<Offre> list = new ArrayList<>();
            try {
               // String req = "Select * from offre where ( isAccepted = 0 && id_offre = '" + p.getO().getId_offre() + "') ";
              
               String req = "SELECT * " 
           + "FROM offre " 
           + "JOIN postulation ON offre.id_offre = postulation.id_offre " 
           + "WHERE offre.isAccepted = 0 AND postulation.isAccepted = 0 AND postulation.id_freelancer = '"+ id_freelancer +"'";

                Statement st = cnx.createStatement();
                ResultSet rs = st.executeQuery(req);
                while (rs.next()) {

                    Offre o;

                    o = new Offre(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), (Entreprise) su.getOneById(rs.getInt(9)), rs.getDate(10).toLocalDate(), rs.getDate(11).toLocalDate());
                    //System.out.println(o.toString());
                    list.add(o);

                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
       // }

        return list;                
    }

    public List<Offre> getOffresEncours(int id_freelancer) {
        //ServicePostulation sp = new ServicePostulation();
        //List<Postulation> lp = sp.getPostulationParFreelancer(id_freelancer);
        List<Offre> list = new ArrayList<>();
        ServiceUser su = new ServiceUser();
       // for (Postulation p : lp) {
            try {
                //String req = "Select * from offre where ( isFinished = 0 && id_offre = '" + p.getO().getId_offre() + "') ";
                
                  String req = "SELECT * " 
           + "FROM offre " 
           + "JOIN postulation ON offre.id_offre = postulation.id_offre " 
           + "WHERE offre.isFinished = 0 AND postulation.isAccepted = 1 AND postulation.id_freelancer = '"+ id_freelancer +"'";
                Statement st = cnx.createStatement();
                ResultSet rs = st.executeQuery(req);
                while (rs.next()) {

                    Offre o;

                    o = new Offre(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), (Entreprise) su.getOneById(rs.getInt(9)), rs.getDate(10).toLocalDate(), rs.getDate(11).toLocalDate());
                    // System.out.println(o.toString());
                    list.add(o);

                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        //}

        return list;
    }

    public List<Offre> getOffresTermines(int id_freelancer) {
      //  ServicePostulation sp = new ServicePostulation();
        //List<Postulation> lp = sp.getPostulationParFreelancer(id_freelancer);
        List<Offre> list = new ArrayList<>();
        ServiceUser su = new ServiceUser();
        //for (Postulation p : lp) {
            try {
              //  String req = "Select * from offre where ( isFinished = 1 && id_offre = '" + p.getO().getId_offre() + "') ";
                  String req = "SELECT * " 
           + "FROM offre " 
           + "JOIN postulation ON offre.id_offre = postulation.id_offre " 
           + "WHERE offre.isFinished = 1 AND postulation.isAccepted = 1 AND postulation.id_freelancer = '"+ id_freelancer +"'";
                Statement st = cnx.createStatement();
                ResultSet rs = st.executeQuery(req);
                while (rs.next()) {

                    Offre o;

                    o = new Offre(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), (Entreprise) su.getOneById(rs.getInt(9)), rs.getDate(10).toLocalDate(), rs.getDate(11).toLocalDate());
                    //System.out.println(o.toString());
                    list.add(o);

                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
//        }

        return list;
    }
     public Offre getOneByTitle(String id) {
        Offre o = new Offre();
        ServiceUser su = new ServiceUser();
        try {
            String req = "Select * from offre where title = '" + id + "'";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {

                o = new Offre(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), (Entreprise) su.getOneById(rs.getInt(9)), rs.getDate(10).toLocalDate(), rs.getDate(11).toLocalDate());

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return o;
    }

}

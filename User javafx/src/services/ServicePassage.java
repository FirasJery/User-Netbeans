/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Passage;
import entities.Test;
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
 * @author Ghass
 */
public class ServicePassage implements IService<Passage> {
    
    Connection cnx = DataSource.getInstance().getCnx();
     @Override
    public void ajouter(Passage c) {
        try {
            String req = "INSERT INTO passage (score,etat,idtest,iduser) VALUES ('" + c.getScore() + "', '" + c.getEtat()+ "','" + c.getIdtest().getId()+ "','" + c.getIduser().getId() + "')";
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Passage created !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    
    @Override
    public void supprimer(int id) {
        try {
            String req = "DELETE FROM passage WHERE id = " + id;
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("passage deleted !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    @Override
    public void modifier(Passage c) {
        try {
            String req = "UPDATE passage SET score = '" + c.getScore() + "', etat = '" + c.getEtat() + "', idtest = '" + c.getIdtest().getId() + "',iduser = '" + c.getIduser().getId() + "'  WHERE passage.id = " + c.getId();
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("card updated !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    @Override
    public List<Passage> getAll() {
        List<Passage> list = new ArrayList<>();
        try {
            String req = "Select * from passage";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                ServiceUser sw = new ServiceUser();
                Utilisateur u;
                Test t;
                ServiceTest stt = new ServiceTest();
                t=stt.getOneById(Integer.parseInt(rs.getString("idtest")));
                u=sw.getOneById(rs.getInt("iduser"));
                Passage c = new Passage(rs.getInt(1), rs.getInt("etat"), t,u);
                list.add(c);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }
    
    @Override
    public Passage getOneById(int id) {
        Passage c = null;
        try {
            String req = "Select * from passage WHERE passage.id ='"+id+"'";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                ServiceUser sw = new ServiceUser();
                Utilisateur u;
                Test t;
                ServiceTest stt = new ServiceTest();
                t=stt.getOneById(Integer.parseInt(rs.getString("idtest")));
                u=sw.getOneById(rs.getInt("iduser"));
                 c = new Passage(rs.getInt(1), rs.getInt("etat"), t,u);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return c;
    }
    
    public int getNoteBytest(int idtest,int iduser) throws SQLException
    {
    String req = "Select score from passage WHERE passage.idtest ='"+idtest+"' AND passage.iduser='"+iduser+"' AND passage.etat=1";
    Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            int a=-1;
            while (rs.next()) {
                 a = rs.getInt("score");
            }
            return a;
    }
}

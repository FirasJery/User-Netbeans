/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Passage;
import entities.Question;
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
public class ServiceQuestion implements IService<Question>{
    
   Connection cnx = DataSource.getInstance().getCnx();
     @Override
    public void ajouter(Question c) {
        try {
            String req = "INSERT INTO question (question,reponse,note,idtest,choix1,choix2,choix3,type) VALUES ('" + c.getQuestion() + "', '" + c.getReponse()+ "','" + c.getNote()+ "','" + c.getIdtest().getId() + "','"+c.getChoix1()+"','"+c.getChoix2()+"','"+c.getChoix3()+"','"+c.getTypr()+"'";
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Question created !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    
    @Override
    public void supprimer(int id) {
        try {
            String req = "DELETE FROM question WHERE id = " + id;
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("passage deleted !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    @Override
    public void modifier(Question c) {
        try {
            String req = "UPDATE question SET question = '" + c.getQuestion() + "', reponse = '" + c.getReponse() + "', note = '" + c.getNote() + "',idtest = '" + c.getIdtest().getId() + "',choix1 = '" + c.getChoix1() + "', choix2 = '" + c.getChoix2() + "',choix3 = '" + c.getChoix3() + "',type = '" + c.getTypr() + "' WHERE question.id = " + c.getId();
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Question updated !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    @Override
    public List<Question> getAll() {
        List<Question> list = new ArrayList<>();
        try {
            String req = "Select * from question";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
               
                Test t;
                ServiceTest stt = new ServiceTest();
                t=stt.getOneById(Integer.parseInt(rs.getString("idtest")));
                
                Question c = new Question(rs.getInt(1), rs.getString("question"),rs.getString("reponse"),rs.getInt("note"),t,rs.getString("choix1"),rs.getString("choix2"),rs.getString("choix3"),rs.getString("type"));
                list.add(c);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }
    
    public Question Getquest(int ind){
        List<Question> list= new ArrayList<>();
        list=getAll();
        
        return list.get(ind);
        
    }
    public int getetat(int ind){
     List<Question> list= new ArrayList<>();
        list=getAll();
        if(list.size()<=ind){
        return 1;
        }
        return 0;
    }
    
    
    @Override
    public Question getOneById(int id) {
        Question c = null;
        try {
            String req = "Select * from question WHERE question.id ='"+id+"'";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                  Test t;
                ServiceTest stt = new ServiceTest();
                t=stt.getOneById(Integer.parseInt(rs.getString("idtest")));
                
                 c = new Question(rs.getInt(1), rs.getString("question"),rs.getString("reponse"),rs.getInt("note"),t,rs.getString("choix1"),rs.getString("choix2"),rs.getString("choix3"),rs.getString("type"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return c;
    }
    
        public List<Question> getByTestid(int id) {
        List<Question> list = new ArrayList<>();
        try {
            String req = "Select * from question WHERE question.idtest='"+id+"'";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
               
                Test t;
                ServiceTest stt = new ServiceTest();
                t=stt.getOneById(Integer.parseInt(rs.getString("idtest")));
                
              Question c = new Question(rs.getInt(1), rs.getString("question"),rs.getString("reponse"),rs.getInt("note"),t,rs.getString("choix1"),rs.getString("choix2"),rs.getString("choix3"),rs.getString("type"));
                list.add(c);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }
    
    
     public Question Getquest(int ind,int id){
        List<Question> list= new ArrayList<>();
        list=getByTestid(id);
        System.out.println(list.get(ind));
        return list.get(ind);
        
        
    }
}

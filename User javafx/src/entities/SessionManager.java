/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;


/**
 *
 * @author Firas
 */
public class SessionManager {
    private static SessionManager instance;
    private Utilisateur currentUser;
    private Certif currentCertif;
    private HBox content;
    private Test currentTest;
    private Test t;
 
    
    // other instance variables
    
    private SessionManager() {
        // constructor
    }
    
    public static synchronized SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }
    
    public void setCurrentUser(Utilisateur user) {
        this.currentUser = user;
    }
    
    public void setCurrentCertif(Certif c){
        this.currentCertif = c;
    }
    
    public Utilisateur getCurrentUser() {
        return this.currentUser;
    }
    
    public Certif getCurrentCertif(){
        return this.currentCertif;
    }
    public void setCurrentTest(Test user) {
        this.currentTest = user;
    }
    public Test getCurrentTest(){
        return this.currentTest;
    }
    public void setCurrentHbox(HBox b){
        this.content = b;
        
    }
    
    public HBox getCurrentHbox(){
        return this.content;
    }
    
    
    
    // other methods

    public Test getT() {
        return t;
    }

    public void setT(Test t) {
        this.t = t;
    }
    
    public void showContent(Parent homeView) {
            getInstance().getCurrentHbox().getChildren().clear();
            getInstance().getCurrentHbox().getChildren().add(homeView);
    }
}

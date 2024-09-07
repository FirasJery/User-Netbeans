/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CertifGUI;

import entities.Passage;
import entities.Question;
import entities.SessionManager;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import services.ServicePassage;
import services.ServiceQuestion;

/**
 * FXML Controller class
 *
 * @author Ghass
 */
public class AffichageTestController implements Initializable {

    @FXML
    private Pane pannage;
    @FXML
    private Label numQ;
    @FXML
    private Button but;
    ServiceQuestion sq = new ServiceQuestion();
    private SessionManager sm = SessionManager.getInstance();
    private  int suiv=0;
    private  int score=0;
    @FXML
    private Button subm;
    int size = sq.getByTestid(sm.getCurrentTest().getId()).size();
    int notemax =  sq.getByTestid(sm.getCurrentTest().getId()).stream().mapToInt(e->e.getNote()).sum();

    /**
     * Initializes the controller class.
     */
     public void table(int kiki){
         
         Question offres = sq.Getquest(kiki,sm.getCurrentTest().getId());
         System.out.println(sm.getCurrentTest().getId());
        
        VBox vBox = new VBox();
        
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setSpacing(30);
        

        
        
          vBox = createOffreBox(offres);
            
            
        

        
            
        
        pannage.getChildren().clear();
        pannage.getChildren().add(vBox);
       
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        table(suiv);
        if(suiv+1==size)
        {
        but.setVisible(false);
        subm.setVisible(true);}
        
    }    

     private VBox createOffreBox(Question offre)  {
        VBox box = new VBox();
        
        numQ.setText("Num question :"+String.valueOf(suiv+1));
        box.setAlignment(Pos.CENTER);
        box.setSpacing(30);
        box.setUserData(offre.getId());
         Label quest = new Label();
       quest.setText(offre.getQuestion());
       box.getChildren().clear();
       System.out.println(offre.getTypr());
       // set the ID as the user data for the VBox
switch (offre.getTypr()){
    case"QCM":
       
      pannage.getChildren().clear();
     box.getChildren().addAll(numQ,quest);
       RadioButton ch1= new RadioButton();
       RadioButton ch2= new RadioButton();
       RadioButton ch3= new RadioButton();
        ch1.setText(offre.getChoix1());
        ch2.setText(offre.getChoix2());
        ch3.setText(offre.getChoix3());
        box.getChildren().add(ch1);
        box.getChildren().add(ch2);
        box.getChildren().add(ch3);
      ch1.setOnAction(new EventHandler<ActionEvent>() {
          
          @Override
        public void handle(ActionEvent event) {
            if (ch1.isSelected()){
            if(ch1.getText().equals(offre.getReponse())){
          score+=offre.getNote();
      }
        }}
        
      });
      ch2.setOnAction(new EventHandler<ActionEvent>() {
          
          @Override
        public void handle(ActionEvent event) {
            if (ch2.isSelected()){
            if(ch2.getText().equals(offre.getReponse())){
          score+=offre.getNote();
      }
        }
        }
      });
      
      ch3.setOnAction(new EventHandler<ActionEvent>() {
          
          @Override
        public void handle(ActionEvent event) {
            if (ch3.isSelected()){
            if(ch3.getText().equals(offre.getReponse())){
          score+=offre.getNote();
      }
        }}
        
      });
      
      
      
          
       break;
       
    case"Text" :
        pannage.getChildren().clear();
        box.getChildren().clear();
       TextField rep = new TextField();
       Button bb = new Button();
       bb.setText("Valider reponse");
       bb.setOnMouseClicked(event -> {
        
          if(rep.getText().equals(offre.getReponse()))
        {
        score+=offre.getNote();}
          bb.setVisible(false);
            
        });
        box.getChildren().addAll(numQ,quest);
        
        box.getChildren().add(rep);
         box.getChildren().add(bb);
        break;
}


        
        
        
        
         


        return box;
    }
    
    @FXML
    private void next(ActionEvent event) {
        
        suiv++;
        table(suiv);
        if(suiv+1==size)
        {
        but.setVisible(false);
        subm.setVisible(true);}
        System.out.println(score);
    }

    @FXML
    private void subm(ActionEvent event) {
        Passage p;
        ServicePassage spp = new ServicePassage();
        if(score> notemax*0.6){
        p= new Passage(score,1,sm.getCurrentTest(),sm.getCurrentUser());
        spp.ajouter(p);
    }
        else {
        p= new Passage(score,0,sm.getCurrentTest(),sm.getCurrentUser());
        spp.ajouter(p);
        }
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ReclamationGUI;

import entities.Reclamation;
import entities.Reponse;
import entities.Review;
import entities.Utilisateur;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import services.ServiceRec;
import services.ServiceReponse;
import services.ServiceReview;
import services.ServiceUser;

/**
 * FXML Controller class
 *
 * @author Firas
 */
public class ReclamationBackController implements Initializable {

    @FXML
    private ListView<String> list;
    @FXML
    private TextField id;
    @FXML
    private TextField type;
    @FXML
    private TextArea description;
    @FXML
    private TextField categorie;
    @FXML
    private TextArea description1;
    
    private ServiceRec rv = new ServiceRec();
    private ServiceUser su = new ServiceUser();
    Reclamation rec = new Reclamation();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        list.getItems().clear();
        rv.getAll().forEach(e -> list.getItems().add(String.valueOf(e.getId())));
    }    

    @FXML
    private void setFields(MouseEvent event) {
        int idr = Integer.parseInt(list.getSelectionModel().getSelectedItem());
        Reclamation r = rv.getOneById(idr);
        ServiceUser suu = new ServiceUser();
        id.setText(String.valueOf(r.getId()));
        type.setText(r.getType());
        description.setText(r.getDesc());
        categorie.setText(r.getId_user().getName());
        r.setEtat(1);
        rv.modifier(r);
        
    }

    @FXML
    private void add(ActionEvent event) {
        int idr = Integer.parseInt(list.getSelectionModel().getSelectedItem());
        Reponse c ;
        ServiceReponse spp=new ServiceReponse();
        Reclamation r = rv.getOneById(idr);
       
        c=new Reponse(type.getText(),description1.getText(),r);
        spp.ajouter(c);
        r.setEtat(2);
        rv.modifier(r);
        
    }

    @FXML
    private void del(ActionEvent event) {
        rv.supprimer(Integer.parseInt(id.getText()));
        
        clearTextFields();
        ActionEvent e = null;
        refresh(e);
    }

  
    
    public void clearTextFields(){
        description1.setText("");
        id.setText("");
        type.setText("");
        description1.setText("");
        categorie.setText("");
        
    }

    @FXML
    private void refresh(ActionEvent event) {
        list.getItems().clear();
        rv.getAll().forEach(e -> list.getItems().add(String.valueOf(e.getId())));
    }

    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OffresGUI;

import entities.Freelancer;
import entities.Offre;
import entities.Postulation;
import entities.SessionManager;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import services.ServiceOffre;
import services.ServicePostulation;
import services.ServiceUser;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class PostulationBackController implements Initializable {

    @FXML
    private ListView<String> list;
    @FXML
    private TextField id;
    @FXML
    private ComboBox<String> accept;
    @FXML
    private ComboBox<String> id_sec;
    
    ServicePostulation sp = new ServicePostulation();
    ServiceOffre so = new ServiceOffre();
    ServiceUser su = new ServiceUser();
    
    @FXML
    private ComboBox<String> id_sec1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        list.getItems().clear();
        sp.getAll().forEach(e -> list.getItems().add(String.valueOf(e.getId_postulation())));
        so.getAll().forEach(e -> id_sec.getItems().add(String.valueOf(e.getId_offre())));
        //so.getAll().forEach(e -> System.out.println(e.getId_offre()) );
        su.getAll().forEach(e -> {
            if(e.getRole().compareTo("Freelancer") == 0){
                id_sec1.getItems().add(String.valueOf(e.getId()));
            }
        });
        accept.getItems().add("True");
        accept.getItems().add("False");
        
    }    

    @FXML
    private void setFields(MouseEvent event) {
        int ido = Integer.parseInt(list.getSelectionModel().getSelectedItem());
        Postulation o = sp.getOneById(ido);
        id.setText(String.valueOf(o.getId_postulation()));
        id_sec.setValue(String.valueOf(o.getO().getId_offre()));
        id_sec1.setValue(String.valueOf(o.getF().getId()));
        if(o.getIsAccepted() != 0){
            accept.setValue("True");
        } else {
            accept.setValue("False");
        }
    }


    @FXML
    private void add(ActionEvent event) {
        int x = 0;
        if(accept.getValue().compareTo("True") == 0){
            x = 1;
        }
        Postulation p = new Postulation(so.getOneById(Integer.parseInt(id_sec.getValue())), (Freelancer) su.getOneById(Integer.parseInt(id_sec1.getValue())), x);
        sp.ajouter(p);
        
        clearTextFields();
        ActionEvent e = null;
        refresh(e);
    }
    
    public void clearTextFields(){
        id.clear();
        id_sec.setValue("");
        id_sec1.setValue("");
        accept.setValue("");
    }

    @FXML
    private void del(ActionEvent event) {
        sp.supprimer(Integer.parseInt(id.getText()));
        
        clearTextFields();
        ActionEvent e = null;
        refresh(e);
    }

    @FXML
    private void modif(ActionEvent event) {
        int x = 0;
        if(accept.getValue().compareTo("True") == 0){
            x = 1;
        }
        Postulation p = new Postulation(so.getOneById(Integer.parseInt(id_sec.getValue())), (Freelancer) su.getOneById(Integer.parseInt(id_sec1.getValue())), x);
        p.setId_postulation(Integer.parseInt(id.getText()));
        sp.modifier(p);
        
        clearTextFields();
        ActionEvent e = null;
        refresh(e);
    }

    @FXML
    private void refresh(ActionEvent event) {
        list.getItems().clear();
        sp.getAll().forEach(e -> list.getItems().add(String.valueOf(e.getId_postulation())));
    }

    @FXML
    private void go_offre(ActionEvent event) {
        try {
            Parent page1 = FXMLLoader.load(getClass().getResource("/OffresGUI/OffresBack.fxml"));
            SessionManager.getInstance().showContent(page1);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
}

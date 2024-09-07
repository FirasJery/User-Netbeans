/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WalletGUI;

import entities.Card;
import entities.SessionManager;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import services.ServiceCard;
import services.ServiceUser;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class CarteBackController implements Initializable {

    @FXML
    private ListView<Number> list;
    @FXML
    private TextField id;
    @FXML
    private ComboBox<String> id_sec;
    @FXML
    private TextField num;
    @FXML
    private TextField cvc;
    @FXML
    private TextField zip;
    @FXML
    private TextField ville;
    @FXML
    private TextField date;
    
    ServiceCard sc = new ServiceCard();
    ServiceUser su = new ServiceUser();
    @FXML
    private TextField nom;
    @FXML
    private TextField prenom;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        list.getItems().clear();
        sc.getAll().forEach(e -> list.getItems().add(e.getId()));
        su.getAll().forEach(e -> id_sec.getItems().add(String.valueOf(e.getId())));
        
    }    

    @FXML
    private void setFields(MouseEvent event) {
        int idc = list.getSelectionModel().getSelectedItem().intValue();
        System.out.println(idc);
        Card c = sc.getOneById(idc);
        id.setText(String.valueOf(c.getId()));
        id_sec.setValue(String.valueOf(c.getUser().getId()));
        nom.setText(c.getNom());
        prenom.setText(c.getPrenom());
        zip.setText(String.valueOf(c.getZipcode()));
        ville.setText(c.getVille());
        date.setText(c.getDate());
        cvc.setText(String.valueOf(c.getCvc()));
        num.setText(c.getNum());
        
    }
    
    public void clearTextFields(){
        id.clear();
        id_sec.setValue(null);
        nom.clear();
        prenom.clear();
        zip.clear();
        ville.clear();
        date.clear();
        cvc.clear();
        num.clear();
    }

    @FXML
    private void add(ActionEvent event) {
        Card c = new Card(nom.getText(), prenom.getText(), date.getText(), 
                Integer.parseInt(cvc.getText()), Integer.parseInt(zip.getText()), ville.getText(), 
                num.getText(), su.getOneById(Integer.parseInt(id_sec.getValue())));
        sc.ajouter(c);
        
        clearTextFields();
        ActionEvent e = null;
        refresh(e);
    }

    @FXML
    private void del(ActionEvent event) {
    }

    /*private void modif(ActionEvent event) {
        Card c = new Card(nom.getText(), prenom.getText(), date.getText(), 
                Integer.parseInt(cvc.getText()), Integer.parseInt(zip.getText()), ville.getText(), 
                num.getText(), su.getOneById(Integer.parseInt(id_sec.getValue())));
        c.setId(Integer.parseInt(id.getText()));
        //c.setUser(su.getOneById(Integer.parseInt(id_sec.getValue())));
        sc.modifier(c);
        
        clearTextFields();
        ActionEvent e = null;
        refresh(e);
    }*/

    @FXML
    private void refresh(ActionEvent event) {
        list.getItems().clear();
        sc.getAll().forEach(e -> list.getItems().add(e.getId()));
        clearTextFields();
    }

    @FXML
    private void go_offre(ActionEvent event) {
        try {
            Parent page1 = FXMLLoader.load(getClass().getResource("WalletBack.fxml"));

            SessionManager.getInstance().showContent(page1);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
}

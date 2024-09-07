/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OffresGUI;

import entities.Entreprise;
import entities.Offre;
import entities.SessionManager;
import java.io.IOException;
import java.net.URL;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;
import static java.util.concurrent.TimeUnit.DAYS;
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
import services.ServiceUser;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class OffresBackController implements Initializable {

    @FXML
    private ListView<String> list;
    @FXML
    private ComboBox<String> searchBy;
    @FXML
    private TextField id;
    @FXML
    private TextField titre;
    @FXML
    private TextArea description;
    @FXML
    private TextField categorie;
    @FXML
    private TextField salaire;
    @FXML
    private ComboBox<String> accept;
    @FXML
    private ComboBox<String> fini;
    @FXML
    private ComboBox<String> id_entreprise;
    @FXML
    private DatePicker date_d;
    @FXML
    private DatePicker date_fin;

    ServiceOffre so = new ServiceOffre();
    ServiceUser su = new ServiceUser();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        list.getItems().clear();
        so.getAll().forEach(e -> list.getItems().add(String.valueOf(e.getId_offre())));
        su.getAllEntreprise().forEach(e -> id_entreprise.getItems().add(String.valueOf(e.getId())));
        searchBy.getItems().add("ID");
        searchBy.getItems().add("Title");
        fini.getItems().add("True");
        fini.getItems().add("False");
        accept.getItems().add("True");
        accept.getItems().add("False");
        searchBy.setValue("ID");
    }

    @FXML
    private void filter_search(ActionEvent event) {
        if (searchBy.getValue().compareTo("ID") == 0) {
            list.getItems().clear();
            so.getAll().forEach(e -> list.getItems().add(String.valueOf(e.getId_offre())));
        } else {
            list.getItems().clear();
            so.getAll().forEach(e -> list.getItems().add(String.valueOf(e.getTitle())));
        }
    }

    public void clearTextFields() {
        id.clear();
        id_entreprise.setValue("");
        titre.clear();
        categorie.clear();
        salaire.clear();
        description.clear();
        date_d.setPromptText("");
        date_fin.setPromptText("");
        fini.setValue("");
        accept.setValue("");
    }

    @FXML
    private void add(ActionEvent event) {
        int duree = (int) date_d.getValue().until(date_fin.getValue(), ChronoUnit.DAYS);
        int x1 = 0;
        int x2 = 0;
        if (accept.getValue().compareTo("True") == 0) {
            x1 = 1;
        }
        if (fini.getValue().compareTo("True") == 0) {
            x2 = 1;
        }
        Offre o = new Offre(titre.getText(), description.getText(), categorie.getText(),
                salaire.getText(), duree, x1, x2, (Entreprise) su.getOneById(Integer.parseInt(id_entreprise.getValue())),
                date_d.getValue(), date_fin.getValue());
        so.ajouter(o);

        clearTextFields();
        ActionEvent e = null;
        refresh(e);
    }

    @FXML
    private void del(ActionEvent event) {
        if (id.getText().compareTo("") != 0) {
            so.supprimer(Integer.parseInt(id.getText()));
            
            clearTextFields();
            ActionEvent e = null;
            refresh(e);
        }
    }

    @FXML
    private void modif(ActionEvent event) {
        int duree = (int) date_d.getValue().until(date_fin.getValue(), ChronoUnit.DAYS);
        int x1 = 0;
        int x2 = 0;
        if (accept.getValue().compareTo("True") == 0) {
            x1 = 1;
        }
        if (fini.getValue().compareTo("True") == 0) {
            x2 = 1;
        }
        Offre o = new Offre(titre.getText(), description.getText(), categorie.getText(),
                salaire.getText(), duree, x1, x2, (Entreprise) su.getOneById(Integer.parseInt(id_entreprise.getValue())),
                date_d.getValue(), date_fin.getValue());
        o.setId_offre(Integer.parseInt(id.getText()));
        so.modifier(o);

        clearTextFields();
        ActionEvent e = null;
        refresh(e);
    }

    @FXML
    private void refresh(ActionEvent event) {
        clearTextFields();
        if (searchBy.getValue().compareTo("ID") == 0) {
            list.getItems().clear();
            so.getAll().forEach(e -> list.getItems().add(String.valueOf(e.getId_offre())));
        } else {
            list.getItems().clear();
            so.getAll().forEach(e -> list.getItems().add(String.valueOf(e.getTitle())));
        }
    }

    @FXML
    private void setFields(MouseEvent event) {
        Offre o = new Offre();
        if (searchBy.getValue().compareTo("ID") == 0) {
            o = so.getOneById(Integer.parseInt(list.getSelectionModel().getSelectedItem()));
        } else {
            o = so.getOneByTitle(list.getSelectionModel().getSelectedItem());
        }
        
        id.setText(String.valueOf(o.getId_offre()));
        id_entreprise.setValue(String.valueOf(o.getE().getId()));
        titre.setText(o.getTitle());
        categorie.setText(o.getCategory());
        salaire.setText(o.getType());
        description.setText(o.getDescription());
        date_d.setValue(o.getDate_debut());
        date_fin.setValue(o.getDate_fin());
        fini.setValue(String.valueOf(o.getIsFinished()));
        accept.setValue(String.valueOf(o.getIsAccepted()));
    }

    @FXML
    private void go_post(ActionEvent event) {
        try {
            Parent page1 = FXMLLoader.load(getClass().getResource("/OffresGUI/PostulationBack.fxml"));
            SessionManager.getInstance().showContent(page1);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WalletGUI;

import entities.SessionManager;
import entities.Wallet;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import services.ServiceUser;
import services.ServiceWallet;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class WalletBackController implements Initializable {

    @FXML
    private ListView<Number> list;
    @FXML
    private TextField id;
    @FXML
    private ComboBox<String> id_sec;
    @FXML
    private TextField wallet;
    @FXML
    private TextField solde;
    @FXML
    private TextField bonus;
    @FXML
    private TextField email;
    @FXML
    private TextField cle;
    ServiceWallet sw = new ServiceWallet();
    ServiceUser su = new ServiceUser();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        list.getItems().clear();
        sw.getAll().forEach(e -> list.getItems().add(e.getId()));
        su.getAll().forEach(e -> id_sec.getItems().add(String.valueOf(e.getId())));

    }

    @FXML
    private void setFields(MouseEvent event) {
        int idw = list.getSelectionModel().getSelectedItem().intValue();
        Wallet w = sw.getOneById(idw);
        id.setText(String.valueOf(w.getId()));
        wallet.setText(w.getNum_carte());
        id_sec.setValue(String.valueOf(w.getIduser().getId()));
        email.setText(w.getTel());
        cle.setText(w.getCle());
        solde.setText(String.valueOf(w.getSolde()));
        bonus.setText(String.valueOf(w.getBonus()));
    }

    @FXML
    private void add(ActionEvent event) {
        Wallet w = new Wallet(wallet.getText(), Float.parseFloat(solde.getText()), Float.parseFloat(bonus.getText()),
                email.getText(), su.getOneById(Integer.parseInt(id_sec.getValue())), cle.getText());
        sw.ajouter(w);

        clearTextFields();
        ActionEvent e = null;
        refresh(e);
    }

    public void clearTextFields() {
        id.clear();
        wallet.clear();
        id_sec.setValue("");
        email.clear();
        cle.clear();
        solde.clear();
        bonus.clear();
    }

    @FXML
    private void del(ActionEvent event) {
        int idw = list.getSelectionModel().getSelectedIndex();
        sw.supprimer(idw);

        clearTextFields();
        ActionEvent e = null;
        refresh(e);
    }

    @FXML
    private void modif(ActionEvent event) {
        Wallet w = new Wallet(wallet.getText(), Float.parseFloat(solde.getText()), Float.parseFloat(bonus.getText()),
                email.getText(), su.getOneById(Integer.parseInt(id_sec.getValue())), cle.getText());
        w.setId(Integer.parseInt(id.getText()));
        sw.modifier(w);

        clearTextFields();
        ActionEvent e = null;
        refresh(e);
    }

    @FXML
    private void refresh(ActionEvent event) {
        list.getItems().clear();
        sw.getAll().forEach(e -> list.getItems().add(e.getId()));
        clearTextFields();
    }

    @FXML
    private void go_offre(ActionEvent event) {
        try {
            Parent page1 = FXMLLoader.load(getClass().getResource("CarteBack.fxml"));

            SessionManager.getInstance().showContent(page1);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

}

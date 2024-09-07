/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import entities.SessionManager;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Firas
 */
public class BackGUIController implements Initializable {

    @FXML
    private AnchorPane anchorBack;
    @FXML
    private HBox navbar;
    @FXML
    private HBox content;
    SessionManager sm = SessionManager.getInstance();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        sm.setCurrentHbox(content);
    }

    @FXML
    private void goUtil(MouseEvent event) {

        try {
            Parent page1 = FXMLLoader.load(getClass().getResource("/UserGUI/GestionAdminUI.fxml"));

            SessionManager.getInstance().showContent(page1);
        } catch (IOException ex) {
            Logger.getLogger(BackGUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void goOffre(MouseEvent event) {
        try {
            Parent page1 = FXMLLoader.load(getClass().getResource("/OffresGUI/OffresBack.fxml"));

            SessionManager.getInstance().showContent(page1);
        } catch (IOException ex) {
            Logger.getLogger(BackGUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void goReclam(MouseEvent event) {
        try {
            Parent page1 = FXMLLoader.load(getClass().getResource("/ReclamationGUI/ReclamationBack.fxml"));

            SessionManager.getInstance().showContent(page1);
        } catch (IOException ex) {
            Logger.getLogger(BackGUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void goMessagerie(MouseEvent event) {
        try {
            Parent page1 = FXMLLoader.load(getClass().getResource("/MessagerieGUI/MessagerieBack.fxml"));
            SessionManager.getInstance().showContent(page1);
        } catch (IOException ex) {
            Logger.getLogger(BackGUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void goWallet(MouseEvent event) {
        try {
            Parent page1 = FXMLLoader.load(getClass().getResource("/WalletGUI/WalletBack.fxml"));

            SessionManager.getInstance().showContent(page1);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void goCertif(MouseEvent event) {
        try {
            Parent page1 = FXMLLoader.load(getClass().getResource("/CertifGUI/ListeCertif.fxml"));
            SessionManager.getInstance().showContent(page1);
        } catch (IOException ex) {
            Logger.getLogger(BackGUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void goout(MouseEvent event) {
        try {
            File file = new File("src/tests/IDSession.txt");

            if (file.delete()) {
                System.out.println("File deleted successfully!");
            } else {
                System.out.println("Failed to delete the file.");
            }
            SessionManager.getInstance().setCurrentUser(null);
            Parent page1 = FXMLLoader.load(getClass().getResource("../UserGUI/LoginUI.fxml"));

            Scene scene = new Scene(page1);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(scene);

            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(BackGUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ReclamationGUI;

import entities.Reclamation;
import entities.SessionManager;
import entities.Utilisateur;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import services.ServiceRec;
import services.ServiceUser;

/**
 * FXML Controller class
 *
 * @author Ghass
 */
public class AddRecController implements Initializable {

    @FXML
    private TextArea tfobj;
    @FXML
    private ComboBox<String> combotype;
    SessionManager sessionManager = SessionManager.getInstance();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // TODO
        ObservableList<String> values = FXCollections.observableArrayList("Problème technique", "Interface utilisateur", "Problèmes de paiement", "Demandes de fonctionnalités", "Service client", "Problèmes de sécurité");
        combotype.setItems(values);
        combotype.setValue("Problème technique");
    }

    public static List<String> getWords(String input) {
        String[] wordsArray = input.split("\\s+");
        return Arrays.asList(wordsArray);
    }

    @FXML
    private void add(ActionEvent event
    ) {
        LocalDate today = LocalDate.now();
        ServiceUser sa = new ServiceUser();
        ServiceRec sd = new ServiceRec();
        Reclamation p = new Reclamation();
        List<String> badwords = new ArrayList();
        List<String> listeMots = getWords(tfobj.getText());
        badwords.add("putain");
        badwords.add("batard");
        badwords.add("con");
        boolean test = false;
        for (String ch : listeMots) {
            for (String badword : badwords) {
                if (ch.equals(badword)) {
                    test = true;
                }
            }
        }
         if (tfobj.getText().isEmpty())
        {
             Alert a= new Alert(Alert.AlertType.ERROR);
           a.setContentText("remplir tout les champs ! ");
           a.show();
        }
         else if (test) {
            try {
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setContentText("Contenu inapproprié ! Votre compte est banné !");
                a.show();
                Utilisateur u = sessionManager.getCurrentUser();
                ServiceUser su = new ServiceUser();
                u.setIsBanned(1);
                su.modifierisBanned(u);
                sessionManager.setCurrentUser(null);
                File file = new File("src/tests/IDSession.txt");

                if (file.delete()) {
                    System.out.println("File deleted successfully!");
                } else {
                    System.out.println("Failed to delete the file.");
                }
                Parent page1 = FXMLLoader.load(getClass().getResource("../UserGUI/LoginUI.fxml"));

                Scene scene = new Scene(page1);

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                stage.setScene(scene);

                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(AddRecController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } 
       
        else {
            p.setDesc(tfobj.getText());
            p.setType(combotype.getValue());
            p.setEtat(0);
            p.setId_user(sessionManager.getCurrentUser());

            sd.ajouter(p);
            tfobj.clear();
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText("Votre REclamation sera traitée.");
            a.show();
        }
    }

}

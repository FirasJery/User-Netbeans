/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CertifGUI;

import entities.Certif;
import entities.SessionManager;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import services.ServiceCertif;

/**
 * FXML Controller class
 *
 * @author Ghass
 */
public class ListeCertifController implements Initializable {

    @FXML
    private ScrollPane scrollpane;
    ServiceCertif sc = new ServiceCertif();
    @FXML
    private Pane pane;
    private SessionManager sm = SessionManager.getInstance();

    /**
     * Initializes the controller class.
     */
    public void table() {
        List<Certif> offres = sc.getAll();
        VBox vBox = new VBox();

        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setSpacing(30);

        HBox hBox = new HBox();

        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(100);//

        int count = 0;
        for (Certif offre : offres) {
            VBox box = createOffreBox(offre);

            hBox.getChildren().add(box);
            count++;

            if (count == 1) {
                vBox.getChildren().add(hBox);
                hBox = new HBox();
                hBox.setAlignment(Pos.CENTER);
                hBox.setSpacing(100);
                count = 0;
            }
        }

        if (count > 0) {
            vBox.getChildren().add(hBox);
        }

        scrollpane.setContent(vBox);
        scrollpane.setFitToWidth(true);
        scrollpane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        table();
        // TODO
    }

    private VBox createOffreBox(Certif offre) {
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.setSpacing(30);
        box.setUserData(offre.getId()); // set the ID as the user data for the VBox

        Label titre = new Label("Nom :  " + offre.getNom());

        Label voir = new Label("Contenu      :" + offre.getDesc());
        Label sep = new Label("Clickez ppour ajouter un test");

        voir.setStyle("-fx-text-fill : Black;");

        voir.setFont(Font.font("Serif", FontWeight.LIGHT, 23));
        titre.setFont(Font.font("Arial", FontWeight.BOLD, 29));

        box.getChildren().addAll(titre, voir, sep);

        box.setOnMouseClicked(event -> {
            try {
                sm.setCurrentCertif(offre);
                Parent page1 = FXMLLoader.load(getClass().getResource("AddTest.fxml"));
                
                SessionManager.getInstance().showContent(page1);
            } catch (IOException ex) {
                Logger.getLogger(ListeCertifController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
        return box;
    }

    @FXML
    private void goCrud(ActionEvent event) {
        try {
                Parent page1 = FXMLLoader.load(getClass().getResource("CrudCertifBack.fxml"));
                
                SessionManager.getInstance().showContent(page1);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
    }

}

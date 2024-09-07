/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OffresGUI;

import entities.Offre;
import entities.SessionManager;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import services.ServiceOffre;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.layout.AnchorPane;
import services.ServicePostulation;
import services.ServiceUser;

/**
 * FXML Controller class
 *
 * @author Firas
 */
public class AffichageOffreEntrpriseController implements Initializable {

    @FXML
    private Pane Pane;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private Button btnMesOffres;
    @FXML
    private Button btnEnCours;
    @FXML
    private Button bntTermines;
    public static int id_offreposte;
    @FXML
    private Button bntNoter;
    @FXML
    private Label labelTitlePage;
    @FXML
    private AnchorPane anchor_pane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        afficherOffres();
    }

    public void afficherOffres() {
        ServiceOffre serviceOffre = new ServiceOffre();
        List<Offre> offres = serviceOffre.getOffresNotAcceptedParEntreprise(SessionManager.getInstance().getCurrentUser().getId());
        //List<Offre> l2 = serviceOffre.getOffresPostules(SessionManager.getInstance().getCurrentUser().getId());

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.TOP_LEFT);
        vBox.setSpacing(20);
        vBox.setMinHeight(620);

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        Insets i = new Insets(0, 0, 0, 110);
        hBox.setPadding(i);
        hBox.setSpacing(40);//
        //hBox.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: black; -fx-border-width: 2px;");

        int count = 0;
        for (Offre offre : offres) {
            VBox box = createOffreBox(offre);
            box.setPrefWidth(300);
            box.setPrefHeight(300);
            hBox.getChildren().add(box);
            count++;

            if (count == 3) {
                vBox.getChildren().add(hBox);
                hBox = new HBox();
                hBox.setAlignment(Pos.CENTER_LEFT); // 
                hBox.setPadding(i);
                hBox.setSpacing(40); // 
                // hBox.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: black; -fx-border-width: 2px;");
                count = 0;
            }
        }

        if (count > 0) {
            vBox.getChildren().add(hBox);
        }
        vBox.setStyle("-fx-background-image: url('resources/offrebg.png'); ");
        labelTitlePage.setText("Vos offres".toUpperCase());

        scrollPane.setContent(vBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    }

    private VBox createOffreBox(Offre offre) {
        Glow glow = new Glow();
        DropShadow shadow = new DropShadow();
        ServiceOffre so = new ServiceOffre();
        VBox box = new VBox();
        box.setStyle("-fx-background-color: #BB0000; -fx-background-radius: 20;");

        box.setMaxSize(500, 500);
        box.setSpacing(50);
        box.setAlignment(Pos.CENTER);

        Insets I = new Insets(20, 0, 20, 0);
        box.setPadding(I);
        box.setOpacity(0.7);
        box.setEffect(shadow);

        box.setUserData(offre.getId_offre()); // set the ID as the user data for the VBox

        Label title = new Label(offre.getTitle().toUpperCase());
        Label category = new Label("Categorie : " + offre.getCategory());
        Label duree = new Label("Durée : " + Integer.toString(offre.getDuree()) + " mois");

        title.setStyle("-fx-text-fill: #acaaad;");
        title.setWrapText(true);

        title.setAlignment(Pos.CENTER);
        category.setTextFill(Color.WHITE);
        category.setFont(Font.font("Arial", FontWeight.BOLD, 19));
        duree.setFont(Font.font("Arial", FontWeight.BOLD, 19));
        duree.setTextFill(Color.WHITE);
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        box.getChildren().addAll(title, category, duree);

        box.setOnMouseClicked(event -> {    // edit scene or switch 
            id_offreposte = offre.getId_offre();
            Pane.getChildren().clear();
            FXMLLoader loadOffre = new FXMLLoader(getClass().getResource("PostulationPage.fxml"));
            try {
                Pane.getChildren().add(loadOffre.load());
            } catch (IOException ex) {
                ex.getMessage();
            }
        });

        box.setOnMouseEntered(event -> {
            box.setStyle("-fx-background-color: #acaaad; -fx-background-radius: 40;");
            box.setOpacity(0.78);
            title.setStyle("-fx-text-fill: #BB0000;");
            Color c = new Color(0, 0, 0, 1);
            category.setTextFill(c);
            duree.setTextFill(c);
            box.setEffect(glow);
        });

        box.setOnMouseExited(event -> {
            box.setStyle("-fx-background-color: #BB0000; -fx-background-radius: 20;");
            box.setOpacity(0.7);
            title.setStyle("-fx-text-fill: #acaaad;");
            category.setTextFill(Color.WHITE);
            duree.setTextFill(Color.WHITE);
            box.setEffect(shadow);

        });

        return box;
    }

    public void afficherOffresEncours() {
        ServiceOffre serviceOffre = new ServiceOffre();
        List<Offre> offres = serviceOffre.getOffresAcceptedParEntreprise(SessionManager.getInstance().getCurrentUser().getId());
        //List<Offre> l2 = serviceOffre.getOffresPostules(SessionManager.getInstance().getCurrentUser().getId());

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.TOP_LEFT);
        vBox.setSpacing(20);
        vBox.setMinHeight(620);

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        Insets i = new Insets(0, 0, 0, 110);
        hBox.setPadding(i);
        hBox.setSpacing(40);//

        int count = 0;
        for (Offre offre : offres) {
            VBox box = createOffreBoxEncours(offre);
            box.setPrefWidth(300);
            box.setPrefHeight(300);
            hBox.getChildren().add(box);
            count++;

            if (count == 3) {
                vBox.getChildren().add(hBox);
                hBox = new HBox();
                hBox.setAlignment(Pos.CENTER_LEFT); // 
                hBox.setPadding(i);
                hBox.setSpacing(40); // 

                // hBox.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: black; -fx-border-width: 2px;");
                count = 0;
            }
        }

        if (count > 0) {
            vBox.getChildren().add(hBox);
        }
        vBox.setStyle("-fx-background-image: url('resources/offrebg.png'); ");

        labelTitlePage.setText("Vos offres en cours".toUpperCase());

        scrollPane.setContent(vBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

    }

    private VBox createOffreBoxEncours(Offre offre) {
        Glow glow = new Glow();
        DropShadow shadow = new DropShadow();
        ServiceOffre so = new ServiceOffre();
        VBox box = new VBox();
        box.setStyle("-fx-background-color: #BB0000; -fx-background-radius: 20px;");
        box.setMaxSize(500, 500);
        box.setAlignment(Pos.CENTER);
        box.setSpacing(20);
        Insets I = new Insets(20, 0, 20, 0);
        box.setPadding(I);

        box.setOpacity(0.7);
        box.setEffect(shadow);
        box.setUserData(offre.getId_offre()); // set the ID as the user data for the VBox

        Label title = new Label("Titre : " + offre.getTitle().toUpperCase());
        Label category = new Label("Categorie : " + offre.getCategory());
        Label duree = new Label("Durée : " + Integer.toString(offre.getDuree()) + " mois");
        Label date_debut = new Label("Date début : " + offre.getDate_debut().getDayOfMonth() + " "
                + offre.getDate_debut().getMonth() + " " + offre.getDate_debut().getYear());

        title.setStyle("-fx-text-fill: #acaaad;");
        category.setTextFill(Color.WHITE);
        category.setFont(Font.font("Arial", FontWeight.BOLD, 19));
        duree.setFont(Font.font("Arial", FontWeight.BOLD, 19));
        duree.setTextFill(Color.WHITE);
        date_debut.setFont(Font.font("Arial", FontWeight.BOLD, 19));
        date_debut.setTextFill(Color.WHITE);
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        box.getChildren().addAll(title, category, duree, date_debut);

        box.setOnMouseClicked(event -> {

            Alert A = new Alert(Alert.AlertType.CONFIRMATION);
            A.setContentText("Voulez vous marquez cet offre comme terminé ?");
            Optional<ButtonType> result = A.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                so.terminer(offre);
                System.out.println("offre termine ! ");
                ServicePostulation sp = new ServicePostulation();
                ServiceUser su = new ServiceUser();
                su.modifierTJ(sp.getOneByOffre(id_offreposte).getF());
                
            }

            // edit scene or switch 
        });

        box.setOnMouseEntered(event -> {
            box.setStyle("-fx-background-color: #acaaad; -fx-background-radius: 40;");
            box.setOpacity(0.78);
            title.setStyle("-fx-text-fill: #BB0000;");
            Color c = new Color(0, 0, 0, 1);
            category.setTextFill(c);
            duree.setTextFill(c);
            date_debut.setTextFill(c);
            box.setEffect(glow);

        });

        box.setOnMouseExited(event -> {
            box.setStyle("-fx-background-color: #BB0000; -fx-background-radius: 20;");
            box.setOpacity(0.7);
            title.setStyle("-fx-text-fill: #acaaad;");
            category.setTextFill(Color.WHITE);
            duree.setTextFill(Color.WHITE);

            date_debut.setTextFill(Color.WHITE);
            box.setEffect(shadow);
        });

        return box;
    }

    public void afficherOffresTermines() {
        ServiceOffre serviceOffre = new ServiceOffre();
        List<Offre> offres = serviceOffre.getOffresTerminesParEntreprise(SessionManager.getInstance().getCurrentUser().getId());
        //List<Offre> l2 = serviceOffre.getOffresPostules(SessionManager.getInstance().getCurrentUser().getId());

        VBox vBox = new VBox();
        /*vBox.setAlignment(Pos.CENTER_LEFT);
        vBox.setSpacing(20);*/
        vBox.setAlignment(Pos.TOP_LEFT);
        vBox.setSpacing(20);
        vBox.setMinHeight(620);

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        Insets i = new Insets(0, 0, 0, 110);
        hBox.setPadding(i);
        hBox.setSpacing(40);//
        //hBox.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: black; -fx-border-width: 2px;");

        int count = 0;
        for (Offre offre : offres) {
            VBox box = createOffreBoxTermines(offre);
            box.setPrefWidth(300);
            box.setPrefHeight(300);
            hBox.getChildren().add(box);
            count++;

            if (count == 3) {
                vBox.getChildren().add(hBox);
                hBox = new HBox();
                hBox.setAlignment(Pos.CENTER_LEFT); // 
                hBox.setPadding(i);
                hBox.setSpacing(40); // 
                count = 0;
            }
        }

        if (count > 0) {
            vBox.getChildren().add(hBox);
        }
        vBox.setStyle("-fx-background-image: url('resources/offrebg.png'); ");
        labelTitlePage.setText("Vos offres terminés".toUpperCase());

        scrollPane.setContent(vBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    }

    private VBox createOffreBoxTermines(Offre offre) {
        Glow glow = new Glow();
        DropShadow shadow = new DropShadow();
        ServiceOffre so = new ServiceOffre();
        VBox box = new VBox();
        box.setStyle("-fx-background-color: #BB0000; -fx-background-radius: 20;");

        box.setMaxSize(500, 500);

        box.setAlignment(Pos.CENTER);
        box.setSpacing(20);
        Insets I = new Insets(20, 0, 20, 0);
        box.setPadding(I);
        box.setOpacity(0.7);
        box.setEffect(shadow);
        box.setUserData(offre.getId_offre()); // set the ID as the user data for the VBox

        Label title = new Label("Titre : " + offre.getTitle().toUpperCase());
        Label category = new Label("Categorie : " + offre.getCategory());
        Label duree = new Label("Durée : " + Integer.toString(offre.getDuree()) + " mois");
        Label date_debut = new Label("Date fin : " + offre.getDate_fin().getDayOfMonth() + " "
                + offre.getDate_fin().getMonth() + " " + offre.getDate_fin().getYear());

        title.setStyle("-fx-text-fill: #acaaad;");
        category.setTextFill(Color.WHITE);
        category.setFont(Font.font("Arial", FontWeight.BOLD, 19));
        duree.setFont(Font.font("Arial", FontWeight.BOLD, 19));
        duree.setTextFill(Color.WHITE);
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        date_debut.setFont(Font.font("Arial", FontWeight.BOLD, 19));
        date_debut.setTextFill(Color.WHITE);

        box.getChildren().addAll(title, category, duree, date_debut);

        box.setOnMouseClicked(event -> {

            // edit scene or switch 
        });

        box.setOnMouseEntered(event -> {
            box.setStyle("-fx-background-color: #acaaad; -fx-background-radius: 40;");
            box.setOpacity(0.78);
            title.setStyle("-fx-text-fill: #BB0000;");
            Color c = new Color(0, 0, 0, 1);
            category.setTextFill(c);
            duree.setTextFill(c);
            date_debut.setTextFill(c);
            box.setEffect(glow);

        });

        box.setOnMouseExited(event -> {
            box.setStyle("-fx-background-color: #BB0000; -fx-background-radius: 20;");
            box.setOpacity(0.7);
            title.setStyle("-fx-text-fill: #acaaad;");
            category.setTextFill(Color.WHITE);
            duree.setTextFill(Color.WHITE);
            date_debut.setTextFill(Color.WHITE);
            box.setEffect(shadow);
        });

        return box;
    }

    @FXML
    private void btnAjouterOffreAction(ActionEvent event) {

        Pane.getChildren().clear();
        FXMLLoader load = new FXMLLoader(getClass().getResource("AjoutOffre.fxml"));

        try {

            Pane.getChildren().add(load.load());
        } catch (IOException ex) {
            ex.getMessage();
        }
    }

    @FXML
    private void BtnMesOffresAction(ActionEvent event) {
        afficherOffres();
    }

    @FXML
    private void btnEnCoursAction(ActionEvent event) {
        afficherOffresEncours();
    }

    @FXML
    private void bntTerminesAction(ActionEvent event) {
        afficherOffresTermines();
    }

    @FXML
    private void bntNoterAction(ActionEvent event) {
        Pane.getChildren().clear();
        FXMLLoader loadOffre = new FXMLLoader(getClass().getResource("/ReclamationGUI/PageNoterFreelancers.fxml"));

        try {

            Pane.getChildren().add(loadOffre.load());
        } catch (IOException ex) {
            ex.getMessage();
        }
    }

}

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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import services.ServiceOffre;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class AffichageOffreController implements Initializable {

    private ServiceOffre so = new ServiceOffre();

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private Button btnOffre;
    @FXML
    private Pane Pane;
    public static int id_offre;
    @FXML
    private TextField tfRecherche;
    private List<Offre> offres1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ServiceOffre serviceOffre = new ServiceOffre();
        offres1 = serviceOffre.getAllNotAccepted();
        List<Offre> l2 = serviceOffre.getOffresPostules(SessionManager.getInstance().getCurrentUser().getId());

        Set<Offre> set2 = new HashSet<>(l2);
        Iterator<Offre> iter1 = offres1.iterator();
        while (iter1.hasNext()) {
            Offre offer = iter1.next();
            if (set2.contains(offer)) {
                iter1.remove();
            }
        }
        afficherOffres(offres1);
        tfRecherche.textProperty().addListener((observable, oldValue, newValue) -> {
            String Regex = " "; 
         List<Offre> offres = offres1.stream().filter(o -> o.getTitle().matches(".*"+newValue+".*")).collect(Collectors.toList());
            afficherOffres(offres);
        });
        
        
        
    }

    @FXML
    private void btnOffreAction(ActionEvent event) {
        afficherOffres(offres1);
    }

    @FXML
    private void btnOffreEncoursAction(ActionEvent event) {
        afficherOffresEncours(SessionManager.getInstance().getCurrentUser().getId());
    }

    @FXML
    private void btnOffreTermineAction(ActionEvent event) {
        afficherOffresTermines(SessionManager.getInstance().getCurrentUser().getId());
    }
  
    public void afficherOffres(List<Offre> offres) {
       

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(100);//
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
                hBox.setAlignment(Pos.CENTER);
                hBox.setSpacing(100); // 
                // hBox.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: black; -fx-border-width: 2px;");
                count = 0;
            }
        }

        if (count > 0) {
            vBox.getChildren().add(hBox);
        }

        scrollPane.setContent(vBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    }

    private VBox createOffreBox(Offre offre) {
        
        ServiceOffre so = new ServiceOffre();
        VBox box = new VBox();
        box.setStyle("-fx-background-color: #BB0000; -fx-background-radius: 20;");

        box.setMaxSize(500, 500);
        box.setSpacing(50);
        box.setAlignment(Pos.CENTER);

        Insets I = new Insets(20, 0, 20, 0);
        box.setPadding(I);
        box.setOpacity(0.7);
        Glow glow = new Glow();
        DropShadow shadow = new DropShadow();
        box.setEffect(shadow);
        box.setUserData(offre.getId_offre()); // set the ID as the user data for the VBox

        Label title = new Label(offre.getTitle());
      
        Label category = new Label(offre.getCategory());
       
        Label duree = new Label(Integer.toString(offre.getDuree()) + " mois");

        title.setStyle("-fx-text-fill: #acaaad;");
        title.setWrapText(true);

        title.setAlignment(Pos.CENTER);
        category.setTextFill(Color.WHITE);
        category.setFont(Font.font("Arial", FontWeight.BOLD, 19));
        duree.setFont(Font.font("Arial", FontWeight.BOLD, 19));
        duree.setTextFill(Color.WHITE);
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
     
        box.getChildren().addAll(title, category, duree);

        box.setOnMouseClicked(event -> { // Mouse click
            id_offre = offre.getId_offre();
            Pane.getChildren().clear();
            FXMLLoader loadOffre = new FXMLLoader(getClass().getResource("/OffresGUI/OffrePage.fxml"));

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

    public void afficherOffresEncours(int id_freelancer) {
        ServiceOffre serviceOffre = new ServiceOffre();
        List<Offre> offres = serviceOffre.getOffresEncours(id_freelancer);

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(100);//
        //hBox.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: black; -fx-border-width: 2px;");

        int count = 0;
        for (Offre offre : offres) {
            VBox box = createOffreEncoursBox(offre);
            box.setPrefWidth(300);
            box.setPrefHeight(300);
            hBox.getChildren().add(box);
            count++;

            if (count == 3) {
                vBox.getChildren().add(hBox);
                hBox = new HBox();
                hBox.setAlignment(Pos.CENTER);
                hBox.setSpacing(100); // 
                // hBox.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: black; -fx-border-width: 2px;");
                count = 0;
            }
        }

        if (count > 0) {
            vBox.getChildren().add(hBox);
        }

        scrollPane.setContent(vBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    }

    private VBox createOffreEncoursBox(Offre offre) {
        ServiceOffre so = new ServiceOffre();
        VBox box = new VBox();
        box.setStyle("-fx-background-color: #BB0000; -fx-background-radius: 20;");

        box.setMaxSize(500, 500);
        box.setSpacing(50);
        box.setAlignment(Pos.CENTER);

        Insets I = new Insets(20, 0, 20, 0);
        box.setPadding(I);
        box.setOpacity(0.7);
        Glow glow = new Glow();
        DropShadow shadow = new DropShadow();
        box.setEffect(shadow);
        box.setUserData(offre.getId_offre()); // set the ID as the user data for the VBox

        Label title = new Label(offre.getTitle());
        Label description = new Label(offre.getDescription());
        Label category = new Label(offre.getCategory());
        Label type = new Label(offre.getType());
        Label duree = new Label(Integer.toString(offre.getDuree()) + " mois");

        title.setStyle("-fx-text-fill: #acaaad;");
        title.setWrapText(true);

        title.setAlignment(Pos.CENTER);
        category.setTextFill(Color.WHITE);
        category.setFont(Font.font("Arial", FontWeight.BOLD, 19));
        duree.setFont(Font.font("Arial", FontWeight.BOLD, 19));
        duree.setTextFill(Color.WHITE);
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        box.getChildren().addAll(title, description, category, type, duree);

        box.setOnMouseClicked(event -> {

            // edit scene or switch 
        });

        box.setOnMouseEntered(event -> {
            box.setStyle("-fx-background-color: #FFA07A;");
        });

        box.setOnMouseExited(event -> {
            box.setStyle("-fx-background-color: #8B0000;");
        });

        return box;
    }

    public void afficherOffresTermines(int id_freelancer) {
        ServiceOffre serviceOffre = new ServiceOffre();
        List<Offre> offres = serviceOffre.getOffresTermines(id_freelancer);

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(100);//
        //hBox.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: black; -fx-border-width: 2px;");

        int count = 0;
        for (Offre offre : offres) {
            VBox box = createOffreTermineBox(offre);
            box.setPrefWidth(300);
            box.setPrefHeight(300);
            hBox.getChildren().add(box);
            count++;

            if (count == 3) {
                vBox.getChildren().add(hBox);
                hBox = new HBox();
                hBox.setAlignment(Pos.CENTER);
                hBox.setSpacing(100); // 
                // hBox.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: black; -fx-border-width: 2px;");
                count = 0;
            }
        }

        if (count > 0) {
            vBox.getChildren().add(hBox);
        }

        scrollPane.setContent(vBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    }

    private VBox createOffreTermineBox(Offre offre) {
        ServiceOffre so = new ServiceOffre();
        VBox box = new VBox();
         box.setStyle("-fx-background-color: #BB0000; -fx-background-radius: 20;");

        box.setMaxSize(500, 500);
        box.setSpacing(50);
        box.setAlignment(Pos.CENTER);

        Insets I = new Insets(20, 0, 20, 0);
        box.setPadding(I);
        box.setOpacity(0.7);
        Glow glow = new Glow();
        DropShadow shadow = new DropShadow();
        box.setEffect(shadow);
        box.setUserData(offre.getId_offre()); // set the ID as the user data for the VBox

        Label title = new Label(offre.getTitle());
        Label description = new Label(offre.getDescription());
        Label category = new Label(offre.getCategory());
        Label type = new Label(offre.getType());
        Label duree = new Label(Integer.toString(offre.getDuree()) + " mois");

        title.setStyle("-fx-text-fill: #acaaad;");
        title.setWrapText(true);

        title.setAlignment(Pos.CENTER);
        category.setTextFill(Color.WHITE);
        category.setFont(Font.font("Arial", FontWeight.BOLD, 19));
        duree.setFont(Font.font("Arial", FontWeight.BOLD, 19));
        duree.setTextFill(Color.WHITE);
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        box.getChildren().addAll(title, description, category, type, duree);

        box.setOnMouseClicked(event -> {

            // edit scene or switch 
        });

        box.setOnMouseEntered(event -> {
            box.setStyle("-fx-background-color: #FFA07A;");
        });

        box.setOnMouseExited(event -> {
            box.setStyle("-fx-background-color: #8B0000;");
        });

        return box;
    }

    @FXML
    private void btnOffrePostulesAction(ActionEvent event) {
        afficherOffresPostules(SessionManager.getInstance().getCurrentUser().getId());
    }

    public void afficherOffresPostules(int id_freelancer) {
        ServiceOffre serviceOffre = new ServiceOffre();
        List<Offre> offres = serviceOffre.getOffresPostules(id_freelancer);

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(100);//
        //hBox.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: black; -fx-border-width: 2px;");

        int count = 0;
        for (Offre offre : offres) {
            VBox box = createOffrePostulesBox(offre);
            box.setPrefWidth(300);
            box.setPrefHeight(300);
            hBox.getChildren().add(box);
            count++;

            if (count == 3) {
                vBox.getChildren().add(hBox);
                hBox = new HBox();
                hBox.setAlignment(Pos.CENTER);
                hBox.setSpacing(100); // 
                // hBox.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: black; -fx-border-width: 2px;");
                count = 0;
            }
        }

        if (count > 0) {
            vBox.getChildren().add(hBox);
        }

        scrollPane.setContent(vBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    }

    private VBox createOffrePostulesBox(Offre offre) {
        ServiceOffre so = new ServiceOffre();
        VBox box = new VBox();
        box.setStyle("-fx-background-color: #BB0000; -fx-background-radius: 20;");

        box.setMaxSize(500, 500);
        box.setSpacing(50);
        box.setAlignment(Pos.CENTER);

        Insets I = new Insets(20, 0, 20, 0);
        box.setPadding(I);
        box.setOpacity(0.7);
        Glow glow = new Glow();
        DropShadow shadow = new DropShadow();
        box.setEffect(shadow);
        box.setUserData(offre.getId_offre()); // set the ID as the user data for the VBox

        Label title = new Label(offre.getTitle());
        Label description = new Label(offre.getDescription());
        Label category = new Label(offre.getCategory());
        Label type = new Label(offre.getType());
        Label duree = new Label(Integer.toString(offre.getDuree()) + " mois");

         title.setStyle("-fx-text-fill: #acaaad;");
        title.setWrapText(true);

        title.setAlignment(Pos.CENTER);
        category.setTextFill(Color.WHITE);
        category.setFont(Font.font("Arial", FontWeight.BOLD, 19));
        duree.setFont(Font.font("Arial", FontWeight.BOLD, 19));
        duree.setTextFill(Color.WHITE);
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        box.getChildren().addAll(title, description, category, type, duree);

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

    @FXML
    private void stat(ActionEvent event) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader
                    .load(getClass().getResource("/OffresGUI/statistic.fxml"));

            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.setTitle("Offre Managment");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }

}

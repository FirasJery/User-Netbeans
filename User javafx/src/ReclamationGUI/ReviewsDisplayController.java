/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ReclamationGUI;

import entities.Review;
import entities.SessionManager;
import entities.Transaction;
import entities.Utilisateur;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import services.ServiceReview;

/**
 * FXML Controller class
 *
 * @author Firas
 */
public class ReviewsDisplayController implements Initializable {

    @FXML
    private Pane PaneEntreprise;
    @FXML
    private ScrollPane ScrollPaneEntrep;
    @FXML
    private Pane PaneFreelancer;
    @FXML
    private ScrollPane ScrollPaneFree;
    private SessionManager s = SessionManager.getInstance();
    ServiceReview sr = new ServiceReview();
    Utilisateur u = s.getCurrentUser();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        if (u.getRole().equals("Entreprise")) {
            PaneEntreprise.setVisible(true);
            PaneFreelancer.setVisible(false);
            AfficherVosRevues();
        } else if (u.getRole().equals("Freelancer")) {
            PaneEntreprise.setVisible(false);
            PaneFreelancer.setVisible(true);
            AfficherRevuesFreelancer();
        }

        // TODO
    }

    @FXML
    private void VosRevuesAction(ActionEvent event) {
        AfficherVosRevues();

    }

    @FXML
    private void RevuesAction(ActionEvent event) {
        AfficherRevues();
    }

    public void AfficherVosRevues() {
        int ident = u.getId();
        List<Review> offres = sr.getAll().stream()
                .filter(r -> r.getId_editeur().getId() == ident)
                .collect(Collectors.toList());
        VBox vBox = new VBox();

        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setSpacing(30);
        vBox.setPrefWidth(1200);


        HBox hBox = new HBox();

        hBox.setAlignment(Pos.CENTER);
                

        hBox.setSpacing(100);//

        int count = 0;
        for (Review offre : offres) {
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

        ScrollPaneEntrep.setContent(vBox);
        ScrollPaneEntrep.setFitToWidth(true);
        ScrollPaneEntrep.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    }
     public void AfficherRevues() {
        int ident = u.getId();
        List<Review> offres = sr.getAll();
        VBox vBox = new VBox();

        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setSpacing(30);
        vBox.setPrefWidth(1200);


        HBox hBox = new HBox();

        hBox.setAlignment(Pos.CENTER);
                

        hBox.setSpacing(100);//

        int count = 0;
        for (Review offre : offres) {
            VBox box = createRevuesBox(offre);

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

        ScrollPaneEntrep.setContent(vBox);
        ScrollPaneEntrep.setFitToWidth(true);
        ScrollPaneEntrep.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    }

    private VBox createOffreBox(Review offre) {
        VBox box = new VBox();

        box.setAlignment(Pos.CENTER);
        box.setSpacing(30);
        box.setUserData(offre.getId()); // set the ID as the user data for the VBox

        Label Date = new Label("Message :  " + offre.getMessage());
        Label montant = new Label("Note :" + String.valueOf(offre.getNote()) + "/10");
        Label rec = new Label("Noté par :" + offre.getId_editeur().getName());
        Label sep = new Label("____________________________________________________________________________________________________________________");

        Button bb = new Button();
        bb.setText("Supprimer");

        rec.setStyle("-fx-text-fill : Blue;");
        montant.setStyle("-fx-text-fill : WHITE;");
        Date.setStyle("-fx-text-fill : WHITE;");

        rec.setFont(Font.font("Serif", FontWeight.LIGHT, 23));
        montant.setFont(Font.font("Serif", FontWeight.LIGHT, 23));
        Date.setFont(Font.font("Arial", FontWeight.BOLD, 29));

        Date.setWrapText(true);
        box.getChildren().addAll(Date, montant, rec, bb, sep);
        URL imageUrl = getClass().getResource("/resources/trans.jpg");
        box.setStyle("-fx-background-image: url('" + imageUrl + "');");
        box.setPrefWidth(1200);
        bb.setOnMouseClicked(event -> {

            sr.supprimer(offre.getId());
            AfficherVosRevues();

        });
        return box;
    }
    private VBox createRevuesBox(Review offre) {
        VBox box = new VBox();

        box.setAlignment(Pos.CENTER);
        box.setSpacing(30);
        box.setUserData(offre.getId()); // set the ID as the user data for the VBox

        Label Date = new Label("Message :  " + offre.getMessage());
        Label montant = new Label("Note :" + String.valueOf(offre.getNote()) + "/10");
        Label rec = new Label("Noté par :" + offre.getId_editeur().getName());
        Label sep = new Label("____________________________________________________________________________________________________________________");

    

        rec.setStyle("-fx-text-fill : Blue;");
        montant.setStyle("-fx-text-fill : WHITE;");
        Date.setStyle("-fx-text-fill : WHITE;");

        rec.setFont(Font.font("Serif", FontWeight.LIGHT, 23));
        montant.setFont(Font.font("Serif", FontWeight.LIGHT, 23));
        Date.setFont(Font.font("Arial", FontWeight.BOLD, 29));

        Date.setWrapText(true);
        box.getChildren().addAll(Date, montant, rec, sep);
        URL imageUrl = getClass().getResource("/resources/trans.jpg");
        box.setStyle("-fx-background-image: url('" + imageUrl + "');");
        box.setPrefWidth(1200);
      
        return box;
    }
    
     public void AfficherRevuesFreelancer() {
        int ident = u.getId();
        List<Review> offres = sr.getAll();
        VBox vBox = new VBox();

        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setSpacing(30);
        vBox.setPrefWidth(1200);


        HBox hBox = new HBox();

        hBox.setAlignment(Pos.CENTER);
                

        hBox.setSpacing(100);//

        int count = 0;
        for (Review offre : offres) {
            VBox box = createRevuesBox(offre);

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

        ScrollPaneFree.setContent(vBox);
        ScrollPaneFree.setFitToWidth(true);
        ScrollPaneFree.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    }


}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OffresGUI;

import entities.Freelancer;
import entities.Postulation;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import services.ServiceOffre;
import services.ServicePostulation;
import services.ServiceUser;
import java.io.File;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.shape.Circle;

/**
 * FXML Controller class
 *
 * @author Firas
 */
public class PostulationPageController implements Initializable {

    @FXML
    private Pane Pane;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private Label labelTitlePos;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        afficherPostes();
    }

    public void afficherPostes() {

        ServicePostulation sp = new ServicePostulation();
        List<Postulation> lp = sp.getParOffre(AffichageOffreEntrpriseController.id_offreposte);

        VBox vBox = new VBox();

        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(100);//
        //hBox.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: black; -fx-border-width: 2px;");

        int count = 0;
        for (Postulation p : lp) {
            VBox box = createPostulationBox(p);
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
        //  vBox.setStyle("-fx-background-color: #BB0000; ");
        labelTitlePos.setText(labelTitlePos.getText().toUpperCase());
        scrollPane.setContent(vBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    }

    private VBox createPostulationBox(Postulation poste) {
        ServiceOffre so = new ServiceOffre();
        ServicePostulation sp = new ServicePostulation();
        VBox box = new VBox();
        box.setStyle("-fx-background-color: #BB0000; -fx-background-radius: 20;");
        box.setMaxSize(500, 500);

        box.setAlignment(Pos.CENTER);
        box.setSpacing(20);
        Insets I = new Insets(20, 0, 20, 0);
        box.setPadding(I);
        box.setUserData(poste.getId_postulation()); // set the ID as the user data for the VBox

        Hyperlink title = new Hyperlink(poste.getF().getName().toUpperCase() + " " + poste.getF().getLastName().toUpperCase());
        Label category = new Label(poste.getO().getTitle());

        title.setStyle("-fx-text-fill: #acaaad;");
        category.setTextFill(Color.WHITE);
        category.setFont(Font.font("Arial", FontWeight.BOLD, 19));
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        box.getChildren().addAll(title, category);

        box.setOnMouseClicked(event -> { // Mouse click

            Alert A = new Alert(Alert.AlertType.CONFIRMATION);
            A.setContentText("Voulez vous accepter cette postulation ?");
            Optional<ButtonType> result = A.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                sp.modifier(poste);
                System.out.println(poste.getO());
                so.accepter(poste.getO());

            }

        });
        title.setOnMouseClicked(event -> { // Mouse click
            ServiceUser su = new ServiceUser();
            Stage popupWindow = new Stage();
            popupWindow.setWidth(500);
            popupWindow.setHeight(800);

            // create an ImageView and 6 Labels for the pop-up window
            ImageView imageView = new ImageView();
            Freelancer f = (Freelancer) su.getOneById(poste.getF().getId());
            String ImagePath = f.getImagePath();
            Image image = new Image(new File(ImagePath).toURI().toString());
            double minWidth = Math.min(image.getWidth(), image.getHeight());

            WritableImage resizedImage = new WritableImage((int) minWidth, (int) minWidth);
            PixelWriter pixelWriter = resizedImage.getPixelWriter();

            for (int x = 0; x < minWidth; x++) {
                for (int y = 0; y < minWidth; y++) {
                    pixelWriter.setArgb(x, y, image.getPixelReader().getArgb(x, y));
                }
            }
            imageView.setImage(resizedImage);

            imageView.setPreserveRatio(true);
            imageView.setFitHeight(300);
            imageView.setFitWidth(300);
            Circle clip = new Circle(300 / 2, 300 / 2, 300 / 2);
            imageView.setClip(clip);
            // Circle circle = new Circle();
            //imageView.setClip(circle);

            Label label1 = new Label(f.getName().toUpperCase() + " " + f.getLastName().toUpperCase());
            Label label2 = new Label("Bio : " + f.getBio());
            Label label3 = new Label("Experience : " + f.getExperience());
            Label label4 = new Label("Taches finis : " + f.getTotal_jobs());
            Label label5 = new Label("Note : " + f.getRating());
            label1.setFont(Font.font("Arial", FontWeight.BOLD, 24));
            label1.setTextFill(Color.GREY);
            label2.setFont(Font.font("Arial", FontWeight.BOLD, 19));
            label3.setFont(Font.font("Arial", FontWeight.BOLD, 19));
            label4.setFont(Font.font("Arial", FontWeight.BOLD, 19));
            label5.setFont(Font.font("Arial", FontWeight.BOLD, 19));

            // create a button for the pop-up window
            javafx.scene.control.Button closeButton = new javafx.scene.control.Button("Close");
            closeButton.setOnAction(e -> popupWindow.close());

            // create a layout for the button
            HBox buttonLayout = new HBox(closeButton);
            buttonLayout.setAlignment(Pos.CENTER);

            // create a layout for the ImageView and Labels
            VBox popupLayout = new VBox(imageView, label1, label2, label3, label4, label5);
            popupLayout.setAlignment(Pos.CENTER);
            popupLayout.setSpacing(40);

            // create a layout for the entire pop-up window
            // set the scene for the pop-up window
            Scene scene = new Scene(popupLayout);
            popupWindow.setScene(scene);

            // set the modality of the pop-up window
            popupWindow.initModality(Modality.APPLICATION_MODAL);

            // show the pop-up window
            popupWindow.showAndWait();

        });

        box.setOnMouseEntered(event -> {
            box.setStyle("-fx-background-color: #acaaad; -fx-background-radius: 40px;");
            title.setStyle("-fx-text-fill: #BB0000;");
            Color c = new Color(0, 0, 0, 1);
            category.setTextFill(c);

        });

        box.setOnMouseExited(event -> {
            box.setStyle("-fx-background-color: #BB0000; -fx-background-radius: 20;");
            title.setStyle("-fx-text-fill: #acaaad;");
            category.setTextFill(Color.WHITE);

        });

        return box;
    }

}

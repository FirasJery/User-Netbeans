/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ReclamationGUI;

import entities.Freelancer;
import entities.Utilisateur;
import java.io.File;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import services.ServiceUser;

/**
 * FXML Controller class
 *
 * @author Firas
 */
public class TopFiveController implements Initializable {

    @FXML
    private ScrollPane Pane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        ServiceUser su = new ServiceUser();
        List<Utilisateur> lu = su.getAll();
        Comparator c = Comparator.comparing(Freelancer::getRating).reversed();

        List<Freelancer> lf = (List<Freelancer>) lu.stream().filter(e -> e instanceof Freelancer)
                .map(e -> (Freelancer) e)
                .sorted(c)
                .limit(5)
                .collect(Collectors.toList());
        List<Freelancer> Topfive = (List<Freelancer>) lf.stream()
                .sorted(c)
                .limit(5)
                .collect(Collectors.toList());

        System.out.println(Topfive.size());
        System.out.println(Topfive);
        /////////////////////////////// graphics
        Pane.setPrefWidth(1200);

        HBox hbox1 = new HBox();
        HBox hbox2 = new HBox();
        HBox hbox3 = new HBox();

        for (int i = 0; i < 5; i++) {
            Freelancer f = Topfive.get(i);

            ImageView imageView = new ImageView();
            Image image = new Image(new File(f.getImagePath()).toURI().toString());
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
            imageView.setFitHeight(150);
            imageView.setFitWidth(150);
            Circle clip = new Circle(150 / 2, 150 / 2, 150 / 2);
            imageView.setClip(clip);
            /////////////////////////////////////////////////////////////////////////////////////////
            int j=i+1;
            Label Tilte = new Label("Top " + j);
            Tilte.setFont(Font.font("Arial", FontWeight.BOLD, 20));
            Tilte.setStyle("-fx-text-fill: #ffffff;");
            Label nameLabel = new Label(f.getName().toUpperCase() + " " + f.getLastName().toUpperCase());
            nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
            nameLabel.setStyle("-fx-text-fill: #ffffff;");
            Label ratingLabel = new Label("Rating: " + f.getRating());
            ratingLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
                        ratingLabel.setStyle("-fx-text-fill: #ffffff;");


            Separator separator = new Separator(Orientation.HORIZONTAL);
            separator.setPrefWidth(1200);

            VBox vbox = new VBox(Tilte,imageView, nameLabel, ratingLabel, separator);
            
            vbox.setAlignment(Pos.CENTER);
            vbox.setSpacing(10);
            
            
            
           
            if (i < 1) {
                hbox1.getChildren().addAll(vbox);
                hbox1.setStyle("-fx-background-image: url('resources/podium.png');");
            } else if (i < 3) {
                hbox2.getChildren().add(vbox);
            } else {
                hbox3.getChildren().add(vbox);
            }
        }

// Add the HBoxes to the Pane
          VBox v = new VBox();
          v.setPrefHeight(800);
          v.getChildren().addAll(hbox1,hbox2,hbox3);
          v.setStyle("-fx-background-image: url('resources/hola.jpg');");

        Pane.setContent(v);
        Pane.setFitToWidth(true);
        Pane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CertifGUI;

import entities.Certif;
import entities.SessionManager;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import services.ServiceCertif;

/**
 * FXML Controller class
 *
 * @author Ghass
 */

public class ListeCertifFrontController implements Initializable {

    @FXML
    private Pane pana;
    @FXML
    private ScrollPane scrollpanne;
    ServiceCertif sc = new ServiceCertif();
    private SessionManager sm = SessionManager.getInstance();
    /**
     * Initializes the controller class.
     */
    
     public void table(){
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

        scrollpanne.setContent(vBox);
        scrollpanne.setFitToWidth(true);
        scrollpanne.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        table();
    }    
    
     
    private VBox createOffreBox(Certif offre)  {
        VBox box = new VBox();
        
        box.setAlignment(Pos.CENTER);
        box.setSpacing(30);
          URL imageUrl = getClass().getResource("/resources/trans.jpg");
        box.setStyle("-fx-background-image: url('" + imageUrl + "');");
        box.setPrefWidth(1200);
         box.setUserData(offre.getId()); // set the ID as the user data for the VBox


        Label titre = new Label("Titre :"+offre.getNom());

        File imagef = new File(offre.getBadge());
        Image image = new Image(imagef.toURI().toString());
        ImageView imm = new ImageView(image);
      
        Label desc = new Label(offre.getDesc());
        Label voir = new Label("Cliquez pour Afficher Les tests");
        Label sep = new Label("_________________________________________________________________________________________________");
        
        
        titre.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        
      desc.setStyle("-fx-text-fill : white;");
      voir.setStyle("-fx-text-fill : white;");
            titre.setStyle("-fx-text-fill : white;");

        
         voir.setFont(Font.font("Serif", FontWeight.LIGHT, 15));
        desc.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        
       
        imm.setFitWidth(100);
        imm.setFitHeight(100);
        
        box.getChildren().addAll( titre,desc,imm,voir,sep);
        
        
        
        
          box.setOnMouseClicked(event -> {
            sm.setCurrentCertif(offre);
            pana.getChildren().clear();
        FXMLLoader loadOffre = new FXMLLoader(getClass().getResource("/CertifGUI/ListTestFont.fxml"));
        
        try {
           
            pana.getChildren().add(loadOffre.load());
        } catch (IOException ex) {
            ex.getMessage();
        }
        
        });


        return box;
    }

}

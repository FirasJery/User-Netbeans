/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ReclamationGUI;

import entities.Reclamation;
import entities.Reponse;
import entities.SessionManager;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import services.ServiceRec;
import services.ServiceReponse;

/**
 * FXML Controller class
 *
 * @author Ghass
 */
public class SuivieRecController implements Initializable {

    @FXML
    private ScrollPane scrollpane;
    private ServiceRec sr = new ServiceRec();
    SessionManager sessionManager = SessionManager.getInstance();

    /**
     * Initializes the controller class.
     */
    public void table() {
        List<Reclamation> offres = sr.getMyRtrans(sessionManager.getCurrentUser().getId());
        VBox vBox = new VBox();

        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setSpacing(30);

        HBox hBox = new HBox();

        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(100);//

        int count = 0;
        for (Reclamation offre : offres) {
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
        // TODO
        table();
    }

    private VBox createOffreBox(Reclamation offre) {
        VBox box = new VBox();

        box.setAlignment(Pos.CENTER);
        box.setSpacing(30);
        URL imageUrl = getClass().getResource("/resources/trans.jpg");
        box.setStyle("-fx-background-image: url('" + imageUrl + "');");
        Insets I = new Insets(20,0,20,0); 
        box.setPadding(I);
        box.setPrefWidth(1200);
        box.setUserData(offre.getId()); // set the ID as the user data for the VBox

        Label titre = new Label("Type :  " + offre.getType());
        titre.setTextFill(Color.WHITE);
                
        Button bb = new Button();
        bb.setText("Supprimer");

        Label user = new Label(offre.getId_user().getName());
        Label etat = new Label();
        switch (offre.isEtat()) {
            case 0:
                etat = new Label("Etat :               Non Traitée");
                break;
            case 2:
                etat = new Label("Etat :                      Traitée");
                break;
            case 1:
                etat= new Label("Etat :                      Ouverte");
                break;
        }

        Label voir = new Label("Contenu      :" + offre.getDesc());
        Label sep = new Label("____________________________________________________________________________________________________________________");

        user.setStyle("-fx-text-fill : white;");
        voir.setStyle("-fx-text-fill : white;");
        etat.setStyle("-fx-text-fill : Red;");

        voir.setFont(Font.font("Serif", FontWeight.LIGHT, 23));
        titre.setFont(Font.font("Arial", FontWeight.BOLD, 29));

        user.setWrapText(true);
        box.getChildren().addAll(titre, voir, etat, bb);
        if(offre.isEtat()==2){
        Button bbt = new Button();
        bbt.setText("voir reponse");
        box.getChildren().add(bbt);
        
        
         bbt.setOnMouseClicked(event -> {
             ServiceReponse spp = new ServiceReponse();
             Reponse p;
             p=spp.getOneByIdRec(offre.getId());
             Label rep = new Label (p.getContenu());
             rep.setTextFill(Color.WHITE);
             box.getChildren().add(rep);
             bbt.setVisible(false);
             
        });}
       
        bb.setOnMouseClicked(event -> {

            sr.supprimer(offre.getId());
            table();

        });

        
        return box;
    }
}

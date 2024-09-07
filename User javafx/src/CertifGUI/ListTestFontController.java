/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CertifGUI;

import entities.Facture;
import entities.SessionManager;
import entities.Test;
import entities.Wallet;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import services.ServiceFacture;
import services.ServicePassage;

import services.ServiceTest;
import services.ServiceWallet;

/**
 * FXML Controller class
 *
 * @author Ghass
 */
public class ListTestFontController implements Initializable {

    @FXML
    private Pane panna;
    @FXML
    private ScrollPane scrollpanne;
    @FXML
    private Label nomcert;
    private SessionManager sm = SessionManager.getInstance();
    ServiceTest st = new ServiceTest();

    /**
     * Initializes the controller class.
     */
    public void table(){
        
        List<Test> succes = st.getSuccess(sm.getCurrentUser().getId(),sm.getCurrentCertif().getId());
        List<Test> all = st.getByIdCertif(sm.getCurrentCertif().getId());
        System.out.println(all);
        System.out.println("/////////////");
        System.out.println(succes);
         Set<Test> set2 = new HashSet<>(succes);
        Iterator<Test> iter1 = all.iterator();
        while (iter1.hasNext()) {
            Test offer = iter1.next();
            if (set2.contains(offer)) {
                iter1.remove();
            }
        }
        VBox vBox = new VBox();
                vBox.setPrefWidth(1200);

        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setSpacing(30);
        

        HBox hBox = new HBox();
         
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(100);//
        

        int count = 0;
        for (Test offre : all) {
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
    public void tableSucc() throws SQLException{
        List<Test> offres = st.getSuccess(sm.getCurrentUser().getId(),sm.getCurrentCertif().getId());
        VBox vBox = new VBox();
                vBox.setPrefWidth(1200);

        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setSpacing(30);
        

        HBox hBox = new HBox();
         
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(100);//
        

        int count = 0;
        for (Test offre : offres) {
            VBox box = createOffreBoxSucces(offre);
            
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
        // 
        nomcert.setText(sm.getCurrentCertif().getNom());
        table();
    }    
    
     private VBox createOffreBox(Test offre)  {
        VBox box = new VBox();
                box.setPrefWidth(1200);

        box.setAlignment(Pos.CENTER);
        box.setSpacing(30);
         URL imageUrl = getClass().getResource("/resources/trans.jpg");
        box.setStyle("-fx-background-image: url('" + imageUrl + "');");
         box.setUserData(offre.getId()); // set the ID as the user data for the VBox


        Label titre = new Label("Titre :  "+offre.getTitre());
        
        
         
        Label cat = new Label("Categorie :"+offre.getCategorie());
       
        Label voir = new Label("Contenu      :"+offre.getDesc());
        Label prix = new Label("Prix :"+offre.getPrix());
        Button bb = new Button();
        bb.setText("Passer");
        Label sep = new Label("---------------------------------------------------------------------------------------------");

     
        
   
      voir.setStyle("-fx-text-fill : white;");
      cat.setStyle("-fx-text-fill : white;");

                  titre.setStyle("-fx-text-fill : white;");
                                    prix.setStyle("-fx-text-fill : white;");

        
         voir.setFont(Font.font("Serif", FontWeight.LIGHT, 23));
        titre.setFont(Font.font("Arial", FontWeight.BOLD, 29));
        
       
        box.getChildren().addAll( titre,cat,voir,prix,bb,sep);
        
        bb.setOnMouseClicked(event -> {
            
          Alert A = new Alert(Alert.AlertType.CONFIRMATION);
          A.setContentText("Voulez vous payer" + prix.getText() +" pour passer ce test ?");
          
           Optional<ButtonType> result = A.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                ServiceWallet sw = new ServiceWallet();
                Wallet w = new Wallet();
                w = sw.getOneByUserId(sm.getCurrentUser().getId());
                
                if (sw.Checksolde(w.getId(), offre.getPrix()))
                {
                    sw.LowerSolde(w, ( int ) offre.getPrix());
                    Facture f ;
                    f= new Facture(offre.getPrix(),w,sm.getCurrentCertif().getNom(),sm.getCurrentUser());
                    ServiceFacture sf = new ServiceFacture();
                    sf.ajouter(f);
                  sm.setCurrentTest(offre);
                     panna.getChildren().clear();
        FXMLLoader loadOffre = new FXMLLoader(getClass().getResource("/CertifGUI/AffichageTest.fxml"));
        
        try {
           
            panna.getChildren().add(loadOffre.load());
        } catch (IOException ex) {
            ex.getMessage();
        }
        
                }
                else {
               A.setAlertType(Alert.AlertType.ERROR);
               A.setContentText("Solde insuffisant");
               A.show();
                }
                
            }
          

        
        
               
            
        });
        return box;
}
     
     private VBox createOffreBoxSucces(Test offre) throws SQLException  {
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.setSpacing(30);
                        box.setPrefWidth(1200);

         URL imageUrl = getClass().getResource("/resources/trans.jpg");
        box.setStyle("-fx-background-image: url('" + imageUrl + "');");

         box.setUserData(offre.getId()); // set the ID as the user data for the VBox


        Label titre = new Label("Titre :  "+offre.getTitre());
        
        ServicePassage spp = new ServicePassage();
         
        Label cat = new Label("Categorie :"+offre.getCategorie());
       
        Label voir = new Label("Contenu      :"+offre.getDesc());
        Label score = new Label("note :"+spp.getNoteBytest(offre.getId(), sm.getCurrentUser().getId()));
       
        Label sep = new Label("---------------------------------------------------------------------------------------------");

     
        
                     titre.setStyle("-fx-text-fill : white;");

      voir.setStyle("-fx-text-fill : white;");
            cat.setStyle("-fx-text-fill : white;");

                  score.setStyle("-fx-text-fill : white;");

    
        
         voir.setFont(Font.font("Serif", FontWeight.LIGHT, 23));
        titre.setFont(Font.font("Arial", FontWeight.BOLD, 29));
        
       
        box.getChildren().addAll( titre,cat,voir,score,sep);
        
        
        return box;
}
     
     public void showContent(String pathfxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(pathfxml));
            Parent homeView = loader.load();
            sm.getCurrentHbox().getChildren().clear();
            sm.getCurrentHbox().getChildren().add(homeView);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    @FXML
    private void succ(ActionEvent event) throws SQLException {
        tableSucc();
    }
}

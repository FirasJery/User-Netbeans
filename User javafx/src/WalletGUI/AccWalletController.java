/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WalletGUI;

import entities.SessionManager;
import entities.Wallet;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import services.ServiceWallet;

/**
 * FXML Controller class
 *
 * @author Ghass
 */
public class AccWalletController implements Initializable {

    @FXML
    private ScrollPane scrollpane;
    SessionManager sessionManager = SessionManager.getInstance();
    private ServiceWallet sw = new ServiceWallet();
    @FXML
    private Pane panne;
    @FXML
    private Label labelnom;
    @FXML
    private Label labelkey;
    @FXML
    private Text zz;
    @FXML
    private Text z;
    @FXML
    private AnchorPane anchor;

    /**
     * Initializes the controller class.
     */

    public void table() {
        Wallet offres = sw.getOneByUserId(sessionManager.getCurrentUser().getId());
        VBox vBox = new VBox();

        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setSpacing(30);

        HBox hBox = new HBox();

        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(100);//

        VBox box = createOffreBox(offres);

        hBox.getChildren().add(box);

        vBox.getChildren().add(hBox);

        scrollpane.setContent(vBox);
        scrollpane.setFitToWidth(true);
        scrollpane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        URL imageUrl = getClass().getResource("/resources/hola.jpg");
        anchor.setStyle("-fx-background-image: url('" + imageUrl + "');");
        labelnom.setText(sw.getOneByUserId(sessionManager.getCurrentUser().getId()).getNum_carte());
        labelkey.setText(sw.getOneByUserId(sessionManager.getCurrentUser().getId()).getCle());
        table();
    }

    private VBox createOffreBox(Wallet offre) {
        VBox box = new VBox();

        box.setAlignment(Pos.CENTER);
        box.setSpacing(30);
        box.setUserData(offre.getId()); // set the ID as the user data for the VBox

        Label titre = new Label(offre.getNum_carte());

        Label solde = new Label("Solde :" + String.valueOf(offre.getSolde()) + "$");
        Label bonus = new Label("Bonus :" + String.valueOf(offre.getBonus()) + "$");

        Button bb = new Button();
        bb.setText("Depot");

        Label user = new Label(offre.getIduser().getName());

        Label sep = new Label("_________________________________________________________________________________________________");

        titre.setFont(Font.font("Arial", FontWeight.BOLD, 32));

        user.setStyle("-fx-text-fill : Green;");
        titre.setStyle("-fx-text-fill : RED;");
        URL imageUrl = getClass().getResource("/resources/wall.jpg");

        Label nn = new Label("");
        Label nnn = new Label("");

        user.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        solde.setFont(Font.font("Arial", FontWeight.BOLD, 35));
        bonus.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        solde.setStyle("-fx-text-fill : BLACK;");
        bonus.setStyle("-fx-text-fill : BLACK;");
        user.setWrapText(true);
        bb.setPrefWidth(100);
        bb.setStyle("-fx-background-color : BLACK;");
        bb.setStyle("-fx-text-color : WHITE;");
        bb.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        box.getChildren().addAll(nn, nnn, solde, bonus, bb);
        //box.setStyle("-fx-background-color : #FF2E2E");
        box.setStyle("-fx-background-image: url('" + imageUrl + "');");
        box.setPrefHeight(366);
        box.setPrefWidth(373);
        bb.setOnMouseClicked(event -> {
            panne.getChildren().clear();
            FXMLLoader loadOffre = new FXMLLoader(getClass().getResource("/WalletGUI/DepotWallet.fxml"));
            try {

                panne.getChildren().add(loadOffre.load());
            } catch (IOException ex) {
                ex.getMessage();
            }

        });

        return box;
    }

    @FXML
    private void carte(ActionEvent event) {
        panne.getChildren().clear();
        FXMLLoader loadOffre = new FXMLLoader(getClass().getResource("/WalletGUI/AddCard.fxml"));

        try {

            panne.getChildren().add(loadOffre.load());
        } catch (IOException ex) {
            ex.getMessage();
        }

    }

    @FXML
    private void transf(ActionEvent event) {

        panne.getChildren().clear();
        FXMLLoader loadOffre = new FXMLLoader(getClass().getResource("/WalletGUI/TransferSolde.fxml"));

        try {

            panne.getChildren().add(loadOffre.load());
        } catch (IOException ex) {
            ex.getMessage();
        }
    }

    @FXML
    private void Suiv(ActionEvent event) {
        panne.getChildren().clear();
        FXMLLoader loadOffre = new FXMLLoader(getClass().getResource("/WalletGUI/SuivTrans.fxml"));

        try {

            panne.getChildren().add(loadOffre.load());
        } catch (IOException ex) {
            ex.getMessage();
        }

    }
}

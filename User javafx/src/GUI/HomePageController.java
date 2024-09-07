/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import entities.SessionManager;
import entities.Utilisateur;
import entities.Wallet;
import java.awt.AWTException;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.Bloom;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import services.NotifService;
import services.ServiceWallet;
import javafx.application.Platform;
import java.awt.event.ActionEvent;
import java.io.File;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import services.ServiceUser;
import tests.FxMain;

/**
 * FXML Controller class
 *
 * @author Firas
 */
public class HomePageController implements Initializable {

    @FXML
    private HBox navbar;
    @FXML
    private HBox content;
    @FXML
    private HBox bottom_content;
    @FXML
    private ImageView home_btn;
    @FXML
    private Label offres_btn;
    @FXML
    private Label reclamations_btn;
    @FXML
    private Label messages_btn;
    @FXML
    private ImageView profile_btn;
    @FXML
    private Label dc_btn;
    @FXML
    private Label wallet_btn;

    SessionManager sessionManager = SessionManager.getInstance();
    String offre_path = "";

    //Effects
    Bloom bloom = new Bloom(0.3);
    @FXML
    private AnchorPane AnchorHome;
    @FXML
    private Label bntCertif;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (FxMain.ContinueSession) {
            ServiceUser su = new ServiceUser();
            Utilisateur u = su.getOneById(FxMain.id_continue);
            sessionManager.setCurrentUser(u);
        }
        switch (sessionManager.getCurrentUser().getRole()) {
            case "Freelancer":
                offres_btn.setText("Offres");
                offre_path = "/OffresGUI/affichageOffre.fxml";
                break;
            case "Entreprise":
                offres_btn.setText("Offres");
                offre_path = "/OffresGUI/AffichageOffreEntrprise.fxml";
                bntCertif.setVisible(false);
                break;
            default:
                break;
        }
        // Instance Wallet
        ServiceWallet sw = new ServiceWallet();
        if (sw.ChercherW(sessionManager.getCurrentUser().getId()) == -1) {
            Wallet w;
            w = new Wallet(sessionManager.getCurrentUser().getName(), 0, 0, sessionManager.getCurrentUser().getEmail(), sessionManager.getCurrentUser(), sw.generateRandomString(9));
            sw.ajouter(w);
        }
        //
        //showContent(offre_path);
        
        showContent("HomePageContent.fxml");

       /* NotifService nn = new NotifService();
        try {
            List<String> lss = nn.getNotifmsg(sessionManager.getCurrentUser().getId());
            for (String s : lss) {
                showNotification(s);
            }
        } catch (SQLException ex) {
            Logger.getLogger(HomePageController.class.getName()).log(Level.SEVERE, null, ex);
        }*/

    }
  
    public void showNotification(String message) {
        Platform.runLater(() -> {
            SystemTray tray = SystemTray.getSystemTray();
            java.awt.Image image = Toolkit.getDefaultToolkit().getImage("icon.png");
            TrayIcon trayIcon = new TrayIcon(image, "Freelancy");
            trayIcon.setImageAutoSize(true);
            trayIcon.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Handle user clicking on the notification
                }
            });
            try {
                tray.add(trayIcon);
                trayIcon.displayMessage("freelancy", message, TrayIcon.MessageType.INFO);
            } catch (AWTException ex) {
                System.out.println("TrayIcon could not be added.");
            }
        });

        //showContent(offre_path);
        showContent("HomePageContent.fxml");

    }

    public void showContent(String pathfxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(pathfxml));
            Parent homeView = loader.load();
            content.getChildren().clear();
            content.getChildren().add(homeView);
            

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        
    }

    @FXML
    private void go_home(MouseEvent event) {
        if (event.isPrimaryButtonDown()) {
            showContent("HomePageContent.fxml");
        }
    }

    @FXML
    private void home_effect(MouseEvent event) {
        if (home_btn.getEffect() == null) {
            home_btn.setEffect(bloom);
        } else {
            home_btn.setEffect(null);
        }
    }

    @FXML
    private void go_msg(MouseEvent event) {
        if (event.isPrimaryButtonDown()) {
            showContent("/MessagerieGUI/messagerieInterface.fxml");

        }

    }

    @FXML
    private void go_offres(MouseEvent event) {
        showContent(offre_path);
    }

    @FXML
    private void go_profile(MouseEvent event) {
        switch (sessionManager.getCurrentUser().getRole()) {
            case "Freelancer":
                showContent("/UserGUI/FreelancerProfileUI.fxml");
                break;
            case "Entreprise":
                showContent("/UserGUI/EntrepriseProfileUI.fxml");
                break;
            default:
                break;
        }
    }

    @FXML
    private void Se_deconnecterMousePressed(MouseEvent event) {
        File file = new File("src/tests/IDSession.txt");

        if (file.delete()) {
            System.out.println("File deleted successfully!");
        } else {
            System.out.println("Failed to delete the file.");
        }
        SessionManager.getInstance().setCurrentUser(null);
        try {

            Parent page1 = FXMLLoader.load(getClass().getResource("/UserGUI/LoginUI.fxml"));

            Scene scene = new Scene(page1);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(scene);

            stage.show();

        } catch (IOException ex) {

            System.out.println(ex.getMessage());

        }
    }

    @FXML
    private void Go_Reclamations(MouseEvent event) {
        showContent("/ReclamationGUI/AffReclamationFXML.fxml");
    }

    @FXML
    private void wall(MouseEvent event) {

        showContent("/WalletGUI/AccWallet.fxml");
    }

    @FXML
    private void MousePressedCertif(MouseEvent event) {
                showContent("/CertifGUI/ListeCertifFront.fxml");

    }


}

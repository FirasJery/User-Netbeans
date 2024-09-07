/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserGUI;

import GUI.ControleSaisieTextFields;
import entities.Admin;
import entities.SessionManager;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;
import services.PasswordEncryption;
import services.ServiceUser;

/**
 * FXML Controller class
 *
 * @author Firas
 */
public class AddAdminUIController implements Initializable {

    @FXML
    private TextField TF_NOM;
    @FXML
    private Button AddUser;
    @FXML
    private TextField TF_Email;
    @FXML
    private Button button_anuuler;
    @FXML
    private ImageView ImagePreview;
    @FXML
    private Button btnImage;
    @FXML
    private TextField tfPrenom;
    @FXML
    private TextField tfUsername;
    @FXML
    private PasswordField pfPassword;
    @FXML
    private PasswordField pfconfirm;
    @FXML
    private Label labelImage;

    private ControleSaisieTextFields cs;
    private String ImagePath;
    @FXML
    private Label labelnomError;
    @FXML
    private Label labelprenomerror;
    @FXML
    private Label labelusernameerror;
    @FXML
    private Label labelemailerror;
    @FXML
    private Label labelmdperror;
    @FXML
    private Label labelconfirmerror;
    @FXML
    private Button btnRetour;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cs = new ControleSaisieTextFields();
        ImagePath = "resources/profile.jpg";

        TF_NOM.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                String text = TF_NOM.getText();
                if (cs.isEmpty(text)) {
                    labelnomError.setText("Champs vide !");

                } else if (!cs.checkOnlyString(text)) {
                    labelnomError.setText("nom n'est pas valide ! ");

                } else {
                    labelnomError.setText(" ");
                }

            }
        });
        tfPrenom.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                String text = tfPrenom.getText();
                if (cs.isEmpty(text)) {
                    labelprenomerror.setText("Champs vide !");

                } else if (!cs.checkOnlyString(text)) {
                    labelprenomerror.setText("prenom n'est pas valide ! ");

                } else {
                    labelprenomerror.setText(" ");
                }

            }
        });

        TF_Email.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                String text = TF_Email.getText();
                if (cs.isEmpty(text)) {
                    labelemailerror.setText("Champs vide !");

                } else if (!cs.isEmailAdress(text)) {
                    labelemailerror.setText("email n'est pas valide ! ");
                } else {
                    labelemailerror.setText(" ");
                }

            }
        });
        pfPassword.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                String text = pfPassword.getText();
                if (cs.isEmpty(text)) {
                    labelmdperror.setText("Champs vide !");

                } else if (text.length() <= 8) {

                    labelmdperror.setText("mot de pasee trop court  ! ");

                } else {
                    labelmdperror.setText(" ");
                }

            }
        });
        pfconfirm.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                String text = pfconfirm.getText();
                if (cs.isEmpty(text)) {
                    labelconfirmerror.setText("Champs vide !");

                } else if (text.length() <= 8) {

                    labelconfirmerror.setText("mot de pasee trop court  ! ");

                } else if (!(pfconfirm.getText().equals(pfPassword.getText()))) {

                    labelconfirmerror.setText("mot de passe different  ! ");

                } else {
                    labelconfirmerror.setText(" ");
                }

            }
        });

    }

    @FXML
    private void On_AddUser_clicked(ActionEvent event) throws Exception {
        ServiceUser sa = new ServiceUser();
        Admin a = new Admin();
        Alert A = new Alert(Alert.AlertType.ERROR);
        if (TF_NOM.getText().isEmpty() || tfPrenom.getText().isEmpty() || tfUsername.getText().isEmpty() || TF_Email.getText().isEmpty() || pfPassword.getText().isEmpty() || pfconfirm.getText().isEmpty()) {
            A.setContentText("remplir tout les champs ! ");
            A.show();
        } else if (!cs.checkOnlyString(TF_NOM.getText())) {
            A.setContentText("nom invalide ! ");
            A.show();
        } else if (!cs.checkOnlyString(tfPrenom.getText())) {
            A.setContentText("prenom invalide ! ");
            A.show();
        } else if (!cs.checkOnlyString(tfUsername.getText())) {
            A.setContentText("username invalide ! ");
            A.show();
        } else if (!cs.isEmailAdress(TF_Email.getText())) {
            A.setContentText("Email invalide ! ");
            A.show();
        } else if (pfPassword.getText().length() < 8) {
            A.setContentText("mdp trop court ! ");
            A.show();
        } else if (!(pfPassword.getText().equals(pfconfirm.getText()))) {
            A.setContentText("mdps ne sont pas conformes ! ");
            A.show();
        } else if (sa.ChercherMail(TF_Email.getText()) == 1)
        {
             A.setContentText("mail existant ! ");
            A.show();
        }else {
           // String encrypt = PasswordEncryption.encrypt(pfPassword.getText());
            String hashed = BCrypt.hashpw(pfPassword.getText(), BCrypt.gensalt(13));
            a.setName(TF_NOM.getText());
            a.setLastName(tfPrenom.getText());
            a.setUserName(tfUsername.getText());
            a.setEmail(TF_Email.getText());
            a.setPassword(hashed);
            a.setImagePath(ImagePath);
            sa.ajouter(a);
               try {
            Parent page1 = FXMLLoader.load(getClass().getResource("/UserGUI/GestionAdminUI.fxml"));
            
            SessionManager.getInstance().showContent(page1);

        } catch (IOException ex) {
            Logger.getLogger(AddAdminUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
        }

    }

    @FXML
    private void On_anuuler_clicked(ActionEvent event) {
        TF_NOM.clear();
        tfPrenom.clear();
        tfUsername.clear();
        TF_Email.clear();
        pfPassword.clear();
        pfconfirm.clear();
        labelImage.setText("");
        
    }

    @FXML
    private void btnImageAction(ActionEvent event) {
         FileChooser fc = new FileChooser();
        File defaultDir = new File("resources");
        fc.setInitialDirectory(defaultDir);
        File SelectedFile = fc.showOpenDialog(null);
        if (SelectedFile != null) {
            
            ImagePath = defaultDir.getName() + "/" + SelectedFile.getName();
            labelImage.setText(ImagePath);
            ImagePreview.setImage(new Image(new File(ImagePath).toURI().toString()));
        } else {

            ImagePath = "resources/profile.jpg";
            labelImage.setText(ImagePath);
            ImagePreview.setImage(new Image(new File(ImagePath).toURI().toString()));

        }
    }

    @FXML
    private void btnRetourAction(ActionEvent event) {
        try {
            Parent page1 = FXMLLoader.load(getClass().getResource("/UserGUI/GestionAdminUI.fxml"));
            
            SessionManager.getInstance().showContent(page1);
        } catch (IOException ex) {
            Logger.getLogger(AddAdminUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

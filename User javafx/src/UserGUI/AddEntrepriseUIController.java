/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserGUI;

import GUI.ControleSaisieTextFields;
import entities.Entreprise;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;
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
import services.PasswordEncryption;
import services.ServiceUser;
import org.mindrot.jbcrypt.BCrypt;

/**
 * FXML Controller class
 *
 * @author Firas
 */
public class AddEntrepriseUIController implements Initializable {

    @FXML
    private TextField tf_nom;
    @FXML
    private TextField tf_mail;
    @FXML
    private TextField tf_password;
    @FXML
    private Button btn_select_image;
    @FXML
    private Button btn_inscrire;
    @FXML
    private Button btn_annuler;
    @FXML
    private Label image_link;
    @FXML
    private TextField tf_info;
    @FXML
    private TextField tf_domaine;
    @FXML
    private TextField tf_adresse;
    @FXML
    private TextField tf_nbe;

    private String ImagePath;
    private ControleSaisieTextFields cs = new ControleSaisieTextFields();
    @FXML
    private Label labelNomError;
    @FXML
    private Label labelMailError;
    @FXML
    private Label labelPasswordError;
    @FXML
    private Label labelInfoError;
    @FXML
    private Label labelDomaineError;
    @FXML
    private Label labelAdresseError;
    @FXML
    private Label labelNbeError;
    @FXML
    private ImageView ImagePreview;
    @FXML
    private Button btnRetour;
    @FXML
    private TextField tfusername;
    @FXML
    private Label labelerrorusername;
    @FXML
    private PasswordField tfconfirm;
    @FXML
    private Label labelerrorconfirm;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ImagePath = "resources/profile.jpg";

        tf_nom.textProperty().addListener((observable, oldValue, newValue) -> {
            
                
                if (cs.isEmpty(newValue)) {
                    labelNomError.setText("Champs vide !");

                } else if (!cs.checkOnlyString(newValue)) {
                    labelNomError.setText("nom n'est pas valide ! ");

                } else {
                    labelNomError.setText(" ");
                }

            
        });
        tfusername.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                String text = tfusername.getText();
                if (cs.isEmpty(text)) {
                    labelerrorusername.setText("Champs vide !");

                } else if (!cs.checkOnlyString(text)) {
                    labelNomError.setText("username n'est pas valide ! ");

                } else {
                    labelerrorusername.setText(" ");
                }

            }
        });

        tf_mail.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                String text = tf_mail.getText();
                if (cs.isEmpty(text)) {
                    labelMailError.setText("Champs vide !");

                } else if (!cs.isEmailAdress(text)) {
                    labelMailError.setText("email n'est pas valide ! ");
                } else {
                    labelMailError.setText(" ");
                }

            }
        });
        tf_password.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                String text = tf_password.getText();
                if (cs.isEmpty(text)) {
                    labelPasswordError.setText("Champs vide !");

                } else if (text.length() <= 8) {

                    labelPasswordError.setText("mot de pasee trop court  ! ");

                } else {
                    labelPasswordError.setText(" ");
                }

            }
        });
        tfconfirm.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                String text = tfconfirm.getText();
                if (cs.isEmpty(text)) {
                    labelerrorconfirm.setText("Champs vide !");

                } else if (text.length() <= 8) {

                    labelerrorconfirm.setText("mot de pasee trop court  ! ");

                } else {
                    labelerrorconfirm.setText(" ");
                }

            }
        });
        tf_info.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                String text = tf_info.getText();
                if (cs.isEmpty(text)) {
                    labelInfoError.setText("Champs vide !");

                } else if (!cs.checkOnlyString(text)) {
                    labelInfoError.setText("Information ne doit pas commencer par un chiffre! ");

                } else {
                    labelInfoError.setText("");
                }

            }
        });
        tf_domaine.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                String text = tf_domaine.getText();
                if (cs.isEmpty(text)) {
                    labelDomaineError.setText("Champs vide !");

                } else if (!cs.checkOnlyString(text)) {
                    labelDomaineError.setText("Domaine ne doit pas commencer par un chiffre! ");

                } else {
                    labelDomaineError.setText("");
                }

            }
        });
        tf_adresse.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                String text = tf_adresse.getText();
                if (cs.isEmpty(text)) {
                    labelAdresseError.setText("Champs vide !");

                } else if (!cs.checkOnlyString(text)) {
                    labelAdresseError.setText("Adresse ne doit pas commencer par un chiffre! ");

                } else {
                    labelAdresseError.setText(" ");
                }

            }
        });
        tf_nbe.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                String text = tf_nbe.getText();
                if (cs.isEmpty(text)) {
                    labelNbeError.setText("Champs vide !");

                } else if (!cs.checkOnlyInteger(text)) {
                    labelNbeError.setText("Saisir des chiffres seuelement !");

                } else {
                    labelNbeError.setText("");
                }

            }
        });
    }

    @FXML
    private void btn_inscrire_action(ActionEvent event) throws IOException, Exception {
        ServiceUser sa = new ServiceUser();
        Entreprise f = new Entreprise();

        if (cs.isEmpty(tfconfirm.getText()) || cs.isEmpty(tfusername.getText()) ||cs.isEmpty(tf_nom.getText()) || cs.isEmpty(tf_mail.getText()) || cs.isEmpty(tf_password.getText()) || cs.isEmpty(tf_info.getText()) || cs.isEmpty(tf_adresse.getText()) || cs.isEmpty(tf_domaine.getText()) || cs.isEmpty(tf_nbe.getText())) {
            Alert A = new Alert(Alert.AlertType.ERROR);
            A.setContentText("Veuillez remplir tout les champs ! ");
            A.showAndWait();
        } else if (cs.isEmpty(tf_nom.getText())) {
            Alert A = new Alert(Alert.AlertType.ERROR);
            A.setContentText("Champs nom vide !");
            A.showAndWait();
        } else if (!cs.checkOnlyString(tf_nom.getText())) {
            Alert A = new Alert(Alert.AlertType.ERROR);
            A.setContentText("nom n'est pas valide ! ");
            A.showAndWait();

        } else if (cs.isEmpty(tf_mail.getText())) {
            Alert A = new Alert(Alert.AlertType.ERROR);
            A.setContentText("Champs email vide !");
            A.showAndWait();
        } else if (!cs.isEmailAdress(tf_mail.getText())) {
            Alert A = new Alert(Alert.AlertType.ERROR);
            A.setContentText("email n'est pas valide ! ");
            A.showAndWait();
        } else if (cs.isEmpty(tf_password.getText())) {
            Alert A = new Alert(Alert.AlertType.ERROR);
            A.setContentText("Champs mot de passe vide !");
            A.showAndWait();
        } else if (tf_password.getText().length() <= 8) {
            Alert A = new Alert(Alert.AlertType.ERROR);
            A.setContentText("mot de pasee trop court  ! ");
            A.showAndWait();
        } else if (cs.isEmpty(tf_info.getText())) {
            Alert A = new Alert(Alert.AlertType.ERROR);
            A.setContentText("Champs info vide !");
            A.showAndWait();
        } else if (!cs.checkOnlyString(tf_info.getText())) {
            Alert A = new Alert(Alert.AlertType.ERROR);
            A.setContentText("Information ne doit pas commencer par un chiffre! ");
            A.showAndWait();
        } else if (cs.isEmpty(tf_domaine.getText())) {
            Alert A = new Alert(Alert.AlertType.ERROR);
            A.setContentText("Champs domaine vide !");
            A.showAndWait();
        } else if (!cs.checkOnlyString(tf_domaine.getText())) {
            Alert A = new Alert(Alert.AlertType.ERROR);
            A.setContentText("Domaine ne doit pas commencer par un chiffre! ");
            A.showAndWait();
        } else if (cs.isEmpty(tf_adresse.getText())) {
            Alert A = new Alert(Alert.AlertType.ERROR);
            A.setContentText("Champs adresse vide !");
            A.showAndWait();
        } else if (!cs.checkOnlyString(tf_adresse.getText())) {
            Alert A = new Alert(Alert.AlertType.ERROR);
            A.setContentText("Adresse ne doit pas commencer par un chiffre! ");
            A.showAndWait();
        } else if (cs.isEmpty(tf_nbe.getText())) {
            Alert A = new Alert(Alert.AlertType.ERROR);
            A.setContentText("Champs nombre employes vide !");
            A.showAndWait();
        } else if (!cs.checkOnlyInteger(tf_nbe.getText())) {
            Alert A = new Alert(Alert.AlertType.ERROR);
            A.setContentText("Nombre employes des chiffres seulement !");
            A.showAndWait();
        }else if ( ! (tfconfirm.getText().equals(tf_password.getText()))) {
            Alert A = new Alert(Alert.AlertType.ERROR);
            A.setContentText("mdps non conformes !");
            A.showAndWait();
        } //////////////////////////////////////////////////////////////////////////
        else if (sa.ChercherMail(tf_mail.getText()) == 1) {
            Alert A = new Alert(Alert.AlertType.ERROR);
            A.setContentText("L'adresse mail est deja utilisée !  ");
            A.showAndWait();
        } else {
            String hashed = BCrypt.hashpw(tf_password.getText(), BCrypt.gensalt(13));
           // String encrypt = PasswordEncryption.encrypt(tf_password.getText());
            f.setName(tf_nom.getText());
            f.setUserName(tfusername.getText());
            f.setEmail(tf_mail.getText());
            f.setPassword(hashed);
            f.setImagePath(ImagePath);
            f.setDomaine(tf_domaine.getText());
            f.setInfo(tf_info.getText());
            f.setLocation(tf_adresse.getText());
            f.setNumberOfEmployees(Integer.parseInt(tf_nbe.getText()));

            sa.ajouter(f);

            Parent page1 = FXMLLoader.load(getClass().getResource("/UserGUI/LoginUI.fxml"));

            Scene scene = new Scene(page1);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(scene);

            stage.show();

        }
    }

    public static void copyFileToDirectory(File sourceFile, File destDir) throws IOException {
    Path sourcePath = sourceFile.toPath();
    Path destPath = destDir.toPath().resolve(sourceFile.getName());
    Files.copy(sourcePath, destPath, StandardCopyOption.REPLACE_EXISTING);
}
    @FXML
    private void btn_image_action(ActionEvent event) throws IOException {
        FileChooser fc = new FileChooser();
        File defaultDir = new File("resources");
        fc.setInitialDirectory(defaultDir);
        File SelectedFile = fc.showOpenDialog(null);
        copyFileToDirectory(SelectedFile, defaultDir);
        if (SelectedFile != null) {
            
            ImagePath = defaultDir.getName() + "/" + SelectedFile.getName();
            image_link.setText(ImagePath);
            ImagePreview.setImage(new Image(new File(ImagePath).toURI().toString()));
        } else {

            ImagePath = "resources/profile.jpg";
            image_link.setText(ImagePath);
            ImagePreview.setImage(new Image(new File(ImagePath).toURI().toString()));

        }
    }

    @FXML
    private void btn_annuler_action(ActionEvent event) {

        tf_adresse.clear();
        tf_domaine.clear();
        tf_info.clear();
        tf_mail.clear();
        tf_nbe.clear();
        tf_nom.clear();
        tf_password.clear();
        ImagePath = "";

    }

    @FXML
    private void btnRetourAction(ActionEvent event) throws IOException {
        Parent page1 = FXMLLoader.load(getClass().getResource("/UserGUI/LoginUI.fxml"));

        Scene scene = new Scene(page1);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(scene);

        stage.show();
    }

}

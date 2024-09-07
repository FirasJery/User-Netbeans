package GUI;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import javax.mail.MessagingException;
import java.util.ResourceBundle;

import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import services.ServiceUser;

public class AddAdminUIController implements Initializable {

    @FXML
    private Button AddUser;
    @FXML
    private Button button_annuler;
    @FXML
    private TextField TF_nom;
    @FXML
    private PasswordField tfPassword;
    @FXML
    private PasswordField tfConfirmer;
    @FXML
    private TextField tfPrenom;
    @FXML
    private Button Add_image_button;
    @FXML
    private TextField TfUsername;
    @FXML
    private TextField TF_email;
    @FXML
    private ImageView ImagePreviw;

    private ServiceUser serviceUser = new ServiceUser();

    private Alert alert = new Alert(Alert.AlertType.NONE);

    private String ImagePath ;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialize your controller here
    }

    @FXML
    private void On_AddUser_clicked(ActionEvent event) throws IOException, MessagingException {
        ServiceUser sa = new ServiceUser();
        String nom = TF_nom.getText();
        String prenom = tfPrenom.getText();
        String email = TF_email.getText();
        String username = TfUsername.getText();
        String password = tfPassword.getText();
        String confirmer = tfConfirmer.getText();
        String role = "Admin";
        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty() || confirmer.isEmpty()) {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("Veuillez remplir tous les champs !");
            alert.show();
        }
        // name should be at least 4 characters
        else if (nom.length() < 4) {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("Nom doit contenir au moins 4 caractères !");
            alert.show();
        }
        // name should be at least 4 characters
        else if (prenom.length() < 4) {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("Prenom doit contenir au moins 4 caractères !");
            alert.show();
        }
        //username must have an uppercase letter at least
        else if (!username.matches(".*[A-Z].*")) {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("Username doit contenir au moins une lettre majuscule !");
            alert.show();
        }
        else if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$"))
        {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("Mail invalide !");
            alert.show();
        }
        // password should be at least 8
        else if (password.length() < 8) {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("Mot de passe doit contenir au moins 8 caractères !");
            alert.show();
        }
        else if (!password.equals(confirmer)) {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("Les mots de passe ne correspondent pas !");
            alert.show();
        }
        //user should select a picture
        else if (ImagePath == null) {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("Veuillez choisir une image");
            alert.show();
        }
        else {
            List<User> userList = serviceUser.getAll();
            for (User u : userList) {
                if (u.getEmail().equals(email)) {
                    alert.setAlertType(Alert.AlertType.WARNING);
                    alert.setContentText("Email existe déjà !");
                    alert.show();
                    return;
                }
            }
            User u = new User (nom, prenom, email, username, password, role, ImagePath);
            sa.ajouter(u);
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setContentText("Admin ajouté avec succès !");
            alert.show();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GestionAdminUI.fxml"));
            this.button_annuler.getScene().setRoot(loader.load());
        }



    }

    @FXML
    private void On_annuler_clicked(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GestionAdminUI.fxml"));
        this.button_annuler.getScene().setRoot(loader.load());
    }


    @FXML
    public void add_image_action(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        fileChooser.setTitle("Choisir une image");
        ImagePath = fileChooser.showOpenDialog(null).getAbsolutePath();
        ImagePreviw.setImage(new javafx.scene.image.Image("file:"+ImagePath));
    }
}

package GUI;

import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import services.ServiceUser;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserModifController implements Initializable {
    @FXML
    private Button BtnModifier;
    @FXML
    private Button button_annuler;
    @FXML
    private TextField TF_nom;
    @FXML
    private TextField tfPrenom;
    @FXML
    private TextField TF_email;
    @FXML
    private Button Add_image_button;
    @FXML
    private ImageView ImagePreviw;

    private User user ;
    private String ImagePath;

    private Alert alert = new Alert(Alert.AlertType.NONE);

    private ServiceUser serviceUser = new ServiceUser();
    @FXML
    private TextField TfUsername;

    @FXML
    public void On_annuler_clicked(ActionEvent actionEvent) throws IOException {
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setUser(User u )
    {
        this.user = u ;
        TF_nom.setText(u.getName());
        tfPrenom.setText(u.getLastName());
        TF_email.setText(u.getEmail());
        TfUsername.setText(u.getUserName());
        ImagePath = user.getImagePath();
        System.out.println(ImagePath);
        ImagePreviw.setImage(new javafx.scene.image.Image("file:"+ImagePath));
    }

    @FXML
    public void OnModifierClicked(ActionEvent actionEvent) throws IOException {
        // check for empty fields
        if(TF_nom.getText().isEmpty() || tfPrenom.getText().isEmpty() || TF_email.getText().isEmpty() || TfUsername.getText().isEmpty())
        {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("Veuillez remplir tous les champs");
            alert.show();
            return;
        }
        // check for name and last name length > 4
        if(TF_nom.getText().length() < 4 || tfPrenom.getText().length() < 4)
        {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("Nom et prénom doivent contenir au moins 4 caractères");
            alert.show();
            return;
        }
        user.setName(TF_nom.getText());
        user.setLastName(tfPrenom.getText());
        user.setEmail(TF_email.getText());
        user.setUserName(TfUsername.getText());
        user.setImagePath(ImagePath);
        serviceUser.modifier(user);
        alert.setAlertType(Alert.AlertType.INFORMATION);
        alert.setContentText("User Modifié !");
        alert.show();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GestionAdminUI.fxml"));
        this.button_annuler.getScene().setRoot(loader.load());
    }
}

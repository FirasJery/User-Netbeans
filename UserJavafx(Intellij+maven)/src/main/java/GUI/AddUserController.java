
package GUI;

import com.github.sarxos.webcam.Webcam;
import entities.User;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import services.ServiceUser;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class AddUserController implements Initializable {
    @FXML
    private TextField TF_nom;
    @FXML
    private TextField TF_email;
    @FXML
    private Button Button_inscrire;
    @FXML
    private Button button_annuler;
    @FXML
    private Button Add_image_button;
    @FXML
    private Label imageLabel;
    @FXML
    private ImageView ImagePreviw;
    @FXML
    private Button btnRetour;
    @FXML
    private TextField TFPrenom;
    @FXML
    private TextField TfUsername;
    @FXML
    private PasswordField tfPassword;
    @FXML
    private PasswordField tfConfirm;
    @FXML
    private Button BtnOpenCamera;
    @FXML
    private Button BtnPrendrePhoto;


    private String ImagePath;
    private ServiceUser serviceUser = new ServiceUser();
    private Alert alert = new Alert(Alert.AlertType.NONE);
    private int code ;

    private Webcam webcam = null;
    Thread webcamThread;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialize the webcam
        List<Webcam> webcams = Webcam.getWebcams();
        // Print information about each webcam
        for (Webcam webcam : webcams) {
          if(!webcam.getName().isEmpty())
          {
              this.webcam = webcam ;
          }
        }
        // If no webcams are found
        if (webcams.size() == 0) {
            System.out.println("No webcams found.");
        }


    }

    @FXML
    private void ON_inscrire_clicked(ActionEvent event) throws IOException {
        String nom = TF_nom.getText();
        String prenom = TFPrenom.getText();
        String email = TF_email.getText();
        String username = TfUsername.getText();
        String password = tfPassword.getText();
        String confirm = tfConfirm.getText();
        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("Veuillez remplir tous les champs");
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
        else if (!password.equals(confirm)) {
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
            User u = new User(nom , prenom , username ,email , password  , "User" ,ImagePath);
            serviceUser.ajouter(u);
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setContentText("User ajouté avec succès");
            alert.show();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LoginUI.fxml"));
            this.button_annuler.getScene().setRoot(loader.load());
        }

    }

    @FXML
    private void on_annuler_clicked(ActionEvent event) {
        TFPrenom.clear();
        TfUsername.clear();
        tfPassword.clear();
        tfConfirm.clear();
        TF_nom.clear();
        TF_email.clear();
        ImagePath = "";
        ImagePreviw.setImage(null);


    }

    @FXML

    private void add_image_action(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        fileChooser.setTitle("Choisir une image");
        ImagePath = fileChooser.showOpenDialog(null).getAbsolutePath();
        ImagePreviw.setImage(new javafx.scene.image.Image("file:"+ImagePath));


    }

    @FXML
    private void btnRetourAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/LoginUI.fxml"));
        this.button_annuler.getScene().setRoot(loader.load());


    }

    @FXML
    public void OnOpenCameraClicked(ActionEvent actionEvent) {
        BtnOpenCamera.setVisible(false);
        BtnPrendrePhoto.setVisible(true);

        if (!webcam.isOpen()) {
            webcam.close();
            webcam.setViewSize(new Dimension(640, 480));
            webcam.open();
        }
        //ImagePreviw.setImage(SwingFXUtils.toFXImage(webcam.getImage(), null));
        webcamThread = new Thread(() -> {
            while (webcam.isOpen()) {
                try {
                    WritableImage image = SwingFXUtils.toFXImage(webcam.getImage(), null);
                    Platform.runLater(() -> ImagePreviw.setImage(image));
                    Thread.sleep(50); // update the image view every 50 milliseconds
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        });
        webcamThread.setDaemon(true);
        webcamThread.start();

    }

    @FXML
    public void OnPrendreClicked(ActionEvent actionEvent) {
        if (webcam.isOpen()) {
            BufferedImage image = webcam.getImage();
            ImagePreviw.setImage(SwingFXUtils.toFXImage(image, null));
            webcam.close();
            saveImage(image);
            BtnPrendrePhoto.setVisible(false);
            BtnOpenCamera.setVisible(true);
        }
    }

    private void saveImage(BufferedImage image) {
        try {
            code = generateImageName();
            ImagePath = "src/main/resources/images/" + code + ".png";

            File outputfile = new File(ImagePath);
            imageLabel.setText(ImagePath);
            ImageIO.write(image, "png", outputfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static int generateImageName() {
        Random random = new Random();
        return 100000 + random.nextInt(900000);
    }
}

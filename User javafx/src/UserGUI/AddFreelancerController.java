/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserGUI;

import entities.Freelancer;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import services.ServiceUser;
import GUI.ControleSaisieTextFields;
import static UserGUI.AddEntrepriseUIController.copyFileToDirectory;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.FileChooser;
import com.github.sarxos.webcam.Webcam;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javafx.scene.media.MediaView;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import services.*;
import org.mindrot.jbcrypt.BCrypt;

/**
 * FXML Controller class
 *
 * @author Firas
 */
public class AddFreelancerController implements Initializable {

    private ControleSaisieTextFields cs = new ControleSaisieTextFields();

    @FXML
    private TextField TF_nom;
    @FXML
    private TextField TF_email;
    @FXML
    private TextField TF_mdp;
    @FXML
    private TextField TF_bio;
    @FXML
    private TextField Tf_experience;
    @FXML
    private TextField TF_education;
    @FXML
    private Button Button_inscrire;
    @FXML
    private Button button_annuler;

    private String ImagePath;

    @FXML
    private Button Add_image_button;
    @FXML
    private Label imageLabel;
    @FXML
    private Label labelNomError;

    @FXML
    private Label labelMdpError;
    @FXML
    private Label labelBioError;
    @FXML
    private Label labelExpError;
    @FXML
    private Label labelEduError;
    @FXML
    private ImageView ImagePreviw;
    @FXML
    private Label labelEmailError;
    @FXML
    private Button btnRetour;
    @FXML
    private TextField tfprenom;
    @FXML
    private Label labelerrorprenom;
    @FXML
    private TextField tfusername;
    @FXML
    private Label labelerrorusername;
    @FXML
    private PasswordField tfconfirm;
    @FXML
    private Label labelerrorconfirm;
    @FXML
    private Button btnSnap;
    private Webcam webcam = null;
    Thread webcamThread;
    @FXML
    private MediaView MediaView;
    private int code ; 

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // TODO 
        // Initialize the webcam
        webcam = Webcam.getDefault();

        //////////////////////////////////////////////////
        ImagePath = "resources/profile.jpg";
        ImagePreviw.setImage(new Image(new File(ImagePath).toURI().toString()));
        TF_nom.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                String text = TF_nom.getText();
                if (!cs.checkOnlyString(text)) {
                    labelNomError.setText("nom n'est pas valide ! ");
                } else {
                    labelNomError.setText("");
                }
            }
        });
        tfprenom.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                String text = tfprenom.getText();
                if (!cs.checkOnlyString(text)) {
                    labelerrorprenom.setText("prenom n'est pas valide ! ");
                } else {
                    labelerrorprenom.setText("");
                }
            }
        });
        tfusername.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                String text = tfusername.getText();
                if (!cs.checkOnlyString(text)) {
                    labelerrorusername.setText("username n'est pas valide ! ");
                } else {
                    labelerrorusername.setText("");
                }
            }
        });

        TF_email.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                String text = TF_email.getText();
                if (!cs.isEmailAdress(text)) {
                    labelEmailError.setText("Email n'est pas valide ! ");
                } else {
                    labelEmailError.setText("");
                }
            }
        });
        TF_mdp.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                String text = TF_mdp.getText();
                if (text.length() <= 8) {
                    labelMdpError.setText("mot de pasee trop court  ! ");
                } else {
                    labelMdpError.setText("");
                }
            }
        });
        tfconfirm.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                String text = tfconfirm.getText();
                if (text.length() <= 8) {
                    labelerrorconfirm.setText("mot de pasee trop court  ! ");
                } else {
                    labelerrorconfirm.setText("");
                }
            }
        });
        TF_bio.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                String text = TF_bio.getText();
                if (!cs.checkOnlyString(text)) {
                    labelBioError.setText("bio ne doit pas commencer par un chiffre! ");
                } else {
                    labelBioError.setText("");
                }
            }
        });
        TF_education.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                String text = TF_education.getText();
                if (!cs.checkOnlyString(text)) {
                    labelEduError.setText("Education ne doit pas commencer par un chiffre! ");

                } else {
                    labelEduError.setText("");
                }
            }
        });
        Tf_experience.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                String text = Tf_experience.getText();
                if (!cs.checkOnlyString(text)) {
                    labelExpError.setText("Experience ne doit pas commencer par un chiffre! ");

                } else {
                    labelExpError.setText("");
                }

            }
        });

    }

    @FXML
    private void ON_inscrire_clicked(ActionEvent event) throws IOException, Exception {
        ServiceUser sa = new ServiceUser();
        Freelancer f = new Freelancer();

        if (cs.isEmpty(tfprenom.getText()) || cs.isEmpty(tfusername.getText()) || cs.isEmpty(TF_nom.getText()) || cs.isEmpty(TF_email.getText()) || cs.isEmpty(TF_mdp.getText()) || cs.isEmpty(TF_bio.getText()) || cs.isEmpty(TF_education.getText()) || cs.isEmpty(Tf_experience.getText())) {
            Alert A = new Alert(Alert.AlertType.ERROR);
            A.setContentText("Veuillez remplir tout les champs ! ");
            A.showAndWait();
        } else if (!cs.checkOnlyString(TF_nom.getText())) {
            Alert A = new Alert(Alert.AlertType.ERROR);
            A.setContentText("nom n'est pas valide !  ");
            A.showAndWait();

        } else if (!cs.isEmailAdress(TF_email.getText())) {
            Alert A = new Alert(Alert.AlertType.ERROR);
            A.setContentText("Email n'est pas valide ! ");
            A.showAndWait();

        } else if (TF_mdp.getText().length() <= 8) {

            Alert A = new Alert(Alert.AlertType.ERROR);
            A.setContentText("mot de pasee trop court  ! ");
            A.showAndWait();
        } else if (!cs.checkOnlyString(TF_education.getText())) {
            Alert A = new Alert(Alert.AlertType.ERROR);
            A.setContentText("Education n'est pas valide !  ");
            A.showAndWait();

        } else if (!cs.checkOnlyString(Tf_experience.getText())) {
            Alert A = new Alert(Alert.AlertType.ERROR);
            A.setContentText("Experience n'est pas valide !  ");
            A.showAndWait();

        } else if (!cs.checkOnlyString(TF_bio.getText())) {
            Alert A = new Alert(Alert.AlertType.ERROR);
            A.setContentText("Bio n'est pas valide !  ");
            A.showAndWait();

        } else if (sa.ChercherMail(TF_email.getText()) == 1) {
            Alert A = new Alert(Alert.AlertType.ERROR);
            A.setContentText("L'adresse mail est deja utilisée !  ");
            A.showAndWait();
        } else if (!(TF_mdp.getText()).equals(tfconfirm.getText())) {
            Alert A = new Alert(Alert.AlertType.ERROR);
            A.setContentText("mdps non conforme  ");
            A.showAndWait();
        } else {
            String hashed = BCrypt.hashpw(TF_mdp.getText(), BCrypt.gensalt(13));
           // String pwd = PasswordEncryption.encrypt(TF_mdp.getText());
            f.setName(TF_nom.getText());
            f.setLastName(tfprenom.getText());
            f.setUserName(tfusername.getText());
            f.setEmail(TF_email.getText());
            f.setPassword(hashed);
            f.setBio(TF_bio.getText());
            f.setEducation(TF_education.getText());
            f.setExperience(Tf_experience.getText());
            f.setTotal_jobs(0);
            f.setRating(0);
            f.setImagePath(ImagePath);
            sa.ajouter(f);

            ///////////////////////////////////////////////// switch
            Parent page1 = FXMLLoader.load(getClass().getResource("/UserGUI/LoginUI.fxml"));

            Scene scene = new Scene(page1);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(scene);

            stage.show();

        }
    }

    @FXML
    private void on_annuler_clicked(ActionEvent event) {
        TF_bio.clear();
        TF_education.clear();
        Tf_experience.clear();
        TF_mdp.clear();
        TF_nom.clear();
        tfprenom.clear();
        tfusername.clear();
        tfconfirm.clear();
        TF_email.clear();
        ImagePath = "resources/profile.jpg";
        ImagePreviw.setImage(new Image(new File(ImagePath).toURI().toString()));

    }

    @FXML

    private void add_image_action(ActionEvent event) throws IOException {
        FileChooser fc = new FileChooser();
        File defaultDir = new File("resources");
        fc.setInitialDirectory(defaultDir);
        File SelectedFile = fc.showOpenDialog(null);

        if (SelectedFile != null) {
            copyFileToDirectory(SelectedFile, defaultDir);

            ImagePath = defaultDir.getName() + "/" + SelectedFile.getName();
            imageLabel.setText(ImagePath);
            ImagePreviw.setImage(new Image(new File(ImagePath).toURI().toString()));
        } else {

            ImagePath = "resources/profile.jpg";
            imageLabel.setText(ImagePath);
            ImagePreviw.setImage(new Image(new File(ImagePath).toURI().toString()));

        }

    }

    @FXML
    private void btnRetourAction(ActionEvent event) throws IOException {
        if (webcam.isOpen()) {
            webcam.close();
        }
        Parent page1 = FXMLLoader.load(getClass().getResource("/UserGUI/LoginUI.fxml"));

        Scene scene = new Scene(page1);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(scene);

        stage.show();
    }

    @FXML
    private void TakePhotoAction(ActionEvent event) {
      
       
        if (!webcam.isOpen()) {
            webcam.close();
            webcam.setViewSize(new Dimension(640, 480));
            webcam.open();
        }
        //ImagePreviw.setImage(SwingFXUtils.toFXImage(webcam.getImage(), null));
        webcamThread = new Thread(() -> {
            while (webcam.isOpen()) {
                try {
                    Image image = SwingFXUtils.toFXImage(webcam.getImage(), null);
                    Platform.runLater(() -> {
                        ImagePreviw.setImage(image);
                    });
                    Thread.sleep(50); // update the image view every 50 milliseconds
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        });
        webcamThread.setDaemon(true);
        webcamThread.start();
        btnSnap.setVisible(true);
       
    }

    @FXML
    private void btnSnapAction(ActionEvent event) {
        if (webcam.isOpen()) {
            BufferedImage image = webcam.getImage();
            ImagePreviw.setImage(SwingFXUtils.toFXImage(image, null));
            webcam.close();
            saveImage(image);
            btnSnap.setVisible(false);
        }

    }

    private void saveImage(BufferedImage image) {
        try {
            code = ForgotPasswordController.generateVerificationCode();
            ImagePath = "resources/" + code + ".png";
            File outputfile = new File(ImagePath);
            imageLabel.setText(ImagePath);
            ImageIO.write(image, "png", outputfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

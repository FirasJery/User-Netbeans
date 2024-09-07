/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserGUI;

import GUI.ControleSaisieTextFields;
import GUI.ControleSaisieTextFields;
import entities.Freelancer;
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
import services.ServiceUser;

/**
 * FXML Controller class
 *
 * @author Firas
 */
public class ModifierProfilFreelancerController implements Initializable {
    
    @FXML
    private Button btnRetour;
    @FXML
    private TextField TF_nom;
    @FXML
    private Label labelNomError;
    @FXML
    private TextField tfprenom;
    @FXML
    private Label labelerrorprenom;
    @FXML
    private TextField tfusername;
    @FXML
    private Label labelerrorusername;
    @FXML
    private TextField TF_email;
    @FXML
    private Label labelEmailError;
    @FXML
    private PasswordField TF_mdp;
    @FXML
    private Label labelMdpError;
    @FXML
    private PasswordField tfconfirm;
    @FXML
    private Label labelerrorconfirm;
    @FXML
    private TextField TF_bio;
    @FXML
    private Label labelBioError;
    @FXML
    private TextField Tf_experience;
    @FXML
    private Label labelExpError;
    @FXML
    private TextField TF_education;
    @FXML
    private Label labelEduError;
    @FXML
    private Button button_annuler;
    @FXML
    private Button Add_image_button;
    @FXML
    private Label imageLabel;
    @FXML
    private ImageView ImagePreviw;
    @FXML
    private Button btnmodif;
    private ControleSaisieTextFields cs = new ControleSaisieTextFields();
    private String ImagePath;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
         ServiceUser sa = new ServiceUser();
        int id_free= SessionManager.getInstance().getCurrentUser().getId();
          Freelancer fm = (Freelancer) sa.getOneById(id_free);
          
        TF_nom.setText(fm.getName());
        tfprenom.setText(fm.getLastName());
        tfusername.setText(fm.getUserName());
        TF_email.setText(fm.getEmail());
        TF_mdp.setText(fm.getPassword());
        tfconfirm.setText(fm.getPassword());
        Tf_experience.setText(fm.getExperience());
        TF_education.setText(fm.getEducation());
        TF_bio.setText(fm.getBio());
        ImagePath = "resources/profile.jpg";
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
    private void btnRetourAction(ActionEvent event) {
        try {
            
            Parent page1 = FXMLLoader.load(getClass().getResource("/GUI/FreelancerProfileUI.fxml"));
            
            Scene scene = new Scene(page1);
            
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            
            stage.setScene(scene);
            
            stage.show();
            
        } catch (IOException ex) {
            
            System.out.println(ex.getMessage());
            
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
        ImagePath = "";
    }
    
    @FXML
    private void add_image_action(ActionEvent event) {
        FileChooser fc = new FileChooser();
        File defaultDir = new File("resources");
        fc.setInitialDirectory(defaultDir);
        File SelectedFile = fc.showOpenDialog(null);
        if (SelectedFile != null) {
            
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
    private void btnModifAction(ActionEvent event) {
        ServiceUser sa = new ServiceUser();
        Freelancer f = new Freelancer();
        Freelancer fm = (Freelancer) SessionManager.getInstance().getCurrentUser();
      
        
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
            
        } else if (sa.ChercherMail(TF_email.getText()) == 1 && !TF_email.getText().equals(fm.getEmail())) {
            Alert A = new Alert(Alert.AlertType.ERROR);
            A.setContentText("L'adresse mail est deja utilisée !  ");
            A.showAndWait();
        } else if (!(TF_mdp.getText()).equals(tfconfirm.getText())) {
            Alert A = new Alert(Alert.AlertType.ERROR);
            A.setContentText("mdps non conforme  ");
            A.showAndWait();
        } else {
                        String hashed = BCrypt.hashpw(TF_mdp.getText(), BCrypt.gensalt(13));

            try {
                f.setId(fm.getId());
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
                sa.modifier(f);

                ///////////////////////////////////////////////// switch
                Parent page1 = FXMLLoader.load(getClass().getResource("/GUI/FreelancerProfileUI.fxml"));
                
                Scene scene = new Scene(page1);
                
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                
                stage.setScene(scene);
                
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(ModifierProfilFreelancerController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
}

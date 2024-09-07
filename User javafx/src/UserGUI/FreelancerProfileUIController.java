/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserGUI;

import GUI.ControleSaisieTextFields;
import static UserGUI.AddEntrepriseUIController.copyFileToDirectory;
import entities.Entreprise;
import entities.Freelancer;
import entities.SessionManager;
import entities.Utilisateur;
import java.io.File;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
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
public class FreelancerProfileUIController implements Initializable {

    @FXML
    private VBox VboxImage;
    @FXML
    private ImageView ImageViewEnt;
    @FXML
    private VBox VboxImageModif;
    @FXML
    private ImageView ImageViewEntModif;
    @FXML
    private HBox HboxNom;
    @FXML
    private Label label_nom;
    @FXML
    private HBox HboxNomModifier;
    @FXML
    private TextField tfNomModif;
    @FXML
    private HBox HboxAdress;
    @FXML
    private Label label_adresse;
    @FXML
    private HBox HboxAdressModif;
    @FXML
    private TextField tfAdressModif;
    @FXML
    private HBox HboxDomaine;
    @FXML
    private Label label_domaine;
    @FXML
    private HBox HboxDomaineModif;
    @FXML
    private TextField tfDomaineModif;
    @FXML
    private HBox HboxInfo;
    @FXML
    private Label label_info;
    @FXML
    private HBox HboxInfoModif;
    @FXML
    private TextArea tfInfoModif;
    @FXML
    private HBox Hboxnbe;
    @FXML
    private Label label_nbe;
    @FXML
    private HBox HboxnbeModif;
    @FXML
    private TextField tfNbeModif;

    /////////////////////////////////////////////////
    private ServiceUser su = new ServiceUser();
    private SessionManager s = SessionManager.getInstance();
    private ControleSaisieTextFields cs = new ControleSaisieTextFields();
    private Alert A = new Alert(Alert.AlertType.WARNING);
    private String ImagePath;
    @FXML
    private VBox VboxNotAdvanced;
    @FXML
    private VBox VboxAdvanced;
    @FXML
    private HBox HboxEmail;
    @FXML
    private Label label_Email;
    @FXML
    private HBox HboxEmailModifier;
    @FXML
    private TextField tfEmailModif;
    @FXML
    private HBox HboxPassword;
    @FXML
    private Label labelPassword;
    @FXML
    private HBox HboxPasswordModif;
    @FXML
    private PasswordField pfModif;
    @FXML
    private PasswordField pfconfirm;
    @FXML
    private Button btntRetourAdvanced;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // TODO
        afficherEntreprise();
        ImagePath = "vide";

    }
     
    private String transformToStars(String input) {
    StringBuilder output = new StringBuilder();
    for (int i = 0; i < input.length(); i++) {
        output.append("*");
    }
    return output.toString();
}
    private void afficherEntreprise() {
        ServiceUser sa = new ServiceUser();
        Freelancer f = (Freelancer) sa.getOneById(SessionManager.getInstance().getCurrentUser().getId());
        label_nom.setText("Nom :    " + f.getName());
        label_adresse.setText("Prenom :    " + f.getLastName());
        label_domaine.setText("Experience :     " + f.getExperience());
        label_info.setText("Education    " + f.getEducation());
        label_nbe.setText("Bio :      " + f.getBio() + "");
        String mdp = transformToStars(f.getPassword());
        labelPassword.setText("Mot de passe :       " + mdp);
        label_Email.setText("Email :        "+f.getEmail());
        System.out.println(f.getImagePath());
        if (f.getImagePath() != null) {
            File imagef = new File(f.getImagePath());
            Image image = new Image(imagef.toURI().toString());

            double minWidth = Math.min(image.getWidth(), image.getHeight());

            WritableImage resizedImage = new WritableImage((int) minWidth, (int) minWidth);
            PixelWriter pixelWriter = resizedImage.getPixelWriter();

            for (int x = 0; x < minWidth; x++) {
                for (int y = 0; y < minWidth; y++) {
                    pixelWriter.setArgb(x, y, image.getPixelReader().getArgb(x, y));
                }
            }

            ImageViewEnt.setImage(resizedImage);
            ImageViewEnt.setFitWidth(300);
            ImageViewEnt.setFitHeight(300);
            ImageViewEnt.setPreserveRatio(true);
            Circle clip = new Circle(300 / 2, 300 / 2, 300 / 2);
            ImageViewEnt.setClip(clip);
        }
    }

    @FXML
    private void HyperModifierNomAction(ActionEvent event) {
        HboxNom.setVisible(false);
        HboxNomModifier.setVisible(true);
    }

    @FXML
    private void btnModifNomAction(ActionEvent event) {

        if (!tfNomModif.getText().isEmpty() && cs.checkOnlyString(tfNomModif.getText())) {
            Utilisateur u = s.getCurrentUser();
            u.setName(tfNomModif.getText()); // 
            su.modifierNom(u); // 
            afficherEntreprise();
            HboxNom.setVisible(true);
            HboxNomModifier.setVisible(false);
        } else {
            A.setContentText("Nom non valide ! ");
            A.show();
        }

    }

    @FXML
    private void btnAnuulerNomAcrion(ActionEvent event) {
        HboxNom.setVisible(true);
        HboxNomModifier.setVisible(false);
    }
///////////////////////////////////////////////////////////////////// prenom 

    @FXML
    private void HperModifierAdresseAction(ActionEvent event) {
        HboxAdress.setVisible(false);
        HboxAdressModif.setVisible(true);
    }

    @FXML
    private void btnModifAdressAction(ActionEvent event) {
        if (!tfAdressModif.getText().isEmpty() && cs.checkOnlyString(tfAdressModif.getText())) {
            Freelancer u = (Freelancer) s.getCurrentUser();
            u.setLastName(tfAdressModif.getText());
            su.modifierPrenom(u);
            afficherEntreprise();
            HboxAdress.setVisible(true);
            HboxAdressModif.setVisible(false);
        } else {
            A.setContentText("Prenom non valide ! ");
            A.show();
        }
    }

    @FXML
    private void btnAnnulerAdressModifAction(ActionEvent event) {
        HboxAdress.setVisible(true);
        HboxAdressModif.setVisible(false);
    }

    /////////////////////////////////////////////////////////////////// modif experience
    @FXML
    private void HyperModifierDomaineAction(ActionEvent event) {
        HboxDomaine.setVisible(false);
        HboxDomaineModif.setVisible(true);
    }

    @FXML
    private void btnDomaineModifAction(ActionEvent event) {
        if (!tfDomaineModif.getText().isEmpty() && cs.checkOnlyString(tfDomaineModif.getText())) {
            Freelancer u = (Freelancer) s.getCurrentUser();
            u.setExperience(tfDomaineModif.getText());
            su.modifierExp(u);
            afficherEntreprise();
            HboxDomaine.setVisible(true);
            HboxDomaineModif.setVisible(false);
        } else {
            A.setContentText("experience non valide ! ");
            A.show();
        }
    }

    @FXML
    private void btnDomaineAnnulerAction(ActionEvent event) {
        HboxDomaine.setVisible(true);
        HboxDomaineModif.setVisible(false);
    }

///////////////////////////////////////////////////////// modif education
    @FXML
    private void HyperModifierInfoAction(ActionEvent event) {
        HboxInfo.setVisible(false);
        HboxInfoModif.setVisible(true);
    }

    @FXML
    private void btnInfoModifAction(ActionEvent event) {
        if (!tfInfoModif.getText().isEmpty() && cs.checkOnlyString(tfInfoModif.getText())) {
            Freelancer u = (Freelancer) s.getCurrentUser();
            u.setEducation(tfInfoModif.getText());
            su.modifierEdu(u);
            afficherEntreprise();
            HboxInfo.setVisible(true);
            HboxInfoModif.setVisible(false);
        } else {
            A.setContentText("education non valide ! ");
            A.show();
        }
    }

    @FXML
    private void btnInfoAnnulerAction(ActionEvent event) {
        HboxInfo.setVisible(true);
        HboxInfoModif.setVisible(false);
    }

    //////////////////////////////////////////////////// modif bio
    @FXML
    private void HyperModifierNbeAction(ActionEvent event) {
        Hboxnbe.setVisible(false);
        HboxnbeModif.setVisible(true);
    }

    @FXML
    private void btnNbeModifAction(ActionEvent event) {
        if (!tfNbeModif.getText().isEmpty() && cs.checkOnlyString(tfNbeModif.getText())) {
            Freelancer u = (Freelancer) s.getCurrentUser();
            u.setBio(tfNbeModif.getText());
            su.modifierBio(u);
            afficherEntreprise();
            Hboxnbe.setVisible(true);
            HboxnbeModif.setVisible(false);
        } else {
            A.setContentText("Bio non valide ! ");
            A.show();
        }
    }

    @FXML
    private void btnNbeAnnulerAction(ActionEvent event) {
        Hboxnbe.setVisible(true);
        HboxnbeModif.setVisible(false);
    }

    ///////////////////////////////////////////////////////////////// modif image
    @FXML
    private void HyperModifierPhotoAction(ActionEvent event) {
        VboxImage.setVisible(false);
        VboxImageModif.setVisible(true);
    }

    @FXML
    private void btnChoisirImage(ActionEvent event) throws IOException {
        FileChooser fc = new FileChooser();
        File defaultDir = new File("resources");
        fc.setInitialDirectory(defaultDir);
        File SelectedFile = fc.showOpenDialog(null);
        copyFileToDirectory(SelectedFile, defaultDir);

        if (SelectedFile != null) {

            ImagePath = defaultDir.getName() + "/" + SelectedFile.getName();

            ImageViewEntModif.setImage(new Image(new File(ImagePath).toURI().toString()));
            ImageViewEntModif.setPreserveRatio(true);
            ImageViewEntModif.setFitWidth(270);
            ImageViewEntModif.setFitHeight(270);
        } else {

            ImagePath = "vide";

        }
    }

    @FXML
    private void btnModifierImage(ActionEvent event) {
        if (ImagePath.equals("vide")) {
            A.setContentText("pas d'image selectionée !");
            A.show();
        } else {
            Freelancer u = (Freelancer) s.getCurrentUser();
            u.setImagePath(ImagePath);
            su.modifierImage(u);
            afficherEntreprise();
            VboxImage.setVisible(true);
            VboxImageModif.setVisible(false);
        }
    }

    @FXML
    private void btnAnuulerImage(ActionEvent event) {
        VboxImage.setVisible(true);
        VboxImageModif.setVisible(false);
    }
/////////////////////////////////////////////////////////////////// email

    @FXML
    private void HyperModifierEmailAction(ActionEvent event) {
        HboxEmail.setVisible(false);
        HboxEmailModifier.setVisible(true);
    }

    @FXML
    private void btnModifEmailAction(ActionEvent event) {
        if (tfEmailModif.getText().isEmpty() || !cs.isEmailAdress(tfEmailModif.getText())) {
            A.setContentText("Email non valide ! ");
            A.show();
        } else if (su.ChercherMail(tfEmailModif.getText()) == 1) {
            A.setContentText("Email Existant ! ");
            A.show();
        } else {

            Freelancer u = (Freelancer) s.getCurrentUser();
            u.setEmail(tfEmailModif.getText());
            su.modifierEmail(u);
            afficherEntreprise();
            HboxEmail.setVisible(true);
            HboxEmailModifier.setVisible(false);
        }
    }

    @FXML
    private void btnAnuulerEmailAcrion(ActionEvent event) {
        HboxEmail.setVisible(true);
        HboxEmailModifier.setVisible(false);
    }
//////////////////////////////////////////////////////////////////////////////// mdp 

    @FXML
    private void HperModifierMdpAction(ActionEvent event) {
        HboxPassword.setVisible(false);
        HboxPasswordModif.setVisible(true);
    }

    @FXML
    private void btnModifPasswordAction(ActionEvent event) throws Exception {

        if (pfModif.getText().isEmpty() || pfconfirm.getText().isEmpty() || pfconfirm.getText().length() < 8) {
            A.setContentText("mot de passe non valide 8 charachteres requis ! ");
            A.show();
        } else if (!pfModif.getText().equals(pfconfirm.getText())) {
            A.setContentText("mots de passes non conformes ! ");
            A.show();
        } else {

            Freelancer u = (Freelancer) s.getCurrentUser();
            //String crypt = PasswordEncryption.encrypt(pfModif.getText());
            String hashed = BCrypt.hashpw(pfModif.getText(), BCrypt.gensalt(13));
            u.setPassword(hashed);
            su.modifierPassword(u);
            afficherEntreprise();
            HboxPassword.setVisible(true);
            HboxPasswordModif.setVisible(false);
        }
    }

    @FXML
    private void btnAnnulerPasswordModifAction(ActionEvent event) {
        HboxPassword.setVisible(true);
        HboxPasswordModif.setVisible(false);
    }

    @FXML
    private void btnAdvancedAction(ActionEvent event) {
        VboxNotAdvanced.setVisible(false);
        VboxAdvanced.setVisible(true);
        btntRetourAdvanced.setVisible(true);

    }

    @FXML
    private void bntRetourAdvancedAction(ActionEvent event) {
        VboxNotAdvanced.setVisible(true);
        VboxAdvanced.setVisible(false);
        btntRetourAdvanced.setVisible(false);
    }

}

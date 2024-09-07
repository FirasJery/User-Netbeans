/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserGUI;

import GUI.ControleSaisieTextFields;
import static UserGUI.AddEntrepriseUIController.copyFileToDirectory;
import entities.Entreprise;
import entities.SessionManager;
import entities.Utilisateur;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import services.ServiceUser;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

/**
 * FXML Controller class
 *
 * @author Firas
 */
public class EntrepriseProfileUIController implements Initializable {

    @FXML
    private Label label_info;
    @FXML
    private Label label_adresse;
    @FXML
    private Label label_domaine;
    @FXML
    private Label label_nbe;
    @FXML
    private ImageView ImageViewEnt;
    @FXML
    private Label label_nom;
    @FXML
    private HBox HboxNom;
    @FXML
    private HBox HboxNomModifier;
    @FXML
    private TextField tfNomModif;
    @FXML
    private HBox HboxAdress;
    @FXML
    private HBox HboxAdressModif;
    @FXML
    private TextField tfAdressModif;
    @FXML
    private HBox HboxDomaine;
    @FXML
    private HBox HboxDomaineModif;
    @FXML
    private VBox VboxImage;
    @FXML
    private VBox VboxImageModif;
    @FXML
    private ImageView ImageViewEntModif;
    @FXML
    private HBox HboxInfo;
    @FXML
    private HBox HboxInfoModif;
    @FXML
    private TextArea tfInfoModif;
    @FXML
    private HBox Hboxnbe;
    @FXML
    private HBox HboxnbeModif;
    @FXML
    private TextField tfNbeModif;
    @FXML
    private TextField tfDomaineModif;

    //////////////////////// my variables
    private ServiceUser su = new ServiceUser();
    private SessionManager s = SessionManager.getInstance();
    private ControleSaisieTextFields cs = new ControleSaisieTextFields();
    private Alert A = new Alert(Alert.AlertType.WARNING);
    private String ImagePath;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        afficherEntreprise();
        ImagePath = "vide";
    }

    private void afficherEntreprise() {
        ServiceUser sa = new ServiceUser();
        Entreprise f = (Entreprise) sa.getOneById(SessionManager.getInstance().getCurrentUser().getId());
        label_nom.setText("Entreprise :        "+f.getName().toUpperCase());
        label_info.setText("Information :        "+f.getInfo());
        label_adresse.setText("Adresse :        "+f.getLocation());
        label_domaine.setText("Domaine :        "+f.getDomaine());
        Integer a = f.getNumberOfEmployees();
        label_nbe.setText("Nombre Employes :        "+a.toString());
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
            //ImageViewEnt.setFitWidth(300);
            // ImageViewEnt.setFitHeight(300);
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
            u.setName(tfNomModif.getText());
            su.modifierNom(u);
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
///////////////////////////////////////////////////////////////////// adresse 

    @FXML
    private void HperModifierAdresseAction(ActionEvent event) {
        HboxAdress.setVisible(false);
        HboxAdressModif.setVisible(true);
    }

    @FXML
    private void btnModifAdressAction(ActionEvent event) {
        if (!tfAdressModif.getText().isEmpty() && cs.checkOnlyString(tfAdressModif.getText())) {
            Entreprise u = (Entreprise) s.getCurrentUser();
            u.setLocation(tfAdressModif.getText());
            su.modifierAdresse(u);
            afficherEntreprise();
            HboxAdress.setVisible(true);
            HboxAdressModif.setVisible(false);
        } else {
            A.setContentText("Adresse non valide ! ");
            A.show();
        }
    }

    @FXML
    private void btnAnnulerAdressModifAction(ActionEvent event) {
        HboxAdress.setVisible(true);
        HboxAdressModif.setVisible(false);
    }

    /////////////////////////////////////////////////////////////////// modif domaine
    @FXML
    private void HyperModifierDomaineAction(ActionEvent event) {
        HboxDomaine.setVisible(false);
        HboxDomaineModif.setVisible(true);
    }

    @FXML
    private void btnDomaineModifAction(ActionEvent event) {
        if (!tfDomaineModif.getText().isEmpty() && cs.checkOnlyString(tfDomaineModif.getText())) {
            Entreprise u = (Entreprise) s.getCurrentUser();
            u.setDomaine(tfDomaineModif.getText());
            su.modifierDomaine(u);
            afficherEntreprise();
            HboxDomaine.setVisible(true);
            HboxDomaineModif.setVisible(false);
        } else {
            A.setContentText("Domaine non valide ! ");
            A.show();
        }
    }

    @FXML
    private void btnDomaineAnnulerAction(ActionEvent event) {
        HboxDomaine.setVisible(true);
        HboxDomaineModif.setVisible(false);
    }

///////////////////////////////////////////////////////// modif info 
    @FXML
    private void HyperModifierInfoAction(ActionEvent event) {
        HboxInfo.setVisible(false);
        HboxInfoModif.setVisible(true);
    }

    @FXML
    private void btnInfoModifAction(ActionEvent event) {
        if (!tfInfoModif.getText().isEmpty() && cs.checkOnlyString(tfInfoModif.getText())) {
            Entreprise u = (Entreprise) s.getCurrentUser();
            u.setInfo(tfInfoModif.getText());
            su.modifierinfo(u);
            afficherEntreprise();
            HboxInfo.setVisible(true);
            HboxInfoModif.setVisible(false);
        } else {
            A.setContentText("Info non valide ! ");
            A.show();
        }
    }

    @FXML
    private void btnInfoAnnulerAction(ActionEvent event) {
        HboxInfo.setVisible(true);
        HboxInfoModif.setVisible(false);
    }

    //////////////////////////////////////////////////// modif nbe 
    @FXML
    private void HyperModifierNbeAction(ActionEvent event) {
        Hboxnbe.setVisible(false);
        HboxnbeModif.setVisible(true);
    }

    @FXML
    private void btnNbeModifAction(ActionEvent event) {
        if (!tfNbeModif.getText().isEmpty() && cs.checkOnlyInteger(tfNbeModif.getText())) {
            Entreprise u = (Entreprise) s.getCurrentUser();
            u.setNumberOfEmployees(Integer.parseInt(tfNbeModif.getText()));
            su.modifiernbe(u);
            afficherEntreprise();
            Hboxnbe.setVisible(true);
            HboxnbeModif.setVisible(false);
        } else {
            A.setContentText("Nombre Employes non valide ! ");
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
            Entreprise u = (Entreprise) s.getCurrentUser();
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

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CertifGUI;

import entities.Certif;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import services.ServiceCertif;

/**
 * FXML Controller class
 *
 * @author Ghass
 */
public class AccBackController implements Initializable {

    @FXML
    private TextField tfnom;
    @FXML
    private TextArea tfdesc;
    @FXML
    private ImageView imagePreviw;
    @FXML
    private Label imageLabel;
private String ImagePath;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ImagePath = "";
    }    

    @FXML
    private void badge(ActionEvent event) {
         FileChooser fc = new FileChooser();
        File defaultDir = new File("src/resources");
        fc.setInitialDirectory(defaultDir);
        File SelectedFile = fc.showOpenDialog(null);
        if (SelectedFile != null) {
            
            ImagePath = defaultDir.getName() + "/" + SelectedFile.getName();
            imageLabel.setText(ImagePath);
            imagePreviw.setImage(new Image(new File(ImagePath).toURI().toString()));
        } else {

            ImagePath = "resources/profile.jpg";
            imageLabel.setText(ImagePath);
            imagePreviw.setImage(new Image(new File(ImagePath).toURI().toString()));

        }

    }

    @FXML
    private void ajj(ActionEvent event) {
        ServiceCertif sf = new ServiceCertif();
        Certif f = new Certif();
       if (tfnom.getText().isEmpty() || tfdesc.getText().isEmpty())
       {
           Alert a= new Alert(Alert.AlertType.ERROR);
           a.setContentText("remplir tout les champs ! ");
           a.show();
       }else if (ImagePath == "")
       {
            Alert a= new Alert(Alert.AlertType.ERROR);
           a.setContentText("selectionnez une image ! ");
           a.show();
       }else {
               
        f.setNom(tfnom.getText());
        f.setDesc(tfdesc.getText());
        f.setBadge(ImagePath);
        sf.ajouter(f);
         Alert a= new Alert(Alert.AlertType.INFORMATION);
           a.setContentText("CErtification ajoutée.");
           a.show();
       }
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CertifGUI;

import static UserGUI.AddEntrepriseUIController.copyFileToDirectory;
import entities.Certif;
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
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import services.ServiceCertif;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class CrudCertifBackController implements Initializable {

    @FXML
    private ListView<String> list;
    @FXML
    private TextField id;
    @FXML
    private TextField titre;
    @FXML
    private TextArea desc;

    ServiceCertif sc = new ServiceCertif();
    @FXML
    private ImageView image;
    private String path;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        list.getItems().clear();
        sc.getAll().forEach(e -> list.getItems().add(e.getNom()));

    }

    @FXML
    private void setFields(MouseEvent event) {
        Certif c = sc.getOneByName(list.getSelectionModel().getSelectedItem());
        id.setText(String.valueOf(c.getId()));
        titre.setText(c.getNom());
        desc.setText(c.getDesc());
        //System.out.println(c.getBadge());
        path = c.getBadge();
        Image i = new Image(getClass().getResource("../" + path).toString(), true);
        image.setImage(i);
    }

    @FXML
    private void add(ActionEvent event) {
        Certif c = new Certif(titre.getText(), desc.getText(), path);
        sc.ajouter(c);

        clearTextFields();
        ActionEvent e = null;
        refresh(e);
    }

    public void clearTextFields() {
        id.setText("");
        titre.setText("");
        desc.setText("");
        path = "";
        image.setImage(null);
    }

    @FXML
    private void del(ActionEvent event) {
        sc.supprimer(sc.getOneByName(list.getSelectionModel().getSelectedItem()).getId());
    }

    @FXML
    private void modif(ActionEvent event) {
        Certif c = new Certif(titre.getText(), desc.getText(), path);
        c.setId(Integer.parseInt(id.getText()));
        sc.modifier(c);

        clearTextFields();
        ActionEvent e = null;
        refresh(e);
    }

    @FXML
    private void refresh(ActionEvent event) {
        list.getItems().clear();
        sc.getAll().forEach(e -> list.getItems().add(e.getNom()));
    }

    @FXML
    private void parcourir(ActionEvent event) throws IOException {
        FileChooser fc = new FileChooser();
        File defaultDir = new File("src/resources/");
        fc.setInitialDirectory(defaultDir);
        File SelectedFile = fc.showOpenDialog(null);

        if (SelectedFile != null) {
            copyFileToDirectory(SelectedFile, defaultDir);

            path = "../resources/" + SelectedFile.getName();
            Image im = new Image(getClass().getResource(path).toString(), true);
            image.setImage(im);
        } else {

            path = "../resources/DefaultProfile.png";
            image.setImage(new Image(getClass().getResource(path).toString(), true));

        }
    }

    @FXML
    private void goListe(ActionEvent event) {
        try {
            Parent page1 = FXMLLoader.load(getClass().getResource("ListeCertif.fxml"));

            SessionManager.getInstance().showContent(page1);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

}

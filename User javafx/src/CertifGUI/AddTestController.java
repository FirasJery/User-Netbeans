/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CertifGUI;

import entities.Certif;
import entities.SessionManager;
import entities.Test;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import services.ServiceCertif;
import services.ServiceTest;

/**
 * FXML Controller class
 *
 * @author Ghass
 */
public class AddTestController implements Initializable {

    @FXML
    private TextField tfnom;
    @FXML
    private ComboBox<String> combocat;
    @FXML
    private TextArea tfdesc;
    private SessionManager sm = SessionManager.getInstance();
    Certif c;
    ServiceTest st = new ServiceTest();
    ServiceCertif sr = new ServiceCertif();
    public static int idd;
    //SessionManager s = SessionManager.getInstance();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        combocat.setItems(st.getall());
    }


    void getPoste(Certif u) {

        idd = u.getId();

        c = (u);

    }

    @FXML
    private void add(ActionEvent event) throws IOException {

        Test t = new Test();
        System.out.println(idd);
        t.setCategorie(combocat.getValue());
        t.setDesc(tfdesc.getText());
        t.setTitre(tfnom.getText());
        t.setIdcertif(sr.getOneById(sm.getCurrentCertif().getId()));
        st.ajouter(t);
        sm.setT(t);
        
        Parent page1 = FXMLLoader.load(getClass().getResource("AddQuestions.fxml"));

        SessionManager.getInstance().showContent(page1);
    }

}

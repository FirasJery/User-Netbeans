/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OffresGUI;

import entities.Freelancer;
import entities.Offre;
import entities.Postulation;
import entities.SessionManager;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import services.ServiceOffre;
import services.ServicePostulation;
import services.ServiceUser;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import javafx.scene.control.Alert;

/**
 * FXML Controller class
 *
 * @author Firas
 */
public class OffrePageController implements Initializable {

    @FXML
    private TextArea tfDesc;
    @FXML
    private TextField tfTitre;
    @FXML
    private TextField tfDomaine;
    @FXML
    private TextField tfType;
    @FXML
    private TextField tfDuree;
    @FXML
    private Button btnPostuler;
    public static final String ACCOUNT_SID = "";
    public static final String AUTH_TOKEN = "";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ServiceOffre so = new ServiceOffre();
        Offre o = so.getOneById(AffichageOffreController.id_offre);
        tfTitre.setText(o.getTitle());
        tfDesc.setText(o.getDescription());
        tfDesc.setWrapText(true); // Activate line wrapping
        tfDesc.setEditable(false); // Set the text area to read-only
        tfDomaine.setText(o.getCategory());
        tfType.setText(o.getType());
        tfDuree.setText(o.getDuree() + "");
    }

    @FXML
    private void btnPostulerAction(ActionEvent event) {
        ServicePostulation sp = new ServicePostulation();
        ServiceOffre so = new ServiceOffre();
        ServiceUser su = new ServiceUser();
        Offre o = so.getOneById(AffichageOffreController.id_offre);
        Freelancer f = (Freelancer) su.getOneById(SessionManager.getInstance().getCurrentUser().getId());
        Postulation p = new Postulation(o, f, 0);
        sp.ajouter(p);
        btnPostuler.setVisible(false);
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setContentText("Postulation sera notifiée par message Whatsapp ");
        a.show();
       Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber("whatsapp:+21627505807"),
                new com.twilio.type.PhoneNumber("whatsapp:+14155238886"),
                "merci pour votre postulation , votre demande est prise en charge et en cours de traitement.")
                .create();

        System.out.println(message.getSid());
    }

}

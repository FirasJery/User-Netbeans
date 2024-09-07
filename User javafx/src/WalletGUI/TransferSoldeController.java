/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WalletGUI;

import static UserGUI.ForgotPasswordController.code;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import entities.Notification;
import entities.SessionManager;
import entities.Sms;
import entities.Transaction;
import entities.Utilisateur;
import entities.Wallet;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import services.EmailSender;
import services.ServiceTrans;
import services.ServiceWallet;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.time.LocalDateTime;
import services.NotifService;
import GUI.ControleSaisieTextFields ;

/**
 * FXML Controller class
 *
 * @author Ghass
 */
public class TransferSoldeController implements Initializable {

    @FXML
    private Pane panne;
    @FXML
    private TextField tfnumw;
    @FXML
    private TextField tfmon;
    private ServiceWallet sww = new ServiceWallet();
    SessionManager sessionManager = SessionManager.getInstance();
     ControleSaisieTextFields cs = new ControleSaisieTextFields();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void env(ActionEvent event) throws SQLException {
        
        if(!cs.checkOnlyInteger(tfmon.getText()) || tfmon.getText().isEmpty())
        {
         Alert a = new Alert(Alert.AlertType.ERROR); 
            a.setContentText("entrez un montant valide ! ");
            a.show();
        }
        else if(sww.Checksolde(sww.getOneByUserId(sessionManager.getCurrentUser().getId()).getId(), Integer.parseInt(tfmon.getText())))
        {
        Wallet w = new Wallet();
        Wallet w1 = new Wallet();
        w=sww.getOneByKey(tfnumw.getText());
        w1=sww.getOneByUserId(sessionManager.getCurrentUser().getId());
        sww.LowerSolde(sww.getOneByUserId(sessionManager.getCurrentUser().getId()), Integer.parseInt(tfmon.getText()));
        sww.AddSolde(w,Float.parseFloat(tfmon.getText()) );
        Utilisateur u ;
        u=w.getIduser();
        Transaction t ;
        Notification n;
        ServiceTrans st = new ServiceTrans();
        NotifService snn=new NotifService();
        LocalDate today = LocalDate.now();
        LocalDateTime now = LocalDateTime.now();
       // n=new Notification(u.getId(),"You Recieved "+tfmon.getText()+" DT From "+sessionManager.getCurrentUser().getName(),now,0);
        t= new Transaction(java.sql.Date.valueOf(today),Float.parseFloat(tfmon.getText()),w1,w,1);
        st.ajouter(t);
        //snn.insertNotification(n);
        String to1 = w1.getTel();
        System.out.println(to1);
        EmailSender.sendEmail("firas.eljary@esprit.tn", "espritmain47", u.getEmail(), "Solde Recu", "Vous Avez Recu  : " + tfmon.getText()+"DT  de la part de "+sessionManager.getCurrentUser().getName());
          //Twilio.init("ACdcb48291656a65f9ffc9b17524733acd", "635fcc0c90bf06ec1455cd7847c580ce");
          /* Message message = Message.creator(
                new PhoneNumber(to1),
                new PhoneNumber("+12708354673"),
                "U have successfully transfered "+tfmon.getText()+" Dt To : "+w.getIduser().getName()
        ).create();

       
        
        Message message2 = Message.creator(
                new PhoneNumber(w.getTel()),
                new PhoneNumber("+12708354673"),
                "U recieved "+tfmon.getText()+" Dt From : "+sessionManager.getCurrentUser().getName()
        ).create();

        */
        
        Alert alert = new Alert(Alert.AlertType.NONE);
         alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setContentText("Solde envoye a :"+w.getIduser().getName());
                alert.show();
                
        }
        else {
        Alert alert = new Alert(Alert.AlertType.NONE);
         alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("Solde Insuffisant");
                alert.show();
        }
    }
    
}

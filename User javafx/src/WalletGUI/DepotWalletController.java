/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WalletGUI;

import entities.SessionManager;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import services.ServiceCard;
import services.ServiceWallet;
import GUI.ControleSaisieTextFields ;

/**
 * FXML Controller class
 *
 * @author Ghass
 */
public class DepotWalletController implements Initializable {

    
    
    ControleSaisieTextFields cs = new ControleSaisieTextFields();
    @FXML
    private TextField tfmontant;
    @FXML
    private ComboBox<String> combocarte;
    private ServiceCard scc = new ServiceCard();
    private ServiceWallet sww = new ServiceWallet();
    SessionManager sessionManager = SessionManager.getInstance();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        combocarte.setItems(scc.getMyCardW(sessionManager.getCurrentUser().getId()));
        // TODO
    }    

    @FXML
    private void depo(ActionEvent event) {
        if (! cs.checkOnlyInteger(tfmontant.getText()) || tfmontant.getText().isEmpty() ) 
        {
            Alert a = new Alert(Alert.AlertType.ERROR); 
            a.setContentText("entrez un montant valide ! ");
            a.show();
        }
        else if(scc.Checksolde(Integer.parseInt(combocarte.getValue()), Integer.parseInt(tfmontant.getText())))
        {
        scc.Soustraire(scc.getOneByNum(Integer.parseInt(combocarte.getValue())), Integer.parseInt(tfmontant.getText()));
        sww.AddSolde(sww.getOneByUserId(sessionManager.getCurrentUser().getId()), Integer.parseInt(tfmontant.getText()));
           Alert a = new Alert(Alert.AlertType.INFORMATION); 
            a.setContentText("Depot reussi ! ");
            a.show();
        }
        else {
            Alert a = new Alert(Alert.AlertType.ERROR, "Solde insuffisant", ButtonType.OK);
            a.showAndWait();  
            
    }}

    
}

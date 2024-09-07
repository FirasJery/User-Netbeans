/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserGUI;

import entities.SessionManager;
import java.io.File;
import java.io.FileWriter;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.PasswordEncryption;
import services.ServiceUser;
import org.mindrot.jbcrypt.BCrypt;

/**
 * FXML Controller class
 *
 * @author Firas
 */
public class LoginUIController implements Initializable {

    @FXML
    private Button Button_se_connecter;
    @FXML
    private TextField TF_Email_login;
    @FXML
    private TextField TF_Paswword_login;
    @FXML
    private Button button_inscrire;
    @FXML
    private Button btn_entrep;
    @FXML
    private Button btnMdpOublie;
    @FXML
    private CheckBox checkboxResteConnecte;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void On_annuler_clicked(ActionEvent event) {
        TF_Email_login.setText("");
        TF_Paswword_login.setText("");

    }

    @FXML
    private void ON_seconnecter_clicked(ActionEvent event) throws IOException, Exception {
       String page = ""; 
        String email = TF_Email_login.getText();
        //String password = PasswordEncryption.encrypt(TF_Paswword_login.getText());
                String password =  TF_Paswword_login.getText();

        int id = -1;
        ServiceUser sa = new ServiceUser();
        Alert alert = new Alert(Alert.AlertType.NONE);
        SessionManager sessionManager = SessionManager.getInstance();
        id = sa.authentification(email, password);
        String role = "";
        if (id == -3)
        {
            
              alert.setAlertType(Alert.AlertType.WARNING);
                alert.setContentText(" mail erroné ! ");
                alert.show();
        }
        else if (id == -2 )
        {
             alert.setAlertType(Alert.AlertType.WARNING);
                alert.setContentText(" mot de passe erroné ! ");
                alert.show();
        }
        else if (id == 0 )
        {
            alert.setAlertType(Alert.AlertType.WARNING);
                alert.setContentText(" votre compte est signalé ! ");
                alert.show();
        }
        else {
            if (checkboxResteConnecte.isSelected())
            {
                  try {
                File file = new File("src/tests/IDSession.txt");
                FileWriter writer = new FileWriter(file);
                writer.write(Integer.toString(id));
                writer.close();
                System.out.println("ID saved to file!");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            System.out.println("Checkbox is not selected.");
        }
            
            sessionManager.setCurrentUser(sa.getOneById(id)); ////
            role = sa.getOneById(id).getRole();
            System.out.println("role : " +role);
            switch (role) {
            case "Admin":
               
                page = "/GUI/BackGUI.fxml" ;
                break;
            case "Entreprise":
              
                page = "/GUI/HomePage.fxml" ;

                break;
            case "Freelancer":
             
                page = "/GUI/HomePage.fxml" ;
                
                break;
        }
             try {

            Parent page1 = FXMLLoader.load(getClass().getResource(page));
            
            Scene scene = new Scene(page1);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(scene);
           

            stage.show();

        } catch (IOException ex) {

           System.out.println(ex.getMessage());

        }
        }

        
    }

  
    
    @FXML
    private void ON_inscrire_clicked(ActionEvent event) {
        try {

            Parent page1 = FXMLLoader.load(getClass().getResource("/UserGUI/AddFreelancer.fxml"));

            Scene scene = new Scene(page1);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(scene);

            stage.show();

        } catch (IOException ex) {

           System.out.println(ex.getMessage());

        }

    }

    @FXML
    private void btn_ins_ent_action(ActionEvent event) {
         try {

            Parent page1 = FXMLLoader.load(getClass().getResource("/UserGUI/AddEntrepriseUI.fxml"));

            Scene scene = new Scene(page1);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(scene);

            stage.show();

        } catch (IOException ex) {

           System.out.println(ex.getMessage());

        }
    }

    @FXML
    private void btnMdpOublieAction(ActionEvent event) {
            try {

            Parent page1 = FXMLLoader.load(getClass().getResource("/UserGUI/ForgotPassword.fxml"));

            Scene scene = new Scene(page1);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(scene);

            stage.show();

        } catch (IOException ex) {

           System.out.println(ex.getMessage());

        }
    }
    
    
}

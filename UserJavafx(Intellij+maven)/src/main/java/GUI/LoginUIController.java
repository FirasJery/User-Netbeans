
package GUI;

import entities.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import services.ServiceUser;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class LoginUIController implements Initializable {

    @FXML
    private Button Button_se_connecter;
    @FXML
    private TextField TF_Email_login;
    @FXML
    private Button button_inscrire;
    @FXML
    private Button btn_entrep;
    @FXML
    private PasswordField TF_Paswword_login;
    @FXML
    private Hyperlink LinkReset;

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
    private void ON_seconnecter_clicked(ActionEvent event) throws IOException {
        String page = "";
        String email = TF_Email_login.getText();
        String password = TF_Paswword_login.getText();
        int id = -1;
        ServiceUser sa = new ServiceUser();
        Alert alert = new Alert(Alert.AlertType.NONE);
        SessionManager sessionManager = SessionManager.getInstance();
        id = sa.authentification(email, password);
        String role = "";
        if (id == -1) {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText(" erroné ! ");
            alert.show();
        } else {
            sessionManager.setCurrentUser(sa.getOneById(id));
            role = sa.getOneById(id).getRole();
            System.out.println("before" + role);

            switch (role) {
                case "Admin":
                    System.out.println("admin");
                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.setContentText("Admin connecté");
                    alert.show();
                    page = "/GestionAdminUI.fxml";
                    break;
                case "Entreprise":
                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.setContentText("Entreprise connecté");
                    alert.show();
                    page = "/EntrepriseProfileUI.fxml";

                    break;
                case "Freelancer":
                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.setContentText("Freemlancer connecté");
                    alert.show();
                    page = "/UserProfileUI.fxml";

                    break;
            }
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(page));
                this.btn_entrep.getScene().setRoot(loader.load());

            } catch (IOException ex) {

                System.out.println(ex.getMessage());

            }
        }


    }

    @FXML
    private void ON_inscrire_clicked(ActionEvent event) {
        try {

            Parent page1 = FXMLLoader.load(getClass().getResource("/AddUser.fxml"));
            this.TF_Email_login.getScene().setRoot(page1);

        } catch (IOException ex) {

            System.out.println(ex.getMessage());

        }

    }


    @FXML
    public void OnResetClicked(ActionEvent actionEvent) {
        try {

            Parent page1 = FXMLLoader.load(getClass().getResource("/ForgotPassword.fxml"));

            Scene scene = new Scene(page1);

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            stage.setScene(scene);

            stage.show();

        } catch (IOException ex) {

            System.out.println(ex.getMessage());

        }
    }
}


package GUI;

import entities.SessionManager;
import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class UserProfileUIController implements Initializable {

    @FXML
    private Label Label_MsgA;
    @FXML
    private ImageView ImageProfile;
    @FXML
    private Label label_email;
    @FXML
    private Button btnDeconnecter;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        User u = SessionManager.getInstance().getCurrentUser();
        label_email.setText(u.getEmail());
        ImageProfile.setImage(new Image("file:"+u.getImagePath()));
        this.Label_MsgA.setText("Bienvenue " + u.getName() + " " + u.getLastName());

    }

    @FXML
    private void btnDeconnecterAction(ActionEvent event) {
        SessionManager.getInstance().setCurrentUser(null);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LoginUI.fxml"));
            this.btnDeconnecter.getScene().setRoot(loader.load());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }


}

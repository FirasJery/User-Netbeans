package GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import services.ServiceMailing;
import services.ServiceUser;

import java.io.IOException;


public class ForgotPasswordController {
    @FXML
    private Button BtnAnnuler;
    @FXML
    private Button BtnEnvoyerMail;
    @FXML
    private TextField TF_Email_reset;
    @FXML
    private Pane paneEmail;
    @FXML
    private Pane paneCode;
    @FXML
    private TextField tfCode;
    @FXML
    private Button submit;
    @FXML
    private Pane panePassword;
    @FXML
    private Button BtnModifier;
    @FXML
    private PasswordField tfComfirm;
    @FXML
    private PasswordField tfPassword;

    private Alert a = new Alert(Alert.AlertType.NONE);
    private ServiceUser serviceUser = new ServiceUser();
    private String code ;
    private String email;

    @FXML
    public void On_annuler_clicked(ActionEvent actionEvent) {
    }

    @FXML
    public void OnEnvoyerMailClicked(ActionEvent actionEvent) {
        this.email  = TF_Email_reset.getText();
        int cherch = this.serviceUser.ChercherMail(email);
        System.out.println("id = " + cherch);
        if(cherch != -1)
        {
             this.code = GenerateResetCode();
            ServiceMailing.sendHtmlNotification(email, "Reset Password", this.code);
            this.paneEmail.setVisible(false);
            this.paneCode.setVisible(true);
        }
        else
        {
            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText("Email introuvable");
            a.show();
        }

    }

    public String GenerateResetCode()
    {
        String code = "";
        for(int i = 0; i < 6; i++)
        {
            code += (int)(Math.random() * 10);
        }
        return code;
    }

    @FXML
    public void OnsubmitClicked(ActionEvent actionEvent) {
        if(tfCode.getText().equals(this.code))
        {
            this.paneCode.setVisible(false);
            this.panePassword.setVisible(true);
        }
        else
        {
            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText("Code incorrect");
            a.show();
        }

    }

    @FXML
    public void OnModifierClicked(ActionEvent actionEvent) throws IOException {
        String password = tfPassword.getText();
        String confirm = tfComfirm.getText();
        if(password.isEmpty())
        {
            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText("Veuillez remplir le champ");
            a.show();
            return;
        }
        if(password.length() < 8)
        {
            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText("Mot de passe doit contenir au moins 8 caractères");
            a.show();
            return;
        }
        if (!tfPassword.getText().equals(confirm))
        {
            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText("Les mots de passe ne correspondent pas");
            a.show();
            return;
        }

        this.serviceUser.UpdatePassword(email, password);
        a.setAlertType(Alert.AlertType.INFORMATION);
        a.setContentText("Mot de passe modifié avec succès");
        a.show();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/LoginUI.fxml"));
        this.BtnAnnuler.getScene().setRoot(loader.load());
    }
}

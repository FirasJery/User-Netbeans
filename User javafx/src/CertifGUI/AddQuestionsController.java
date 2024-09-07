/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CertifGUI;

import entities.Question;
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
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import services.ServiceQuestion;

/**
 * FXML Controller class
 *
 * @author Firas
 */
public class AddQuestionsController implements Initializable {

    @FXML
    private TextField question;
    @FXML
    private TextField reponse;
    @FXML
    private TextField note;
    @FXML
    private ComboBox<String> type;

    private TextField choix1 = new TextField();
    private TextField choix2 = new TextField();
    private TextField choix3 = new TextField();

    SessionManager sm = SessionManager.getInstance();
    ServiceQuestion sq = new ServiceQuestion();
    @FXML
    private AnchorPane anchor;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        type.getItems().add("QCM");
        type.getItems().add("Text");

        anchor.getChildren().add(choix1);
        anchor.getChildren().add(choix2);
        anchor.getChildren().add(choix3);

        choix1.setVisible(false);
        choix2.setVisible(false);
        choix3.setVisible(false);
    }

    @FXML
    private void ajouterQuestion(ActionEvent event) {
        Test t = new Test();
        t = sm.getT();
        Question q = new Question(question.getText(), reponse.getText(), Integer.parseInt(note.getText()), t);

        q.setChoix1(choix1.getText());
        q.setChoix2(choix2.getText());
        q.setChoix3(choix3.getText());
        q.setTypr(type.getValue());
        sq.ajouter(q);
        question.clear();
        reponse.clear();
        note.clear();
    }

    @FXML
    private void finQuestion(ActionEvent event) throws IOException {
        Test t = new Test();
        t = sm.getT();
        Question q = new Question(question.getText(), reponse.getText(), Integer.parseInt(note.getText()), t);
        q.setChoix1(choix1.getText());
        if (type.getValue().compareTo("QCM") == 0) {
            q.setChoix2(choix2.getText());
            q.setChoix3(choix3.getText());
            q.setTypr(type.getValue());
        } else {
            q.setChoix2("");
            q.setChoix3("");
            q.setTypr(type.getValue());
        }
        if (question.getText().isEmpty() || reponse.getText().isEmpty())
        {
             Alert a= new Alert(Alert.AlertType.ERROR);
           a.setContentText("remplir tout les champs ! ");
           a.show();
        }
        else {
        sq.ajouter(q);
         Alert a= new Alert(Alert.AlertType.INFORMATION);
           a.setContentText("question ajoutée ");
           a.show();
        question.clear();
        reponse.clear();
        note.clear();
        Parent page1 = FXMLLoader.load(getClass().getResource("ListeCertif.fxml"));

        SessionManager.getInstance().showContent(page1);
        }
    }

    @FXML
    private void typeSwitched(ActionEvent event) {
        if (type.getValue().compareTo("QCM") == 0) {
            choix1.setTranslateX(203);
            choix1.setTranslateY(220);
            choix2.setTranslateX(203);
            choix2.setTranslateY(270);
            choix3.setTranslateX(203);
            choix3.setTranslateY(320);
            choix1.setVisible(true);
            choix2.setVisible(true);
            choix3.setVisible(true);
        } else {
            choix1.setTranslateX(203);
            choix1.setTranslateY(220);
            choix1.setVisible(false);
            choix2.setVisible(false);
            choix3.setVisible(false);
        }
    }

}

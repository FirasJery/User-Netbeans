/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.effect.Bloom;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Firas
 */
public class HomePageContentController implements Initializable {

    @FXML
    private AnchorPane background;
    @FXML
    private Label rechercher_btn;

    Bloom bloom = new Bloom(0.3);

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //  background.setStyle("-fx-background-image: url('bg.png'); -fx-background-size: cover;");
        URL imageUrl = getClass().getResource("/resources/bg.png");

// Set the background of the AnchorPane to the image
        background.setStyle("-fx-background-image: url('" + imageUrl + "');");

    }

    private void dragEnteredLAbel(MouseEvent event) {

        System.out.println("test");
    }

    @FXML
    private void rechercher_fx(MouseEvent event) {
        if (rechercher_btn.getEffect() == null) {
            rechercher_btn.setEffect(bloom);
        } else {
            rechercher_btn.setEffect(null);
        }
    }

    @FXML
    private void rechercher(MouseEvent event) {
    }

}

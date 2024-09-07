
package tests;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FxMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/LoginUI.fxml"));
            // Set up the scene and the stage
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/style.css");
            primaryStage.setScene(scene);
            primaryStage.setTitle("User"); // Set the title of the window
            primaryStage.show(); // Show the window
        } catch (IOException ex) {
            Logger.getLogger(FxMain.class.getName()).log(Level.SEVERE, null, ex);
        }

    }


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}

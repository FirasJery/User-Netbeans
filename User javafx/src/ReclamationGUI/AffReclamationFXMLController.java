/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ReclamationGUI;

import entities.SessionManager;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import services.ServiceReview;

/**
 * FXML Controller class
 *
 * @author Ghass
 */
public class AffReclamationFXMLController implements Initializable {

    @FXML
    private Pane affpane;
    SessionManager sessionManager = SessionManager.getInstance();
    String offre_path = "";
    @FXML
    private Hyperlink BtnStat;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        if (sessionManager.getCurrentUser().getRole().equals("Entreprise"))
        {
            BtnStat.setVisible(false);
        }
            
    }

    @FXML
    private void HyperAjoutRecAction(ActionEvent event) {
        affpane.getChildren().clear();
        FXMLLoader loadOffre = new FXMLLoader(getClass().getResource("/ReclamationGUI/AddRec.fxml"));
        try {
            affpane.getChildren().add(loadOffre.load());
        } catch (IOException ex) {
            ex.getMessage();
        }
    }

    @FXML
    private void HypeSuivieRecAction(ActionEvent event) {
        affpane.getChildren().clear();
        FXMLLoader loadOffre = new FXMLLoader(getClass().getResource("/ReclamationGUI/SuivieRec.fxml"));
        try {
            affpane.getChildren().add(loadOffre.load());
        } catch (IOException ex) {
            ex.getMessage();
        }
    }

    @FXML
    private void HyperReviews(ActionEvent event) {

        affpane.getChildren().clear();
        FXMLLoader loadOffre = new FXMLLoader(getClass().getResource("/ReclamationGUI/ReviewsDisplay.fxml"));
        try {
            affpane.getChildren().add(loadOffre.load());
        } catch (IOException ex) {
            ex.getMessage();
        }
    }

    @FXML
    private void HyperTopFive(ActionEvent event) {
        affpane.getChildren().clear();
        FXMLLoader loadOffre = new FXMLLoader(getClass().getResource("/ReclamationGUI/TopFive.fxml"));
        try {
            affpane.getChildren().add(loadOffre.load());
        } catch (IOException ex) {
            ex.getMessage();
        }
    }

    @FXML
    private void showStat(ActionEvent event) {
        ServiceReview sr = new ServiceReview();
        boolean testvide = false ;
        if (SessionManager.getInstance().getCurrentUser().getRole().compareTo("Freelancer") == 0) {
            int l[] = new int[10];
            l = sr.getReviewsDivided(SessionManager.getInstance().getCurrentUser().getId());
            for (int i : l)
            {
                if (i != 0)
                {
                    testvide = true ; 
                }
            }
            if (testvide){
            // Compute statistics for weight and material
            Map<Float, Integer> weightStats = new HashMap<Float, Integer>();
            for (int i = 0; i < 10; i++) {
                weightStats.put((float) i, l[i]);
                System.out.println("s");
            }
            DefaultCategoryDataset weightDataset = new DefaultCategoryDataset();
            weightStats.forEach((material, weight) -> weightDataset.setValue(weight, "Note", material));
            // Create chart for weight statistics
            JFreeChart weightChart = ChartFactory.createBarChart("Statistiques review", "Total", "Note", weightDataset);
            ChartPanel weightChartPanel = new ChartPanel(weightChart);
            // Create chart panel and add to container
            SwingNode swingNode = new SwingNode();
            swingNode.setContent(weightChartPanel);
            Stage popupWindow = new Stage();
            popupWindow.setHeight(1000);
            popupWindow.setWidth(1000);
            VBox popupLayout = new VBox(swingNode);
            popupLayout.setAlignment(Pos.CENTER);
            popupLayout.setSpacing(40);
            Scene scene = new Scene(popupLayout);
            popupWindow.setScene(scene);

            // set the modality of the pop-up window
            popupWindow.initModality(Modality.APPLICATION_MODAL);

            // show the pop-up window
            popupWindow.showAndWait();
            }
            else 
            {
                Alert A = new Alert(Alert.AlertType.INFORMATION);
                A.setContentText("pas de revues pour vous Mr " + SessionManager.getInstance().getCurrentUser().getLastName());
                A.show();
            }

        }
    }

}

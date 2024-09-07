/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OffresGUI;



import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import entities.Offre;
import java.awt.Graphics2D;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.StackPane;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import services.ServiceOffre;



public class StatisticController implements Initializable {



    @FXML
    private StackPane chartContainer;
    private ServiceOffre productDao = new ServiceOffre();


    private ChartPanel weightChartPanel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<Offre> products = productDao.getAll();
        // Compute statistics for weight and material
        Map<String, Float> weightStats = products.stream()
                .collect(Collectors.groupingBy(Offre::getType, Collectors.summingDouble(Offre::getDuree)))
                .entrySet().stream().collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue().floatValue()));
        // Create dataset for weight chart
        DefaultCategoryDataset weightDataset = new DefaultCategoryDataset();
        weightStats.forEach((material, weight) -> weightDataset.setValue(weight, "Duree", material));
        // Create chart for weight statistics
        JFreeChart weightChart = ChartFactory.createBarChart("Offre Type Statistics", "Type", "Duree", weightDataset);
        weightChartPanel = new ChartPanel(weightChart);
        // Create chart panel and add to container
        SwingNode swingNode = new SwingNode();
        swingNode.setContent(weightChartPanel);
        chartContainer.getChildren().add(swingNode);
    }

    @FXML
    private void print(ActionEvent event) throws FileNotFoundException, DocumentException {
        Document document = new Document(PageSize.A4);
     //   PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(System.getProperty("user.home") + "/Desktop/chart.pdf"));

       PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("chart.pdf"));
        document.open();
        document.newPage();

        Graphics2D g2 = (Graphics2D) weightChartPanel.getGraphics();
        PdfContentByte cb = writer.getDirectContent();
        PdfTemplate tp = cb.createTemplate(weightChartPanel.getWidth(), weightChartPanel.getHeight());
        Graphics2D g2d = tp.createGraphics(weightChartPanel.getWidth(), weightChartPanel.getHeight());
        weightChartPanel.print(g2d);
        g2d.dispose();
        cb.addTemplate(tp, 0, 0);

        document.close();
        writer.close();
        
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("PDF Export");
    alert.setHeaderText(null);
    alert.setContentText("PDF file has been saved to the desktop.");
    alert.showAndWait();
    }
}

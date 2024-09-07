/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WalletGUI;

import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfFormField;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import entities.Data;
import entities.Facture;
import entities.Reclamation;
import entities.SessionManager;
import entities.Transaction;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import services.ServiceFacture;
import services.ServiceRec;
import services.ServiceTrans;
import services.ServiceWallet;
/**
 * FXML Controller class
 *
 * @author Ghass
 */
public class SuivTransController implements Initializable {

    @FXML
    private ScrollPane scrollpane;
    private ServiceTrans sr = new ServiceTrans();
    private ServiceWallet swall = new ServiceWallet();
    private ServiceFacture sff= new ServiceFacture();
    SessionManager sessionManager = SessionManager.getInstance();
    @FXML
    private Label tfenv;
    @FXML
    private Label tfrec;
    @FXML
    private Label tfdash;
    @FXML
    private Pane pane;
    private Pane pana;

    /**
     * Initializes the controller class.
     */
     public void table(){
        List<Transaction> offres = sr.getMyStrans(swall.getOneByUserId(sessionManager.getCurrentUser().getId()).getId());
        VBox vBox = new VBox();
        
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setSpacing(30);
        

        HBox hBox = new HBox();
         
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(100);//
        

        int count = 0;
        for (Transaction offre : offres) {
            VBox box = createOffreBox(offre);
            
            hBox.getChildren().add(box);
            count++;

            if (count == 1) {
                vBox.getChildren().add(hBox);
                hBox = new HBox();
                hBox.setAlignment(Pos.CENTER);
                hBox.setSpacing(100);
                count = 0;
            }
        }

        if (count > 0) {
            vBox.getChildren().add(hBox);
        }

        scrollpane.setContent(vBox);
        scrollpane.setFitToWidth(true);
        scrollpane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    }
     public void tableF(){
        List<Facture> offres = sff.getMyFacts(sessionManager.getCurrentUser().getId());
        VBox vBox = new VBox();
        
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setSpacing(30);
        

        HBox hBox = new HBox();
         
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(100);//
        

        int count = 0;
        for (Facture offre : offres) {
            VBox box = createOffreBoxF(offre);
            
            hBox.getChildren().add(box);
            count++;

            if (count == 1) {
                vBox.getChildren().add(hBox);
                hBox = new HBox();
                hBox.setAlignment(Pos.CENTER);
                hBox.setSpacing(100);
                count = 0;
            }
        }

        if (count > 0) {
            vBox.getChildren().add(hBox);
        }

        scrollpane.setContent(vBox);
        scrollpane.setFitToWidth(true);
        scrollpane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    }
      public void tableR(){
        List<Transaction> offres = sr.getMyRtrans(swall.getOneByUserId(sessionManager.getCurrentUser().getId()).getId());
        VBox vBox = new VBox();
        
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setSpacing(30);
        

        HBox hBox = new HBox();
         
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(100);//
        

        int count = 0;
        for (Transaction offre : offres) {
            VBox box = createOffreBoxR(offre);
            
            hBox.getChildren().add(box);
            count++;

            if (count == 1) {
                vBox.getChildren().add(hBox);
                hBox = new HBox();
                hBox.setAlignment(Pos.CENTER);
                hBox.setSpacing(100);
                count = 0;
            }
        }

        if (count > 0) {
            vBox.getChildren().add(hBox);
        }

        scrollpane.setContent(vBox);
        scrollpane.setFitToWidth(true);
        scrollpane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        // TODO
    }    
     private VBox createOffreBox(Transaction offre)  {
        VBox box = new VBox();
        
        box.setAlignment(Pos.CENTER);
        box.setSpacing(30);
         box.setUserData(offre.getId()); // set the ID as the user data for the VBox


        Label Date = new Label("Date :  "+offre.getDate());
        
        
         Button bb = new Button();
         bb.setText("Supprimer");
        
        Label montant = new Label("Montant :"+String.valueOf(offre.getMontant())+"DT");
        Label rec = new Label("Envoye a :"+offre.getR_wallet().getIduser().getName());
        
       
        ;
        Label sep = new Label("____________________________________________________________________________________________________________________");

     
        
      rec.setStyle("-fx-text-fill : Blue;");
      montant.setStyle("-fx-text-fill : WHITE;");
      Date.setStyle("-fx-text-fill : WHITE;");
        
         rec.setFont(Font.font("Serif", FontWeight.LIGHT, 23));
         montant.setFont(Font.font("Serif", FontWeight.LIGHT, 23));
        Date.setFont(Font.font("Arial", FontWeight.BOLD, 29));
        
        Date.setWrapText(true);
        box.getChildren().addAll( Date,montant,rec,sep);
        URL imageUrl = getClass().getResource("/resources/trans.jpg");
       ;
             box.setStyle("-fx-background-color : #FF2E2E");
        box.setStyle("-fx-background-image: url('" + imageUrl + "');");
        bb.setOnMouseClicked(event -> {
            
            sr.supprimer(offre.getId());
            table();
            
        });
        return box;
}
     
     private VBox createOffreBoxF(Facture offre)  {
        VBox box = new VBox();
        
        box.setAlignment(Pos.CENTER);
        box.setSpacing(30);
         box.setUserData(offre.getId()); // set the ID as the user data for the VBox


        Label Date = new Label("Formation :  "+offre.getNom_certif());
        
        
         Button bb = new Button();
         bb.setText("Supprimer");
        
        Label montant = new Label("Montant :"+String.valueOf(offre.getMontant())+"DT");
       
        
       
        ;
        Label sep = new Label("____________________________________________________________________________________________________________________");

     
        
      montant.setStyle("-fx-text-fill : WHITE;");
      Date.setStyle("-fx-text-fill : WHITE;");
        
         Button bbt = new Button();
         bbt.setText("Imprimer");
         montant.setFont(Font.font("Serif", FontWeight.LIGHT, 23));
        Date.setFont(Font.font("Arial", FontWeight.BOLD, 29));
        
        Date.setWrapText(true);
        box.getChildren().addAll( Date,montant,bbt,sep);
        URL imageUrl = getClass().getResource("/resources/trans.jpg");
       ;
             box.setStyle("-fx-background-color : #FF2E2E");
        box.setStyle("-fx-background-image: url('" + imageUrl + "');");
        bbt.setOnMouseClicked(event -> {
            
            try {
                // Create a new PDF document
                Document document = new Document();
                
                // Set the file path for the output PDF
                FileOutputStream fos = new FileOutputStream("MyFacture.pdf");
                
                // Set the writer for the PDF document
                PdfWriter writer = PdfWriter.getInstance(document, fos);
                
                // Open the PDF document
                document.open();
                
                // Get the content byte from the PDF writer
                PdfContentByte content = writer.getDirectContent();
                
                // Load the background image
            
                
                // Draw the background image
              
                
                // Add content to the PDF document
                com.itextpdf.text.Image image = com.itextpdf.text.Image.getInstance(getClass().getResource("logo.png"));
                image.scaleToFit(100, 100);
                com.itextpdf.text.Image image1 = com.itextpdf.text.Image.getInstance(getClass().getResource("stamp.png"));
                image1.setAbsolutePosition(document.right() - image.getScaledWidth(), document.bottom(100)- image.getScaledHeight());
                image1.scaleToFit(100, 100);
                
                document.add(image);
                
               com.itextpdf.text.Font font = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 50);
               com.itextpdf.text.Font font1 = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 25);
               Paragraph p=new Paragraph("Votre Facture",font);
               p.setAlignment(Paragraph.ALIGN_CENTER);
               document.add(p);
               Paragraph p1=new Paragraph("Certification :"+offre.getNom_certif(),font);
               
               p1.setAlignment(Paragraph.ALIGN_CENTER);
               p1.setSpacingBefore(10f);
            p1.setSpacingAfter(120f);
                document.add(p1);
                Paragraph p2 = new Paragraph("Benificiaire :  "+offre.getUser().getName(),font1);
                 p2.setSpacingBefore(10f);
            p2.setSpacingAfter(10f);
                document.add(p2);
                Paragraph p3 =new Paragraph("Montant payé :       "+offre.getMontant(),font1);
                 p3.setSpacingBefore(10f);
            p3.setSpacingAfter(10f);
                document.add(p3);
                Paragraph p4 =new Paragraph("Source Wallet :      " +offre.getWallet_s().getCle(),font1);
                 p4.setSpacingBefore(10f);
            p4.setSpacingAfter(10f);
                document.add(p4);
                
                document.add(image1);
                
                
                // Close the PDF document
                document.close();
                
                // Show success message
                System.out.println("PDF file created successfully.");
                
            } catch (DocumentException | IOException ex) {
                // Show error message
                ex.printStackTrace();
            }
            
        });
        return box;
}
     public void savePdf() throws Exception {
        
       
    }

     private VBox createOffreBoxR(Transaction offre)  {
        VBox box = new VBox();
        
        box.setAlignment(Pos.CENTER);
        box.setSpacing(30);
         box.setUserData(offre.getId()); // set the ID as the user data for the VBox


        Label Date = new Label("Message :  "+offre.getDate());
        
        
        
        
        Label montant = new Label("Montant :"+String.valueOf(offre.getMontant())+"DT");
        Label rec = new Label("Recu de :"+ offre.getS_wallet().getIduser().getName());
        Label sep = new Label("____________________________________________________________________________________________________________________");
      rec.setStyle("-fx-text-fill : Blue;");
      montant.setStyle("-fx-text-fill : WHITE;");
      Date.setStyle("-fx-text-fill : WHITE;");
       
         rec.setFont(Font.font("Serif", FontWeight.LIGHT, 23));
         montant.setFont(Font.font("Serif", FontWeight.LIGHT, 23));
        Date.setFont(Font.font("Arial", FontWeight.BOLD, 29));
        Button bb = new Button();
        bb.setText("supprimer");
        Date.setWrapText(true);
         URL imageUrl = getClass().getResource("/resources/trans.jpg");
        box.getChildren().addAll( Date,montant,rec,bb);
             box.setStyle("-fx-background-color : #FF2E2E");
        box.setStyle("-fx-background-image: url('" + imageUrl + "');");
        bb.setOnMouseClicked(event -> {
            
            sr.supprimer(offre.getId());
            table();
            
        });
        return box;
}


    @FXML
    private void tfenv(MouseEvent event) {
        table();
    }

    @FXML
    private void tfrec(MouseEvent event) {
        tableR();
    }

    @FXML
    private void tfdash(MouseEvent event) throws SQLException {
        
        tableF();

    }
    
}

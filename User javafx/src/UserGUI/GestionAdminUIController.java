/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserGUI;

import entities.SessionManager;
import entities.Utilisateur;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import services.ServiceUser;

/**
 * FXML Controller class
 *
 * @author Firas
 */
public class GestionAdminUIController implements Initializable {

    @FXML
    private TableView<Utilisateur> tv_users;
    @FXML
    private TableColumn<Utilisateur, String> col_name;
    @FXML
    private TableColumn<Utilisateur, String> col_email;
    @FXML
    private TableColumn<Utilisateur, String> col_role;
    @FXML
    private TableColumn<Utilisateur, String> col_img;
    @FXML
    private TableColumn<Utilisateur, String> col_bio;
    @FXML
    private TableColumn<Utilisateur, String> col_exp;
    @FXML
    private TableColumn<Utilisateur, String> col_ed;
    @FXML
    private TableColumn<Utilisateur, Integer> col_tt;
    @FXML
    private TableColumn<Utilisateur, String> col_dom;
    @FXML
    private TableColumn<Utilisateur, String> col_info;
    @FXML
    private TableColumn<Utilisateur, String> col_loc;
    @FXML
    private TableColumn<Utilisateur, Integer> col_nbe;
    @FXML
    private Button btnAjouter;
    @FXML
    private Button btnSupp;
    @FXML
    private Button btnModif;
    @FXML
    private TableColumn<Utilisateur, Integer> ColumnId;
    @FXML
    private Button btnActualiser;
    public static int id_modif ;  
    @FXML
    private TableColumn<?, ?> col_prenom;
    @FXML
    private TableColumn<?, ?> col_username;
    @FXML
    private TableColumn<?, ?> col_rating;
    public void afficherUsers()
    {
         ServiceUser sa = new ServiceUser();

        List<Utilisateur> lu = sa.getAll();
        ObservableList<Utilisateur> userList = FXCollections.observableArrayList(lu);

        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_prenom.setCellValueFactory(new PropertyValueFactory<>("LastName"));
        col_username.setCellValueFactory(new PropertyValueFactory<>("UserName"));
        col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        col_bio.setCellValueFactory(new PropertyValueFactory<>("bio"));
        col_ed.setCellValueFactory(new PropertyValueFactory<>("education"));
        col_exp.setCellValueFactory(new PropertyValueFactory<>("experience"));
        col_tt.setCellValueFactory(new PropertyValueFactory<>("total_jobs"));
        col_rating.setCellValueFactory(new PropertyValueFactory<>("rating"));
        col_loc.setCellValueFactory(new PropertyValueFactory<>("location"));
        col_role.setCellValueFactory(new PropertyValueFactory<>("role"));
        col_img.setCellValueFactory(new PropertyValueFactory<>("ImagePath"));
        col_dom.setCellValueFactory(new PropertyValueFactory<>("domaine"));
        col_info.setCellValueFactory(new PropertyValueFactory<>("info"));
        col_nbe.setCellValueFactory(new PropertyValueFactory<>("numberOfEmployees"));
        ColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));

        tv_users.setItems(userList);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       afficherUsers();
    }



    @FXML
    private void btnAjouterAction(ActionEvent event) {
        try {

            Parent page1 = FXMLLoader.load(getClass().getResource("/UserGUI/AddAdminUI.fxml"));

            SessionManager.getInstance().showContent(page1);

        } catch (IOException ex) {

            System.out.println(ex.getMessage());

        }
    }

    @FXML
    private void btnSuppAction(ActionEvent event) {

        int SelectedRowIndex = tv_users.getSelectionModel().getSelectedIndex();
        
        int ColumnIndex = tv_users.getColumns().indexOf(ColumnId);
        
        
        Alert A = new Alert(Alert.AlertType.CONFIRMATION);
        if (SelectedRowIndex == -1) {
            A.setAlertType(Alert.AlertType.WARNING);
            A.setContentText("Selectionnez une colonne ! ");
            A.show();
        } else {
            String IdCell = tv_users.getColumns().get(ColumnIndex).getCellData(SelectedRowIndex).toString();
            int id_supp = Integer.parseInt(IdCell);
            ServiceUser su = new ServiceUser();
            A.setAlertType(Alert.AlertType.CONFIRMATION);

            A.setContentText("Voulez vous supprimer l'utilisateur dont l'ID : " + IdCell + " ?");
            Optional<ButtonType> result = A.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                su.supprimer(id_supp);
                A.setAlertType(Alert.AlertType.INFORMATION);
                A.setContentText("Utilisateur Supprimé ! ");
                A.show();
                afficherUsers();
            }

        }
    }

    @FXML
    private void btnModifAction(ActionEvent event) {
            
        
        int SelectedRowIndex = tv_users.getSelectionModel().getSelectedIndex();
        
        int ColumnIndex = tv_users.getColumns().indexOf(ColumnId);
        
        
        Alert A = new Alert(Alert.AlertType.CONFIRMATION);
        if (SelectedRowIndex == -1) {
            A.setAlertType(Alert.AlertType.WARNING);
            A.setContentText("Selectionnez une colonne ! ");
            A.show();
        } else {
            String IdCell = tv_users.getColumns().get(ColumnIndex).getCellData(SelectedRowIndex).toString();
            id_modif = Integer.parseInt(IdCell);
        
        try {

            Parent page1 = FXMLLoader.load(getClass().getResource("/UserGUI/UserModifUI.fxml"));

            SessionManager.getInstance().showContent(page1);

        } catch (IOException ex) {

           System.out.println(ex.getMessage());

        }
        }

    }

    @FXML
    private void btnActualiserAction(ActionEvent event) {
       afficherUsers();
    }

}

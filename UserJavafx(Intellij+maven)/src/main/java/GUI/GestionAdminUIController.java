package GUI;

import entities.SessionManager;
import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import services.ServiceUser;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

public class GestionAdminUIController implements Initializable {
    @FXML
    private Button btnShowStatistics;
    @FXML
    private PieChart userRolePieChart;
    @FXML
    private TextField txtSearch;
    @FXML
    private Button btnDeconnecter;
    @FXML
    private Button btnAjouter;
    @FXML
    private Button btnSupp;
    @FXML
    private Button btnModif;
    private ServiceUser sa = new ServiceUser();

    @FXML
    private ListView<User> ListUsers;
    private ServiceUser serviceUser = new ServiceUser();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<User> lu = this.serviceUser.getAll();
        afficher(lu);
        this.txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                List<User> luf = this.serviceUser.getAll().stream().filter(u -> u.getName().toLowerCase().contains(newValue.toLowerCase()) ||
                        (u.getLastName().toLowerCase().contains(newValue.toLowerCase())) || (u.getEmail().toLowerCase().contains(newValue.toLowerCase())) ||
                        (u.getRole().toLowerCase().contains(newValue.toLowerCase()))).toList();
                afficher(luf);
            } else {
                afficher(lu);
            }
        });
    }

    public void afficher(List<User> lu) {
        ListUsers.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(User user, boolean empty) {
                super.updateItem(user, empty);

                if (empty || user == null) {
                    setText(null);
                } else {
                    setText(user.getName() + " - " + user.getLastName() + " - "
                            + user.getRole() + " - " + user.getEmail());
                }
            }
        });
        ListUsers.getItems().clear();
        for (User u : lu) {
            this.ListUsers.getItems().add(u);
        }
    }

    @FXML
    private void btnDeconnecterAction(ActionEvent event) throws IOException {
        SessionManager.getInstance().setCurrentUser(null);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/LoginUI.fxml"));
        this.btnAjouter.getScene().setRoot(loader.load());

    }

    @FXML
    private void btnAjouterAction(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddAdminUI.fxml"));
        try {
            this.btnAjouter.getScene().setRoot(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btnSuppAction(ActionEvent event) {
        User user = (User) ListUsers.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Suppression de l'utilisateur");
        alert.setContentText("Voulez-vous vraiment supprimer l'utilisateur " + user.getName() + " ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            sa.supprimer(user.getId());
            List<User> lu = this.serviceUser.getAll();
            afficher(lu);
        }
    }

    @FXML
    private void btnModifAction(ActionEvent event) {
        User user = (User) ListUsers.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/UserModifUI.fxml"));
        try {
            Parent root = loader.load();
            UserModifController controller = loader.getController();
            controller.setUser(user);
            this.btnModif.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void btnTriAscAction(ActionEvent event) {
        List<User> sortedUsersAsc = serviceUser.getAllSortedByUsernameAscending();
        afficher(sortedUsersAsc);
    }

    @FXML
    private void btnTriDescAction(ActionEvent event) {
        List<User> sortedUsersDesc = serviceUser.getAllSortedByUsernameDescending();
        afficher(sortedUsersDesc);
    }

    @FXML
    private void btnSearchAction(ActionEvent event) {
        String username = txtSearch.getText();
        if (!username.isEmpty()) {
            List<User> searchResults = serviceUser.searchByUsername(username);
            afficher(searchResults);
        }
    }

    @FXML
    private void showStatistics(ActionEvent event) {
        // Retrieve user role statistics
        Map<String, Integer> statistics = serviceUser.getUserRoleStatistics();

        // Clear existing data
        userRolePieChart.getData().clear();

        // Calculate total number of users
        int totalUsers = statistics.values().stream().mapToInt(Integer::intValue).sum();

        // Add data to PieChart
        statistics.forEach((role, count) -> {
            double percentage = (count * 100.0) / totalUsers;
            PieChart.Data data = new PieChart.Data(role + " (" + String.format("%.2f", percentage) + "%)", count);
            userRolePieChart.getData().add(data);
        });

        // Increase PieChart size
        userRolePieChart.setPrefWidth(400); // Adjust width as needed
        userRolePieChart.setPrefHeight(400); // Adjust height as needed
    }
}

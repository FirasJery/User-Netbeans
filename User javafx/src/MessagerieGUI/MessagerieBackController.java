/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MessagerieGUI;

import entities.Conversation;
import entities.SessionManager;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import services.ServiceMessagerie;
import services.ServiceUser;

/**
 * FXML Controller class
 *
 * @author Firas
 */
public class MessagerieBackController implements Initializable {

    @FXML
    private ListView<String> users;
    private ListView<String> aux = new ListView();
    @FXML
    private ListView<String> conversation;
    @FXML
    private ListView<String> msg_source;
    @FXML
    private TextField search_bar;
    @FXML
    private ListView<String> listUsers;

    private List<Node> listText = new ArrayList();

    private int current_index = 0;

    ServiceMessagerie sm = ServiceMessagerie.getInstance();
    SessionManager ss = SessionManager.getInstance();
    ServiceUser su = new ServiceUser();
    @FXML
    private AnchorPane anchor;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        su.getAll().forEach(e -> listUsers.getItems().add(e.getUserName()));
        conversation.getItems().clear();
        msg_source.getItems().clear();
        users.getItems().clear();
        aux.getItems().clear();

        List<String> l = new ArrayList();
        l.add("id Source");
        l.add("id Destinataire");
        l.add("Status Source");
        l.add("Status Destinatire");

        for (int i = 0; i < 4; i++) {

            Label la = new Label();
            la.setText(l.get(i));
            la.setTranslateX(420);
            la.setTranslateY((i + 1) * 100);
            la.setId("label" + i);
            la.setVisible(false);
            listText.add(la);
            //anchor.getChildren().add((Label) listText.get(i));
            if (i < 2) {
                TextField a = new TextField();
                //a.setText(l.get(i));
                a.setVisible(false);
                a.setId("l" + i);
                a.setTranslateX(550);
                a.setTranslateY(((i + 1) * 100) - 5);
                listText.add(a);
                //anchor.getChildren().add(a);
            } else {
                ComboBox c = new ComboBox();
                c.getItems().add("True");
                c.getItems().add("Pending");
                c.getItems().add("False");
                c.setId("l" + i);
                c.setVisible(false);
                c.setTranslateX(550);
                c.setTranslateY(((i + 1) * 100) - 5);
                listText.add(c);
                //anchor.getChildren().add(c); 
            }
        }

        Button b = new Button();
        b.setText("Confirmer");
        b.setTranslateX(520);
        b.setTranslateY(500);
        b.setVisible(false);
        b.setOnAction(new javafx.event.EventHandler() {
            @Override
            public void handle(Event event) {
                Conversation c = new Conversation(((TextField) anchor.lookup("#l0")).getText(),
                        ((TextField) anchor.lookup("#l1")).getText(), ((ComboBox) anchor.lookup("#l2")).getValue().toString(),
                        ((ComboBox) anchor.lookup("#l3")).getValue().toString());
                System.out.println(c.getId_dest() + "  " + c.getId_source());
                c.setId_convo(-1);
                sm.ajouter(c);
                for (int i = 0; i < listText.size(); i++) {
                    listText.get(i).setVisible(false);
                }
                msg_source.setVisible(true);
                conversation.setVisible(true);
            }

        });
        listText.add(b);

        for (int i = 0; i < listText.size(); i++) {
            anchor.getChildren().add(listText.get(i));
        }

    }

    @FXML
    private void get_convo(MouseEvent event) throws SQLException {
        conversation.getItems().clear();
        msg_source.getItems().clear();
        String contact = users.getSelectionModel().getSelectedItem();
        ServiceMessagerie sm = new ServiceMessagerie();
        Conversation c = sm.findConvo(String.valueOf(ss.getCurrentUser().getId()), String.valueOf(su.getByUsername(contact).getId()));
        ResultSet rs = sm.getMessages(c.getId_convo());
        while (rs.next()) {
            conversation.getItems().add(rs.getString("message"));
            msg_source.getItems().add(su.getOneById(rs.getInt("id_source")).getUserName());
        }

    }

    @FXML
    private void tryTranslate(MouseEvent event) {
    }

    @FXML
    private void search_convo(ActionEvent event) {
        users.getItems().clear();
        aux.getItems().forEach(u -> {
            users.getItems().add(u);
        });
        List<String> listUsers = users.getItems();
        if (search_bar.getText().compareTo("") != 0) {
            List<String> filteredUsers = users.getItems().stream().filter(u -> u.toUpperCase().contains(search_bar.getText().toUpperCase())).collect(Collectors.toList());
            users.getItems().clear();
            users.getItems().forEach(System.out::println);
            filteredUsers.forEach(fu -> users.getItems().add(fu));
        }
    }

    @FXML
    private void get_all_convo(MouseEvent event) {
        users.getItems().clear();
        String user = listUsers.getSelectionModel().getSelectedItem();
        current_index = listUsers.getSelectionModel().getSelectedIndex();

        ss.setCurrentUser(su.getByUsername(user));

        //sm.findContacts(.forEach(System.out::println);
        List<String> rs = new ArrayList();
        rs = sm.findContacts(String.valueOf(ss.getCurrentUser().getId()));
        rs.forEach(e -> users.getItems().add(e));
    }

    @FXML
    private void add_convo(ActionEvent event) {
        msg_source.setVisible(false);
        conversation.setVisible(false);
        for (int i = 0; i < listText.size(); i++) {
            listText.get(i).setVisible(true);
        }
    }

    @FXML
    private void del_convo(ActionEvent event) {
        String u = users.getSelectionModel().getSelectedItem();
        if (u.compareTo("") != 0) {
            Conversation c = sm.findConvo(String.valueOf(su.getByUsername(listUsers.getItems().get(current_index)).getId()), String.valueOf(su.getByUsername(u).getId()));
            sm.supprimer(c.getId_convo());
        }
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MessagerieGUI;

import entities.Conversation;
import entities.SessionManager;
import entities.Utilisateur;
import entities.Translator;
import services.ServiceMessagerie;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import services.ServiceUser;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class MessagerieInterfaceController implements Initializable {

    @FXML
    private ListView<String> users;
    @FXML
    private ListView<String> conversation;
    @FXML
    private ListView<String> msg_source;
    @FXML
    private TextField message;
    @FXML
    private Button send_button;

    //Entities
    private String current_id;
    private Utilisateur p;
    private Conversation c;
    private ListView<String> aux = new ListView();
    List<String> status = new ArrayList();
    List<String> convo = new ArrayList<>();
    ServiceMessagerie sm = ServiceMessagerie.getInstance();
    ServiceUser su = new ServiceUser();
    db_buffer dbt;
    int b_index = 0;
    int convo_index = 0;
    Image tick = new Image(getClass().getResource("../resources/tick.png").toString(), true);
    Image cross = new Image(getClass().getResource("../resources/cross.png").toString(), true);

    ImageView viewt = new ImageView(tick);
    ImageView viewc = new ImageView(cross);

    Image notif = new Image(getClass().getResource("../resources/notif.png").toString(), true);
    ImageView viewn = new ImageView(notif);
    boolean isFade = false;

    List<String> contacts = new ArrayList();
    List<String> last_msg = new ArrayList();
    @FXML
    private Button add_btn;
    @FXML
    private Button del_btn;
    @FXML
    private TextField crud_bar;
    @FXML
    private TextField search_bar;
    @FXML
    private AnchorPane anchor_window;

    private Translator t = new Translator();
    @FXML
    private ComboBox<String> srcLang;
    @FXML
    private ComboBox<String> destLang;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        p = SessionManager.getInstance().getCurrentUser();
        
        current_id = String.valueOf(SessionManager.getInstance().getCurrentUser().getId());
        //c = new Conversation(0, String.valueOf(p.getId()), "SERVER", true);

        current_id = String.valueOf(p.getId());

        contacts = sm.findContacts(current_id);
        last_msg.clear();
        contacts.forEach(con -> {
            users.getItems().add(con);
            int n = sm.findConvo(String.valueOf(p.getId()), String.valueOf(su.getByUsername(con).getId())).get_id();
            last_msg.add(sm.getLastMessage(n));
        });
        for (int i = 0; i < last_msg.size(); i++) {
            if (last_msg.get(i) == null) {
                last_msg.set(i, "");
            }
        }

        dbt = new db_buffer(current_id, last_msg, contacts, conversation, msg_source, 0, anchor_window);

        dbt.setName("606");
        dbt.start();

        status = sm.getStatus(current_id);

        viewt.setFitHeight(15);
        viewt.setPreserveRatio(true);
        viewc.setFitHeight(15);
        viewc.setPreserveRatio(true);

        for (int i = 0; i < status.size(); i++) {
            if (status.get(i).compareTo("pending") == 0) {
                Button a = new Button();
                Button d = new Button();

                String id_contact = new String();
                id_contact = String.valueOf(su.getByUsername(contacts.get(i)).getId());
                String idc = String.valueOf(sm.findConvo(current_id, id_contact).getId_convo());
                a.setTranslateX(220);
                a.setTranslateY(anchor_window.getChildren().get(0).getLayoutY() + (i * 30));
                a.setPrefSize(20, 20);
                a.setGraphic(viewt);

                a.setOnAction(new EventHandler() {
                    @Override
                    public void handle(Event event) {
                        sm.updateStatus(idc, current_id, "true");
                        anchor_window.getChildren().remove(a);
                        anchor_window.getChildren().remove(d);
                    }

                });

                d.setTranslateX(260);
                d.setTranslateY(anchor_window.getChildren().get(0).getLayoutY() + (i * 30));
                d.setPrefSize(20, 20);
                d.setGraphic(viewc);

                d.setOnAction(new EventHandler() {
                    @Override
                    public void handle(Event event) {
                        sm.updateStatus(idc, current_id, "false");
                        anchor_window.getChildren().remove(a);
                        anchor_window.getChildren().remove(d);
                    }

                });

                anchor_window.getChildren().add(a);
                anchor_window.getChildren().add(d);
            }

        }

        users.getItems().stream().forEach(u -> aux.getItems().add(u));

        List<String> lang = new ArrayList();
        lang.add("en");
        lang.add("fr");
        lang.add("sp");
        lang.add("it");
        lang.forEach(e -> {
            srcLang.getItems().add(e);
            destLang.getItems().add(e);
        });
        destLang.setValue("fr");
        srcLang.setValue("en");
    }

    @FXML
    private void send_message(ActionEvent event) {
        String msg = message.getText();
        if (msg.compareTo("") != 0) {
            Conversation c_tmp = sm.findConvo(String.valueOf(p.getId()), String.valueOf(su.getByUsername(users.getItems().get(convo_index)).getId()));
            if (c_tmp != null) {
                sm.updateMessage(c_tmp, String.valueOf(p.getId()), msg);
                conversation.getItems().add(message.getText()); //make the last message get updated from (Conversation : c)
                msg_source.getItems().add(String.valueOf(p.getUserName()));
                dbt.set_last_indexed(message.getText(), convo_index);
                dbt.set_src(String.valueOf(p.getId()));
                message.clear();
            }
        }
    }

    @FXML
    private void get_convo(MouseEvent event) {
        String current_user = users.getSelectionModel().getSelectedItem();
        //System.out.println(current_user);
        convo_index = users.getSelectionModel().getSelectedIndex();

        Conversation temp = sm.findConvo(String.valueOf(p.getId()), String.valueOf(su.getByUsername(current_user).getId()));
        if (temp != null) {
            String lmi = "";
            String srcc = "";
            ResultSet rs = sm.getMessages(temp.getId_convo());
            msg_source.getItems().clear();
            conversation.getItems().clear();
            try {
                while (rs.next()) {
                    msg_source.getItems().add(su.getOneById(rs.getInt("id_source")).getUserName());
                    conversation.getItems().add(rs.getString("message"));
                    lmi = rs.getString("message");
                    srcc = rs.getString("id_source");
                }
                //rs.last();
                dbt.set_last_indexed(lmi, convo_index);
                dbt.set_conversation(conversation);
                dbt.set_src(srcc);
                dbt.set_convo_index(convo_index);

                if (anchor_window.lookup("#n" + convo_index) != null) {
                    anchor_window.getChildren().remove(anchor_window.lookup("#n" + convo_index));
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }

        } else {
            //Create id generating function
            //Create new conversation
            //Add it to SQL
        }

    }

    @FXML
    private void add_convo(ActionEvent event) throws SQLException {
        if (crud_bar.getText().compareTo("") != 0) {
            Utilisateur u_tmp = su.getByUsername(crud_bar.getText());
            if (u_tmp != null) {
                Conversation c_tmp = new Conversation(1, String.valueOf(p.getId()), String.valueOf(u_tmp.getId()), false);
                sm.ajouter(c_tmp);
                contacts.add(crud_bar.getText());
                users.getItems().add(crud_bar.getText());
            }
        }
    }

    @FXML
    private void del_convo(ActionEvent event) {
        if (crud_bar.getText().compareTo("") != 0) {
            Utilisateur u_tmp = su.getByUsername(crud_bar.getText());
            if (u_tmp != null) {
                String idc = String.valueOf(sm.findConvo(current_id, String.valueOf(u_tmp.getId())).getId_convo());
                sm.updateStatus(idc, current_id, "false");
                contacts.remove(crud_bar.getText());
                users.getItems().remove(crud_bar.getText());
            }
        }
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

    public void addNotif(int i) {
        viewn.setId("n" + i);

        viewn.setTranslateX(220);
        viewn.setTranslateY(anchor_window.getChildren().get(0).getLayoutY() + (i * 30));
        viewn.setPreserveRatio(true);
        viewn.setFitHeight(15);
        anchor_window.getChildren().add(viewn);
    }

    public void memes() {
    }

    @FXML
    private void tryTranslate(MouseEvent event) {
        String src = srcLang.getValue();
        String dest = destLang.getValue();
        if (!isFade) {
            if (event.isSecondaryButtonDown()) {
                String message = conversation.getSelectionModel().getSelectedItem();
                if (message != null) {
                    try {
                        message = t.translate(message, src, dest);
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                    DialogPane d = new DialogPane();
                    d.setHeaderText("Translation from " + src + " to " + dest);
                    d.setContentText(message);
                    int lines = Math.round(message.length() / 45);
                    d.setPrefWidth(360);
                    d.setPrefHeight(88 + (lines * 21));
                    if (event.getSceneX() >= anchor_window.getPrefWidth() / 2) {
                        d.setTranslateX(event.getSceneX() - d.getPrefWidth());
                    } else {
                        d.setTranslateX(event.getSceneX());
                    }
                    if (event.getSceneY() >= anchor_window.getPrefHeight() / 2) {
                        d.setTranslateY(event.getSceneY() - d.getPrefHeight());
                    } else {
                        d.setTranslateY(event.getSceneY());
                    }

                    FadeTransition readIt = new FadeTransition(Duration.seconds(5), d);
                    readIt.setFromValue(1);
                    readIt.setToValue(1);

                    FadeTransition fadeOut = new FadeTransition(Duration.seconds(3), d);
                    fadeOut.setFromValue(1);
                    fadeOut.setToValue(0);
                    fadeOut.setOnFinished(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            anchor_window.getChildren().remove(d);
                            isFade = false;
                        }
                    });
                    anchor_window.getChildren().add(d);
                    isFade = true;
                    SequentialTransition seqT = new SequentialTransition(readIt, fadeOut);
                    seqT.play();

                }
            }
        }
    }

}

class db_buffer extends Thread {

    ServiceMessagerie sm = ServiceMessagerie.getInstance();
    ServiceUser su = new ServiceUser();
    private String current_id;
    private List<String> last_m = new ArrayList();
    private String src;
    private List<String> users = new ArrayList();
    private ListView<String> c = new ListView();
    private ListView<String> m = new ListView();

    private Image notif = new Image(getClass().getResource("../resources/notif.png").toString(), true);
    private ImageView viewn = new ImageView(notif);

    int current_convo = 0;

    private AnchorPane anchor_window;

    public db_buffer(String id, List<String> last_m, List<String> c, ListView<String> conversation, ListView<String> source, int i, AnchorPane w) {
        this.current_id = id;
        this.last_m = last_m;
        this.src = "";
        this.users = c;
        this.c = conversation;
        this.m = source;
        this.current_convo = i;
        this.anchor_window = w;

        viewn.setFitHeight(15);
        viewn.setPreserveRatio(true);

    }

    public void set_last(List<String> last_m) {
        this.last_m = last_m;
    }

    public void set_last_indexed(String m, int i) {
        this.last_m.set(i, m);
    }

    public void set_src(String src) {
        this.src = src;
    }

    public void set_users(List<String> c) {
        this.users = c;
    }

    public void set_conversation(ListView<String> c) {
        this.c = c;
    }

    public void set_src(ListView<String> m) {
        this.m = m;
    }

    public void set_convo_index(int i) {
        this.current_convo = i;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            for (int i = 0; i < last_m.size(); i++) {
                if (last_m.get(i).compareTo("") != 0) {
                    if (last_m.get(i).compareTo(sm.getLastMessage(sm.findConvo(current_id, String.valueOf(su.getByUsername(users.get(i)).getId())).getId_convo())) != 0) {
                        last_m.set(i, sm.getLastMessage(sm.findConvo(current_id, String.valueOf(su.getByUsername(users.get(i)).getId())).getId_convo()));
                        if (i == current_convo) {
                            c.getItems().add(last_m.get(i));
                            m.getItems().add(su.getOneById(Integer.parseInt(sm.getLastSource(sm.findConvo(current_id, String.valueOf(su.getByUsername(users.get(i)).getId())).getId_convo()))).getUserName());
                        }
                        if (i != current_convo) {
                            viewn.setId("n" + i);
                            viewn.setTranslateX(220);
                            viewn.setTranslateY(anchor_window.getChildren().get(0).getLayoutY() + (i * 30));
                            viewn.setPreserveRatio(true);
                            viewn.setFitHeight(15);
                            Platform.runLater(() -> anchor_window.getChildren().add(viewn));
                            //anchor_window.getChildren().remove(anchor_window.lookup("#n2"));
                        }

                    }
                }
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}

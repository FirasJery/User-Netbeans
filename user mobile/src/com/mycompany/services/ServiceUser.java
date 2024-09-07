/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.mycompany.entities.Utilisateur;
import com.mycompany.utils.Statics;
import java.util.ArrayList;
import java.util.List;
import java.io.UnsupportedEncodingException;
import com.codename1.io.JSONParser;
import com.codename1.ui.Dialog;
import java.io.IOException;
import java.util.Map;

/**
 *
 * @author Firas
 */
public class ServiceUser {

    public static ServiceUser instance = null;
    private ConnectionRequest req;
    public static boolean resultOk = false;
    List<Utilisateur> Users = new ArrayList<>();
    String json;

    public static ServiceUser getInstance() {
        if (instance == null) {
            instance = new ServiceUser();
        }
        return instance;
    }

    public ServiceUser() {
        req = new ConnectionRequest();
    }

    public boolean AddUser(Utilisateur u) {
        String url = Statics.BASE_URL + "/utilisateur/newFreelancerMobile?name=" + u.getName() + "&lastName=" + u.getLastName()
                + "&userName=" + u.getUserName()
                + "&password=" + u.getPassword() + "&email=" + u.getEmail() + "&image=" + u.getImagePath()
                + "&bio=" + u.getBio() + "&education=" + u.getEducation() + "&experience=" + u.getExperience();
        System.out.println(u.getEmail());
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOk = req.getResponseCode() == 200;
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOk;
    }

    public boolean AddEntreprise(Utilisateur u) {
        String url = Statics.BASE_URL + "/utilisateur/newEntrepriseMobile?name=" + u.getName() + "&lastName=" + u.getLastName()
                + "&userName=" + u.getUserName()
                + "&password=" + u.getPassword() 
                + "&email=" + u.getEmail() 
                + "&image=" + u.getImagePath() 
                + "&info=" + u.getInfo()
                + "&adresse=" + u.getAdresse() 
                + "&nbe=" + u.getNbe() 
                + "&domaine=" + u.getDomaine();
        System.out.println(url);
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOk = req.getResponseCode() == 200;
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOk;
    }

    public boolean Edit(Utilisateur u) {
        String url = Statics.BASE_URL + "/utilisateur/EditMobile?id=" + u.getId()
                + "&name=" + u.getName()
                + "&lastName=" + u.getLastName()
                + "&userName=" + u.getUserName()
                + "&password=" + u.getPassword()
                + "&email=" + u.getEmail()
                + "&info=" + u.getInfo()
                + "&adresse=" + u.getAdresse()
                + "&nbe=" + u.getNbe()
                + "&domaine=" + u.getDomaine()
                + "&bio=" + u.getBio()
                + "&education=" + u.getEducation()
                + "&experience=" + u.getExperience();

        System.out.println(url);
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOk = req.getResponseCode() == 200;
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOk;
    }

    public boolean deleteUserf(Utilisateur u) {

        String url = Statics.BASE_URL + "/utilisateur/deleteJSON?id=" + u.getId();

        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOk = req.getResponseCode() == 200;
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOk;
    }

    public void login(String email, String mdp) {
                    System.out.println("log in method test");

        String url = Statics.BASE_URL + "/utilisateur/loginJSON?email=" + email + "&password=" + mdp;
        System.out.println(url);
        req.setUrl(url);
        System.out.println(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                JSONParser j = new JSONParser();

                String json = new String(req.getResponseData()) + "";
              
                

                try {

                    if (json.equals("incorrect password")) {
                        Dialog.show("Failure", "wrong password", "OK", null);
                    } else if (json.equals("user not found")) {
                        Dialog.show("Failure", "Username not found", "OK", null);
                    } else {
                        System.out.println("data ==" + json);
                        Map<String, Object> user = j.parseJSON(new CharArrayReader(json.toCharArray()));
                        System.out.println(user);
                        Utilisateur u = new Utilisateur();
                        u.setRole((String) user.get("role"));
                        if (u.getRole().equals("Entreprise")) {
                            double nbe = (Double) user.get("nbe");
                            int nbeint = Double.valueOf(nbe).intValue();
                            u.setNbe(nbeint);
                            u.setDomaine((String) user.get("domaine"));
                            u.setInfo((String) user.get("info"));
                            u.setAdresse((String) user.get("location"));

                        }
                        if (u.getRole().equals("Freelancer")) {
                            double rating = (Double) user.get("rating");
                            double totaljobs = (Double) user.get("totalJobs");
                            int ratingint = Double.valueOf(rating).intValue();
                            int tjint = Double.valueOf(totaljobs).intValue();
                            u.setRating(ratingint);
                            u.setTj(tjint);
                            u.setBio((String) user.get("bio"));
                            u.setEducation((String) user.get("education"));
                            u.setExperience((String) user.get("experience"));
                        }

                        double id = (Double) user.get("id");
                        int intId = Double.valueOf(id).intValue();
                        u.setId(intId);
                        u.setName((String) user.get("name"));
                        u.setUserName((String) user.get("username"));
                        u.setLastName((String) user.get("LastName"));
                        u.setEmail((String) user.get("email"));
                        u.setImagePath((String) user.get("ImagePath"));
                        SessionManager.getInstance().setCurrentUser(u);
                        System.out.println(u);

                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        

    }

    public void getonebyid(int idrech) {

        String url = Statics.BASE_URL + "/utilisateur/getonebyidJSON?id=" + idrech;
        System.out.println(url);
        req.setUrl(url);
        System.out.println(url);
        req.setPost(false);
        req.addResponseListener((e) -> {

            JSONParser j = new JSONParser();

            String json = new String(req.getResponseData()) + "";

            try {

                if (json.equals("failure")) {
                    Dialog.show("Failure", "user not found", "OK", null);
                } else {
                    System.out.println("data ==" + json);
                    Map<String, Object> user = j.parseJSON(new CharArrayReader(json.toCharArray()));
                    System.out.println(user);
                    Utilisateur u = new Utilisateur();
                    u.setRole((String) user.get("role"));
                    if (u.getRole().equals("Entreprise")) {
                        double nbe = (Double) user.get("nbe");
                        int nbeint = Double.valueOf(nbe).intValue();
                        u.setNbe(nbeint);
                        u.setDomaine((String) user.get("domaine"));
                        u.setInfo((String) user.get("info"));
                        u.setAdresse((String) user.get("location"));

                    }
                    if (u.getRole().equals("Freelancer")) {
                        double rating = (Double) user.get("rating");
                        double totaljobs = (Double) user.get("totalJobs");
                        int ratingint = Double.valueOf(rating).intValue();
                        int tjint = Double.valueOf(totaljobs).intValue();
                        u.setRating(ratingint);
                        u.setTj(tjint);
                        u.setBio((String) user.get("bio"));
                        u.setEducation((String) user.get("education"));
                        u.setExperience((String) user.get("experience"));
                    }

                    double id = (Double) user.get("id");
                    int intId = Double.valueOf(id).intValue();
                    u.setId(intId);
                    u.setName((String) user.get("name"));
                    u.setUserName((String) user.get("username"));
                    u.setLastName((String) user.get("LastName"));
                    u.setEmail((String) user.get("email"));
                    u.setImagePath((String) user.get("ImagePath"));
                    SessionManager.getInstance().setCurrentUser(u);
                    System.out.println(u);
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);

    }

    //FETCH
    public List<Utilisateur> fetchUsers() {

        req = new ConnectionRequest();
        String fetchURL = Statics.BASE_URL + "/utilisateur/affichageJSON";
        req.setUrl(fetchURL);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                Users = parseTasks(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });

        NetworkManager.getInstance().addToQueueAndWait(req);
        return Users;
    }

    //Parse
    public List<Utilisateur> parseTasks(String jsonText) {

        Users = new ArrayList<>();
        JSONParser jp = new JSONParser();

        try {
            Map<String, Object> tasksListJSON = jp.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJSON.get("root");
            for (Map<String, Object> item : list) {

                Utilisateur t = new Utilisateur();
                t.setId((int) (double) item.get("id"));
                t.setName((String) item.get("name"));
                t.setLastName((String) item.get("LastName"));
                t.setEmail((String) item.get("email"));

                Users.add(t);
            }

        } catch (IOException ex) {
        }

        return Users;
    }

}

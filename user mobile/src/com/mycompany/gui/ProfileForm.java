/*
 * Copyright (c) 2016, Codename One
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, 
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions 
 * of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A 
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT 
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */
package com.mycompany.gui;

import com.codename1.components.FloatingHint;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.ScaleImageLabel;
import com.codename1.io.File;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BASELINE;
import static com.codename1.ui.Component.CENTER;
import static com.codename1.ui.Component.TOP;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Utilisateur;
import com.mycompany.services.ServiceUser;
import com.mycompany.services.SessionManager;
import java.io.IOException;

/**
 * The user profile form
 *
 * @author Shai Almog
 */
public class ProfileForm extends BaseForm {

    public ProfileForm(Resources res) {
        super("Newsfeed", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Profile");
        getContentPane().setScrollVisible(false);

        super.addSideMenu(res);

        // taswiraa bg
        Image img = res.getImage("profile-background.jpg");
        if (img.getHeight() > Display.getInstance().getDisplayHeight() / 3) {
            img = img.scaledHeight(Display.getInstance().getDisplayHeight() / 3);
        }
        ScaleImageLabel sl = new ScaleImageLabel(img);
        sl.setUIID("BottomPad");
        sl.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
////////////// taswira 
        String imagePath = SessionManager.getInstance().getCurrentUser().getImagePath();
        File imageFile = new File(imagePath);
        String imageName = imageFile.getName();
        Image img1 = null;
        try {
            img1 = Image.createImage("/" + imageName);
        } catch (IOException ex) {
            System.out.println(ex.getCause());
        }
        //////////// rating + tj 
        Label facebook = new Label("Rating: " + SessionManager.getInstance().getCurrentUser().getRating(), "BottomPad");
        Label twitter = new Label("Total jobs : " + SessionManager.getInstance().getCurrentUser().getTj(), "BottomPad");
        if (SessionManager.getInstance().getCurrentUser().getRole().equals("Entreprise")) {
            facebook.setVisible(false);
            twitter.setVisible(false);
        }
        facebook.setTextPosition(BOTTOM);
        twitter.setTextPosition(BOTTOM);

        add(LayeredLayout.encloseIn(
                sl,
                BorderLayout.south(
                        GridLayout.encloseIn(3,
                                facebook,
                                FlowLayout.encloseCenter(
                                        new Label(img1, "PictureWhiteBackgrond")),
                                twitter
                        )
                )
        ));

        TextField name = new TextField(SessionManager.getInstance().getCurrentUser().getName());
        name.setUIID("TextFieldBlack");
        addStringValue("Name", name);

        TextField lastname = new TextField(SessionManager.getInstance().getCurrentUser().getLastName());
        lastname.setUIID("TextFieldBlack");
        addStringValue("Lastname", lastname);

        TextField username = new TextField(SessionManager.getInstance().getCurrentUser().getUserName());
        username.setUIID("TextFieldBlack");
        addStringValue("Username", username);

        TextField email = new TextField(SessionManager.getInstance().getCurrentUser().getEmail(), "E-Mail", 20, TextField.EMAILADDR);
        email.setUIID("TextFieldBlack");
        addStringValue("E-Mail", email);

        TextField password = new TextField("", "Password", 20, TextField.PASSWORD);
        password.setUIID("TextFieldBlack");
        addStringValue("Password", password);

        Button Edit = new Button("Edit profile");
        Button Delete = new Button("Delete");

        ////////////////////////////////////////////////////   freelancer ////////////////////////////
        if (SessionManager.getInstance().getCurrentUser().getRole().equals("Freelancer")) {
            TextField bio = new TextField(SessionManager.getInstance().getCurrentUser().getBio(), "Bio", 20, TextField.EMAILADDR);
            bio.setUIID("TextFieldBlack");
            addStringValue("Bio", bio);

            TextField experience = new TextField(SessionManager.getInstance().getCurrentUser().getExperience(), "experience", 20, TextField.EMAILADDR);
            experience.setUIID("TextFieldBlack");
            addStringValue("experience", experience);

            TextField education = new TextField(SessionManager.getInstance().getCurrentUser().getEducation(), "education", 20, TextField.EMAILADDR);
            education.setUIID("TextFieldBlack");
            addStringValue("education", education);

            Edit.addActionListener(e -> {

                if (name.getText().isEmpty()) {
                    Dialog.show("Remplir tout", "", "Annuler", "OK");
                } else {
                    InfiniteProgress ip = new InfiniteProgress();
                    final Dialog iDialog = ip.showInfiniteBlocking();
                    Utilisateur u = new Utilisateur(SessionManager.getInstance().getCurrentUser().getId(), name.getText(), lastname.getText(), username.getText(), email.getText(), password.getText(), bio.getText(), education.getText(), experience.getText());
                    System.out.println("data = " + u);
                    boolean test = ServiceUser.getInstance().Edit(u);
                    if (test){
                        Dialog.show("Profile updated successfully", "", "Annuler", "OK");
                        ServiceUser.getInstance().getonebyid(u.getId());
                        new ProfileForm(res).show();
                    }
                    else {
                        new SignInForm(res).show();
                    }
                    
                    
                    

                }
            });
        }

        /////////////////////// entrep ///////////////////////////////////////////////
        if (SessionManager.getInstance().getCurrentUser().getRole().equals("Entreprise")) {
            TextField info = new TextField(SessionManager.getInstance().getCurrentUser().getInfo(), "info", 20, TextField.EMAILADDR);
            info.setUIID("TextFieldBlack");
            addStringValue("Info", info);

            TextField adresse = new TextField(SessionManager.getInstance().getCurrentUser().getAdresse(), "adresse", 20, TextField.EMAILADDR);
            adresse.setUIID("TextFieldBlack");
            addStringValue("adresse", adresse);

            TextField domaine = new TextField(SessionManager.getInstance().getCurrentUser().getDomaine(), "domaine", 20, TextField.EMAILADDR);
            domaine.setUIID("TextFieldBlack");
            addStringValue("domaine", domaine);

            TextField nbe = new TextField(String.valueOf(SessionManager.getInstance().getCurrentUser().getNbe()), "nbe");
            nbe.setUIID("TextFieldBlack");
            addStringValue("nombre d'employes", nbe);

            Edit.addActionListener(e -> {
                if (name.getText().isEmpty()) {
                    Dialog.show("Remplir tout", "", "Annuler", "OK");
                } else {
                    InfiniteProgress ip = new InfiniteProgress();
                    final Dialog iDialog = ip.showInfiniteBlocking();

                    Utilisateur u = new Utilisateur(SessionManager.getInstance().getCurrentUser().getId(), name.getText(), lastname.getText(), username.getText(), email.getText(), password.getText(), info.getText(), adresse.getText(), domaine.getText(),Integer.parseInt(nbe.getText()));
                    System.out.println("data = " + u);
                    boolean test = ServiceUser.getInstance().Edit(u);
                   if (test){
                        Dialog.show("Profile updated successfully", "", "Annuler", "OK");
                        ServiceUser.getInstance().getonebyid(u.getId());
                        new ProfileForm(res).show();
                    }
                    else {
                        new SignInForm(res).show();
                    }

                }
            });

        }

        Delete.addActionListener(e -> {

            InfiniteProgress ip = new InfiniteProgress();
            final Dialog iDialog = ip.showInfiniteBlocking();
            boolean test = ServiceUser.getInstance().deleteUserf(SessionManager.getInstance().getCurrentUser());
            if (test)
            {
                 Dialog.show("account deleted successfully", "", "Annuler", "OK");
                SessionManager.getInstance().setCurrentUser(new Utilisateur(0));
                new SignInForm(res).show();
            }
          

        });

        addStringValue("", FlowLayout.encloseCenter(Edit));
        addStringValue("", FlowLayout.encloseCenter(Delete));

    }

    private void addStringValue(String s, Component v) {
        add(BorderLayout.west(new Label(s, "PaddedLabel")).
                add(BorderLayout.CENTER, v));
        add(createLineSeparator(0xeeeeee));
    }
}

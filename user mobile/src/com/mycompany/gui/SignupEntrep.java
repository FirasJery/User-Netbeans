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
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Utilisateur;
import com.mycompany.services.ServiceUser;

/**
 * Signup UI
 *
 * @author Shai Almog
 */
public class SignupEntrep extends BaseForm {

    public SignupEntrep(Resources res) {
        super(new BorderLayout());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        tb.setUIID("Container");
        getTitleArea().setUIID("Container");
        Form previous = Display.getInstance().getCurrent();
        tb.setBackCommand("", e -> previous.showBack());
        setUIID("SignIn");
        TextField name = new TextField("", "name", 20, TextField.ANY);
                TextField lastname = new TextField("", "lastname", 20, TextField.ANY);

        TextField info = new TextField("", "info", 20, TextField.ANY);
        TextField username = new TextField("", "Username", 20, TextField.ANY);
        TextField email = new TextField("", "E-Mail", 20, TextField.EMAILADDR);
        TextField password = new TextField("", "Password", 20, TextField.PASSWORD);
        TextField confirmPassword = new TextField("", "Confirm Password", 20, TextField.PASSWORD);
        TextField adresse = new TextField("", "Adresse", 20, TextField.ANY);
         TextField domaine = new TextField("", "Domaine", 20, TextField.ANY);
                TextField nbe = new TextField("", "Nombre des employes", 20, TextField.ANY);
                    lastname.setSingleLineTextArea(false);
        username.setSingleLineTextArea(false);
        email.setSingleLineTextArea(false);
        password.setSingleLineTextArea(false);
        confirmPassword.setSingleLineTextArea(false);
         adresse.setSingleLineTextArea(false);
          info.setSingleLineTextArea(false);
          domaine.setSingleLineTextArea(false);
        Button next = new Button("Sign up");
        Button signIn = new Button("Sign In");
        signIn.addActionListener(e -> previous.showBack());
        signIn.setUIID("Link");
        Label alreadHaveAnAccount = new Label("Already have an account?");

        Container content = BoxLayout.encloseY(
                new Label("Sign Up", "LogoLabel"),
                new FloatingHint(name),
                createLineSeparator(),
                 new FloatingHint(lastname),
                createLineSeparator(),
                new FloatingHint(username),
                createLineSeparator(),
                new FloatingHint(email),
                createLineSeparator(),
                new FloatingHint(password),
                createLineSeparator(),
                new FloatingHint(confirmPassword),
                createLineSeparator(),
                 new FloatingHint(info),
                createLineSeparator(),
                  new FloatingHint(domaine),
                createLineSeparator(),
                new FloatingHint(adresse),
                createLineSeparator(),
                 new FloatingHint(nbe),
                createLineSeparator()
                
                
          
        );
        content.setScrollableY(true);
        add(BorderLayout.CENTER, content);
        add(BorderLayout.SOUTH, BoxLayout.encloseY(
                next,
                FlowLayout.encloseCenter(alreadHaveAnAccount, signIn)
        ));
        next.requestFocus();
        next.addActionListener(e -> {

            if (name.getText().isEmpty()) {
                Dialog.show("Remplir tout", "", "Annuler", "OK");
            } else {
                InfiniteProgress ip = new InfiniteProgress();
                final Dialog iDialog = ip.showInfiniteBlocking();

                Utilisateur u = new Utilisateur(TOP, name.getText(), lastname.getText(), username.getText(), email.getText(), password.getText(), "none", "none", info.getText(), adresse.getText(), domaine.getText(), TOP, CENTER, BASELINE);
                u.setNbe(Integer.parseInt(nbe.getText()));
                System.out.println("data = " + u);
                boolean test = ServiceUser.getInstance().AddEntreprise(u);
                previous.showBack();
            }

        });
    }

}

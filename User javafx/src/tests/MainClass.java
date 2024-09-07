package tests;

import entities.Admin;
import entities.Entreprise;
import entities.Freelancer;
import entities.Utilisateur;
import java.util.ArrayList;
import java.util.List;
import services.PasswordEncryption;
import services.ServiceUser;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Firas
 */
public class MainClass {
    
     public static void main(String[] args) throws Exception {

        ServiceUser sa = new ServiceUser();
        String password = "azertyazerty"; 
         String enc = PasswordEncryption.encrypt(password);
         System.out.println(enc);
         String a = PasswordEncryption.decrypt(enc);
         System.out.println(a);         
        
     }
}

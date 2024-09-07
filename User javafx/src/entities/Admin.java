/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;


/**
 *
 * @author Firas
 */
public class Admin extends Utilisateur{
    
   

    public Admin() {
    }

    public Admin(int id, String name, String LastName, String UserName, String email, String password, String role, String ImagePath) {
        super(id, name, LastName, UserName, email, password, role, ImagePath);
    }

    public Admin(String name, String LastName, String UserName, String email, String password, String role, String ImagePath) {
        super(name, LastName, UserName, email, password, role, ImagePath);
    }

  

   
    

    @Override
    public String toString() {
                return "Admin { name=" + name + ", LastName=" + LastName + ", UserName=" + UserName + ", email=" + email + ", password=" + password + ", role=" + role + ", ImagePath=" + ImagePath + '}';

    }

}

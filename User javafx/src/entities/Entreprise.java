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
public class Entreprise extends Utilisateur{
    
    private String Domaine ; 
    private String Info ;
    private String location;
    private int numberOfEmployees;

    public Entreprise() {
    }

    public Entreprise(String Domaine, String Info, String location, int numberOfEmployees, int id, String name, String LastName, String UserName, String email, String password, String role, String ImagePath) {
        super(id, name, LastName, UserName, email, password, role, ImagePath);
        this.Domaine = Domaine;
        this.Info = Info;
        this.location = location;
        this.numberOfEmployees = numberOfEmployees;
    }

    public Entreprise(String Domaine, String Info, String location, int numberOfEmployees, String name, String LastName, String UserName, String email, String password, String role, String ImagePath) {
        super(name, LastName, UserName, email, password, role, ImagePath);
        this.Domaine = Domaine;
        this.Info = Info;
        this.location = location;
        this.numberOfEmployees = numberOfEmployees;
    }

 

   

    public String getDomaine() {
        return Domaine;
    }

    public void setDomaine(String Domaine) {
        this.Domaine = Domaine;
    }

    public String getInfo() {
        return Info;
    }

    public void setInfo(String Info) {
        this.Info = Info;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getNumberOfEmployees() {
        return numberOfEmployees;
    }

    public void setNumberOfEmployees(int numberOfEmployees) {
        this.numberOfEmployees = numberOfEmployees;
    }

    @Override
    public String toString() {
        return "Entreprise{ name=" + name + ", email=" + email + ", password=" + password  + ", Domaine=" + Domaine + ", Info=" + Info + ", location=" + location + ", numberOfEmployees=" + numberOfEmployees + "} \n";
    }
    
    
    
    
}

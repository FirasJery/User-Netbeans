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
public abstract class Utilisateur {

    protected int id;
    protected String name;
    protected String LastName;
    protected String UserName;
    protected String email;
    protected String password;
    protected String role;
    protected String ImagePath;
    protected int isBanned ; 

    public Utilisateur(int id, String name, String LastName, String UserName, String email, String password, String role, String ImagePath) {
        this.id = id;
        this.name = name;
        this.LastName = LastName;
        this.UserName = UserName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.ImagePath = ImagePath;
    }

    public Utilisateur(String name, String LastName, String UserName, String email, String password, String role, String ImagePath) {
        this.name = name;
        this.LastName = LastName;
        this.UserName = UserName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.ImagePath = ImagePath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String LastName) {
        this.LastName = LastName;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String ImagePath) {
        this.ImagePath = ImagePath;
    }

    @Override
    public String toString() {
        return "";
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Utilisateur other = (Utilisateur) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    public int getIsBanned() {
        return isBanned;
    }

    public void setIsBanned(int isBanned) {
        this.isBanned = isBanned;
    }

  
    
    


  

    public Utilisateur() {
    }

}

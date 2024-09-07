/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author Ghass
 */
public class Reclamation {
    
    private int id;
    private String desc;
    private String type;
    private int etat;
    private Utilisateur user;
    
  
    
    public Reclamation(){}

    public Reclamation(int id, String desc, String type, int etat, Utilisateur user) {
        this.id = id;
        this.desc = desc;
        this.type = type;
        this.etat = etat;
        this.user = user;
    }
    

    @Override
    public String toString() {
        return "reclamation{" + "id=" + id + ", desc=" + desc + ", type=" + type + ", etat=" + etat + ", id_user=" + user + '}';
    }

    public Reclamation(String desc, String type, int etat, Utilisateur id_user) {
        this.desc = desc;
        this.type = type;
        this.etat = etat;
        this.user = id_user;
    }

    public int getId() {
        return id;
    }

    public String getDesc() {
        return desc;
    }

    public String getType() {
        return type;
    }

    public int isEtat() {
        return etat;
    }

    public Utilisateur getId_user() {
        return user;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public void setId_user(Utilisateur id_user) {
        this.user = id_user;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + (int) (this.id ^ (this.id >>> 32));
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
        final Reclamation other = (Reclamation) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    public Utilisateur getUser() {
        return user;
    }

    public void setUser(Utilisateur user) {
        this.user = user;
    }

  
    
}

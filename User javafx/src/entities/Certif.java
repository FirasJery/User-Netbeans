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
public class Certif {
    
    private int id;
    private String nom;
    private String desc;
    private String badge;

    public Certif() {
    }

    public Certif(int id, String nom, String desc, String badge) {
        this.id = id;
        this.nom = nom;
        this.desc = desc;
        this.badge = badge;
    }

    public Certif(String nom, String desc, String badge) {
        this.nom = nom;
        this.desc = desc;
        this.badge = badge;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getDesc() {
        return desc;
    }

    public String getBadge() {
        return badge;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setBadge(String badge) {
        this.badge = badge;
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
        final Certif other = (Certif) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Certif{" + "id=" + id + ", nom=" + nom + ", desc=" + desc + ", badge=" + badge + '}';
    }
    
    
    
}

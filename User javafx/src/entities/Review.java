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
public class Review {
     private int id;
    private Utilisateur id_editeur;
    private Utilisateur id_user;
    private String message;
    private float note;
    
    
    public Review(){}

    public Review(int id, Utilisateur id_editeur, Utilisateur id_user, String message, float note) {
        this.id = id;
        this.id_editeur = id_editeur;
        this.id_user = id_user;
        this.message = message;
        this.note = note;
    }

    public Review(Utilisateur id_editeur, Utilisateur id_user, String message, float note) {
        this.id = id;
        this.id_editeur = id_editeur;
        this.id_user = id_user;
        this.message = message;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public Utilisateur getId_editeur() {
        return id_editeur;
    }

    public Utilisateur getId_user() {
        return id_user;
    }

    public String getMessage() {
        return message;
    }

    public float getNote() {
        return note;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setId_editeur(Utilisateur id_editeur) {
        this.id_editeur = id_editeur;
    }

    public void setId_user(Utilisateur id_user) {
        this.id_user = id_user;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setNote(float note) {
        this.note = note;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + (int) (this.id ^ (this.id >>> 32));
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
        final Review other = (Review) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
    
}

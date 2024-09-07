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
public class Reponse {
    private int id;
    private String objet;
    private String contenu;
    Reclamation idrec;

    public Reponse(int id, String objet,  String contenu, Reclamation idrec) {
        this.id = id;
        this.objet = objet;
        this.contenu = contenu; 
        this.idrec = idrec;
    }

    public Reponse() {
    }

    public Reponse(String objet, String contenu, Reclamation idrec) {
        this.objet = objet;
        this.contenu = contenu;
        this.idrec = idrec;
    }

    public int getId() {
        return id;
    }

    public String getObjet() {
        return objet;
    }

    public String getContenu() {
        return contenu;
    }

    public Reclamation getIdrec() {
        return idrec;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public void setIdrec(Reclamation idrec) {
        this.idrec = idrec;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + this.id;
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
        final Reponse other = (Reponse) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Reponse{" + "id=" + id + ", objet=" + objet + ", contenu=" + contenu + ", idrec=" + idrec + '}';
    }
    
    
    
    
}

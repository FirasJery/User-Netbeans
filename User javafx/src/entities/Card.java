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
public class Card {
    
    private int id;
    private String nom;
    private String prenom;
    private String date;
    private int cvc;
    private int zipcode;
    private String ville;
    private String num;
    private Utilisateur user;
     
public Card(){
}

    public Card(String nom, String prenom, String date, int cvc, int zipcode, String ville, String num, Utilisateur user) {
        this.nom = nom;
        this.prenom = prenom;
        this.date = date;
        this.cvc = cvc;
        this.zipcode = zipcode;
        this.ville = ville;
        this.num = num;
        this.user=user;
    }

    public Card(int id, String nom, String prenom, String date, int cvc, int zipcode, String ville, String num,Utilisateur user) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.date = date;
        this.cvc = cvc;
        this.zipcode = zipcode;
        this.ville = ville;
        this.num = num;
        this.user=user;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getDate() {
        return date;
    }

    public int getCvc() {
        return cvc;
    }

    public int getZipcode() {
        return zipcode;
    }

    public String getVille() {
        return ville;
    }

    public String getNum() {
        return num;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setCvc(int cvc) {
        this.cvc = cvc;
    }

    public void setSipcode(int zipcode) {
        this.zipcode = zipcode;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public Utilisateur getUser() {
        return user;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }

    public void setUser(Utilisateur user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (int) (this.id ^ (this.id >>> 32));
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
        final Card other = (Card) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "card{ nom=" + nom + ", prenom=" + prenom + ", date=" + date + ", cvc=" + cvc + ", zipcode=" + zipcode + ", ville=" + ville + ", num=" + num + '}';
    }
    
}

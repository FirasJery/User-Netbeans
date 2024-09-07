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
public class Facture {
    
    private int id;
    private float montant;
    private Wallet wallet_s;
    private String nom_certif;
    private Utilisateur user;

    public Facture() {
    }

    public Facture(int id, float montant, Wallet wallet_s, String nom_certif, Utilisateur user) {
        this.id = id;
        this.montant = montant;
        this.wallet_s = wallet_s;
        this.nom_certif = nom_certif;
        this.user = user;
    }

    public Facture(float montant, Wallet wallet_s, String nom_certif, Utilisateur user) {
        this.montant = montant;
        this.wallet_s = wallet_s;
        this.nom_certif = nom_certif;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public float getMontant() {
        return montant;
    }

    public Wallet getWallet_s() {
        return wallet_s;
    }

    public String getNom_certif() {
        return nom_certif;
    }

    public Utilisateur getUser() {
        return user;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMontant(float montant) {
        this.montant = montant;
    }

    public void setWallet_s(Wallet wallet_s) {
        this.wallet_s = wallet_s;
    }

    public void setNom_certif(String nom_certif) {
        this.nom_certif = nom_certif;
    }

    public void setUser(Utilisateur user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + this.id;
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
        final Facture other = (Facture) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Facture{" + "montant=" + montant + ", wallet_s=" + wallet_s + ", nom_certif=" + nom_certif + ", user=" + user + '}';
    }
    
    
    
    
}

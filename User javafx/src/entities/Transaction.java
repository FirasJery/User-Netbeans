/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.sql.Date;

/**
 *
 * @author Ghass
 */
public class Transaction {
    private int id;
    private Date date;
    private float montant;
    private Wallet s_wallet;
    private Wallet r_wallet;
    private int etat;

    public Transaction() {
    }

    public Transaction(int id, Date date, float montant, Wallet s_wallet, Wallet r_wallet, int etat) {
        this.id = id;
        this.date = date;
        this.montant = montant;
        this.s_wallet = s_wallet;
        this.r_wallet = r_wallet;
        this.etat = etat;
    }

    public Transaction(Date date, float montant, Wallet s_wallet, Wallet r_wallet, int etat) {
        this.id = id;
        this.date = date;
        this.montant = montant;
        this.s_wallet = s_wallet;
        this.r_wallet = r_wallet;
        this.etat = etat;
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public float getMontant() {
        return montant;
    }

    public Wallet getS_wallet() {
        return s_wallet;
    }

    public Wallet getR_wallet() {
        return r_wallet;
    }

    public int isEtat() {
        return etat;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setMontant(float montant) {
        this.montant = montant;
    }

    public void setS_wallet(Wallet s_wallet) {
        this.s_wallet = s_wallet;
    }

    public void setR_wallet(Wallet r_wallet) {
        this.r_wallet = r_wallet;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + (int) (this.id ^ (this.id >>> 32));
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
        final Transaction other = (Transaction) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
    
    
}

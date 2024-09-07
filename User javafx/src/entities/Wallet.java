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
public class Wallet {
    private int id;
    private String num_carte;
    private float solde;
    private float bonus;
    private String tel;
    private Utilisateur iduser;
    private String cle;

    public Wallet() {
    }

    
    
    public Wallet(int id, String num_carte, float solde, float bonus,String tel,Utilisateur iduser,String cle) {
        this.id = id;
        this.num_carte = num_carte;
        this.solde = solde;
        this.bonus = bonus;
        this.iduser = iduser;
        this.tel=tel;
        this.cle=cle;
    }

    public Wallet(String num_carte, float solde, float bonus,String tel,Utilisateur iduser,String cle) {
        this.num_carte = num_carte;
        this.solde = solde;
        this.bonus = bonus;
        this.tel=tel;
        this.iduser = iduser;
        this.cle=cle;
    }

    public String getCle() {
        return cle;
    }

    public void setCle(String cle) {
        this.cle = cle;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
    
    
    
    

    public int getId() {
        return id;
    }

    public String getNum_carte() {
        return num_carte;
    }

    public float getSolde() {
        return solde;
    }

    public float getBonus() {
        return bonus;
    }

    public void setId(int id) {
        this.id = id;
    }

   

    public Wallet(int id, String num_carte, float solde, float bonus, Utilisateur iduser, String cle) {
        this.id = id;
        this.num_carte = num_carte;
        this.solde = solde;
        this.bonus = bonus;
        this.iduser = iduser;
        this.cle = cle;
    }


    public void setNum_carte(String num_carte) {
        this.num_carte = num_carte;
    }

    public void setSolde(float solde) {
        this.solde = solde;
    }

    @Override
    public String toString() {
        return "wallet{" + "id=" + id + ", num_carte=" + num_carte + ", solde=" + solde + ", bonus=" + bonus + ", iduser=" + iduser + '}';
    }

    public Utilisateur getIduser() {
        return iduser;
    }

    public void setIduser(Utilisateur iduser) {
        this.iduser = iduser;
    }

    public void setBonus(float bonus) {
        this.bonus = bonus;
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
        final Wallet other = (Wallet) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
    
    
}

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
public class Passage {
    
    private int id;
    private int score;
    private int etat;
    private Test idtest;
    private Utilisateur iduser;

    public Passage() {
    }

    public Passage(int id, int score, int etat, Test idtest, Utilisateur iduser) {
        this.id = id;
        this.score = score;
        this.etat = etat;
        this.idtest = idtest;
        this.iduser = iduser;
    }

    public Passage(int score, int etat, Test idtest, Utilisateur iduser) {
        this.score = score;
        this.etat = etat;
        this.idtest = idtest;
        this.iduser = iduser;
    }

    public int getId() {
        return id;
    }

    public int getScore() {
        return score;
    }

    public int getEtat() {
        return etat;
    }

    public Test getIdtest() {
        return idtest;
    }

    public Utilisateur getIduser() {
        return iduser;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public void setIdtest(Test idtest) {
        this.idtest = idtest;
    }

    public void setIduser(Utilisateur iduser) {
        this.iduser = iduser;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this.id;
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
        final Passage other = (Passage) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Passage{" + "id=" + id + ", score=" + score + ", etat=" + etat + ", idtest=" + idtest + ", iduser=" + iduser + '}';
    }
    
    
    
}

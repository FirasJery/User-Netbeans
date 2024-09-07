/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author ASUS
 */
public class Postulation {

    private int id_postulation;
    private Offre o ;
    private Freelancer f;
    private int isAccepted ; 

    public Postulation(int id_postulation, Offre o, Freelancer f,  int isAccepted) {
        this.id_postulation = id_postulation;
        this.o = o;
        this.f = f;
        this.isAccepted = isAccepted;
    }

    public Postulation(Offre o, Freelancer f,  int isAccepted) {
        this.o = o;
        this.f = f;

        this.isAccepted = isAccepted;
    }

    public Postulation() {

    }

    public int getId_postulation() {
        return id_postulation;
    }

    public void setId_postulation(int id_postulation) {
        this.id_postulation = id_postulation;
    }

    public Offre getO() {
        return o;
    }

    public void setO(Offre o) {
        this.o = o;
    }

    public Freelancer getF() {
        return f;
    }

    public void setF(Freelancer f) {
        this.f = f;
    }

    public int getIsAccepted() {
        return isAccepted;
    }

    public void setIsAccepted(int isAccepted) {
        this.isAccepted = isAccepted;
    }

    @Override
    public String toString() {
        return "Postulation{, o=" + o.getTitle() + ", f=" + f.getName() + ", isAccepted=" + isAccepted + '}';
    }
    
    
    
    
}


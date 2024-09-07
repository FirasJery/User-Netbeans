/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;
import java.time.LocalDate;

/**
 *
 * @author ASUS
 */
public class Offre {

    int id_offre;
    private String title;
    private String description;
    private String category;
    private String type;
    private int duree;
    private int isAccepted ; //=0 todher 
    private int isFinished ; 
    Entreprise E ; 
    LocalDate date_debut;
    LocalDate date_fin; 



    public Offre() {
              this.date_debut = LocalDate.of(2000, 1, 1);
                this.date_fin = LocalDate.of(2000, 1, 1);
    }

    public Offre(int id_offre, String title, String description, String category, String type, int duree, int isAccepted, int isFinished, Entreprise E, LocalDate date_debut, LocalDate date_fin) {
        this.id_offre = id_offre;
        this.title = title;
        this.description = description;
        this.category = category;
        this.type = type;
        this.duree = duree;
        this.isAccepted = isAccepted;
        this.isFinished = isFinished;
        this.E = E;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
    }

    public Offre(String title, String description, String category, String type, int duree, int isAccepted, int isFinished, Entreprise E, LocalDate date_debut, LocalDate date_fin) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.type = type;
        this.duree = duree;
        this.isAccepted = isAccepted;
        this.isFinished = isFinished;
        this.E = E;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
    }
    

    public int getId_offre() {
        return id_offre;
    }

    public void setId_offre(int id_offre) {
        this.id_offre = id_offre;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public int getIsAccepted() {
        return isAccepted;
    }

    public void setIsAccepted(int isAccepted) {
        this.isAccepted = isAccepted;
    }

    public int getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(int isFinished) {
        this.isFinished = isFinished;
    }

    public Entreprise getE() {
        return E;
    }

    public void setE(Entreprise E) {
        this.E = E;
    }

    public LocalDate getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(LocalDate date_debut) {
        this.date_debut = date_debut;
    }

    public LocalDate getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(LocalDate date_fin) {
        this.date_fin = date_fin;
    }

    @Override
    public String toString() {
        return "Offre{" + "title=" + title + ", description=" + description + ", category=" + category + ", type=" + type + ", duree=" + duree + ", isAccepted=" + isAccepted + ", isFinished=" + isFinished + ", E=" + E.getName() + ", date_debut=" + date_debut + ", date_fin=" + date_fin + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + this.id_offre;
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
        final Offre other = (Offre) obj;
        if (this.id_offre != other.id_offre) {
            return false;
        }
        return true;
    }

    
    
    
    
}

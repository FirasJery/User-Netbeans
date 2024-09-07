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
public class Test {
    
    private int id;
    private String titre;
    private String categorie;
    private String desc;
    private Certif idcertif;
    private float prix;

    public Test() {
    }

    public Test(int id, String titre, String categorie, String desc, Certif idcertif,float prix) {
        this.id = id;
        this.titre = titre;
        this.categorie = categorie;
        this.desc = desc;
        this.idcertif = idcertif;
        this.prix=prix;
    }

    public Test(String titre, String categorie, String desc, Certif idcertif,float prix) {
        this.titre = titre;
        this.categorie = categorie;
        this.desc = desc;
        this.idcertif = idcertif;
        this.prix=prix;
    }

    public int getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public String getCategorie() {
        return categorie;
    }

    public String getDesc() {
        return desc;
    }

    public Certif getIdcertif() {
        return idcertif;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setIdcertif(Certif idcertif) {
        this.idcertif = idcertif;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    @Override
    public int hashCode() {
        int hash = 7;
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
        final Test other = (Test) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Test{" + "id=" + id + ", titre=" + titre + ", categorie=" + categorie + ", desc=" + desc + ", idcertif=" + idcertif + '}';
    }
    
    
    
}

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

    
public class Question {
    
    private int id;
    private String question;
    private String reponse;
    private int note;
    private Test idtest;
    private String choix1;
    private String choix2;
    private String choix3;
    private String typr;

    public Question() {
    }
 public Question( String question, String reponse, int note, Test idtest) {
        this.id = id;
        this.question = question;
        this.reponse = reponse;
        this.note = note;
        this.idtest = idtest;
      
    }
    public Question(int id, String question, String reponse, int note, Test idtest, String choix1, String choix2, String choix3, String typr) {
        this.id = id;
        this.question = question;
        this.reponse = reponse;
        this.note = note;
        this.idtest = idtest;
        this.choix1 = choix1;
        this.choix2 = choix2;
        this.choix3 = choix3;
        this.typr = typr;
    }

    public Question(String question, String reponse, int note, Test idtest, String choix1, String choix2, String choix3, String typr) {
        this.question = question;
        this.reponse = reponse;
        this.note = note;
        this.idtest = idtest;
        this.choix1 = choix1;
        this.choix2 = choix2;
        this.choix3 = choix3;
        this.typr = typr;
    }

    public int getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getReponse() {
        return reponse;
    }

    public int getNote() {
        return note;
    }

    public Test getIdtest() {
        return idtest;
    }

    public String getChoix1() {
        return choix1;
    }

    public String getChoix2() {
        return choix2;
    }

    public String getChoix3() {
        return choix3;
    }

    public String getTypr() {
        return typr;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public void setIdtest(Test idtest) {
        this.idtest = idtest;
    }

    public void setChoix1(String choix1) {
        this.choix1 = choix1;
    }

    public void setChoix2(String choix2) {
        this.choix2 = choix2;
    }

    public void setChoix3(String choix3) {
        this.choix3 = choix3;
    }

    public void setTypr(String typr) {
        this.typr = typr;
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
        final Question other = (Question) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Question{" + "id=" + id + ", question=" + question + ", reponse=" + reponse + ", note=" + note + ", idtest=" + idtest + ", choix1=" + choix1 + ", choix2=" + choix2 + ", choix3=" + choix3 + ", typr=" + typr + '}';
    }

    
    
    
}

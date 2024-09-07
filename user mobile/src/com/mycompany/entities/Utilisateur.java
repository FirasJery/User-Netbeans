/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entities;

/**
 *
 * @author Firas
 */
public class Utilisateur {

    private int id;
    private String name;
    private String LastName;
    private String UserName;
    private String email;
    private String password;
    private String role;
    private String ImagePath;
    private String info;
    private String adresse;
    private String domaine;
    private int nbe;
    private String bio;
    private String education;
    private String experience;
    private int rating;
    private int tj;
    private int isBanned;
    private int isVerified;

    public Utilisateur() {
    }

    public Utilisateur(int id, String name, String LastName, String UserName, String email, String password, String info, String adresse, String domaine, int nbe) {
        this.id = id;
        this.name = name;
        this.LastName = LastName;
        this.UserName = UserName;
        this.email = email;
        this.password = password;
        this.info = info;
        this.adresse = adresse;
        this.domaine = domaine;
        this.nbe = nbe;
    }

    public Utilisateur(int id, String name, String LastName, String UserName, String email, String password, String bio, String education, String experience) {
        this.id = id;
        this.name = name;
        this.LastName = LastName;
        this.UserName = UserName;
        this.email = email;
        this.password = password;
        this.bio = bio;
        this.education = education;
        this.experience = experience;
    }
    
    public Utilisateur(int id, String name, String LastName, String UserName, String email, String password, String role, String ImagePath) {
        this.id = id;
        this.name = name;
        this.LastName = LastName;
        this.UserName = UserName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.ImagePath = ImagePath;
    }

    public Utilisateur(String name, String LastName, String UserName, String email, String password, String role, String ImagePath) {
        this.name = name;
        this.LastName = LastName;
        this.UserName = UserName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.ImagePath = ImagePath;
    }

    public Utilisateur(int id, String name, String LastName, String UserName, String email, String password, String role, String ImagePath, String info, String adresse, String domaine, int nbe, int isBanned, int isVerified) {
        this.id = id;
        this.name = name;
        this.LastName = LastName;
        this.UserName = UserName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.ImagePath = ImagePath;
        this.info = info;
        this.adresse = adresse;
        this.domaine = domaine;
        this.nbe = nbe;
        this.isBanned = isBanned;
        this.isVerified = isVerified;
    }

    public Utilisateur(int id, String name, String LastName, String UserName, String email, String password, String role, String ImagePath, String bio, String education, String experience, int rating, int tj, int isBanned, int isVerified) {
        this.id = id;
        this.name = name;
        this.LastName = LastName;
        this.UserName = UserName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.ImagePath = ImagePath;
        this.bio = bio;
        this.education = education;
        this.experience = experience;
        this.rating = rating;
        this.tj = tj;
        this.isBanned = isBanned;
        this.isVerified = isVerified;
    }

    public Utilisateur(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String LastName) {
        this.LastName = LastName;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String ImagePath) {
        this.ImagePath = ImagePath;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getDomaine() {
        return domaine;
    }

    public void setDomaine(String domaine) {
        this.domaine = domaine;
    }

    public int getNbe() {
        return nbe;
    }

    public void setNbe(int nbe) {
        this.nbe = nbe;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getTj() {
        return tj;
    }

    public void setTj(int tj) {
        this.tj = tj;
    }

    public int getIsBanned() {
        return isBanned;
    }

    public void setIsBanned(int isBanned) {
        this.isBanned = isBanned;
    }

    public int getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(int isVerified) {
        this.isVerified = isVerified;
    }

    @Override
    public String toString() {
        return "Utilisateur{" + "id=" + id + ", name=" + name + ", LastName=" + LastName + ", UserName=" + UserName + ", email=" + email + ", password=" + password + ", role=" + role + ", ImagePath=" + ImagePath + ", info=" + info + ", adresse=" + adresse + ", domaine=" + domaine + ", nbe=" + nbe + ", bio=" + bio + ", education=" + education + ", experience=" + experience + ", rating=" + rating + ", tj=" + tj + ", isBanned=" + isBanned + ", isVerified=" + isVerified + '}';
    }
}

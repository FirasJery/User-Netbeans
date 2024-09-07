/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;


/**
 *
 * @author Firas
 */
public class Freelancer  extends Utilisateur{
        private String bio ; 
        private String experience ; 
        private String education ; 
        private int total_jobs ; 
        private float rating ; 

    public Freelancer(String bio, String experience, String education, int total_jobs, float rating, int id, String name, String LastName, String UserName, String email, String password, String role, String ImagePath) {
        super(id, name, LastName, UserName, email, password, role, ImagePath);
        this.bio = bio;
        this.experience = experience;
        this.education = education;
        this.total_jobs = total_jobs;
        this.rating = rating;
    }

    public Freelancer(String bio, String experience, String education, int total_jobs, float rating, String name, String LastName, String UserName, String email, String password, String role, String ImagePath) {
        super(name, LastName, UserName, email, password, role, ImagePath);
        this.bio = bio;
        this.experience = experience;
        this.education = education;
        this.total_jobs = total_jobs;
        this.rating = rating;
    }

    public Freelancer() {
    }

 
   
    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public int getTotal_jobs() {
        return total_jobs;
    }

    public void setTotal_jobs(int total_jobs) {
        this.total_jobs = total_jobs;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Freelancer{" + "bio=" + bio + ", experience=" + experience + ", education=" + education + ", total_jobs=" + total_jobs + ", rating=" + rating + '}';
    }

    
   

    
    
    
}

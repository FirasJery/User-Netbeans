
package entities;


import java.util.Objects;

public class User
{

    private int id;
    private String name;
    private String LastName;
    private String UserName;
    private String email;
    private String password;
    private String role;
    private String ImagePath;

    public User() {
    }

    public User(int id, String name, String lastName, String userName, String email, String password, String role, String imagePath) {
        this.id = id;
        this.name = name;
        this.LastName = lastName;
        this.UserName = userName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.ImagePath = imagePath;
    }



    public User( String name, String lastName, String userName, String email, String password, String role, String imagePath) {
        this.name = name;
        this.LastName = lastName;
        this.UserName = userName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.ImagePath = imagePath;
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

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
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

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", LastName='" + LastName + '\'' +
                ", UserName='" + UserName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", ImagePath='" + ImagePath + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

}


package entities;



public class SessionManager {
    private static SessionManager instance;
    private User currentUser;
    // other instance variables
    
    private SessionManager() {
        // constructor
    }
    
    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }
    
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }
    
    public User getCurrentUser() {
        return this.currentUser;
    }
    
    // other methods
}

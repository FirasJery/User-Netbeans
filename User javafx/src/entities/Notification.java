/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.time.LocalDateTime;

/**
 *
 * @author Ghass
 */
public class Notification {
    
    private int id;
    private int userId;
    private String message;
    private LocalDateTime timestamp;
    private int isRead;

    public Notification() {
    }

    public Notification(int id, int userId, String message, LocalDateTime timestamp, int isRead) {
        this.id = id;
        this.userId = userId;
        this.message = message;
        this.timestamp = timestamp;
        this.isRead = isRead;
    }

    public Notification(int userId, String message, LocalDateTime timestamp, int isRead) {
        this.userId = userId;
        this.message = message;
        this.timestamp = timestamp;
        this.isRead = isRead;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getIsRead() {
        return isRead;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + this.id;
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
        final Notification other = (Notification) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Notification{" + "id=" + id + ", userId=" + userId + ", message=" + message + ", timestamp=" + timestamp + ", isRead=" + isRead + '}';
    }
    
    
    
    
}

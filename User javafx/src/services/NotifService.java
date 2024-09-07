/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Notification;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import utils.DataSource;

/**
 *
 * @author Ghass
 */
public class NotifService {

    Connection cnx = DataSource.getInstance().getCnx();

    public void insertNotification(Notification notification) throws SQLException {
        String sql = "INSERT INTO notification (id_user, message, date, isRead) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = cnx.prepareStatement(sql);
        statement.setInt(1, notification.getUserId());
        statement.setString(2, notification.getMessage());
        statement.setTimestamp(3, Timestamp.valueOf(notification.getTimestamp()));
        statement.setInt(4, notification.getIsRead());
        statement.executeUpdate();
    }

    public List<Notification> getNotificationsForUser(int userId) throws SQLException {
        String sql = "SELECT * FROM notification WHERE id_user = ?";
        PreparedStatement statement = cnx.prepareStatement(sql);
        statement.setInt(1, userId);
        ResultSet resultSet = statement.executeQuery();
        List<Notification> notifications = new ArrayList<>();
        while (resultSet.next()) {
            Notification notification = new Notification(
                    resultSet.getInt("id"),
                    resultSet.getInt("id_user"),
                    resultSet.getString("message"),
                    resultSet.getTimestamp("date").toLocalDateTime(),
                    resultSet.getInt("isRead")
            );
            notifications.add(notification);
        }
        return notifications;
    }

    public List<String> getNotifmsg(int userId) throws SQLException {

        String sql = "SELECT message,date FROM notification WHERE (id_user = ? AND isRead=0) ";
        PreparedStatement statement = cnx.prepareStatement(sql);
        statement.setInt(1, userId);
        ResultSet resultSet = statement.executeQuery();
        List<String> notifications = new ArrayList<>();
        while (resultSet.next()) {
            String msg = resultSet.getString("message");
            String dtte = resultSet.getTimestamp("date").toString();
            String notif = dtte + ":" + msg;

            notifications.add(notif);
        }
        return notifications;
    }

    public void markNotificationAsRead(int notificationId) throws SQLException {
        String sql = "UPDATE notifications SET isRead = 1 WHERE id = ?";
        PreparedStatement statement = cnx.prepareStatement(sql);
        statement.setInt(1, notificationId);
        statement.executeUpdate();
    }

}

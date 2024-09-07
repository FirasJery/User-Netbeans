package services;

import entities.User;
import utils.DataSource;

import java.sql.*;
import java.util.*;


public class ServiceUser implements IService<User> {
    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouter(User u) {
        try {
            String req = "INSERT INTO utilisateur (name, LastName ,UserName, email, password, role, ImagePath) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, u.getName());
            ps.setString(2, u.getLastName());
            ps.setString(3, u.getUserName());
            ps.setString(4, u.getEmail());
            ps.setString(5, u.getPassword());
            ps.setString(6, u.getRole());
            ps.setString(7, u.getImagePath());
            ps.executeUpdate();
            System.out.println("User created !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void supprimer(int id) {
        try {
            String req = "DELETE FROM utilisateur WHERE id = " + id;
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("user deleted !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(User u) {
        try {
            String req = "UPDATE utilisateur SET name = ?, LastName = ?, UserName = ?, email = ?, password = ?, role = ?, ImagePath = ? WHERE id = ?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, u.getName());
            ps.setString(2, u.getLastName());
            ps.setString(3, u.getUserName());
            ps.setString(4, u.getEmail());
            ps.setString(5, u.getPassword());
            ps.setString(6, u.getRole());
            ps.setString(7, u.getImagePath());
            ps.setInt(8, u.getId());
            ps.executeUpdate();
            System.out.println("user updated !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<User> getAll() {
        List<User> list = new ArrayList<>();
        try {
            String req = "Select * from utilisateur";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                User u;
                u = new User(rs.getInt("id"), rs.getString("name"), rs.getString("LastName"), rs.getString("UserName"), rs.getString("email"), rs.getString("password"), rs.getString("role"), rs.getString("ImagePath"));
                list.add(u);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }

    @Override
    public User getOneById(int id) {
        User u = new User();
        try {
            String req = "Select * from utilisateur where id = ? ";
            PreparedStatement st = cnx.prepareStatement(req);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                u = new User(rs.getInt("id"), rs.getString("name"), rs.getString("LastName"), rs.getString("UserName"), rs.getString("email"), rs.getString("password"), rs.getString("role"), rs.getString("ImagePath"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return u;
    }

    public int authentification(String email, String password) {
        int id = -1;
        try {
            String req = "SELECT * from `utilisateur` WHERE email = ? AND password = ? ";
            PreparedStatement st = cnx.prepareStatement(req);
            st.setString(1, email);
            st.setString(2, password);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return id;
    }


    public List<User> getAllSortedByUsernameAscending() {
        List<User> userList = getAll();
        Collections.sort(userList, Comparator.comparing(User::getUserName));
        return userList;
    }

    public List<User> getAllSortedByUsernameDescending() {
        List<User> userList = getAllSortedByUsernameAscending();
        Collections.reverse(userList);
        return userList;
    }

    public List<User> searchByUsername(String username) {
        List<User> searchResults = new ArrayList<>();
        try {
            String req = "SELECT * FROM utilisateur WHERE UserName LIKE ?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, "%" + username + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User(rs.getInt("id"), rs.getString("name"), rs.getString("LastName"), rs.getString("UserName"), rs.getString("email"), rs.getString("password"), rs.getString("role"), rs.getString("ImagePath"));
                searchResults.add(user);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return searchResults;
    }


    public Map<String, Integer> getUserRoleStatistics() {
        Map<String, Integer> statistics = new HashMap<>();
        try {
            String query = "SELECT role, COUNT(*) AS count FROM utilisateur GROUP BY role";
            PreparedStatement statement = cnx.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String role = resultSet.getString("role");
                int count = resultSet.getInt("count");
                statistics.put(role, count);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return statistics;
    }

    public int ChercherMail(String email) {
        int id = -1;
        try {
            String req = "SELECT * from `utilisateur` WHERE email = ? ";
            PreparedStatement st = cnx.prepareStatement(req);
            st.setString(1, email);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println("id in search " + id);
        return id;
    }

    public void UpdatePassword(String email, String password) {
        try {
            String req = "UPDATE user SET password = ? WHERE email = ?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, password);
            ps.setString(2, email);
            ps.executeUpdate();
            System.out.println("password updated !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}

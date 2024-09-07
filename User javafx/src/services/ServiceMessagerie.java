/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Conversation;
import utils.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ASUS
 */
public class ServiceMessagerie implements IService<Conversation> {

    private static ServiceMessagerie instance;
    private List<Conversation> cl = new ArrayList();
    private final Connection cnx;
    private ServiceUser su = new ServiceUser();

    public ServiceMessagerie() {
        cnx = DataSource.getInstance().getCnx();
    }

    public static ServiceMessagerie getInstance() {
        if (instance == null) {
            instance = new ServiceMessagerie();
            instance.setList(instance.getAll());
        }
        return instance;
    }

    @Override
    public void ajouter(Conversation t) {
        List<Conversation> s = this.getAll();
        String status;
        if (s != null) {
            for (int i = 0; i < s.size(); i++) {
                if (t.getId_convo() == s.get(i).getId_convo()) {
                    return;
                }
            }
        }
        if (t.get_status_src()) {
            status = "true";
        } else {
            status = "false";
        }
        try {
            PreparedStatement ps;
            ps = cnx.prepareStatement("INSERT INTO CONVERSATION(id_source, id_dest, status_src, status_dest) VALUES(?, ?, 'true', 'pending')");
            ps.setString(1, t.getId_source());
            ps.setString(2, t.getId_dest());
            ps.executeUpdate();
            cl.add(t);
        } catch (SQLException ex) {
            Logger.getLogger(ServiceMessagerie.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Conversation> getAll() {
        Statement st;
        List<Conversation> l = new ArrayList();
        int i = 0;
        try {
            st = cnx.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM CONVERSATION");

            while (rs.next()) {
                boolean status = false;
                if (rs.getString(4).compareTo("true") == 0) {
                    status = true;
                }
                Conversation c = new Conversation(rs.getInt(1), rs.getString(2), rs.getString(3), status);
                l.add(c);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return l;
    }

    @Override
    public Conversation getOneById(int id) {
        PreparedStatement ps;
        Conversation c = null;
        try {
            ps = cnx.prepareStatement("SELECT * FROM CONVERSATION WHERE(ID = ?)");
            ps.setString(1, Integer.toString(id));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                boolean status = false;
                if (rs.getString(4).compareTo("true") == 0) {
                    status = true;
                }
                c = new Conversation(rs.getInt(1), rs.getString(2), rs.getString(3), status);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return c;
    }

    public Conversation findConvo(String id1, String id2) {
        PreparedStatement ps;

        Conversation c = null;
        try {
            System.out.println("FIND CONVO " + id1 + " " + id2);
            ps = cnx.prepareStatement("SELECT * FROM CONVERSATION WHERE(((ID_SOURCE = ?)OR(ID_SOURCE = ?))AND((ID_DEST = ?)OR(ID_DEST = ?)))");
            ps.setString(1, id1);
            ps.setString(2, id2);
            ps.setString(3, id1);
            ps.setString(4, id2);
            ResultSet rs = ps.executeQuery();
            rs.first();
            boolean status = false;
            if (rs.getString(4).compareTo("false") != 0) {
                status = true;
            }
            c = new Conversation(rs.getInt("id"), rs.getString(2), rs.getString(3), status);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return c;
    }

    public ResultSet findConvo(String id1, String id2, int i) {
        PreparedStatement ps;
        try {
            ps = cnx.prepareStatement("SELECT * FROM CONVERSATION WHERE((ID_SOURCE = ?)OR(ID_SOURCE = ?)AND(ID_DEST = ?)OR(ID_DEST = ?))");
            ps.setString(1, id1);
            ps.setString(2, id2);
            ps.setString(3, id1);
            ps.setString(4, id2);
            return ps.executeQuery();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public List<String> findContacts(String id) {
        PreparedStatement ps;
        List<String> contacts = new ArrayList();
        try {
            ps = cnx.prepareStatement("SELECT * FROM CONVERSATION WHERE(((ID_SOURCE = ?)AND(STATUS_SRC != 'false'))OR((ID_DEST = ?)AND(STATUS_DEST != 'false')))");

            ps.setString(1, id);
            ps.setString(2, id);

            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                System.out.println("boucle");
                boolean status = false;
                
                if (rs.getString("id_source").compareTo(id) == 0) {
                    contacts.add(su.getOneById(rs.getInt("id_dest")).getUserName());
                }
                if (rs.getString("id_dest").compareTo(id) == 0) {
                    contacts.add(su.getOneById(rs.getInt("id_source")).getUserName());
                }
                
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return contacts;
    }

    @Override
    public void modifier(Conversation t) {
        PreparedStatement ps;
        try {
            ps = cnx.prepareStatement("UPDATE CONVERSATION SET(STATUS = ?) WHERE (ID = ?)");
            ps.setString(1, String.valueOf(t.get_status_src()));
            ps.setString(2, String.valueOf(t.getId_convo()));
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void updateStatus(String idc, String id, String status) {
        PreparedStatement ps;
        try {
            ps = cnx.prepareStatement("SELECT * FROM CONVERSATION WHERE((((ID_SOURCE = ?)AND(STATUS_SRC != 'false'))OR((ID_DEST = ?)AND(STATUS_DEST != 'false')))AND(ID = ?))");
            ps.setString(1, id);
            ps.setString(2, id);
            ps.setString(3, idc);
            ResultSet rs = ps.executeQuery();
            rs.first();
            if (rs.getString("id_source").compareTo(id) == 0) {
                    PreparedStatement ips = cnx.prepareStatement("UPDATE CONVERSATION SET STATUS_SRC = ? WHERE (ID = ?)");
                    ips.setString(1, status);
                    ips.setString(2, idc);
                    ips.executeUpdate();
                }
                if (rs.getString("id_dest").compareTo(id) == 0) {
                    
                    PreparedStatement ips = cnx.prepareStatement("UPDATE CONVERSATION SET STATUS_DEST = ? WHERE (ID = ?)");
                    ips.setString(1, status);
                    ips.setString(2, idc);
                    ips.executeUpdate();

                }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public List<String> getStatus(String id) {
        PreparedStatement ps;
        List<String> stats = new ArrayList();
        String q = "SELECT (CASE WHEN (id_source = ?)AND(status_src != 'false') then status_src WHEN (id_dest = ?)AND(status_dest != 'false') then status_dest END) AS STAT FROM CONVERSATION HAVING(STAT != '')";
        try {
            ps = cnx.prepareStatement(q);
            ps.setString(1, id);
            ps.setString(2, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                stats.add(rs.getString("stat"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return stats;
    }

    @Override
    public void supprimer(int d) {
        PreparedStatement ps;
        try {
            ps = cnx.prepareStatement("DELETE FROM CONVERSATION WHERE(ID = ?)");
            ps.setString(1, String.valueOf(d));
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void updateMessage(Conversation c, String user_id, String msg) {
        PreparedStatement ps;
        String dest;
        if (c.getId_dest().compareTo(user_id) == 0) {
            dest = c.getId_source();
        } else {
            dest = c.getId_dest();
        }
        try {
            ps = cnx.prepareStatement("INSERT INTO MESSAGE(id_source, id_dest, message, id_convo) VALUES(?, ?, ?, ?)");
            ps.setString(1, user_id);
            ps.setString(2, dest);
            ps.setString(3, msg);
            ps.setString(4, String.valueOf(c.getId_convo()));
            ps.executeUpdate();
            //c.addMessage(msg);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public ResultSet getMessages(int idc) {
        PreparedStatement ps;
        try {
            ps = cnx.prepareStatement("SELECT * FROM MESSAGE WHERE(ID_CONVO = ?)");
            ps.setString(1, String.valueOf(idc));
            ResultSet rs = ps.executeQuery();
            return rs;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public String getLastMessage(int idc) {
        PreparedStatement ps;
        try {
            ps = cnx.prepareStatement("SELECT * FROM MESSAGE WHERE(ID_CONVO = ?)");
            ps.setString(1, String.valueOf(idc));
            ResultSet rs = ps.executeQuery();

            rs.last();
            
            return rs.getString("message");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return "";
    }

    public String getLastSource(int idc) {
        PreparedStatement ps;
        try {
            ps = cnx.prepareStatement("SELECT * FROM MESSAGE WHERE(ID_CONVO = ?)");
            ps.setString(1, String.valueOf(idc));
            ResultSet rs = ps.executeQuery();
            rs.last();
            return rs.getString("id_source");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public void setList(List<Conversation> l) {
        this.cl = l;
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pmanager;

import java.sql.Connection;

/**
 *
 * @author Nervaner
 */
public class PermissionFactory {
    boolean adminFlag;
    String userName;
    Connection con;
    
    
    
    public boolean Authentication(String login, String password) {
        try {
            java.sql.Statement s = con.createStatement();
            java.sql.ResultSet rs = s.executeQuery("select * from users where login = '" + login + "'");
            if (!rs.next() || !rs.getString("password").equals(password))
                return false;
            adminFlag = rs.getString("adminflag").equals("t");
        } catch (Exception e) {
            System.out.println("Failed to execute login query.");
        }
        return true;
    }
}

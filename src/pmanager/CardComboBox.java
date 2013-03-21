/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pmanager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JComboBox;

/**
 *
 * @author Kel
 */
public class CardComboBox extends JComboBox implements CardDataGrabber{
    private String columnName;
    private HashMap data;
    //private Connection con;
    
    public CardComboBox(String columnName, Connection con, String query) {
        this.columnName = columnName;
        //this.con = con;
        data = new HashMap();
        try {
            Statement s = con.createStatement();
            String q = "SELECT id, name FROM " + query;
            ResultSet rs = s.executeQuery(q);
            while (rs.next()) {
                data.put(rs.getString(2), rs.getString(1));
                addItem(rs.getString(2));
            }
            s.close();
                    
        } catch (Exception e) {
            System.out.println("CardComboBox: failed to exec query");
        }
    }
    
    @Override
    public String getColumnName() {
        return columnName;
    }
    
    @Override
    public String getData(){
        return (String)data.get(getSelectedItem());
    }
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pmanager.card;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JComboBox;
import pmanager.DatabaseConnection;

/**
 *
 * @author Kel
 */
public class CardComboBox extends JComboBox implements CardDataGrabber{
    private String columnName;
    private ArrayList<Object[]> data;
    
    public CardComboBox(String columnName, DatabaseConnection con, String query) {
        this.columnName = columnName;
        try {
            data = con.execQuery("SELECT id, name FROM " + query);
            for (int i = 0; i < data.size(); ++i) {
                addItem(data.get(i)[1]);
            }
            /*
            Statement s = con.createStatement();
            String q = "SELECT id, name FROM " + query;
            ResultSet rs = s.executeQuery(q);
            while (rs.next()) {
                data.put(rs.getString(2), rs.getString(1));
                addItem(rs.getString(2));
            }
            s.close();
             */       
        } catch (Exception e) {
            //TODO поправить эксепшен
            System.out.println("CardComboBox: failed to exec query");
        }
    }
    
    @Override
    public String getColumnName() {
        return columnName;
    }
    
    @Override
    public String getData(){
        return (String)data.get(getSelectedIndex())[1];
    }
    
}

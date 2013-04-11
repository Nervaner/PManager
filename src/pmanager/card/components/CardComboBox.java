/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pmanager.card.components;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JComboBox;
import pmanager.DatabaseConnection;
import pmanager.TableCellModel;
import pmanager.card.CardComponentInterface;

/**
 *
 * @author Kel
 */
public class CardComboBox extends JComboBox implements CardComponentInterface{
    private String columnName;
    private ArrayList<Object[]> data;
    
    public CardComboBox() {
    
    }
    
    public CardComboBox(String columnName, DatabaseConnection con, String query) {
        this.columnName = columnName;
        try {
            data = con.execQuery("SELECT id, name FROM " + query);
            for (int i = 0; i < data.size(); ++i) {
                addItem(data.get(i)[1]);
            }
            //TODO надо как то выделить ту строку, которая сейчас используется
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
        return data.get(getSelectedIndex())[0].toString();
    }

    @Override
    public void init(TableCellModel tcm, DatabaseConnection con, int index) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pmanager.card.components;

import java.awt.Dimension;
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
    
    @Override
    public String getColumnName() {
        return columnName;
    }
    
    @Override
    public String getData(){
        Object obj = data.get(getSelectedIndex())[0];
        if (obj.getClass().getName().equals("java.lang.Integer")) {
            return obj.toString();
        } else {
            return "'" + obj.toString() + "'";
        }
    }

    @Override
    public void init(Dimension dim, TableCellModel tcm, DatabaseConnection con, Object value) {
        this.columnName = tcm.columnName;
        this.setPreferredSize(dim);
        try {
            data = con.getDataFromLink(tcm.linkedTable);
            for (int i = 0; i < data.size(); ++i) {
                addItem(data.get(i)[1]);
            }
            if (value != null) {
                for (int i = 0; i < data.size(); ++i) {
                    if (data.get(i)[0].equals(value)) {
                        this.setSelectedIndex(i);    
                        break;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("[CardComboBox] failed to exec query");
        }
    }

    
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pmanager.card;

import pmanager.card.CardDataGrabber;
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
public class CardFlagComboBox extends JComboBox implements CardDataGrabber{
    private String columnName;
    
    public CardFlagComboBox(String columnName, int index) {
        this.columnName = columnName;
        
        addItem("false");
        addItem("true");
        setSelectedIndex(index);
    }
    
    @Override
    public String getColumnName() {
        return columnName;
    }
    
    @Override
    public String getData(){
        return Integer.toString(getSelectedIndex());
    }
    
}

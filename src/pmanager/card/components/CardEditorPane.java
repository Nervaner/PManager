/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pmanager.card.components;

import javax.swing.JEditorPane;
import pmanager.card.CardComponentInterface;

/**
 *
 * @author Nervaner
 */
public class CardEditorPane extends JEditorPane implements CardComponentInterface {
    private String columnName;
    private boolean intFlag;
    
    public CardEditorPane(String columnName) {
        this.columnName = columnName;
    }

    public CardEditorPane(String columnName, String text) {
        super("", text);
        this.columnName = columnName;
    }
    
    public CardEditorPane(String columnName, int value) {
        super("", Integer.toString(value));
        this.columnName = columnName;
    }
    
    @Override
    public String getColumnName() {
        return columnName;
    }
    
    @Override
    public String getData(){
        return "'" + getText() + "'";
    }

    @Override
    public void init(String columnName, Object[] args) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

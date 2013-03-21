/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pmanager;

import javax.swing.JEditorPane;

/**
 *
 * @author Nervaner
 */
public class CardEditorPane extends JEditorPane implements CardDataGrabber {
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
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pmanager.card;

import javax.swing.JTextField;

/**
 *
 * @author Kel
 */
public class CardTextField extends JTextField implements CardDataGrabber {
    private String columnName;
    private boolean intFlag;

    public CardTextField(String columnName, String text) {
        super(text);
        intFlag = false;
        this.columnName = columnName;
    }
    
    public CardTextField(String columnName, int value) {
        super(Integer.toString(value));
        intFlag = true;
        this.columnName = columnName;
    }
    
    @Override
    public String getColumnName() {
        return columnName;
    }
    
    @Override
    public String getData(){
        if (intFlag) {
            return getText();
        } else {
            return "'" + getText() + "'";
        }
    }
    
}

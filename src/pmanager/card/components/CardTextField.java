/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pmanager.card.components;

import javax.swing.JTextField;
import pmanager.card.CardComponentInterface;

/**
 *
 * @author Kel
 */
public class CardTextField extends JTextField implements CardComponentInterface {
    private String columnName;
    private boolean intFlag;
    
    public CardTextField() {
        super();
    }

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

    @Override
    public void init(String columnName, Object[] args) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}

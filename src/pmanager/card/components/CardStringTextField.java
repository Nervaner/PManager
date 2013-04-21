/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pmanager.card.components;

import java.awt.Dimension;
import javax.swing.JTextField;
import pmanager.DatabaseConnection;
import pmanager.TableCellModel;
import pmanager.card.CardComponentInterface;

/**
 *
 * @author Kel
 */
public class CardStringTextField extends JTextField implements CardComponentInterface {
    private String columnName;
    
    public CardStringTextField() {
        super();
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
    public void init(Dimension dim, TableCellModel tcm, DatabaseConnection con, Object value) {
        this.columnName = tcm.columnName;
        this.setPreferredSize(dim);
        if (value != null) {
            this.setText((String)value);
        }
    }
    
}

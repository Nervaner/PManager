/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pmanager.card.components;

import javax.swing.JTextField;
import pmanager.DatabaseConnection;
import pmanager.TableCellModel;
import pmanager.card.CardComponentInterface;

/**
 *
 * @author Kel
 */
public class CardIntTextField extends JTextField implements CardComponentInterface {
    private String columnName;
    
    public CardIntTextField() {
        super();
    }

    @Override
    public String getColumnName() {
        return columnName;
    }
    
    @Override
    public String getData(){
        return getText();
    }

    @Override
    public void init(TableCellModel tcm, DatabaseConnection con, int index) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}

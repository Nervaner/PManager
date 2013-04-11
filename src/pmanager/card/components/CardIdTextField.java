/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pmanager.card.components;

import java.awt.Color;
import javax.swing.JTextField;
import pmanager.DatabaseConnection;
import pmanager.TableCellModel;
import pmanager.card.CardComponentInterface;

/**
 *
 * @author Kel
 */
public class CardIdTextField extends JTextField implements CardComponentInterface {
    private String columnName;
    
    public CardIdTextField() {
        super("0");
        this.setEditable(false);
        this.setBackground(Color.lightGray);
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
    public void init(TableCellModel tcm, DatabaseConnection con, Object value) {
        this.columnName = tcm.columnName;
        if (value != null) {
            this.setText(value.toString());
        }
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pmanager.card.components;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.jdesktop.swingx.JXDatePicker;
import pmanager.DatabaseConnection;
import pmanager.TableCellModel;
import pmanager.card.CardComponentInterface;

/**
 *
 * @author Kel
 */
public class CardDatePicker extends JXDatePicker implements CardComponentInterface {
    private String columnName;
    
    public CardDatePicker() {
        super(new Date());
    }
    
    public CardDatePicker(String columnName, Date selected) {
        super(selected);
        this.columnName = columnName;
    }
    
    @Override
    public String getColumnName() {
        return columnName;
    }
    
    @Override
    public String getData(){
        SimpleDateFormat sf = new SimpleDateFormat("dd.MM.yyyy");
        return "'" + sf.format(getDate()) + "'";
    }

    @Override
    public void init(TableCellModel tcm, DatabaseConnection con, int index) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

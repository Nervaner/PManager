/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pmanager.card;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.jdesktop.swingx.JXDatePicker;

/**
 *
 * @author Kel
 */
public class CardDatePicker extends JXDatePicker implements CardDataGrabber {
    private String columnName;
    
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
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pmanager.card.components;

import java.awt.Dimension;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.SpringLayout;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.jdesktop.swingx.JXDatePicker;
import pmanager.DatabaseConnection;
import pmanager.TableCellModel;
import pmanager.card.CardComponentInterface;

/**
 *
 * @author Kel
 */
public class CardDateTimeField extends JPanel implements CardComponentInterface{
    private String columnName;
    private JXDatePicker datePicker;
    private JSpinner timePicker;
    
    public CardDateTimeField() {
        super();
        timePicker = new JSpinner( new SpinnerDateModel() );
        datePicker = new JXDatePicker(new Date());
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timePicker, "HH:mm");
        timeEditor.getTextField().setEditable(false);
        timePicker.setEditor(timeEditor);
        timePicker.setValue(new Date()); 
    }
    
    @Override
    public String getColumnName() {
        return columnName;
    }
    
    @Override
    public String getData(){
        SimpleDateFormat sf1 = new SimpleDateFormat("dd.MM.yyyy");
        SimpleDateFormat sf2 = new SimpleDateFormat("HH:mm");
        System.out.println("'" + sf1.format(datePicker.getDate()) + ", " + sf2.format((Date)timePicker.getValue()) + "'");
        return "'" + sf1.format(datePicker.getDate()) + ", " + sf2.format((Date)timePicker.getValue()) + "'";
    }

    @Override
    public void init(Dimension dim, TableCellModel tcm, DatabaseConnection con, Object value) {
        this.columnName = tcm.columnName;
        Dimension panelDim = new Dimension(dim.width, dim.height * 2 + 5);
        SpringLayout sl = new SpringLayout();
        this.setLayout(sl);
        datePicker.setPreferredSize(dim);
        timePicker.setPreferredSize(dim);
        this.setPreferredSize(panelDim);
        this.add(datePicker);
        this.add(timePicker);
        sl.putConstraint(SpringLayout.NORTH, datePicker, 0, SpringLayout.NORTH, this);
        sl.putConstraint(SpringLayout.WEST, datePicker, 0, SpringLayout.WEST, this);
        
        sl.putConstraint(SpringLayout.SOUTH, timePicker, 0, SpringLayout.SOUTH, this);
        sl.putConstraint(SpringLayout.WEST, timePicker, 0, SpringLayout.WEST, this);
        
        if (value != null) {
            datePicker.setDate((Date)value);
            timePicker.setValue(value);
        }
    }
}

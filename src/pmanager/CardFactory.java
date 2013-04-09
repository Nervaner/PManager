/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pmanager;

import pmanager.card.CardComponentInterface;
import pmanager.card.components.CardDatePicker;
import pmanager.card.components.CardComboBox;
import pmanager.card.components.CardTextField;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.Date;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import javax.swing.*;
import org.jdesktop.swingx.JXDatePicker;
import pmanager.TableCellModel;
import pmanager.TableDataModel;
import pmanager.TableInternalFrame;
import pmanager.card.components.CardComboBox;
import pmanager.card.CardComponentInterface;
import pmanager.card.components.CardDatePicker;
import pmanager.card.components.CardFlagComboBox;
import pmanager.card.CardIF;
import pmanager.card.components.CardTextField;


/**
 *
 * @author Nervaner
 */
public class CardFactory {
    private DatabaseConnection con;
    
    
    
    
    public CardFactory(DatabaseConnection c) {
        con = c;
    }
    
    public void deleteRow(TableDataModel tdm, int index){
        if (index == -1) 
            return;
        //TODO а это вообще работало?
        //Object[] row = tdm.getRow(index);
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM ");
        sb.append(tdm.tableName);
        sb.append(" WHERE ");
        for (int i = 0; i < tdm.cellsModel.length; ++i) {
            if (i != 0)
                sb.append(" and ");
            sb.append(tdm.cellsModel[i].columnName);
            sb.append("='");
            sb.append(tdm.getValueAt(index, i));
            sb.append("'");
        }
        
        try {
            con.execUpdate(sb.toString());
        } catch (Exception e) {
            System.out.println("failed to delete row");
        }
    }
            
    public void grabCardData(CardIF card, String tableName) {
        Component[] com = card.getContentPane().getComponents();
        StringBuilder sb = new StringBuilder();
                        
        sb.append("UPDATE OR INSERT INTO ");
        sb.append(tableName);
        sb.append(" (");
        int cnIndex = sb.length();        
        sb.append(") VALUES (");
        boolean first = true;
        for (Component c: com) {
            if (c instanceof CardComponentInterface) {
                
                sb.insert(cnIndex, ((CardComponentInterface)c).getColumnName());
                if (first)
                    first = false;
                else {
                    sb.insert(cnIndex, ", ");
                    cnIndex += 2;
                }
                cnIndex += ((CardComponentInterface)c).getColumnName().length();
                sb.append(((CardComponentInterface)c).getData());
                sb.append(", ");
            }
            
        }
        sb.delete(sb.length() - 2, sb.length());
        sb.append(")");
        try { 
            con.execUpdate(sb.toString());
        } catch (Exception e) {
            System.out.println("failed to commit card change");
            System.out.println(sb.toString());
        }
        card.dispose();
    }
    
    public CardIF makeCard(TableInternalFrame table, TableDataModel tdm, int index){
        SpringLayout sl = new SpringLayout();
        JPanel panel = new JPanel(sl);

        Component comp = panel;
        Component edit;
        Dimension editDim = new Dimension(130, 25);
        TableCellModel c;
        for (int i = 0; i < tdm.cellsModel.length; ++i) {
            c = tdm.cellsModel[i];
            
            
            try {
                Class cls = Class.forName(c.cellClass);
                
            } catch (Exception e) {
                
            }
            
            
            
            switch (c.cellClass) {
                case "none":
                    edit = new CardTextField(c.columnName, index == -1 ? 0: (int)tdm.getValueAt(index, i) );
                    edit.setVisible(false);
                    panel.add(edit);
                    continue;
                case "combo":
                    edit = new CardComboBox(c.columnName, con, c.tableLink);
                    break;
                case "f_combo":
                    edit = new CardFlagComboBox(c.columnName, index == -1 ? 0: (int)tdm.getValueAt(index, i));
                    break;
                case "date":
                    SimpleDateFormat sf = new SimpleDateFormat("dd.MM.yyyy");//("dd.MM.yyyy, hh:mm:ss.SSS");
                    Date date = new Date();//TODO возможно стоит как то занулить все не связанное с самой датой 
                    if (index != -1) {
                        date = (Date)tdm.getValueAt(index, i);//data[i];//date = sf.parse(d);
                    }
                    edit = new CardDatePicker(c.columnName, date);
                    break;
                default:
                    if (c.dataType.equals("int")) {
                        edit = new CardTextField(c.columnName, index == -1 ? 0: (int)tdm.getValueAt(index, i));
                    } else {
                        edit = new CardTextField(c.columnName, index == -1 ? "": (String)tdm.getValueAt(index, i));
                    }
                    
            }
            
     
            Component label = new JLabel(c.columnLabel + ":");
 
            edit.setMaximumSize(editDim);
            edit.setPreferredSize(editDim);
            
            panel.add(label);
            panel.add(edit);
            sl.putConstraint(SpringLayout.WEST, edit, 120, SpringLayout.WEST, panel);
            sl.putConstraint(SpringLayout.NORTH, edit, 30, SpringLayout.NORTH, comp);
            sl.putConstraint(SpringLayout.EAST, label, -10, SpringLayout.WEST, edit);
            sl.putConstraint(SpringLayout.NORTH, label, 3, SpringLayout.NORTH, edit);
            comp = edit;
        }
        JButton b = new JButton("ok");
        
        Dimension buttonSize = new Dimension(100,30);
        b.setMaximumSize(buttonSize);
        b.setPreferredSize(buttonSize);
        panel.add(b);
        sl.putConstraint(SpringLayout.NORTH, b, 10,SpringLayout.SOUTH, comp);
        sl.putConstraint(SpringLayout.WEST, b, 80, SpringLayout.WEST, panel);
        CardIF card = new CardIF(tdm.tableName , panel);
        b.setActionCommand("card accept");
        b.addActionListener(table);
        return card;
    }
}

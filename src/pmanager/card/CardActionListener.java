/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pmanager.card;

import pmanager.card.CardComponentInterface;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.activation.ActivationInstantiator;
import java.sql.Connection;
import java.sql.Statement;
import javax.swing.JPanel;
import pmanager.TableDataModel;

/**
 *
 * @author Gedeon
 */
public class CardActionListener implements ActionListener{
    private CardIF card;
    private TableDataModel tdm;
    private Connection con;
    
    public CardActionListener(Connection con, TableDataModel tdm, CardIF card) {
        this.con = con;
        this.tdm = tdm;
        this.card = card;
    }
    
    private String grabCardData() {
        Component[] com = card.getContentPane().getComponents();
        StringBuilder sb = new StringBuilder();
                        
        sb.append("UPDATE OR INSERT INTO ");
        sb.append(tdm.tableName);
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
        return sb.toString();
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        try { 
            Statement s = con.createStatement();
            s.executeUpdate(grabCardData());
            s.close();
            card.dispose();
        } catch (Exception e) {
            System.out.println("failed to commit card change");
            System.out.println(grabCardData());
        }
    }
}

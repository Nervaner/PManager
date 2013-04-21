/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pmanager.card;

import java.awt.Dimension;
import pmanager.DatabaseConnection;
import pmanager.TableCellModel;

/**
 *
 * @author Nervaner
 */
public interface CardComponentInterface {
    
    public String getColumnName();
    
    public String getData();
    
    public void init(Dimension dim, TableCellModel tcm, DatabaseConnection con, Object value);
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pmanager.card;

/**
 *
 * @author Nervaner
 */
public interface CardComponentInterface {
    
    public String getColumnName();
    
    public String getData();
    
    public void init(String columnName, Object[] args);
    
}

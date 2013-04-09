/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pmanager;

/**
 *
 * @author Nervaner
 */
public class TableCellModel {
    public String columnName;
    public String columnLabel;
    public String cellClass;
    //public Class cellClass;
    public String dataType;
    public String tableLink;
    
    public TableCellModel(String columnName, String columnLabel, String cellClass, String dataType, String tableLink) {
        this.columnName = columnName;
        this.columnLabel = columnLabel;
        this.cellClass = cellClass;
        this.dataType = dataType;
        this.tableLink = tableLink;
    }
     
     
}

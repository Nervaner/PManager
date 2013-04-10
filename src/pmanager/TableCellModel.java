/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pmanager;

import java.util.ArrayList;

/**
 *
 * @author Nervaner
 */
public class TableCellModel {
    public String columnName;
    public String columnLabel;
    public String cellClass;
    public String dataType;
    public String linkedTable;
    private ArrayList mask;
    
    
    public TableCellModel(String columnName, String columnLabel, String cellClass, String dataType, String linkedTable) {
        this.columnName = columnName;
        this.columnLabel = columnLabel;
        this.cellClass = cellClass;
        this.dataType = dataType;
        this.linkedTable = linkedTable;
    }
    
    public boolean isMasked() {
        return !linkedTable.isEmpty();
    }

    public void setMask(ArrayList mask) {
        this.mask = mask;
    }
    
    public Object getMaskedData(int index) {
        return mask.get(index);
    }
     
     
}

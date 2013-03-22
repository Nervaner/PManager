/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pmanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Gedeon
 */
public class TableDataModel extends AbstractTableModel {
    public String tableName;
    public String tableLabel;
    public String query;
    public String tableLink;
    public TableCellModel[] cellsModel;
    private TableInternalFrame bindedFrame;
    private HashMap data;
            
    public TableDataModel(String tableName, String tableLabel, TableCellModel[] cellsModel, String query) {
        this.tableName = tableName;
        this.tableLabel = tableLabel;
        this.tableLink = tableLink;
        HashSet set = new HashSet();
        data = new HashMap();
        for (int i = 0; i < cellsModel.length; ++i) {
            if (cellsModel[i].tableLink.length() > 3 ) 
                set.add(cellsModel[i].tableLink);
            data.put(cellsModel[i].columnName, new ArrayList<>());
        }
        this.query = query;
        this.cellsModel = cellsModel; 
    }
    
    public void bindFrame(TableInternalFrame tif) {
        if (bindedFrame != null)
            bindedFrame.dispose();
        bindedFrame = tif;
    }
    
    public void add(String key, Object o) {
        getColumn(key).add(o);
    }
    
    public ArrayList getColumn(String key) {
        return (ArrayList)data.get(key);
    }
   
    public void clear() {
        for (Object o : data.values()) {
            ((ArrayList)o).clear();
        }
    }
  
    @Override
    public Object getValueAt(int row, int column) {
        try {
            return ((ArrayList)data.get(cellsModel[column].columnName)).get(row);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return cellsModel[column].columnLabel;
    }

    @Override
    public int getRowCount() {
        return ((ArrayList)data.get(cellsModel[0].columnName)).size();
    }

    @Override
    public int getColumnCount() {
        return cellsModel.length;
    }

    
}

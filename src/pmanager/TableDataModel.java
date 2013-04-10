/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pmanager;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Gedeon
 */
public class TableDataModel extends AbstractTableModel {
    public String tableName;
    public String tableLabel;
    public String query;
    public TableCellModel[] cellsModel;
    private TableInternalFrame bindedFrame;
    private ArrayList<Object[]> data;
            
    public TableDataModel(String tableName, String tableLabel, TableCellModel[] cellsModel) {
        this.tableName = tableName;
        this.tableLabel = tableLabel;
        this.query = query;
        this.cellsModel = cellsModel; 
    }
    
    public void bindFrame(TableInternalFrame tif) {
        if (bindedFrame != null) {
            bindedFrame.dispose();
        }
        bindedFrame = tif;
    }
    
    public void setData(ArrayList<Object[]> data) {
        this.data = data;
    }
    
    public void clear() {
        data.clear();
    }
  
    @Override
    public Object getValueAt(int row, int column) {
        try {
            if (cellsModel[column].isMasked()) {
                return cellsModel[column].getMaskedData(row);
            } else {
                return data.get(row)[column];
            }
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
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return cellsModel.length;
    }

    
}

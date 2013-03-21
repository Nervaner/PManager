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
    //private ArrayList<Object[]> data;
    private HashMap data;
            
    public TableDataModel(String tableName, String tableLabel, TableCellModel[] cellsModel, String wherePart) {
        this.tableName = tableName;
        this.tableLabel = tableLabel;
        this.tableLink = tableLink;
        StringBuilder sb = new StringBuilder();
        HashSet set = new HashSet();
        data = new HashMap();
        for (int i = 0; i < cellsModel.length; ++i) {
            if (cellsModel[i].tableLink.length() > 3 ) 
                set.add(cellsModel[i].tableLink);
            data.put(cellsModel[i].columnName, new ArrayList<>());
        }
        sb.append("SELECT ");
        for (int i = 0; i < cellsModel.length; ++i) {
            if (set.contains(cellsModel[i].tableLink) ){ 
                sb.append(cellsModel[i].tableLink);
                sb.append(".name");
            } else {
                sb.append(tableName);
                sb.append(".");
                sb.append(cellsModel[i].columnName);
            }
            
            if (i != cellsModel.length - 1)
                sb.append(", ");
        }
        sb.append(" FROM ");
        sb.append(tableName);
        for (Object s : set) {
            sb.append(", ");
            sb.append((String)s);
            
        }
        //sb.delete(sb.length()-1, sb.length());
        
        if (!wherePart.isEmpty()) {
            
            sb.append(" WHERE ");
            sb.append(wherePart);
        }
            
            
        
        //TODO переделать доп запросы нахрен
        
//        if (!set.isEmpty())
//            sb.append(" WHERE ");
//        for (int i = 0; i < cellsModel.length; ++i) {
//            if (set.contains(cellsModel[i].tableLink) ) {
//                sb.append(cellsModel[i].tableLink);
//                sb.append(".id = ");
//                sb.append(tableName);
//                sb.append(".id");
//                if (i != cellsModel.length - 1)
//                    sb.append(" and ");
//            }
//        }
//        if (sb.subSequence(sb.length() - 4, sb.length()).equals("and ") )
//            sb.delete(sb.length() - 4, sb.length());
        
        System.out.println(sb.toString());
        this.query = sb.toString();
        this.cellsModel = cellsModel; 
    }
    
    public void bindFrame(TableInternalFrame tif) {
        if (bindedFrame != null)
            bindedFrame.dispose();
        bindedFrame = tif;
    }
    
//    public void add(Object[] o) {
//        data.add(o);
//    }
    public void add(String key, Object o) {
        getColumn(key).add(o);
    }
    
    public ArrayList getColumn(String key) {
        return (ArrayList)data.get(key);
    }
    
        
//    public Object[] getRow(int row) {
//        return data.get(row);
//    }
    
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
        //return data.get(row)[column];
    }

    @Override
    public String getColumnName(int column) {
        return cellsModel[column].columnLabel;
    }

    @Override
    public int getRowCount() {
        //return data.size();
        return ((ArrayList)data.get(cellsModel[0].columnName)).size();
    }

    @Override
    public int getColumnCount() {
        return cellsModel.length;
    }

    
}

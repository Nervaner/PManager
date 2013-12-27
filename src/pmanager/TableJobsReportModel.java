/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pmanager;

import java.util.ArrayList;
import java.util.Date;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Kelardis
 */
public class TableJobsReportModel extends AbstractTableModel{
    private ArrayList<Object[]> data;
    private String[] columns = {"Пользователь", "Задача", "Время выполнения"};
    TimeTool timeTool;
    
    public TableJobsReportModel(ArrayList<Object[]> arr) {
        timeTool = new TimeTool();
        data = arr;
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }
    
    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex < 2) {
            return data.get(rowIndex)[columnIndex];
        } else {
            //TODO фу так делать. Возможно даже когда нибудь стоит перерделать.
            return timeTool.calculateWorkHours((Date)data.get(rowIndex)[2], (Date)data.get(rowIndex)[3]); 
        }
            
    }
    
}

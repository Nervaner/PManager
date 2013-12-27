/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pmanager;

import java.awt.BorderLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SpringLayout;

/**
 *
 * @author Kelardis
 */
public class TableJobsReport extends JInternalFrame implements ActionListener{
    private DatabaseConnection con;
    private JPanel tableControlPanel, sumPanel;
    private JScrollPane tableScrollPane;
    private JComboBox projectSelector, employeeSelector, taskSelector;
    private ArrayList<Object[]> projectArr, employeeArr, taskArr;    
    private JButton selectorButton;
    private JTable table;
    private JLabel sumLabel, sumValue; 
    
    private JComboBox feelComboBox(ArrayList<Object[]> arr, String request, String init) {
        JComboBox comboBox = new JComboBox();
        try {
            arr = con.execQuery(request); 
        } catch (Exception e) {
            
        }
        arr.add(0, new Object[2]);
        arr.get(0)[0] = 0;
        arr.get(0)[1] = init;
        for (Object[] obj: arr) {
            comboBox.addItem(obj[1]);               
        }
        return comboBox;
    }
    
    public TableJobsReport(DatabaseConnection con) {
        super("Отчет по проведенным работам", true, true, true, false);
        this.con = con;
        
        
        projectSelector = feelComboBox(projectArr, "SELECT id, name from projects", "-проект-");      
        employeeSelector = feelComboBox(employeeArr, "select id, name from employees", "-пользователь-");
        taskSelector = feelComboBox(taskArr, "select id, name from tasks", "-задача-");
        
        selectorButton = new JButton("Ок");
        selectorButton.addActionListener(this);       
        
        tableControlPanel = new JPanel();
        tableControlPanel.setLayout(new BoxLayout(tableControlPanel, BoxLayout.LINE_AXIS)); 
        tableControlPanel.add(projectSelector);
        tableControlPanel.add(employeeSelector);
        tableControlPanel.add(taskSelector);
        tableControlPanel.add(selectorButton);
         
        table = new JTable();
        table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().setSelectionInterval(0, 0);
        table.setFillsViewportHeight(true);
        
        tableScrollPane = new JScrollPane();       
        tableScrollPane.setViewportView(table);   
        
        sumLabel = new JLabel("Суммарно: ");
        sumValue = new JLabel("0");
        
        SpringLayout sl = new SpringLayout();
        sumPanel = new JPanel(sl);
        sumPanel.add(sumLabel);
        sumPanel.add(sumValue);
        sl.putConstraint(SpringLayout.NORTH, sumPanel, 5, SpringLayout.NORTH, sumLabel);
        sl.putConstraint(SpringLayout.SOUTH, sumPanel, 5, SpringLayout.SOUTH, sumLabel);
        sl.putConstraint(SpringLayout.WEST, sumPanel, 20, SpringLayout.WEST, sumLabel);
        sl.putConstraint(SpringLayout.WEST, sumValue, 5, SpringLayout.EAST, sumLabel);
        
        
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());      
        contentPanel.add(tableControlPanel, BorderLayout.NORTH);
        contentPanel.add(tableScrollPane, BorderLayout.CENTER);
        contentPanel.add(sumPanel,BorderLayout.SOUTH);
        
        setContentPane(contentPanel);
        setSize(400, 300);
        setVisible(true);
    }
    
    
    
    
    
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        ArrayList<Object[]> arr;
        String request = "SELECT e.name, t.name, j.startdate, j.completiondate FROM jobs j, tasks t, employees e WHERE j.employeeid = e.id and j.taskid = t.id";
        if (projectSelector.getSelectedIndex() > 0) {
            request += " and t.projectid = " + projectArr.get(projectSelector.getSelectedIndex())[1];
        }
        if (employeeSelector.getSelectedIndex() > 0) {
            request += " and e.employeeid = " + employeeArr.get(employeeSelector.getSelectedIndex())[1]; 
        }
        if (taskSelector.getSelectedIndex() > 0) {
            request += " and t.id = " + taskArr.get(taskSelector.getSelectedIndex())[1];
        }
        try {
            arr = con.execQuery(request);
            table.setModel(new TableJobsReportModel(arr));
            int sum = 0;
            for (int i = 0; i < table.getRowCount(); ++i) {
                sum += (int)table.getValueAt(i, 2);
            }
            sumValue.setText(Integer.toString(sum));
        } catch (Exception exc) {
            
        }
    }
    
    
    
}

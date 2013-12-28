/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pmanager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

/**
 *
 * @author Kelardis
 */
public class GanttChartSelectorFrame extends JFrame implements ActionListener {
    private DatabaseConnection con;
    private ArrayList<Object[]> arr;
    private JComboBox projectSelector;
    public GanttChartSelectorFrame(DatabaseConnection con) {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.con = con;
        SpringLayout sl = new SpringLayout();
        JPanel panel = new JPanel(sl);
        projectSelector = new JComboBox();
        JButton button = new JButton("Ok");
        button.addActionListener(this);
        try {
            arr = con.execQuery("SELECT id, name from projects");
        }  catch(Exception e) {
            System.err.println("[GanttChartSelectorFrame]: Failed to get list of projects");
        }
        for (Object[] obj: arr) {
            projectSelector.addItem(obj[1]);               
        }
        
        panel.add(projectSelector);
        sl.putConstraint(SpringLayout.NORTH, projectSelector, 5, SpringLayout.NORTH, panel);
        sl.putConstraint(SpringLayout.EAST, projectSelector, -5, SpringLayout.EAST, panel);
        sl.putConstraint(SpringLayout.WEST, projectSelector, 5, SpringLayout.WEST, panel);
        
        panel.add(button);
        sl.putConstraint(SpringLayout.NORTH, button, 5, SpringLayout.SOUTH, projectSelector);
        sl.putConstraint(SpringLayout.EAST, button, 0, SpringLayout.EAST, projectSelector);
        sl.putConstraint(SpringLayout.WEST, button, 0, SpringLayout.WEST, projectSelector);
        
        
        setContentPane(panel);
        setBounds(450, 300, 300, 110);
        setResizable(false);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        GanttChart gchart = new GanttChart(con);
        gchart.make((int)arr.get(projectSelector.getSelectedIndex())[0]);
        this.dispose();
    }
    
}

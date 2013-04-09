/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pmanager;

import java.awt.BorderLayout;
import java.sql.Connection;
import java.util.ArrayList;
import javax.swing.*;
import pmanager.card.CardComponentInterface;

/**
 *
 * @author Nervaner
 */
public class MainFrame extends JFrame {
    //private TableDataModelFactory tdmFactory;
    private Connection con;
    private DatabaseConnection dbCon;
    private TableFactory tableFactory;
    private JDesktopPane desktopPane;
    private JMenuBar mainMenu;
    
    
    public MainFrame(String title) throws Exception {
        super(title);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(1280, 800);
        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch(Exception e) {
            throw(new Exception("Failed to set LookAndFeel."));
        }
        
        try {
            Class cls = Class.forName("pmanager.ExampleClass");
            Object obj = cls.newInstance();
            Object obj2 = cls.newInstance();
            ((ExampleClass)obj).init(1);
            ((ExampleClass)obj2).init(2);
            System.out.println(((CardComponentInterface)obj).getData());
            System.out.println(((CardComponentInterface)obj2).getData());
        } catch(Exception e) {
            System.out.println("FAAAAAAAIL");
        }
        
           
        dbCon = new DatabaseConnection("E:/test.fdb");
        
        mainMenu = new JMenuBar();
        desktopPane = new JDesktopPane();
        add(desktopPane);
        
        tableFactory = new TableFactory(dbCon, desktopPane);
        
        JMenu tables = new JMenu("Таблицы");
        
        JMenuItem miUsers = new JMenuItem("Пользователи");
        JMenuItem miCompanies = new JMenuItem("Компании");
        JMenuItem miEmployees = new JMenuItem("Разработчики");
        JMenuItem miProjects = new JMenuItem("Проекты");
        JMenuItem miTasks = new JMenuItem("Задачи");
        JMenuItem miGanttChart = new JMenuItem("Диаграмма");
        
        miUsers.setActionCommand("users");
        miCompanies.setActionCommand("companies");
        miEmployees.setActionCommand("employees");      
        miProjects.setActionCommand("projects");
        miTasks.setActionCommand("tasks");
        miGanttChart.setActionCommand("chart");
        
        miUsers.addActionListener(tableFactory);
        miCompanies.addActionListener(tableFactory);
        miEmployees.addActionListener(tableFactory);
        miProjects.addActionListener(tableFactory);
        miTasks.addActionListener(tableFactory);
        miGanttChart.addActionListener(tableFactory);
        
        tables.add(miUsers);
        tables.add(miCompanies);
        tables.add(miEmployees);
        tables.add(miProjects);
        tables.add(miTasks);
        tables.add(miGanttChart);
        
        mainMenu.add(tables);
        setJMenuBar(mainMenu);
        
        
        
        
        
    }
   
}

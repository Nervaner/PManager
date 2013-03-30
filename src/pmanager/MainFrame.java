/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pmanager;

import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import javax.swing.*;
import org.firebirdsql.jdbc.FBSQLException;

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
        
 /*       try { 
            Class.forName("org.firebirdsql.jdbc.FBDriver");           
            con = DriverManager.getConnection("jdbc:firebirdsql:localhost/3050:E:/test.fdb", "SYSDBA", "masterkey");
        } catch(FBSQLException e) {
            throw(new Exception("Failed to connect database"));
        }
   */     
        dbCon = new DatabaseConnection("E:/test.fdb");
        ArrayList lst = dbCon.execQuery("select * from projects");
        
        
        mainMenu = new JMenuBar();
        desktopPane = new JDesktopPane();
        add(desktopPane);
        
        tableFactory = new TableFactory(con, desktopPane);
        
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

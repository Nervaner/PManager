/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pmanager;

import java.awt.BorderLayout;
import java.net.URI;
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
        //TODO проверить работоспособность в упакованном виде
        String path = getClass().getClassLoader().getResource("").getPath();
        path += "../../DB/TEST.fdb";
        URI dbpath = new URI(path);
        dbpath = dbpath.normalize();
        dbCon = new DatabaseConnection(dbpath.getPath().substring(1));    
        
        mainMenu = new JMenuBar();
        desktopPane = new JDesktopPane();
        add(desktopPane);
        
        tableFactory = new TableFactory(dbCon, desktopPane);
        
        JMenu tables = new JMenu("Таблицы");
        JMenu reports = new JMenu("Отчеты");
        
        JMenuItem miUsers = new JMenuItem("Пользователи");
        JMenuItem miCompanies = new JMenuItem("Компании");
        JMenuItem miEmployees = new JMenuItem("Разработчики");
        JMenuItem miProjects = new JMenuItem("Проекты");
        JMenuItem miTasks = new JMenuItem("Задачи");
        JMenuItem miJobs = new JMenuItem("Проведенные работы");
        JMenuItem miGanttChart = new JMenuItem("Диаграмма");
        
        miUsers.setActionCommand("users");
        miCompanies.setActionCommand("companies");
        miEmployees.setActionCommand("employees");      
        miProjects.setActionCommand("projects");
        miTasks.setActionCommand("tasks");
        miJobs.setActionCommand("jobs");
        miGanttChart.setActionCommand("chart");
        
        miUsers.addActionListener(tableFactory);
        miCompanies.addActionListener(tableFactory);
        miEmployees.addActionListener(tableFactory);
        miProjects.addActionListener(tableFactory);
        miTasks.addActionListener(tableFactory);
        miJobs.addActionListener(tableFactory);
        miGanttChart.addActionListener(tableFactory);
        
        tables.add(miUsers);
        tables.add(miCompanies);
        tables.add(miEmployees);
        tables.add(miProjects);
        tables.add(miTasks);
        tables.add(miJobs);
        reports.add(miGanttChart);
        
        mainMenu.add(tables);
        mainMenu.add(reports);
        setJMenuBar(mainMenu);
        
        
        
        
        
    }
   
}

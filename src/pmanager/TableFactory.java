/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pmanager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JDesktopPane;

/**
 *
 * @author Nervaner
 */
public class TableFactory implements ActionListener{
    //private TableDataModelFactory tdmFactory;
    private DatabaseConnection dbCon;
    private CardFactory cardFactory;
    private JDesktopPane desktopPane;
    private GanttChart gChart;
    private Map m;
    
    TableFactory(DatabaseConnection dc, JDesktopPane dp) {
        dbCon = dc;
        cardFactory = new CardFactory(dbCon);
        gChart = new GanttChart(dbCon);
        desktopPane = dp;
        m = new HashMap();
        
        TableCellModel[] cellsModel1 = {
            new TableCellModel("login", "Логин", "edit", "string", ""),  
            new TableCellModel("passwd", "Пароль", "edit", "string", ""),
            new TableCellModel("adminFlag", "Администратор","f_combo", "int", "")
        };
        m.put("users", new TableDataModel("users", "Пользователи", cellsModel1));
        TableCellModel[] cellsModel2 = {
            new TableCellModel("id", "Id", "none", "int", ""),  
            new TableCellModel("name", "Компания", "edit", "string", ""),
            new TableCellModel("details", "Описание", "text", "text", "")
        };
        m.put("companies", new TableDataModel("companies", "Компании", cellsModel2));
        TableCellModel[] cellsModel3 = {
            new TableCellModel("id", "Id", "none", "int", ""),  
            new TableCellModel("name", "Имя", "edit", "string", ""),
            new TableCellModel("companyId", "Компания", "combo", "", "companies"),
            new TableCellModel("login", "Логин", "edit", "string", "")
        };
        m.put("employees", new TableDataModel("employees", "Разработчики", cellsModel3));
                //"select e.id, e.name, c.name, e.login from employees e, companies c where e.companyId = c.id"));
        TableCellModel[] cellsModel4 = {
            new TableCellModel("id", "Id", "none", "int", ""),  
            new TableCellModel("name", "Имя", "edit", "string", ""),
            new TableCellModel("startDate", "Дата начала", "date", "date", ""),
            new TableCellModel("status", "Статус", "edit", "int", "")
        };
        m.put("projects", new TableDataModel("projects", "Проекты", cellsModel4));
        TableCellModel[] cellsModel5 = {
            new TableCellModel("id", "Id", "none", "int", ""),
            new TableCellModel("companyId", "Компания", "combo", "int", "companies"),
            new TableCellModel("projectId", "Проект", "combo", "int", "projects"),
            new TableCellModel("activity", "Активность","f_combo", "int", "t/f")
        };
        m.put("contracts", new TableDataModel("contracts", "Контракты", cellsModel5));
        TableCellModel[] cellsModel9 = {
            new TableCellModel("employeeId", "Разработчик", "combo", "int", "employees"),
            new TableCellModel("projectId", "Проект", "combo", "int", "projects"),
            new TableCellModel("role", "Менеджер", "f_combo", "int", "m/e") 
        };
        m.put("projectEmployees", new TableDataModel("projectEmployees", "Назначенные разработчики", cellsModel9));
        TableCellModel[] cellsModel6 = {
            new TableCellModel("id", "Id", "none", "int", ""),
            new TableCellModel("name", "Имя", "edit", "string", ""),
            new TableCellModel("projectId", "Проект", "combo", "", "projects"), 
            new TableCellModel("plannedTime", "Планируемое время", "edit", "int", ""),
            new TableCellModel("status", "Статус", "edit", "int", "")
        };
        m.put("tasks", new TableDataModel("tasks", "Задачи", cellsModel6));
        TableCellModel[] cellsModel7 = {
            new TableCellModel("employeeId", "Разработчик", "combo", "int", "employees"),
            new TableCellModel("taskId", "Задача", "combo", "int", "tasks"),
            new TableCellModel("startDate", "Дата начала", "date", "date", ""),
            new TableCellModel("completionDate", "Дата завершения", "date", "date", ""),
            new TableCellModel("destription", "Описание", "edit", "string", "")
        };
        m.put("jobs", new TableDataModel("jobs", "Проведенные работы", cellsModel7));
        TableCellModel[] cellsModel8 = {
            new TableCellModel("firstId", "Зависит", "combo", "int", ""),
            new TableCellModel("secondId", "от", "combo", "int", ""),
        };
        m.put("tasksDependency", new TableDataModel("tasksDependency", "Зависимость задач", cellsModel8));
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String event = e.getActionCommand();
        if (m.containsKey(event)) {
            TableDataModel tdm = (TableDataModel)m.get(event);
            dbCon.refeelDataModel(tdm);
            TableInternalFrame tif = new TableInternalFrame(tdm, dbCon, cardFactory);
            tdm.bindFrame(tif); 
            desktopPane.add(tif);
            tif.toFront();
        } else if (event.equals("chart")) {
            TableDataModel tdm = (TableDataModel)m.get("tasks");
            dbCon.refeelDataModel(tdm);
            gChart.make(tdm);
        }
      
    }
    
}

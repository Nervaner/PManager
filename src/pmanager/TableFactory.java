/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pmanager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JDesktopPane;

/**
 *
 * @author Nervaner
 */
public class TableFactory implements ActionListener{
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
            new TableCellModel("login", "Логин", "CardStringTextField", ""),  
            new TableCellModel("passwd", "Пароль", "CardStringTextField", ""),
            new TableCellModel("adminFlag", "Администратор","CardComboBox", "tfmask")
        };
        m.put("users", new TableDataModel("users", "Пользователи", cellsModel1));
        TableCellModel[] cellsModel2 = {
            new TableCellModel("id", "Id", "CardIdTextField", ""),  
            new TableCellModel("name", "Компания", "CardStringTextField", ""),
            new TableCellModel("details", "Описание", "CardStringTextField", "")
        };
        m.put("companies", new TableDataModel("companies", "Компании", cellsModel2));
        TableCellModel[] cellsModel3 = {
            new TableCellModel("id", "Id", "CardIdTextField", ""),  
            new TableCellModel("name", "Имя", "CardStringTextField", ""),
            new TableCellModel("companyId", "Компания", "CardComboBox", "companies"),
            new TableCellModel("login", "Логин", "CardComboBox", "SELECT login, login FROM users")
        };
        m.put("employees", new TableDataModel("employees", "Разработчики", cellsModel3));
        TableCellModel[] cellsModel4 = {
            new TableCellModel("id", "Id", "CardIdTextField", ""),  
            new TableCellModel("name", "Имя", "CardStringTextField", ""),
            new TableCellModel("startDate", "Дата начала", "CardDateTimeField", ""),
            new TableCellModel("status", "Статус", "CardComboBox", "tfmask")
        };
        m.put("projects", new TableDataModel("projects", "Проекты", cellsModel4));
        TableCellModel[] cellsModel5 = {
            new TableCellModel("id", "Id", "CardIdTextField", ""),
            new TableCellModel("companyId", "Компания", "CardComboBox", "companies"),
            new TableCellModel("projectId", "Проект", "CardComboBox", "projects"),
            new TableCellModel("activity", "Активность","CardComboBox", "tfmask")
        };
        m.put("contracts", new TableDataModel("contracts", "Контракты", cellsModel5));
        TableCellModel[] cellsModel9 = {
            new TableCellModel("employeeId", "Разработчик", "CardComboBox", "employees"),
            new TableCellModel("projectId", "Проект", "CardComboBox", "projects"),
            new TableCellModel("role", "Менеджер", "CardComboBox", "tfmask") 
        };
        m.put("projectEmployees", new TableDataModel("projectEmployees", "Назначенные разработчики", cellsModel9));
        TableCellModel[] cellsModel6 = {
            new TableCellModel("id", "Id", "CardIdTextField", ""),
            new TableCellModel("name", "Имя", "CardStringTextField", ""),
            new TableCellModel("projectId", "Проект", "CardComboBox", "projects"), 
            new TableCellModel("plannedTime", "Планируемое время", "CardIntTextField", ""), 
            new TableCellModel("status", "Статус", "CardComboBox", "tfmask")
        };
        m.put("tasks", new TableDataModel("tasks", "Задачи", cellsModel6));
        TableCellModel[] cellsModel7 = {
            new TableCellModel("employeeId", "Разработчик", "CardComboBox", "employees"),
            new TableCellModel("taskId", "Задача", "CardComboBox", "tasks"),
            new TableCellModel("startDate", "Дата начала", "CardDatePicker", ""),
            new TableCellModel("completionDate", "Дата завершения", "CardDatePicker", ""),
            new TableCellModel("destription", "Описание", "CardStringTextField", "")
        };
        m.put("jobs", new TableDataModel("jobs", "Проведенные работы", cellsModel7));
        TableCellModel[] cellsModel8 = {
            new TableCellModel("firstId", "Зависит", "CardComboBox", "tasks"),
            new TableCellModel("secondId", "от", "CardComboBox", "tasks"),
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

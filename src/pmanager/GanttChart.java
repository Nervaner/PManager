/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pmanager;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Kel
 */
public class GanttChart {
    private DatabaseConnection con;
    private Date projectStartDate;
    private ArrayList<Object[]> tasks;
    private Map<Integer, Date> taskStart, taskEnd;
    private Map<Integer, Integer> tasksColor;
    private TimeTool timeTool;
    
    private static int TASK_NOT_STARTED = 0;
    private static int TASK_IN_PROGRESS = 1;
    private static int TASK_DONE = 2;

    private String taskColorSet[] =  {
        "red",
        "yellow",
        "green"
    };
    
    
    private Date getFirstJobDate(int taskId) {
        Date date = null, fDate;
        try {
            ArrayList<Object[]> list;
            list = con.execQuery("select startDate from jobs where taskid = " + taskId + "order by startdate");
            if (!list.isEmpty()) {
                fDate = (Date)list.get(0)[0];
                if (date == null || fDate.before(date) ) {
                    date = fDate;
                }
            }                  
            
        } catch (Exception e) {
            System.out.println("[Gant Chart] failed to get start date");
            e.printStackTrace();
        }
        return date;
    }
    
    private Date getLastJobDate(int taskId) {
        Date date = null, fDate;
        try {
            ArrayList<Object[]> list;
            list = con.execQuery("select completionDate from jobs where taskid = " + taskId + " order by completiondate desc");
            if (!list.isEmpty()) {
                fDate = (Date)list.get(0)[0];
                if (date == null || fDate.after(date) ) {
                    date = fDate;
                }
            }       
        } catch (Exception e) {
            System.out.println("[Gant Chart] failed to get end date");
            e.printStackTrace();
        }
        return date;
    }
    
    private Date getTaskStartDate(int taskId) {
        Date date = taskStart.get(taskId), bDate = null;
        if (date == null) {
            date = getFirstJobDate(taskId);
            if (date == null) {
                date = projectStartDate;
                try {
                    ArrayList<Object[]> list;
                    list = con.execQuery("select masterid from tasksdependency where slaveid = " + taskId);
                    for (Object[] obj: list){
                        bDate = getTaskEndDate((int)obj[0]);
                        if (bDate != null && bDate.after(date)) {
                            date = bDate;
                        }
                    }
                } catch (Exception e) {
                    System.out.println("[Gant Chart] failed to get dependency data");
                    e.printStackTrace();
                }
            } else {
                tasksColor.put(taskId, TASK_IN_PROGRESS);
            }
            taskStart.put(taskId, date);
        }
        
        return date;
    }
    
    private Date getTaskEndDate(int taskId) {
        Date date = taskEnd.get(taskId), bDate = null;
        if (date == null) {
            date = getTaskStartDate(taskId);
            try {
                ArrayList<Object[]> list;
                list = con.execQuery("select masterid from tasksdependency where slaveid = " + taskId);
                for (Object[] obj: list) {
                    bDate = getTaskEndDate((int)obj[0]);
                    if (date == null || bDate.after(date)) {
                        date = bDate;
                    }
                }
                list = con.execQuery("select plannedtime from tasks where id = " + taskId);
                date = countEndDate(date, (int)list.get(0)[0]);

                bDate = getLastJobDate(taskId);
                if (bDate != null && bDate.after(date)) {
                    date = bDate;
                }
            } catch (Exception e) {
                System.out.println("[Gant Chart] failed to get dependency data");
                e.printStackTrace();
            }

            taskEnd.put(taskId, date);
        }
        return date;
    }
    //подразумеваем что проект начат в пределах рабочего дня(теперь есть автоподстройка)
    private Date countEndDate(Date start, int shift) {
        timeTool.set(start);
        return timeTool.addHour(shift);
    }
    
    public void make(int projectId){                
        try {
            tasks = con.execQuery("SELECT * FROM tasks WHERE projectid = " + Integer.toString(projectId));
            ArrayList<Object[]> buf = con.execQuery("SELECT startdate FROM projects WHERE id = " + Integer.toString(projectId));
            projectStartDate = (Date)buf.get(0)[0];
        } catch (Exception e) {
            System.out.println("[Gant Chart] failed to get project data");
            e.printStackTrace();
            return;
        }
        StringBuilder sb = new StringBuilder();
        
        java.util.Date beginDate = null, endDate = null, bDate, partStartDate, partEndDate;
        
        for (Object[] task: tasks) {
            tasksColor.put((int)task[0], TASK_NOT_STARTED);
            bDate = getTaskStartDate((int)task[0]);
            if (beginDate == null || bDate.before(beginDate)) {
                beginDate = bDate;
            }
            bDate = getTaskEndDate((int)task[0]);
            if (endDate == null || bDate.after(endDate)) {
                endDate = bDate;
            }
            if ((int)task[4] == 1) {
                tasksColor.put((int)task[0], TASK_DONE);
            }
        }
        
        int allHours = timeTool.calculateWorkHours(beginDate, endDate);
        allHours += TimeTool.DAY_END_HOUR - timeTool.getHour();
        timeTool.addHour(TimeTool.DAY_END_HOUR - timeTool.getHour());
        endDate = timeTool.get();
        int h;
        
        sb.append("<html>\n<body>\n<table border=\"1\">\n");
        sb.append("<th colspan=\"");
        sb.append(allHours + 1);
        sb.append("\">Gantt diagram</th>\n");
        
        
        //nope
        //time tracking
        timeTool.set(beginDate);
        sb.append("<tr>\n");
        sb.append("<td>Hour:</td>\n");
        for (int i = 0; i < allHours; ++i) {    
            sb.append("<td>");
            sb.append(timeTool.getHour() + 1);
            timeTool.addHour(1);
            sb.append("</td>\n");
        }
        sb.append("</tr>\n");
        
        //date tracking
        timeTool.set(beginDate);
        sb.append("<tr>\n");
        sb.append("<td>Date:</td>\n");       
        h = allHours;
        while (h > TimeTool.HOUR_PER_DAY) {
            sb.append("<td colspan=\"");
            sb.append(TimeTool.HOUR_PER_DAY); 
            sb.append("\">");
            sb.append(timeTool.getYearMonthDay());
            sb.append("</td>\n");
            h = h - TimeTool.HOUR_PER_DAY;
            timeTool.addHour(TimeTool.HOUR_PER_DAY);      
        }
        sb.append("<td colspan=\"");
        sb.append(h);
        sb.append("\">");
        sb.append(timeTool.getYearMonthDay());
        sb.append("</td>\n");
        sb.append("</tr>\n"); 
        
        //tasks        
        for (Object[] task: tasks) {
            sb.append("<tr>\n");
            sb.append("<td>");
            sb.append(task[1]);
            sb.append("</td>\n");
            partStartDate = getTaskStartDate((int)task[0]);
            partEndDate = getTaskEndDate((int)task[0]);
            h = timeTool.calculateWorkHours(beginDate, partStartDate);
            if (h > 0) {
                sb.append("<td colspan=\"");
                sb.append(h);
                sb.append("\">&nbsp</td>\n");
            }            
            h = timeTool.calculateWorkHours(partStartDate, partEndDate);
            sb.append("<td colspan=\"");
            sb.append(h);
            sb.append("\" align=\"center\" style=\"background-color: ");
            sb.append(taskColorSet[tasksColor.get((int)task[0])]);
            sb.append("\">");
            sb.append(h);
            sb.append("</td>\n");
            h = timeTool.calculateWorkHours(partEndDate, endDate);
            if (h > 0) {
                sb.append("<td colspan=\"");
                sb.append(h);
                sb.append("\">&nbsp</td>\n");
            }
            sb.append("</tr>\n");       
        }
      
        sb.append("</table>\n</body>\n</html>\n");
        
        try {
            FileWriter fw = new FileWriter("chart.html");
            fw.write(sb.toString());
            fw.close();
            Desktop.getDesktop().open(new File("chart.html"));
        } catch (Exception e) {
            System.out.println("Chart blowed up");
            e.printStackTrace();
        }
  
    }
    
    public GanttChart(DatabaseConnection con) {
        this.con = con;
        timeTool = new TimeTool();
        taskStart = new HashMap();
        taskEnd = new HashMap();
        tasksColor = new HashMap(); 
    }
    
}

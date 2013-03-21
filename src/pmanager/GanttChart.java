/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pmanager;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.sql.Connection;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author Kel
 */
public class GanttChart {
    private TableDataModelFactory tdmFactory;
    private Connection con;
   
    
    private Date getFirstDate() {
        Date date = new Date();
        
        return date;
    }
    
    private Date getLastDate() {
        Date date = new Date();
        
        return date;
    }
    
    private int getHours(Date first, Date last) {
        
        return 0;
    }
    
    
    public void make(TableDataModel tdm){
        
        StringBuilder sb = new StringBuilder();
        Calendar cr = new GregorianCalendar();
        Date firstDate = getFirstDate();
        Date lastDate = getLastDate();
        int allHours = getHours(firstDate, lastDate);
        
        
        sb.append("<html><body><table border = \"1\">");
        sb.append("<th colspan = ");
        sb.append(allHours + 1);
        sb.append(">Gantt diagram</th>");
        
        //tasks
        int h = 0;
        for (int i = 0; i < tdm.getRowCount(); ++i){
            sb.append("<tr>");
            sb.append("<td>");
            sb.append(tdm.getValueAt(i, 1));
            sb.append("</td>");
            h = getHours(firstDate, lastDate);
            
            
            
            sb.append("</tr>");
//            if not task:
//				continue
//			result += '<tr>'
//			result += '<td>%s</td>' % task.name
//			h = getHours(firstDate, task.beginDate)
//			if h:
//				result += '<td colspan = "%d">&nbsp</td>' % h
//			result += '<td colspan = "%d" style = "background-color: %s">&nbsp</td>' % (
//				getHours(task.beginDate, task.endDate), colors[task.state])
//			h = getHours(task.endDate, lastDate)
//			if h:
//				result += '<td colspan = "%d">&nbsp</td>' % h
//			result += '</tr>'
        }
        
        // /tasks
        sb.append("<tr>");
        sb.append("<td>Date</td>");
        sb.append("<td colspan = \"");
        cr.setTime(firstDate);
        sb.append(24 - cr.get(Calendar.HOUR_OF_DAY));
        sb.append("\">");
        sb.append(cr.get(Calendar.DATE));
        sb.append("</td>");        
        //Date newDate = firstDate - 
        sb.append("</tr>");
        
        
        
        sb.append("</table></body></html>");
        
        try {
            //FileOutputStream fo = new FileOutputStream("chart.html");
            FileWriter fw = new FileWriter("chart.html");
            fw.write(sb.toString());
            fw.close();
        } catch (Exception e) {
            System.out.print("Chart blowed up");
        }
        
        
        
//                firstDate = self.getFirstDate()
//		lastDate = self.getLastDate()
//		allHours = getHours(firstDate, lastDate)
//		result = '<html><body><table border = "1">'
//		result += '<th colspan = %s>Gantt diagram</th>' % (allHours + 1)
//		for task in self.tasks:
//			if not task:
//				continue
//			result += '<tr>'
//			result += '<td>%s</td>' % task.name
//			h = getHours(firstDate, task.beginDate)
//			if h:
//				result += '<td colspan = "%d">&nbsp</td>' % h
//			result += '<td colspan = "%d" style = "background-color: %s">&nbsp</td>' % (
//				getHours(task.beginDate, task.endDate), colors[task.state])
//			h = getHours(task.endDate, lastDate)
//			if h:
//				result += '<td colspan = "%d">&nbsp</td>' % h
//			result += '</tr>'
//			
//		result += '<tr>'
//		result += '<td>Date</td>'
//		result += '<td colspan = "%d" >%s</td>' % (24 - firstDate.hour, firstDate.date())
//		newDate = firstDate + datetime.timedelta(hours = (24 - firstDate.hour))
//		h = 0
//		hours = allHours - (24 - firstDate.hour)
//		while hours > 0:
//			newDate +=  datetime.timedelta(hours = 24 * h)
//			result += '<td colspan = "24">%s</td>' % newDate.date()
//			h += 1
//			hours -= 24
//		result += '</tr>'
//		
//		result += '<tr>'
//		result += '<td>Hour</td>'
//		for h in range(allHours):
//			newDate = (firstDate + datetime.timedelta(hours = h))
//			result += '<td style = "font-size: 8;">%s</td>' % newDate.hour
//		result += '</tr>'
//
//		result += '</table><table><th colspan = 2>Denotation:</th>'
//		for i, den in enumerate(['Task is not started', 'Task is in progress', 'Task finished']):
//			result += '<tr><td style = "background-color: %s; width: 25px;">&nbsp;</td><td>%s</td></tr>' % (colors[i], den)
//		result += '</table></body></html>'
//		diagram = open('diagram.html', 'w')
//		diagram.write(result)
		
    }
    
    public GanttChart(TableDataModelFactory tdmFactory, Connection con) {
        this.tdmFactory = tdmFactory;
        this.con = con;
    }
    
}

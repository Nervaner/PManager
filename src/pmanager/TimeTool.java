/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pmanager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author Kel
 */
public class TimeTool {
//    public final static int DAY_PER_WEEK = 5;    
    public final static int DAY_START_HOUR = 10;
    public final static int DAY_END_HOUR = 18;
    public final static int HOUR_PER_DAY = DAY_END_HOUR - DAY_START_HOUR;
    
    //определяем выходные дни недели (с праздниками наверное не стоит заморачиваться)
    private final static boolean[] holyday = {
        true,     //Sunday
        false,    //Monday
        false,    //Tuesday
        false,    //Wednesday
        false,    //Thursday
        false,    //Friday
        true      //Saturday
    }; 
    
    private SimpleDateFormat dateFormat;
    
    private Calendar cr;
    
    public TimeTool() {
        cr = new GregorianCalendar();
        dateFormat = new SimpleDateFormat("E  dd MMM yyyy"); 
    }
    
    //выставляем календарь. оперируем на уровне часов
    public void set(Date date) {
        cr.setTime(date);
        //автоподстройка времени, если оно не входит в интервал рабочего дня       
        int curHour = cr.get(Calendar.HOUR_OF_DAY); 
        if (curHour < DAY_START_HOUR || curHour > DAY_END_HOUR) {
            cr.set(Calendar.HOUR_OF_DAY, DAY_START_HOUR);
        }
        cr.set(Calendar.MINUTE, 0);
        cr.set(Calendar.SECOND, 0);
        cr.set(Calendar.MILLISECOND, 0);
        if (curHour == DAY_END_HOUR) {
            addHour(0);
        }        
    }
    
    public Date get() {
        return cr.getTime();
    }
    
    public String getYearMonthDay() {
        return dateFormat.format(cr.getTime());
    }
    
    public int getHour() {
        return cr.get(Calendar.HOUR_OF_DAY);
    }
    
    public Date addHour(int hour) {
        int buf;
        do {
            if (hour < 8) {
                buf = DAY_END_HOUR - getHour();
                if (hour > buf) {
                    cr.add(Calendar.HOUR_OF_DAY, buf);
                    hour -= buf;
                } else {
                    cr.add(Calendar.HOUR_OF_DAY, hour);
                    hour = 0;
                }
            } else {
                cr.add(Calendar.DAY_OF_YEAR, 1);
                hour -= 8;
            }
            if (getHour() == DAY_END_HOUR) {
                cr.add(Calendar.DAY_OF_YEAR, 1);
                cr.set(Calendar.HOUR_OF_DAY, DAY_START_HOUR);
            }
            while (holyday[cr.get(Calendar.DAY_OF_WEEK) - 1]) {
                cr.add(Calendar.DAY_OF_YEAR, 1);                
            }           
        } while (hour > 0);
        return cr.getTime();
    }
    //TODO: возможно когда-нибудь переписать на более константный подсчет, а как то оооооочень неэффективно
    public int calculateWorkHours(Date beginDate, Date endDate) {
        int hours = 0;
        set(beginDate);
        while (get().before(endDate)) {
            addHour(1);
            hours++;
        }
        return hours;
    }
}

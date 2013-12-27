/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pmanager;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JTable;

/**
 *
 * @author Nervaner
 */
public class AppRun {
    
    public static void main(String args[]) {
        try {
            JFrame login = new LoginFrame();
            JFrame mainFrame = new MainFrame("Main Frame");
            mainFrame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        } 
        
     
     }
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pmanager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

/**
 *
 * @author Nervaner
 */
public class LoginFrame extends JFrame implements ActionListener{
    private DatabaseConnection con;  
    private JTextField loginTextField;
    private JPasswordField passwordField;
    JFrame mainFrame;
    
    public LoginFrame(DatabaseConnection con, JFrame mainFrame) {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //this.con = con;
        this.mainFrame = mainFrame;
        SpringLayout sl = new SpringLayout();
        JPanel loginPanel = new JPanel(sl);
        JLabel loginLable = new JLabel("Логин:");
        JLabel passwordLable = new JLabel("Пароль:");
        loginTextField = new JTextField();
        passwordField = new JPasswordField();
        loginTextField.setText("User");
        passwordField.setText("User");
        //JLabel loginStatusLable = new JLabel();
        JButton loginButton = new JButton("Войти");      
        loginButton.setActionCommand("Login");
        loginButton.addActionListener(this);
             
        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
            //TODO проверить работоспособность в упакованном виде
            String path = getClass().getClassLoader().getResource("").getPath();
            path += "../../DB/TEST.fdb";
            URI dbpath = new URI(path);
            dbpath = dbpath.normalize();
            this.con = new DatabaseConnection(dbpath.getPath().substring(1));    
        } catch(Exception e) {
            System.err.println("blow up");
            //throw(new Exception("Failed to set LookAndFeel."));
        }       
        
        
        
        
        loginPanel.add(loginLable);
        loginPanel.add(loginTextField);
        loginPanel.add(passwordLable);
        loginPanel.add(passwordField);
        loginPanel.add(loginButton);
        
        sl.putConstraint(SpringLayout.NORTH, loginLable, 20, SpringLayout.NORTH, loginPanel);
        sl.putConstraint(SpringLayout.EAST, loginLable, 60, SpringLayout.WEST, loginPanel);
        sl.putConstraint(SpringLayout.NORTH, passwordLable, 10, SpringLayout.SOUTH, loginLable);
        sl.putConstraint(SpringLayout.EAST, passwordLable, 0, SpringLayout.EAST, loginLable);
        sl.putConstraint(SpringLayout.NORTH, loginTextField, 0, SpringLayout.NORTH, loginLable);
        sl.putConstraint(SpringLayout.WEST, loginTextField, 10, SpringLayout.EAST, loginLable);
        sl.putConstraint(SpringLayout.EAST, loginTextField, -10, SpringLayout.EAST, loginPanel);
        sl.putConstraint(SpringLayout.NORTH, passwordField, 0, SpringLayout.NORTH, passwordLable);
        sl.putConstraint(SpringLayout.WEST, passwordField, 10, SpringLayout.EAST, passwordLable);
        sl.putConstraint(SpringLayout.EAST, passwordField, -10, SpringLayout.EAST, loginPanel);
        sl.putConstraint(SpringLayout.NORTH, loginButton, 10, SpringLayout.SOUTH, passwordLable);
        sl.putConstraint(SpringLayout.WEST, loginButton, 0, SpringLayout.WEST, passwordLable);
        sl.putConstraint(SpringLayout.EAST, loginButton, 0, SpringLayout.EAST, passwordField);
        this.setContentPane(loginPanel);
        this.setResizable(false);
        setTitle("Авторизация");
        setVisible(true);
        setBounds(450, 300, 350, 140);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        Date date = new Date();
        String request;
        if (e.getActionCommand().equals("Login")) {
            try {
                request = "UPDATE journal SET login = '" + loginTextField.getText() + "', passwd = '" + new String(passwordField.getPassword()) + "', entranceDate = '" + dateFormat.format(date) + "' WHERE id = 1;";
                System.out.println(request);
                con.execUpdate(request);
                this.setVisible(false);
                mainFrame.setVisible(true);
            } catch (SQLException exc){ 
                System.err.println(exc.getMessage());
            } 
        } else {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
}

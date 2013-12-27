/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pmanager;

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
public class LoginFrame extends JFrame{
   
        
    public LoginFrame() {
        SpringLayout sl = new SpringLayout();
        JPanel loginPanel = new JPanel(sl);
        JLabel loginLable = new JLabel();
        JLabel passwordLable = new JLabel();
        JTextField loginTextField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JLabel loginStatusLable = new JLabel();
        JButton loginButton = new JButton();
        
        loginLable.setText("Логин:");
        passwordLable.setText("Пароль:");
        loginTextField.setMinimumSize(new java.awt.Dimension(100, 20));
        loginButton.setText("Войти");
        loginButton.setActionCommand("Login");
        
        loginPanel.add(loginLable);
        sl.putConstraint(SpringLayout.WEST, loginLable, 20, SpringLayout.WEST, loginPanel);
        
        
        //кривосгенеренный код
        setTitle("Авторизация");
        setVisible(true);

        LoginLable.setText("Логин:");

        PasswordLable.setText("Пароль:");

        LoginTextField.setMinimumSize(new java.awt.Dimension(100, 20));

        LoginButton.setText("Войти");
        LoginButton.setActionCommand("Login");
        LoginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                //LoginButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout LoginPanelLayout = new javax.swing.GroupLayout(LoginPanel);
        LoginPanel.setLayout(LoginPanelLayout);
        LoginPanelLayout.setHorizontalGroup(
            LoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LoginPanelLayout.createSequentialGroup()
                .addGroup(LoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(LoginPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(LoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(LoginStatusLable, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(LoginPanelLayout.createSequentialGroup()
                                .addGroup(LoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(PasswordLable)
                                    .addComponent(LoginLable))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(LoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(LoginTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(PasswordField, javax.swing.GroupLayout.DEFAULT_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(LoginPanelLayout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addComponent(LoginButton)))
                .addContainerGap(13, Short.MAX_VALUE))
        );
        LoginPanelLayout.setVerticalGroup(
            LoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LoginPanelLayout.createSequentialGroup()
                .addComponent(LoginStatusLable, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(LoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LoginLable)
                    .addComponent(LoginTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(LoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(PasswordLable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PasswordField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LoginButton)
                .addGap(0, 25, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout LoginInternalFrameLayout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(LoginInternalFrameLayout);
        LoginInternalFrameLayout.setHorizontalGroup(
            LoginInternalFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(LoginPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        LoginInternalFrameLayout.setVerticalGroup(
            LoginInternalFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(LoginPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setBounds(160, 60, 190, 150);
        //конец кривосгенеренного кода
        
        
        
        
//        setLayout(new SpringLayout());
//        setSize(100, 100);
//        setVisible(true);
        
    }
}

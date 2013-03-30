/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pmanager.card;

import javax.swing.*;

/**
 *
 * @author Nervaner
 */
public class CardIF extends JInternalFrame{
    
    public CardIF(String title, JPanel cp) {
        super(title, true, true, true, true);
        setContentPane(cp);
        setVisible(true);
        setBounds(160, 60, 300, 300);
    }
}

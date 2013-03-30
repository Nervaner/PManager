/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pmanager;

import pmanager.card.CardIF;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import org.jdesktop.swingx.JXDatePicker;

/**
 *
 * @author Gedeon
 */
public class TableInternalFrame extends JInternalFrame implements ActionListener {
    private JButton deleteButton;
    private JButton editButton;
    private JButton addButton;
    private JPanel tableControlPanel;
    private JScrollPane tableScrollPane;
    private JTable table;
    private TableDataModelFactory tdmFactory;
    private CardFactory cardFactory;
    private CardIF card;
    private TableDataModel tdm;
    
    private JButton makeIconedButton(String imgURL){
        JButton btn = new JButton();
        btn.setMaximumSize(new Dimension(30,30));
        try {
            BufferedImage image = ImageIO.read(getClass().getResource(imgURL));
            BufferedImage resizedImage = new BufferedImage(20, 20, image.getType());
            Graphics2D g = resizedImage.createGraphics();
            g.drawImage(image, 0, 0, 20, 20, null);
            g.dispose();
            btn.setIcon(new ImageIcon(resizedImage));
            btn.addActionListener(this);
        } catch (IOException e) {
            System.out.println("Icon images missed");
        }
        return btn;
    }
    
    
    public TableInternalFrame(TableDataModel tdm, TableDataModelFactory tdmFactory, CardFactory cardFactory) {
        super(tdm.tableLabel, true, true, true, true);
        this.tdmFactory = tdmFactory;
        this.cardFactory = cardFactory;
        this.tdm = tdm;
        
        deleteButton = makeIconedButton("/icons/delete.png");
        deleteButton.setActionCommand("delete");
        editButton = makeIconedButton("/icons/edit.png");
        editButton.setActionCommand("edit");
        addButton = makeIconedButton("/icons/add.png");
        addButton.setActionCommand("add");
        tableScrollPane = new JScrollPane();
        
        tableControlPanel = new JPanel();
        tableControlPanel.setLayout(new BoxLayout(tableControlPanel, BoxLayout.LINE_AXIS));
        table = new JTable(tdm);
        //table.getSelectionModel().addListSelectionListener(new TableActionListener());
        table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().setSelectionInterval(0, 0);
        tableControlPanel.add(addButton);
        tableControlPanel.add(editButton);
        tableControlPanel.add(deleteButton);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        
        table.setFillsViewportHeight(true);
        tableScrollPane.setViewportView(table);
        
        contentPanel.add(tableControlPanel, BorderLayout.NORTH);
        contentPanel.add(tableScrollPane, BorderLayout.CENTER);
        
        //setMinimumSize(new Dimension(200,200));
        setContentPane(contentPanel);
        //setBounds(160, 60, 400, 300);
        setSize(400, 300);
        resizable = true;
        setVisible(true);
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()) {
            case "add":
                card = cardFactory.makeCard(this, tdm, -1);
                break;
            case "edit":
                card = cardFactory.makeCard(this, tdm, table.getSelectedRow());
                break;
            case "delete":
                cardFactory.deleteRow(tdm, table.getSelectedRow());
                break;
            case "card accept":
                cardFactory.grabCardData(card, tdm.tableName);
        }
        
        if (e.getActionCommand().equals("card accept")) {
            tdmFactory.feelDataModel(tdm);
            tdm.fireTableDataChanged();
            toFront();
            repaint();
        } else {
            getDesktopPane().add(card);
            card.toFront();
            card.repaint();
        }
            
    }
}

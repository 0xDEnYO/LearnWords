/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LearnWords.view.components;

import LearnWords.controller.ProgramParameters;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 *
 * @author Daniel Bläcker (d.blaecker@gmail.com)
 */
public abstract class MyAbstractDataEntryForm extends JPanel
{


    protected JPanel pnl_leftHalf, pnl_rightHalf;

    public MyAbstractDataEntryForm()
    {

        // Create JPanels for left and right half
        // The left side contains form fields that display data or can be used to add new data
        pnl_leftHalf = new JPanel();
        pnl_leftHalf.setLayout(new BoxLayout(pnl_leftHalf, BoxLayout.Y_AXIS));
        pnl_leftHalf.setBackground(ProgramParameters.COLOR_GUI_BACKGROUND);
        pnl_leftHalf.setMinimumSize(new Dimension(400, 500));
        pnl_leftHalf.setPreferredSize(new Dimension(400, 500));

        // The right side contains a table that shows the existing user data and provides options to alter/delete
        pnl_rightHalf = new JPanel();
        pnl_rightHalf.setLayout(new BoxLayout(pnl_rightHalf, BoxLayout.Y_AXIS));
        pnl_rightHalf.setBackground(ProgramParameters.COLOR_GUI_BACKGROUND);
        pnl_rightHalf.setMinimumSize(new Dimension(400, 500));
        pnl_rightHalf.setPreferredSize(new Dimension(400, 500));

        // Set main content panel to vertical box layout and add panels representing left and right half
        this.setBackground(ProgramParameters.COLOR_GUI_FOREGROUND);
        //pnl_LearnWords.setPreferredSize(new Dimension(2000, 1500));
        this.setLayout(new FlowLayout());

        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Add left and right half panel to main panel
        this.add(pnl_leftHalf);
        // create free space between the halfs
        this.add(Box.createRigidArea(new Dimension(10, 10)));
        this.add(pnl_rightHalf);
        
        this.setBackground(ProgramParameters.COLOR_GUI_BACKGROUND);

    }
    
    //public abstract void updateTable(ArrayList<? extends DataEntry> allItems);
    public abstract void updateTable();
    
    public abstract void updateForm();

}

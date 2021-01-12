/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LearnWords.view.views;

import LearnWords.controller.Controller;
import LearnWords.view.gui.GUI;
import LearnWords.controller.ProgramParameters;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Daniel Bläcker (d.blaecker@gmail.com)
 */
public class ViewTeacherMenu extends MyAbstractView
{

    // Action listener that will manage all the interactions on the view
    private class MyAL implements ActionListener, Runnable
    {

        @Override
        public void actionPerformed(ActionEvent ae)
        {
            if (ae.getSource() instanceof JButton)
            {

                switch (ae.getActionCommand())
                {

                    case "REQUESTS":
                        GUI.getGUI().activateView(ProgramParameters.VIEW_TITLE_REQUESTS);
                        break;
                    case "MAINTAINWORDS":
                        GUI.getGUI().activateView(ProgramParameters.VIEW_TITLE_MAINTAINWORDS);
                        break;
                    case "EVALUATE":
                        GUI.getGUI().activateView(ProgramParameters.VIEW_TITLE_EVALUATE);
                        break;
                    default:
                        Controller.setErrorMessage("Error in ActionListener (Switch)");

                }

                Thread th = new Thread(this);
                th.start();

            } else
            {
                Controller.setErrorMessage("Error: Unknown Action Event in Class ViewLogin");
            }
        }

        @Override
        public void run()
        {
            try
            {
                Thread.sleep(ProgramParameters.ERROR_MSG_TIME);
            } catch (InterruptedException ex)
            {
                Logger.getLogger(ViewLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
            Controller.setErrorMessage("");
        }

    }

    private final JPanel pnl_teacherMenu;
    private final JButton btn_requests, btn_maintainWords, btn_evaluate;
    private GridBagConstraints gbc;
    // Saves the (only existing) instance of this class in this variable (Singleton)
    protected static MyAbstractView instance = null;

    private ViewTeacherMenu()
    {

        // save the main panel (that contains the programs's content) in a variable for later usage     
        pnl_content.setLayout(new GridBagLayout());

        // Create menu elements
        // 1) Surrounding Panel
        pnl_teacherMenu = new JPanel(new GridLayout(1, 3));
        //pnl_teacherMenu.setBackground(Color.yellow);
        pnl_teacherMenu.setPreferredSize(new Dimension(600, 200));

        // 2) Menu Button#1 (incl action listener)
        ImageIcon icon = GUI.createImageIcon("../images/btn_requests.gif", "Requests - with this menu item a teacher can respond to student's requests");
        btn_requests = new JButton(icon);
        btn_requests.setBorder(BorderFactory.createEmptyBorder());
        btn_requests.setContentAreaFilled(false);
        btn_requests.setActionCommand("REQUESTS");
        btn_requests.addActionListener(new ViewTeacherMenu.MyAL());

        // 3) Menu Button#2 (incl action listener)
        icon = null;
        icon = GUI.createImageIcon("../images/btn_maintainWords.gif", "Maintain Words - with this menu a teacher can maintain the all words that are available in the program");
        btn_maintainWords = new JButton(icon);
        btn_maintainWords.setBorder(BorderFactory.createEmptyBorder());
        btn_maintainWords.setContentAreaFilled(false);
        btn_maintainWords.setActionCommand("MAINTAINWORDS");
        btn_maintainWords.addActionListener(new ViewTeacherMenu.MyAL());

        // 4) Menu Button#3 (incl action listener)
        icon = null;
        icon = GUI.createImageIcon("../images/btn_evaluate.gif", "Evaluate - with this menu item a teacher can evaluate the performance of her/his students");
        btn_evaluate = new JButton(icon);
        btn_evaluate.setBorder(BorderFactory.createEmptyBorder());
        btn_evaluate.setContentAreaFilled(false);
        btn_evaluate.setActionCommand("EVALUATE");
        btn_evaluate.addActionListener(new ViewTeacherMenu.MyAL());

        // Adjust GridBagLayout constraints and add buttons accordingly
        //####################################            
        gbc = new GridBagConstraints();

        // Button LEARNWORDS
        gbc.gridx = 0;
        gbc.gridy = 1;
        pnl_teacherMenu.add(btn_requests, gbc);

        // Button MANAGEWORDS
        gbc.gridx = 0;
        gbc.gridy = 2;
        pnl_teacherMenu.add(btn_maintainWords, gbc);

        // Button HISTORY
        gbc.gridx = 0;
        gbc.gridy = 3;
        pnl_teacherMenu.add(btn_evaluate, gbc);

        // Set title and subtitle
        this.title = ProgramParameters.VIEW_TITLE_MENU_TEACHER;
        this.subtitle = ProgramParameters.VIEW_SUBTITLE_MENU;

        // Define availability for BACK, MENU and LOGOUT buttons in footer and header
        this.backButton = true;
        this.menuButton = true;
        this.logoutButton = true;

        // Add all elements to main content panel
        gbc = new GridBagConstraints();
        this.pnl_content.add(pnl_teacherMenu, gbc);

    }

    public static MyAbstractView getView()
    {
        if (instance == null|| (!(instance instanceof ViewTeacherMenu)))
        {
            instance = new ViewTeacherMenu();
        }
        return instance;
    }

}

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
public class ViewMenu extends MyAbstractView
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

                    case "LEARNWORDS":
                        GUI.getGUI().activateView(ProgramParameters.VIEW_TITLE_LEARNWORDS);
                        break;
                    case "MANAGEWORDS":
                        GUI.getGUI().activateView(ProgramParameters.VIEW_TITLE_MANAGEWORDS);
                        break;
                    case "HISTORY":
                        GUI.getGUI().activateView(ProgramParameters.VIEW_TITLE_HISTORY);
                        break;
                    case "SETTINGS":
                        GUI.getGUI().activateView(ProgramParameters.VIEW_TITLE_SETTINGS);
                        break;
                    case "TEACHERMENU":
                        GUI.getGUI().activateView(ProgramParameters.VIEW_TITLE_MENU_TEACHER);
                        Controller.saveAllObjectsToCSV();
                        break;
                        
                    default:
                        Controller.setErrorMessage("Error in ActionListener (Switch)");

                }

                Thread th = new Thread(this);
                th.start();

            } else
            {
                Controller.setErrorMessage("Unknown Action Event in Class ViewLogin");
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

    private final JPanel pnl_menu;
    private final JButton btn_learnWords, btn_manageWords, btn_history, btn_settings, btn_teacherMenu;
    private GridBagConstraints gbc;
    // Saves the (only existing) instance of this class in this variable (Singleton)
    protected static MyAbstractView instance = null;

    private ViewMenu()
    {

        // save the main panel (that contains the programs's content) in a variable for later usage     
        pnl_content.setLayout(new GridBagLayout());

        // Create menu elements
        // 1) Surrounding Panel
        pnl_menu = new JPanel(new GridLayout(2, 4));
        //pnl_menu.setBackground(Color.yellow);
        pnl_menu.setPreferredSize(new Dimension(800, 200));

        // 2) Menu Button#1 (incl action listener)
        ImageIcon icon = GUI.createImageIcon("../images/btn_learnWords.gif", "Learn Words - with this menu item you can access the word practicing functions");
        btn_learnWords = new JButton(icon);
        btn_learnWords.setBorder(BorderFactory.createEmptyBorder());
        btn_learnWords.setContentAreaFilled(false);
        btn_learnWords.setActionCommand("LEARNWORDS");
        btn_learnWords.addActionListener(new ViewMenu.MyAL());

        // 3) Menu Button#2 (incl action listener)
        icon = null;
        icon = GUI.createImageIcon("../images/btn_manageWords.gif", "Manage Words - with this menu item you can manage your word lists and add new words");
        btn_manageWords = new JButton(icon);
        btn_manageWords.setBorder(BorderFactory.createEmptyBorder());
        btn_manageWords.setContentAreaFilled(false);
        btn_manageWords.setActionCommand("MANAGEWORDS");
        btn_manageWords.addActionListener(new ViewMenu.MyAL());

        // 4) Menu Button#3 (incl action listener)
        icon = null;
        icon = GUI.createImageIcon("../images/btn_history.gif", "History - with this menu item you view your history and some statistics");
        btn_history = new JButton(icon);
        btn_history.setBorder(BorderFactory.createEmptyBorder());
        btn_history.setContentAreaFilled(false);
        btn_history.setActionCommand("HISTORY");
        btn_history.addActionListener(new ViewMenu.MyAL());
        // 5) Menu Button#4 (incl action listener)
        icon = null;
        icon = GUI.createImageIcon("../images/btn_settings.gif", "Settings - with this menu item you can change your personal settings");
        btn_settings = new JButton(icon);
        btn_settings.setBorder(BorderFactory.createEmptyBorder());
        btn_settings.setContentAreaFilled(false);
        btn_settings.setActionCommand("SETTINGS");
        btn_settings.addActionListener(new ViewMenu.MyAL());
        // 6) Menu Button#5 (incl action listener)
        icon = null;
        icon = GUI.createImageIcon("../images/btn_teacherMenu.gif", "Teacher Menu - with this menu item you can access functions that are available for teachers only");
        btn_teacherMenu = new JButton(icon);
        btn_teacherMenu.setBorder(BorderFactory.createEmptyBorder());
        btn_teacherMenu.setContentAreaFilled(false);
        btn_teacherMenu.setActionCommand("TEACHERMENU");
        btn_teacherMenu.addActionListener(new ViewMenu.MyAL());

        // Adjust GridBagLayout constraints and add panels accordingly
        //####################################            
        gbc = new GridBagConstraints();

        // Button LEARNWORDS
        gbc.gridx = 0;
        gbc.gridy = 1;
        pnl_menu.add(btn_learnWords, gbc);

        // Button MANAGEWORDS
        gbc.gridx = 0;
        gbc.gridy = 2;
        pnl_menu.add(btn_manageWords, gbc);

        // Button HISTORY
        gbc.gridx = 0;
        gbc.gridy = 3;
        pnl_menu.add(btn_history, gbc);

        // Button SETTINGS
        gbc.gridx = 0;
        gbc.gridy = 4;
        pnl_menu.add(btn_settings, gbc);

        // Button TEACHERMENU
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 2;
        //gbc.weighty = 1.0;
        pnl_menu.add(btn_teacherMenu, gbc);

        // Set title and subtitle
        this.title = ProgramParameters.VIEW_TITLE_MENU_MAIN;
        this.subtitle = ProgramParameters.VIEW_SUBTITLE_MENU;

        // Define availability for BACK, MENU and LOGOUT buttons in footer and header
        this.backButton = false;
        this.menuButton = false;
        this.logoutButton = true;

        // Add all elements to main content panel
        gbc = new GridBagConstraints();
        this.pnl_content.add(pnl_menu, gbc);

    }

    public static MyAbstractView getView()
    {
        if (instance == null|| (!(instance instanceof ViewMenu)))
        {
            instance = new ViewMenu();
        }
        return instance;
    }

}

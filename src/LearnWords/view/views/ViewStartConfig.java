/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LearnWords.view.views;

import LearnWords.controller.Controller;
import LearnWords.view.gui.GUI;
import LearnWords.controller.ProgramParameters;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author Daniel Bläcker (d.blaecker@gmail.com)
 */
public class ViewStartConfig extends MyAbstractView
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

                    case "LOGIN":
                        // TODO
                        //
                        if (Controller.verifyUserLogin(txt_user.getText(), String.valueOf(txt_pw.getPassword())))
                        {
                            Controller.activateView(ProgramParameters.VIEW_TITLE_MENU_MAIN);
                        }

                        break;
                    default:
                        Controller.setErrorMessage("Error in ActionListener (switch)");

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
                Logger.getLogger(ViewStartConfig.class.getName()).log(Level.SEVERE, null, ex);
            }
            Controller.setErrorMessage("");
        }

    }

    private final JPanel pnl_login;
    private final JButton btn_login;
    private final JLabel lbl_login, lbl_user, lbl_pw;
    private final JTextField txt_user;
    private final JPasswordField txt_pw;

    private GridBagConstraints gbc;
    // Saves the (only existing) instance of this class in this variable (Singleton)
    protected static MyAbstractView instance = null;

    private ViewStartConfig()
    {
        // save the main panel (that contains the programs's content) in a variable for later usage     
        pnl_content.setLayout(new GridBagLayout());

        // Add view's elements to main content panel
        // 1) Surrounding Panel for login mask
        pnl_login = new JPanel(new GridBagLayout());
        pnl_login.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        pnl_login.setPreferredSize(new Dimension(400, 300));
        // 2) Labels
        lbl_login = new JLabel("PLEASE ENTER YOUR USER CREDENTIALS");
        lbl_login.setFont(ProgramParameters.FONT_TXT_SIZE_HEADING3);
        lbl_login.setHorizontalAlignment(JLabel.CENTER);
        lbl_user = new JLabel("Username");
        lbl_user.setFont(ProgramParameters.FONT_TXT_SIZE_HEADING3);
        lbl_user.setHorizontalAlignment(JLabel.CENTER);
        lbl_pw = new JLabel("Password");
        lbl_pw.setFont(ProgramParameters.FONT_TXT_SIZE_HEADING3);
        lbl_pw.setHorizontalAlignment(JLabel.CENTER);
        // 3) Textarea fields for entering credentials
        txt_user = new JTextField();
        txt_user.setPreferredSize(new Dimension(200, 25));
        txt_user.setLayout(null);
        txt_user.setHorizontalAlignment(JLabel.CENTER);
        txt_pw = new JPasswordField();
        txt_pw.setPreferredSize(new Dimension(200, 25));
        txt_pw.setLayout(null);
        txt_pw.setHorizontalAlignment(JLabel.CENTER);

        // TMP FOR EASIER LOGIN
        txt_user.setText("daniel.blaecker");
        txt_pw.setText("1234");

        // 4) Login Button (incl action listener)
        ImageIcon icon = GUI.createImageIcon("../images/btn_login.gif", "Login Button - clicking this button triggers a login with the entered credentials");
        btn_login = new JButton(icon);
        btn_login.setBorder(BorderFactory.createEmptyBorder());
        btn_login.setContentAreaFilled(false);
        btn_login.setActionCommand("LOGIN");
        btn_login.addActionListener(new ViewStartConfig.MyAL());

        // Adjust GridBagLayout constraints and add panels accordingly
        //####################################            
        gbc = new GridBagConstraints();

        // Label Login Info Text
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weighty = 2.0;
        pnl_login.add(lbl_login, gbc);

        // Label Username
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weighty = 0.5;
        pnl_login.add(lbl_user, gbc);

        // Input Box Username
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weighty = 0.5;
        pnl_login.add(txt_user, gbc);

        // Label Password
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weighty = 0.5;
        pnl_login.add(lbl_pw, gbc);

        // Input Box Password
        gbc.gridx = 0;
        gbc.gridy = 5;
        //gbc.weighty = 1.0;
        pnl_login.add(txt_pw, gbc);

        //  Button Login
        gbc.gridx = 0;
        gbc.gridy = 6;
        //gbc.weighty = 0.1;
        pnl_login.add(btn_login, gbc);

        // Define availability for BACK, MENU and LOGOUT buttons in footer and header
        this.backButton = false;
        this.menuButton = false;
        this.logoutButton = false;

        // Set title and subtitle
        this.title = ProgramParameters.VIEW_TITLE_LOGIN;
        this.subtitle = ProgramParameters.VIEW_SUBTITLE_LOGIN;

        // Add all elements to main content panel
        gbc = new GridBagConstraints();
        this.pnl_content.add(pnl_login, gbc);

    }

    public static MyAbstractView getView()
    {
        if (instance == null|| (!(instance instanceof ViewStartConfig)))
        {
            instance = new ViewStartConfig();
        }

        return instance;
    }

}

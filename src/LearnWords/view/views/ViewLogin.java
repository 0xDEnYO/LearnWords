/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LearnWords.view.views;

import LearnWords.controller.Controller;
import LearnWords.view.gui.GUI;
import LearnWords.controller.ProgramParameters;
import LearnWords.view.components.MyButton;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author Daniel Bläcker (d.blaecker@gmail.com)
 */
public class ViewLogin extends MyAbstractView {

    // Action listener that will manage all the interactions on the view
    private class MyAL implements ActionListener, Runnable {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (ae.getSource() instanceof JButton || ae.getSource() instanceof JComboBox) {

                switch (ae.getActionCommand()) {

                    case "LOGIN":
                        if (Controller.verifyUserLogin(txt_user.getText(), String.valueOf(txt_pw.getPassword()))) {
                            Controller.activateView(ProgramParameters.VIEW_TITLE_MENU_MAIN);
                        }
                        break;

                    case "STORAGE_OPTIONS":

                        JComboBox cb = (JComboBox) ae.getSource();
                        String selection = (String) cb.getSelectedItem();
                        Controller.setSaveLocation(selection);
                        break;
                    default:
                        Controller.setErrorMessage("Error in ActionListener (switch)");

                }

                Thread th = new Thread(this);
                th.start();

            } else {
                Controller.setErrorMessage("Unknown Action Event in Class ViewLogin");
            }
        }

        @Override
        public void run() {
            try {
                Thread.sleep(ProgramParameters.ERROR_MSG_TIME);
            } catch (InterruptedException ex) {
                Logger.getLogger(ViewLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
            Controller.setErrorMessage("");
        }

    }

    private final JPanel pnl_config, pnl_login, pnl_ViewLogin;
    private JButton btn_login, btn_connect;
    private final JLabel lbl_login, lbl_user, lbl_pw, lbl_config;
    private final JTextField txt_user;
    private final JPasswordField txt_pw;
    private final JComboBox<String> box_storageLocation;

    private GridBagConstraints gbc;
    // Saves the (only existing) instance of this class in this variable (Singleton)
    protected static ViewLogin instance = null;

    private ViewLogin() {
        //BoxLayout box = new BoxLayout(pnl_content, BoxLayout.PAGE_AXIS);
        pnl_content = new JPanel(new GridBagLayout());
        pnl_content.setPreferredSize(new Dimension(800, 400));
        pnl_content.setBackground(ProgramParameters.COLOR_GUI_FOREGROUND);
        pnl_content.setMaximumSize(new Dimension(300, 200));

        // Add view's elements to main content panel
        // Surrounding Panel for config mask
        pnl_config = new JPanel(new GridBagLayout());
        pnl_config.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        pnl_config.setMinimumSize(new Dimension(400, 300));
        pnl_config.setMaximumSize(new Dimension(300, 200));

        // Surrounding Panel for login mask
        pnl_login = new JPanel(new GridBagLayout());
        pnl_login.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        pnl_login.setMinimumSize(new Dimension(400, 400));
        pnl_login.setMaximumSize(new Dimension(300, 200));

        // Surrounding panel for config and login mask 
        gbc = new GridBagConstraints();
        gbc.gridy = 2;
        pnl_ViewLogin = new JPanel();
        pnl_ViewLogin.setLayout(new BoxLayout(pnl_ViewLogin, BoxLayout.X_AXIS));
        pnl_ViewLogin.setBorder(BorderFactory.createEmptyBorder());
        pnl_ViewLogin.setPreferredSize(new Dimension(300, 400));
        pnl_ViewLogin.setBackground(ProgramParameters.COLOR_GUI_FOREGROUND);

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
        btn_login.addActionListener(new ViewLogin.MyAL());

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

        //---------------------------------------------------------------------------------------------------   
        // CONFIG MASK
        //####################################            
        gbc = new GridBagConstraints();

        // Label Config Mask
        lbl_config = new JLabel("Please select your data storage location");
        lbl_config.setFont(ProgramParameters.FONT_TXT_SIZE_HEADING3);
        lbl_config.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridy = 0;
        pnl_config.add(lbl_config, gbc);

        // add free space
        pnl_config.add(Box.createRigidArea(new Dimension(0, 40)), gbc);

        // Dropdown box with predefined values
        String[] options = {
            ProgramParameters.DROPDOWN_CONFIG_DB,
            ProgramParameters.DROPDOWN_CONFIG_CSV};
        box_storageLocation = new JComboBox<>(options);
        box_storageLocation.addActionListener(new MyAL());
        box_storageLocation.setActionCommand("STORAGE_OPTIONS");
        box_storageLocation.setMaximumRowCount(3);
        box_storageLocation.setBackground(Color.WHITE);

        box_storageLocation.setBounds(50, 50, 200, 40);
        gbc.gridy = 1;
        pnl_config.add(box_storageLocation, gbc);

        // add free space
        pnl_config.add(Box.createRigidArea(new Dimension(0, 40)), gbc);

        // Connect-Button
        btn_connect = MyButton.getMyButton(
                "../images/btn_connect.jpg",
                "Connect - with this button you can select your data storage location",
                "CONNECT");
        // Adjust butto size
        btn_connect.setPreferredSize(new Dimension(200, 50));
        gbc.gridy = 2;
        pnl_config.add(btn_connect, gbc);

        // Add config mask panel to main panel
        gbc = new GridBagConstraints();
        gbc.gridy = 0;
        pnl_ViewLogin.add(pnl_config, gbc);

        // set login window invisible until data connection is established
        pnl_login.setVisible(false);

        // Add login mask panel to ViewLogin panel
        gbc.gridy = 1;
        pnl_ViewLogin.add(pnl_login, gbc);

        // Add all elements to main content panel
        gbc = new GridBagConstraints();
        gbc.gridy = 2;

        // Add all items of this view to GUI base main panel
        gbc = new GridBagConstraints();
        pnl_content.add(pnl_ViewLogin, gbc);

    }

    public static MyAbstractView getView() {
        if (instance == null || (!(instance instanceof ViewLogin)) ) {
            instance = new ViewLogin();
        }

        return instance;
    }

    public void showLoginMask() {

        // remove config mask
        pnl_config.setVisible(false);

        // add login mask
        pnl_login.setVisible(true);

    }
    
    
    
    // TMP
    public void clickConnect(){
        btn_connect.doClick();
    }
    public void clickLogin(){
        btn_login.doClick();
    }

}

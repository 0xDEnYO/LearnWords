/*
 * This class builds the (generic) footer for the GUI of the program. It is unique and therefore only one instance of this class is allowed (singleton)
 */
package LearnWords.view.gui;

import LearnWords.view.components.MyButton;
import LearnWords.controller.Controller;
import LearnWords.model.administration.userManagement.User;
import LearnWords.controller.ProgramParameters;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Daniel Bläcker (d.blaecker@gmail.com)
 */
public class GUI_Footer extends JPanel
{

    private final JPanel pnl_footer, pnl_footer_left, pnl_footer_center, pnl_footer_right;
    private final JLabel lbl_footer_message, lbl_footer_loginInformation, footer_copyright;
    private final JButton btn_menu, btn_logout;
    private final GridBagConstraints gbc;

    public GUI_Footer()
    {
        // Create footer main panel (that will incorporate all its elements
        pnl_footer = new JPanel();

        // Create 3 panels to divide the footer in three equal areas (by using GridLayout)
        pnl_footer_left = new JPanel(new FlowLayout(FlowLayout.LEFT));
        //pnl_footer_center = new JPanel(new GridLayout(3, 1));
        pnl_footer_center = new JPanel(new GridBagLayout());
        pnl_footer_right = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        // Assign colors to all panels in the footer
        pnl_footer_left.setBackground(ProgramParameters.COLOR_GUI_BACKGROUND);
        pnl_footer_center.setBackground(ProgramParameters.COLOR_GUI_BACKGROUND);
        pnl_footer_right.setBackground(ProgramParameters.COLOR_GUI_BACKGROUND);

        // Insert labels for error messages, login data and copyright information
        //footer_message = new JLabel("<html>##################################<br>|| SPACE FOR ERROR MESSAGES ETC.||<br>##################################");
        lbl_footer_message = new JLabel(ProgramParameters.TXT_ERRORMESSAGE_DEFAULT);
        lbl_footer_message.setFont(ProgramParameters.FONT_TXT_SIZE_ERROR);
        lbl_footer_message.setForeground(Color.RED);

        // >>>>> ACHTUNG  - NÄCHSTE ZEILE PRÜFEN
        lbl_footer_loginInformation = new JLabel("");
        lbl_footer_loginInformation.setFont(ProgramParameters.FONT_TXT_SIZE_SMALL);

        footer_copyright = new JLabel(ProgramParameters.PROGRAM_NAME + " \u00a9 " + "by " + ProgramParameters.AUTHOR
                + " (" + ProgramParameters.AUTHOR_EMAIL + ")");
        footer_copyright.setFont(ProgramParameters.FONT_TXT_SIZE_SMALL);

        // For all footer center labels set horizontal alignment 
        lbl_footer_message.setHorizontalAlignment(JLabel.CENTER);
        lbl_footer_loginInformation.setHorizontalAlignment(JLabel.CENTER);
        footer_copyright.setHorizontalAlignment(JLabel.CENTER);

        // Adjust GridBagLayout Constraints and add panels accordingly
        gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weighty = 2.0;
        pnl_footer_center.add(lbl_footer_message, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;

        pnl_footer_center.add(lbl_footer_loginInformation, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;

        pnl_footer_center.add(footer_copyright, gbc);

        // Add "MENU" Button with icon in footer
        btn_menu = MyButton.getMyButton("../images/btn_menu.gif",
                "Menu Button - clicking this button leads to main menu",
                "MENU");
        pnl_footer_left.add(btn_menu);

        // Add "LOGOUT" Button with icon in footer
        btn_logout = MyButton.getMyButton("../images/btn_logout.gif",
                "Logout Button - clicking this button will log out the user",
                "LOGOUT");
        pnl_footer_right.add(btn_logout);

        // Add all elements to footer main panel
        pnl_footer.add(pnl_footer_left);
        pnl_footer.add(pnl_footer_center);
        pnl_footer.add(pnl_footer_right);
        pnl_footer.setLayout(new GridLayout(1, 3));

    }

    public void activateMenuButton()
    {
        this.btn_menu.setVisible(true);
    }

    public void deactivateMenuButton()
    {
        this.btn_menu.setVisible(false);

    }

    public void activateLogoutButton()
    {
        this.btn_logout.setVisible(true);
    }

    public void deactivateLogoutButton()
    {
        this.btn_logout.setVisible(false);

    }

    public void setErrorMessage(String text)
    {
        this.lbl_footer_message.setText(text);

    }

    public JPanel getMainPanel()
    {
        return this.pnl_footer;
    }

    public void updateLoginInformation()
    {
        this.lbl_footer_loginInformation.setText("You are logged in as: " + Controller.getCurrentUser().getUsername() + 
                " (your role: " + Controller.getCurrentUser().getRole().getRoleTitle() + ")");
    }

}

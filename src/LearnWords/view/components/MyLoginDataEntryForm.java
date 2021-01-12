/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LearnWords.view.components;

import LearnWords.controller.Controller;
import LearnWords.controller.ProgramParameters;
import LearnWords.model.administration.Login;
import LearnWords.model.administration.userManagement.User;

import LearnWords.view.gui.GUI;
import LearnWords.view.views.ViewLogin;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 *
 * @author Daniel Bläcker (d.blaecker@gmail.com)
 */
public class MyLoginDataEntryForm extends MyAbstractDataEntryForm
{

    private class MyActionListener implements ActionListener, Runnable
    {
        // Action listener that will manage all the interactions on the view

        @Override
        public void actionPerformed(ActionEvent ae)
        {

            // not sure if needed but I had trouble with flickering views
            GUI.getFrame().repaint();

            if (ae.getSource() instanceof JButton)
            {

                switch (ae.getActionCommand())
                {
                    // SAVE LOGIN (Settings > Add/change/delete Login)
                    case "SAVE_LOGIN":
                        String username = form_fields[0].getInput();
                        String password = form_fields[1].getInput();
                        if (username.equals(""))
                        {
                            // username must not be empty
                            Controller.setErrorMessage("ERROR: Username must not be empty. Please try again");
                            break;
                        }

                        // Check if existing login is changed or new login is added
                        Login login = null;
                        if ((login = Login.getLogin(username)) == null)
                        {
                            // Login does not exist yet, add new login
                            login = Login.addLogin(username, password);
                        } else
                        {
                            // login exists, update data
                            login.setUsername(username);
                            login.changePasswordAuthorized(password);

                        }

                        // update table
                        updateTable();

                        Controller.setErrorMessage("Data saved");
                        break;
                    // SAVE LOGIN (Settings > Add/change/delete Login)
                    case "DELETE_LOGIN":
                        username = form_fields[0].getInput();
                        if (Login.removeLogin(username))
                        {
                            // update table
                            updateTable();
                            // Login found and successfully removed
                            Controller.setErrorMessage("Login removed");
                        } else
                        {
                            // Login not found 
                            Controller.setErrorMessage("ERROR: No login with this username could be found");
                        }
                        break;
                    default:
                        Controller.setErrorMessage("Error in ActionListener (Switch)");

                }
                // not sure if needed but I had trouble with flickering views
                GUI.getFrame().repaint();

                // Start timer and let error message disappear
                Thread th = new Thread(this);
                th.start();

            } else
            {
                Controller.setErrorMessage("\nUnknown Action Event in Class MyLoginDataEntryForm\n");
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

    private JLabel lbl_heading, lbl_plsSelect;
    private GridBagConstraints gbc = new GridBagConstraints();
    private MyButton btn_save, btn_delete;
    private int spacer = 20;
    private int counterY = 0;
    private MyTextfield[] form_fields;
    private MyLoginTableModel tblModel;
    private JTable tbl_login;
    private JScrollPane scrollpane;

    public MyLoginDataEntryForm()
    {
        //----------------------------
        // Left half
        // Set layout
        pnl_leftHalf.setLayout(new GridBagLayout());
        // Create form
        // add free space
        setGbc(0, counterY++, 1);
        //pnl_leftHalf.add(Box.createRigidArea(new Dimension(0, spacer * 2)), gbc);
        pnl_leftHalf.add(Box.createRigidArea(new Dimension(0, 15)), gbc);
        // Label Heading
        lbl_heading = new JLabel(" LOGIN INPUT/EDIT FORM");
        lbl_heading.setSize(100, 30);
        lbl_heading.setFont(ProgramParameters.FONT_TXT_SIZE_HEADING2);
        lbl_heading.setAlignmentX(Component.CENTER_ALIGNMENT);
        // Adjust constraints and add element
        setGbc(0, counterY++, 1);
        pnl_leftHalf.add(lbl_heading, gbc);
        // add free space
        setGbc(0, counterY++, 1);
        pnl_leftHalf.add(Box.createRigidArea(new Dimension(0, spacer)), gbc);

        // Add form elements
        form_fields = new MyTextfield[3];
        form_fields[0] = new MyTextfield("Username");
        // add free space
        setGbc(0, counterY++, 1);
        pnl_leftHalf.add(Box.createRigidArea(new Dimension(0, spacer)), gbc);
        // Adjust constraints and add element
        setGbc(0, counterY++, 1);
        pnl_leftHalf.add(form_fields[0], gbc);
        
        form_fields[1] = new MyTextfield("Password");
        // add free space
        setGbc(0, counterY++, 1);
        pnl_leftHalf.add(Box.createRigidArea(new Dimension(0, spacer)), gbc);
        // Adjust constraints and add element
        setGbc(0, counterY++, 1);
        pnl_leftHalf.add(form_fields[1], gbc);

        // add free space
        setGbc(0, counterY++, 1);
        pnl_leftHalf.add(Box.createRigidArea(new Dimension(0, spacer * 3)), gbc);

        // Create Buttons SAVE/DELETE
        // Button "SAVE"
        btn_save = MyButton.getMyButton("../images/btn_save.gif",
                "SAVE LOGIN - with this button you can save the login data in this form",
                "SAVE_LOGIN");
        // Replace Action Listener
        btn_save.removeActionListener(btn_save.getActionListeners()[0]);
        btn_save.addActionListener(new MyActionListener());
        // Adjust constraints and add element
        setGbc(0, counterY++, 1);
        pnl_leftHalf.add(btn_save, gbc);
        // add free space
        setGbc(0, counterY++, 1);
        pnl_leftHalf.add(Box.createRigidArea(new Dimension(0, spacer)), gbc);

        // Button "D"
        btn_delete = MyButton.getMyButton("../images/btn_delete.gif",
                "DELETE LOGIN - with this button you can delete the shown data from your data storage",
                "DELETE_LOGIN");
        // Replace Action Listener
        btn_delete.removeActionListener(btn_delete.getActionListeners()[0]);
        btn_delete.addActionListener(new MyActionListener());
        // Adjust constraints and add element
        setGbc(0, counterY++, 1);
        pnl_leftHalf.add(btn_delete, gbc);
        // add free space
        setGbc(0, counterY++, 1);
        pnl_leftHalf.add(Box.createRigidArea(new Dimension(0, spacer * 9)), gbc);

        //----------------------------
        // Right half
        pnl_rightHalf.setLayout(new BoxLayout(pnl_rightHalf, BoxLayout.Y_AXIS));
        // Create Table
        // 0) JLabel for Text
        lbl_plsSelect = new JLabel("SELECT A LOGIN TO VIEW/EDIT/DELETE");
        lbl_plsSelect.setFont(ProgramParameters.FONT_TXT_SIZE_HEADING2);
        lbl_plsSelect.setAlignmentX(Component.LEFT_ALIGNMENT);
        BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.red), lbl_plsSelect.getBorder());
        pnl_rightHalf.add(lbl_plsSelect);
        pnl_rightHalf.add(Box.createRigidArea(new Dimension(0, 20)));
        // 1) Create JTable       
        tbl_login = new JTable();
        tbl_login.setPreferredSize(new Dimension(380, 400));
        // Create Table Model
        tblModel = new MyLoginTableModel();

        // Add column names
        String[] columnNames =
        {
            "", "Username", "Password (encrypted)"
        };
        //    Add customized table model
        tbl_login.setModel(tblModel);
        tblModel.setColumnIdentifiers(columnNames);

        // Adjust  column widths
        tbl_login.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tbl_login.getColumnModel().getColumn(0).setPreferredWidth(calculateColumnWidth(0.05, tbl_login.getPreferredSize().width));
        tbl_login.getColumnModel().getColumn(1).setPreferredWidth(calculateColumnWidth(0.50, tbl_login.getPreferredSize().width));
        tbl_login.getColumnModel().getColumn(2).setPreferredWidth(calculateColumnWidth(0.45, tbl_login.getPreferredSize().width));

        tbl_login.setFillsViewportHeight(true);

        // 2) Create JScrollpane with JTable inside
        scrollpane = new JScrollPane(tbl_login, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        pnl_rightHalf.add(scrollpane);
    }

    private void setGbc(int gridX, int gridY, int gridwidthX)
    {
        gbc.gridx = gridX;
        gbc.gridy = gridY;
        gbc.weightx = 1;
        gbc.weighty = 1;
        //gbc.fill = GridBagConstraints.NONE;
        gbc.gridwidth = gridwidthX;
        gbc.anchor = GridBagConstraints.WEST;
    }

    private int calculateColumnWidth(double shareInPercent, int tableSize)
    {

        return (int) (shareInPercent * tableSize);

    }

    @Override
    public void updateTable()
    {
        // Get all logins 
        ArrayList<Login> allLogins = Login.getAllLogins();

        // Clear table
        tblModel.getDataVector().removeAllElements();
        // For each entry in word list create one row in table
        for (int i = 0; i < allLogins.size(); i++)
        {
            Login login = (Login) allLogins.get(i);
            String username = login.getUsername();
            String password = Arrays.toString(login.getEncryptedPassword());

            tblModel.addRow(new Object[]
            {
                Boolean.FALSE, username, password
            });
        }
    }

    @Override
    public void updateForm()
    {
        // Get selected login
        Login login = tblModel.getSelectedLogin();
        if (login != null)
        {
            // Update form fields with information of selected login
            form_fields[0].setText(login.getUsername());
            form_fields[1].setText(Arrays.toString(login.getEncryptedPassword()));
        }
    }

}

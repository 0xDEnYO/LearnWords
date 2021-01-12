/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LearnWords.view.components;

import LearnWords.controller.Controller;
import LearnWords.controller.ProgramParameters;
import LearnWords.model.administration.Login;
import LearnWords.model.administration.Person;
import LearnWords.model.administration.userManagement.Role;
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
public class MyUserDataEntryForm extends MyAbstractDataEntryForm
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
                    // SAVE User (Settings > Add/change/delete User)
                    case "SAVE_USER":
                        String username = form_fields[0].getInput();
                        String emailAddress = form_dropdowns[0].getInput();
                        String roleTitle = form_dropdowns[1].getInput();
                        // Check if username field has data
                        if (username.equals("") || username == null)
                        {
                            // username must not be empty
                            Controller.setErrorMessage("ERROR: Username address must not be empty. Please try again");
                            break;
                        }

                        // Get objects
                        Login login = Login.getLogin(username);
                        Person person = Person.getPerson(emailAddress);
                        if (person == null)
                        {
                            // person does not exist
                            Controller.setErrorMessage("ERROR: This person does not exist. Please add person first.");
                            break;
                        }
                        Role role = Role.getRole(roleTitle);
                        if (role == null)
                        {
                            // role does not exist
                            Controller.setErrorMessage("ERROR: This role does not exist. Please add role first.");
                            break;
                        }

                        // Check if existing user data is changed or new user is added
                        User user = null;
                        if ((user = User.getUser(username)) == null)
                        {
                            // User does not exist yet, add new user
                            user = User.addUser(login, person, role);
                        } else
                        {
                            // user exists, update data
                            user.setPerson(person);
                            user.setRole(role);
                        }

                        // update table
                        updateTable();

                        Controller.setErrorMessage("Data saved");
                        break;
                    // SAVE PERSON (Settings > Add/change/delete User)
                    case "DELETE_USER":
                        username = form_fields[0].getInput();
                        if (User.removeUser(username))
                        {
                            // update table
                            updateTable();
                            // User found and successfully removed
                            Controller.setErrorMessage("User removed");
                        } else
                        {
                            // User not found 
                            Controller.setErrorMessage("ERROR: No user with this username could be found");
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
                Controller.setErrorMessage("\nUnknown Action Event in Class MyUserDataEntryForm\n");
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
    private MyComboBox[] form_dropdowns;
    private MyUserTableModel tblModel;
    private JTable tbl_user;
    private JScrollPane scrollpane;

    public MyUserDataEntryForm()
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
        lbl_heading = new JLabel(" USER INPUT/EDIT FORM");
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
        form_fields = new MyTextfield[1];
        form_fields[0] = new MyTextfield("Login / Username", false);
        // add free space
        setGbc(0, counterY++, 1);
        pnl_leftHalf.add(Box.createRigidArea(new Dimension(0, spacer)), gbc);
        // Adjust constraints and add element
        setGbc(0, counterY++, 1);
        pnl_leftHalf.add(form_fields[0], gbc);
        // add free space
        setGbc(0, counterY++, 1);
        pnl_leftHalf.add(Box.createRigidArea(new Dimension(0, spacer)), gbc);

        form_dropdowns = new MyComboBox[2];
        form_dropdowns[0] = new MyComboBox("Person / Email Address");
        // Adjust constraints and add element
        setGbc(0, counterY++, 1);
        pnl_leftHalf.add(form_dropdowns[0], gbc);
        // add free space
        setGbc(0, counterY++, 1);
        pnl_leftHalf.add(Box.createRigidArea(new Dimension(0, spacer)), gbc);

        form_dropdowns[1] = new MyComboBox("Role");
        // Adjust constraints and add element
        setGbc(0, counterY++, 1);
        pnl_leftHalf.add(form_dropdowns[1], gbc);
        // add free space
        setGbc(0, counterY++, 1);
        pnl_leftHalf.add(Box.createRigidArea(new Dimension(0, spacer * 3)), gbc);

        // Create Buttons SAVE/DELETE
        // Button "SAVE"
        btn_save = MyButton.getMyButton("../images/btn_save.gif",
                "SAVE USER - with this button you can save the user data in this form",
                "SAVE_USER");
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
                "DELETE USER - with this button you can delete the shown data from your data storage",
                "DELETE_USER");
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
        lbl_plsSelect = new JLabel("SELECT A USER TO VIEW/EDIT/DELETE");
        lbl_plsSelect.setFont(ProgramParameters.FONT_TXT_SIZE_HEADING2);
        lbl_plsSelect.setAlignmentX(Component.LEFT_ALIGNMENT);
        BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.red), lbl_plsSelect.getBorder());
        pnl_rightHalf.add(lbl_plsSelect);
        pnl_rightHalf.add(Box.createRigidArea(new Dimension(0, 20)));
        // 1) Create JTable       
        tbl_user = new JTable();
        tbl_user.setPreferredSize(new Dimension(380, 400));
        // Create Table Model
        tblModel = new MyUserTableModel();

        // Add column names
        String[] columnNames =
        {
            "", "Username", "Email Address", "Role"
        };
        //    Add customized table model
        tbl_user.setModel(tblModel);
        tblModel.setColumnIdentifiers(columnNames);

        // Adjust  column widths
        tbl_user.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tbl_user.getColumnModel().getColumn(0).setPreferredWidth(calculateColumnWidth(0.05, tbl_user.getPreferredSize().width));
        tbl_user.getColumnModel().getColumn(1).setPreferredWidth(calculateColumnWidth(0.35, tbl_user.getPreferredSize().width));
        tbl_user.getColumnModel().getColumn(2).setPreferredWidth(calculateColumnWidth(0.35, tbl_user.getPreferredSize().width));
        tbl_user.getColumnModel().getColumn(3).setPreferredWidth(calculateColumnWidth(0.25, tbl_user.getPreferredSize().width));

        tbl_user.setFillsViewportHeight(true);

        // 2) Create JScrollpane with JTable inside
        scrollpane = new JScrollPane(tbl_user, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
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
        // Get all users
        ArrayList<User> allUsers = User.getAllUsers();

        // Clear table
        tblModel.getDataVector().removeAllElements();
        // For each entry in word list create one row in table
        for (int i = 0; i < allUsers.size(); i++)
        {
            User user = (User) allUsers.get(i);
            String username = user.getUsername();
            String emailAddress = user.getPerson().getEmailAddress();
            String role = user.getRole().getRoleTitle();

            tblModel.addRow(new Object[]
            {
                Boolean.FALSE, username, emailAddress, role
            });
        }
    }

    @Override
    public void updateForm()
    {
        // Reset dropdown fields with current values
            // Get all Person object identifiers into string array and create dropdown of it
            ArrayList<Person> allPersons = Person.getAllPersons();
            String[] allIdentifiers = new String[200];
            for (int i = 0; i < allPersons.size(); i++)
            {
                allIdentifiers[i] = allPersons.get(i).getEmailAddress();
            }
            form_dropdowns[0].setListOfValues(allIdentifiers);

            // Get all Role object identifiers into string array and create dropdown of it
            ArrayList<Role> allRoles = Role.getAllRoles();
            for (int i = 0; i < allRoles.size(); i++)
            {
                allIdentifiers[i] = allRoles.get(i).getRoleTitle();
            }
            form_dropdowns[1].setListOfValues(allIdentifiers);
        
        
        // Get selected user
        User user = tblModel.getSelectedUser();
        if (user != null)
        {
            // Update form fields with information of selected user
            form_fields[0].setText(user.getUsername());

            // Select the right values
            form_dropdowns[0].setText(user.getPerson().getEmailAddress());
            form_dropdowns[1].setText(user.getRole().getRoleTitle());
        }
    }

}

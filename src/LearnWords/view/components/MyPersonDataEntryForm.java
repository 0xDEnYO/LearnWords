/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LearnWords.view.components;

import LearnWords.controller.Controller;
import LearnWords.controller.ProgramParameters;
import LearnWords.model.administration.Person;
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
public class MyPersonDataEntryForm extends MyAbstractDataEntryForm
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
                    // SAVE PERSON (Settings > Add/change/delete Person)
                    case "SAVE_PERSON":
                        String firstName = form_fields[0].getInput();
                        String lastName = form_fields[1].getInput();
                        String emailAddress = form_fields[2].getInput();
                        if (emailAddress.equals("") || emailAddress == null)
                        {
                            // email address must not be empty
                            Controller.setErrorMessage("ERROR: Email address must not be empty. Please try again");
                            break;
                        }

                        // Check if existing user data is changed or new user is added
                        Person person = null;
                        if ((person = Person.getPerson(emailAddress)) == null)
                        {
                            // Person does not exist yet, add new person
                            person = Person.addPerson(firstName, lastName, emailAddress);
                        } else
                        {
                            // person exists, update data
                            person.setFirstName(firstName);
                            person.setLastName(lastName);
                        }

                        // update table
                        updateTable();

                        Controller.setErrorMessage("Data saved");
                        break;
                    // SAVE PERSON (Settings > Add/change/delete Person)
                    case "DELETE_PERSON":
                        emailAddress = form_fields[2].getInput();
                        if (Person.removePerson(emailAddress))
                        {
                            // update table
                            updateTable();
                            // Person found and successfully removed
                            Controller.setErrorMessage("Person removed");
                        } else
                        {
                            // Person not found 
                            Controller.setErrorMessage("ERROR: No person with this email address could be found");
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
                Controller.setErrorMessage("\nUnknown Action Event in Class MyPersonDataEntryForm\n");
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
    private MyPersonTableModel tblModel;
    private JTable tbl_person;
    private JScrollPane scrollpane;

    public MyPersonDataEntryForm()
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
        lbl_heading = new JLabel(" PERSON INPUT/EDIT FORM");
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
        form_fields[0] = new MyTextfield("First Name");
        // add free space
        setGbc(0, counterY++, 1);
        pnl_leftHalf.add(Box.createRigidArea(new Dimension(0, spacer)), gbc);
        // Adjust constraints and add element
        setGbc(0, counterY++, 1);
        pnl_leftHalf.add(form_fields[0], gbc);
        // add free space
        setGbc(0, counterY++, 1);
        pnl_leftHalf.add(Box.createRigidArea(new Dimension(0, spacer)), gbc);

        form_fields[1] = new MyTextfield("Last Name");
        // Adjust constraints and add element
        setGbc(0, counterY++, 1);
        pnl_leftHalf.add(form_fields[1], gbc);
        // add free space
        setGbc(0, counterY++, 1);
        pnl_leftHalf.add(Box.createRigidArea(new Dimension(0, spacer)), gbc);

        form_fields[2] = new MyTextfield("Email Address", false);
        // Adjust constraints and add element
        setGbc(0, counterY++, 1);
        pnl_leftHalf.add(form_fields[2], gbc);
        // add free space
        setGbc(0, counterY++, 1);
        pnl_leftHalf.add(Box.createRigidArea(new Dimension(0, spacer * 3)), gbc);

        // Create Buttons SAVE/DELETE
        // Button "SAVE"
        btn_save = MyButton.getMyButton("../images/btn_save.gif",
                "SAVE PERSON - with this button you can save the person data in this form",
                "SAVE_PERSON");
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
                "DELETE PERSON - with this button you can delete the shown data from your data storage",
                "DELETE_PERSON");
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
        lbl_plsSelect = new JLabel("SELECT A PERSON TO VIEW/EDIT/DELETE");
        lbl_plsSelect.setFont(ProgramParameters.FONT_TXT_SIZE_HEADING2);
        lbl_plsSelect.setAlignmentX(Component.LEFT_ALIGNMENT);
        BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.red), lbl_plsSelect.getBorder());
        pnl_rightHalf.add(lbl_plsSelect);
        pnl_rightHalf.add(Box.createRigidArea(new Dimension(0, 20)));
        // 1) Create JTable       
        tbl_person = new JTable();
        tbl_person.setPreferredSize(new Dimension(380, 400));
        // Create Table Model
        tblModel = new MyPersonTableModel();

        // Add column names
        String[] columnNames =
        {
            "", "First Name", "Last Name", "Email Address"
        };
        //    Add customized table model
        tbl_person.setModel(tblModel);
        tblModel.setColumnIdentifiers(columnNames);

        // Adjust  column widths
        tbl_person.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tbl_person.getColumnModel().getColumn(0).setPreferredWidth(calculateColumnWidth(0.05, tbl_person.getPreferredSize().width));
        tbl_person.getColumnModel().getColumn(1).setPreferredWidth(calculateColumnWidth(0.25, tbl_person.getPreferredSize().width));
        tbl_person.getColumnModel().getColumn(2).setPreferredWidth(calculateColumnWidth(0.25, tbl_person.getPreferredSize().width));
        tbl_person.getColumnModel().getColumn(3).setPreferredWidth(calculateColumnWidth(0.45, tbl_person.getPreferredSize().width));

        tbl_person.setFillsViewportHeight(true);

        // 2) Create JScrollpane with JTable inside
        scrollpane = new JScrollPane(tbl_person, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
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
        // Get all persons 
        ArrayList<Person> allPersons = Person.getAllPersons();

        // Clear table
        tblModel.getDataVector().removeAllElements();
        // For each entry in word list create one row in table
        for (int i = 0; i < allPersons.size(); i++)
        {
            Person person = (Person) allPersons.get(i);
            String firstName = person.getFirstName();
            String lastName = person.getLastName();
            String emailAddress = person.getEmailAddress();

            tblModel.addRow(new Object[]
            {
                Boolean.FALSE, firstName, lastName, emailAddress
            });
        }
    }

    @Override
    public void updateForm()
    {
        // Get selected person
        Person person = tblModel.getSelectedPerson();
        if (person != null)
        {
            // Update form fields with information of selected person
            form_fields[0].setText(person.getFirstName());
            form_fields[1].setText(person.getLastName());
            form_fields[2].setText(person.getEmailAddress());
        }
    }

}

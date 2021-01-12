/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LearnWords.view.components;

import LearnWords.controller.Controller;
import LearnWords.model.administration.userManagement.User;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Daniel Bläcker (d.blaecker@gmail.com)
 */
public class MyUserTableModel extends DefaultTableModel
{

    private User selectedUser;

    @Override
    public String getColumnName(int i)
    {
        String tmp = "";
        switch (i)
        {
            case 0:
                tmp = "";
                break;
            case 1:
                tmp = "Username";
                break;
            case 2:
                tmp = "EmailAddress";
                break;
            case 3:
                tmp = "Role";
                break;
            default:
                tmp = "ERROR";
        }
        return tmp;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex)
    {

        switch (columnIndex)
        {

            case 0:
                return Boolean.class;
            case 1:
                return String.class;
            case 2:
                return String.class;
            case 3:
                return String.class;
            default:
                return null;

        }

    }

//    private ArrayList<String[]> getSelectedRows()
//    {
//
//        ArrayList<String[]> rows = new ArrayList<>();
//
//        for (int i = 0; i < this.getRowCount(); i++)
//        {
//            // if checkmark is set, add row to selection
//            if (Boolean.valueOf(this.getValueAt(i, 0).toString()))
//            {
//                // Add first name to row
//                String[] row = new String[3];
//                row[0] = this.getValueAt(i, 1).toString();
//                // Add last name to row
//                row[1] = this.getValueAt(i, 2).toString();
//                // Add email address to row
//                row[2] = this.getValueAt(i, 3).toString();
//                rows.add(row);
//            }
//        }
//        return rows;
//    }

    public User getSelectedUser()
    {
        if (selectedUser == null)
        {
            //Controller.setErrorMessage("ERROR: No user selected");
        }
        return selectedUser;
    }

    @Override
    public void fireTableCellUpdated(int row, int column)
    {
        fireTableChanged(new TableModelEvent(this, row, row, column));
        // Uncheck all other rows
        //uncheckAll(row);

        // Get user object and update selectedUser variable
        String username = getValueAt(row, 1).toString();
        User user = User.getUser(username);
        selectedUser = user;
        
        // Update fields in form with values of selected user
        Controller.updateAllDataEntryFormsAndTables();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
        //only column 0 should be editable here
        return columnIndex == 0;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LearnWords.view.components;

import LearnWords.controller.Controller;
import LearnWords.model.administration.userManagement.Role;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Daniel Bläcker (d.blaecker@gmail.com)
 */
public class MyRoleTableModel extends DefaultTableModel
{

    private Role selectedRole;

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
                tmp = "RoleTitle";
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
            default:
                return null;

        }

    }


    public Role getSelectedRole()
    {
        if (selectedRole == null)
        {
            //Controller.setErrorMessage("ERROR: No role selected");
        }
        return selectedRole;
    }

    @Override
    public void fireTableCellUpdated(int row, int column)
    {
        fireTableChanged(new TableModelEvent(this, row, row, column));
        // Uncheck all other rows
        //uncheckAll(row);

        // Get role object and update selectedRole variable
        String roleTitle = getValueAt(row, 1).toString();
        Role role = Role.getRole(roleTitle);
        selectedRole = role;
        
        // Update fields in form with values of selected role
        Controller.updateAllDataEntryFormsAndTables();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
        //only column 0 should be editable here
        return columnIndex == 0;
    }

}

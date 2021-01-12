/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LearnWords.view.components;

import LearnWords.controller.Controller;
import LearnWords.model.administration.Login;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Daniel Bläcker (d.blaecker@gmail.com)
 */
public class MyLoginTableModel extends DefaultTableModel
{

    private Login selectedLogin;

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
                tmp = "Password (encrypted)";
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
            default:
                return null;

        }

    }

    public Login getSelectedLogin()
    {
        if (selectedLogin == null)
        {
            //Controller.setErrorMessage("ERROR: No login selected");
        }
        return selectedLogin;
    }

    @Override
    public void fireTableCellUpdated(int row, int column)
    {
        fireTableChanged(new TableModelEvent(this, row, row, column));
        // Uncheck all other rows
        //uncheckAll(row);

        // Get login object and update selectedLogin variable
        String username = getValueAt(row, 1).toString();
        Login login = Login.getLogin(username);
        selectedLogin = login;
        
        // Update fields in form with values of selected login
        Controller.updateAllDataEntryFormsAndTables();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
        //only column 0 should be editable here
        return columnIndex == 0;
    }

}

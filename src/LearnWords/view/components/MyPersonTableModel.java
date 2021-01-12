/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LearnWords.view.components;

import LearnWords.controller.Controller;
import LearnWords.model.administration.Person;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Daniel Bläcker (d.blaecker@gmail.com)
 */
public class MyPersonTableModel extends DefaultTableModel
{

    private Person selectedPerson;

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
                tmp = "FirstName";
                break;
            case 2:
                tmp = "LastName";
                break;
            case 3:
                tmp = "EmailAddress";
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

    public Person getSelectedPerson()
    {
        if (selectedPerson == null)
        {
            //Controller.setErrorMessage("ERROR: No person selected");
        }
        return selectedPerson;
    }

    @Override
    public void fireTableCellUpdated(int row, int column)
    {
        fireTableChanged(new TableModelEvent(this, row, row, column));
        // Uncheck all other rows
        //uncheckAll(row);

        // Get person object and update selectedPerson variable
        String emailAddress = getValueAt(row, 3).toString();
        Person person = Person.getPerson(emailAddress);
        selectedPerson = person;
        
        // Update fields in form with values of selected person
        Controller.updateAllDataEntryFormsAndTables();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
        //only column 0 should be editable here
        return columnIndex == 0;
    }

}

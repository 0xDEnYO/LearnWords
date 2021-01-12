/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LearnWords.view.components;

import LearnWords.controller.Controller;
import LearnWords.model.functional.WordList;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Daniel Bläcker (d.blaecker@gmail.com)
 */
public class MyWordListTableModel extends DefaultTableModel {

    private JTable tbl;
    

    @Override
    public String getColumnName(int i) {
        String tmp = "";
        switch (i) {
            case 0:
                tmp = "";
                break;
            case 1:
                tmp = "NAME";
                break;
            case 2:
                tmp = "learned";
                break;
            case 3:
                tmp = "unlearned";
                break;
            case 4:
                tmp = "TOTAL";
                break;

            default:
                tmp = "ERROR";
        }
        return tmp;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {

        switch (columnIndex) {

            case 0:
                return Boolean.class;
            case 1:
                return String.class;
            case 2:
                return Integer.class;
            case 3:
                return Integer.class;
            case 4:
                return Integer.class;

            default:
                return Integer.class;

        }

    }

    private ArrayList<String[]> getSelectedRows() {

        ArrayList<String[]> rows = new ArrayList<>();

        for (int i = 0; i < this.getRowCount(); i++) {
            // if checkmark is set, add row to selection
            if (Boolean.valueOf(this.getValueAt(i, 0).toString())) {
                // Add name to row
                String[] row = new String[4];
                row[0] = this.getValueAt(i, 1).toString();
                // Add learned to row
                row[1] = this.getValueAt(i, 2).toString();
                // Add unlearned to row
                row[2] = this.getValueAt(i, 3).toString();
                // Add learned to row
                row[3] = this.getValueAt(i, 4).toString();
                rows.add(row);
            }
        }
        return rows;
    }

    public ArrayList<WordList> getSelectedWordLists() {

        ArrayList<WordList> selection = new ArrayList<>();

        // Get data of selected rows
        ArrayList<String[]> data = getSelectedRows();

        if (data.isEmpty()) {
            Controller.setErrorMessage("ERROR: No rows selected");
            return null;
        }
        // Get word list objects
        for (String[] row : data) {
            // Get word list name
            String name = row[0];
            // find word list object and add to selection
            WordList list = Controller.findWordList(name);
            if (list != null) {
                selection.add(list);
            } else {
                Controller.setErrorMessage("Error: in getSelectedWordLists(): word list not found, name:" + name);
            }
        }

        // Return result
        return selection;

    }

    @Override
    public void fireTableCellUpdated(int row, int column) {
        fireTableChanged(new TableModelEvent(this, row, row, column));
//        // Get word list object
//        String name = getValueAt(row, 1).toString();
//        WordList list = Controller.findWordList(name);
//        // If checkbox was unchecked, remove list from selection
//        if (Boolean.valueOf(getValueAt(row, 0).toString()) == true) {
//            // add word list to selection
//            Controller.addWordListToSelection(list);
//        } else {
//            // Remove word list from selection
//            Controller.removeWordListFromSelection(list);
//        }
        
           // Update selected lists in myWorkspace
           Controller.updateSelectedWordListsInMyWorkspace(getSelectedWordLists());

    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        //only column 0 should be editable here
        return columnIndex == 0;
    }
}

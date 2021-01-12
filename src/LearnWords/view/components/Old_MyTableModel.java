/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LearnWords.view.components;

import java.util.EventListener;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Daniel Bläcker (d.blaecker@gmail.com)
 */
public class Old_MyTableModel extends AbstractTableModel
{

    private final int rows;
    private final int columns;
    private final Object[][] matrix;
    private JTable tbl;

    @Override
    public <T extends EventListener> T[] getListeners(Class<T> type)
    {
        return super.getListeners(type); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void fireTableChanged(TableModelEvent tme)
    {
        super.fireTableChanged(tme); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void fireTableCellUpdated(int rowIndex, int columnIndex)
    {
        super.fireTableCellUpdated(rowIndex, columnIndex); //To change body of generated methods, choose Tools | Templates.

        // select table row if checkbox in column 0 was checked (=getValue of column 0 at this row = true)
        if (columnIndex == 0 && getValueAt(rowIndex, columnIndex).equals(Boolean.TRUE))
        {
            // TODO
            // add code here that will mark the row as highlighted
            // tbl.getSelectionModel().addSelectionInterval(rowIndex, rowIndex+1);  // doesnt work
        }
    }

    @Override
    public void fireTableRowsDeleted(int rowIndex, int columnIndex)
    {
        super.fireTableRowsDeleted(rowIndex, columnIndex); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void fireTableRowsUpdated(int rowIndex, int columnIndex)
    {
        super.fireTableRowsUpdated(rowIndex, columnIndex); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void fireTableRowsInserted(int rowIndex, int columnIndex)
    {
        super.fireTableRowsInserted(rowIndex, columnIndex); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void fireTableStructureChanged()
    {
        super.fireTableStructureChanged(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void fireTableDataChanged()
    {
        super.fireTableDataChanged(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public TableModelListener[] getTableModelListeners()
    {
        return super.getTableModelListeners(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int findColumn(String string)
    {
        return super.findColumn(string); //To change body of generated methods, choose Tools | Templates.
    }

    public Old_MyTableModel(int rowIndex, int columnIndex, JTable tbl)
    {

        matrix = new Object[rowIndex][columnIndex];
        rows = rowIndex;
        columns = columnIndex;
        this.tbl = tbl;
    }

    @Override
    public int getRowCount()
    {
        return this.rows;
    }

    @Override
    public int getColumnCount()
    {
        return this.columns;
    }

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
    public Class<?> getColumnClass(int columnIndex)
    {

        switch (columnIndex)
        {

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

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
        //only column 0 should be editable here
        return columnIndex == 0;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        return matrix[rowIndex][columnIndex];
    }

    @Override
    public void setValueAt(Object o, int rowIndex, int columnIndex)
    {
        try
        {
            switch (columnIndex)
            {

                case 0:     // boolean
                    //matrix[rowIndex][columnIndex] = Boolean.parseBoolean(o.toString());
                    matrix[rowIndex][columnIndex] = (boolean) o;
                    break;
                case 1:     // String
                    matrix[rowIndex][columnIndex] = o.toString();
                    break;
                case 2:     // int
                case 3:     // int
                case 4:
                    matrix[rowIndex][columnIndex] = Integer.parseInt(o.toString());
                    break;
                default:
                    System.out.println("Error in TableModel.getValueAt()");

            }

        } catch (Exception e)
        {
            System.out.println("Exception in TableModel.setValueAt() \nrowindex: " + rowIndex + "\ncolumnIndex: " + columnIndex + "\n" + "Object o: " + o.toString());
            System.out.println("e.getMessage(): " + e.getMessage());
            System.out.println("e.toString(): " + e.toString());
            e.printStackTrace();
            System.out.println("-------------------------");
        }
        fireTableCellUpdated(rowIndex, columnIndex);
    }
    

    
    
    public void addRow(String[] row){
        
        
    }

}

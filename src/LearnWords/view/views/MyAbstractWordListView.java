/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LearnWords.view.views;

import LearnWords.controller.ProgramParameters;
import LearnWords.model.functional.WordList;
import LearnWords.view.components.MyWordListTableModel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 *
 * @author Daniel Bläcker (d.blaecker@gmail.com)
 */
public abstract class MyAbstractWordListView extends MyAbstractView
{
    
    protected final JTable tbl_words;
    protected final MyWordListTableModel tblModel;
    protected final JPanel pnl_leftHalf, pnl_rightHalf, pnl_allContent;
    protected final JScrollPane scrollpane;
    protected final JLabel lbl_plsSelect;
    private GridBagConstraints gbc = new GridBagConstraints();

    public MyAbstractWordListView()
    {
        super();

        // Create JPanels for left and right half
        pnl_leftHalf = new JPanel();
        pnl_leftHalf.setLayout(new BoxLayout(pnl_leftHalf, BoxLayout.Y_AXIS));
        pnl_leftHalf.setBackground(ProgramParameters.COLOR_GUI_FOREGROUND);
        pnl_leftHalf.setMinimumSize(new Dimension(368, 550));

        pnl_rightHalf = new JPanel();
        pnl_rightHalf.setLayout(new BoxLayout(pnl_rightHalf, BoxLayout.Y_AXIS));
        pnl_rightHalf.setBackground(ProgramParameters.COLOR_GUI_FOREGROUND);
        pnl_leftHalf.setBorder(BorderFactory.createRaisedBevelBorder());

        // Set main content panel to vertical box layout and add panels representing left and right half
        pnl_allContent = new JPanel();
        pnl_allContent.setBackground(ProgramParameters.COLOR_GUI_FOREGROUND);
        //pnl_LearnWords.setPreferredSize(new Dimension(2000, 1500));
        pnl_allContent.setLayout(new BoxLayout(pnl_allContent, BoxLayout.X_AXIS));

        // Add left and right half panel to main panel
        pnl_allContent.add(pnl_leftHalf);
        // create free space between the halfs
        pnl_allContent.add(Box.createRigidArea(new Dimension(150, 0)));
        pnl_allContent.add(pnl_rightHalf);

        // 1) Create JTable       
        tbl_words = new JTable();
        // Create Table Model
        tblModel = new MyWordListTableModel();
        //    Add customized table model
        tbl_words.setModel(tblModel);


        // Add column names
        String[] columnNames =
        {
            "", "Name", "Learned", "Unlearned", "Total"
        };
        tbl_words.setModel(tblModel);
        tblModel.setColumnIdentifiers(columnNames);

        // Adjust  column widths
        tbl_words.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tbl_words.getColumnModel().getColumn(0).setPreferredWidth(calculateColumnWidth(0.05, tbl_words.getPreferredSize().width));
        tbl_words.getColumnModel().getColumn(1).setPreferredWidth(calculateColumnWidth(0.50, tbl_words.getPreferredSize().width));
        tbl_words.getColumnModel().getColumn(2).setPreferredWidth(calculateColumnWidth(0.15, tbl_words.getPreferredSize().width));
        tbl_words.getColumnModel().getColumn(3).setPreferredWidth(calculateColumnWidth(0.15, tbl_words.getPreferredSize().width));
        tbl_words.getColumnModel().getColumn(4).setPreferredWidth(calculateColumnWidth(0.15, tbl_words.getPreferredSize().width));
        //tbl_words.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        // System.out.println("Width: " + tbl_words.getPreferredSize().width);

        //tbl_words.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        tbl_words.setFillsViewportHeight(true);

        // 0) JLabel for Text
        lbl_plsSelect = new JLabel("PLEASE SELECT ONE OR MORE WORD LISTS");
        lbl_plsSelect.setFont(ProgramParameters.FONT_TXT_SIZE_HEADING2);
        lbl_plsSelect.setAlignmentX(Component.LEFT_ALIGNMENT);
        BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.red), lbl_plsSelect.getBorder());
        pnl_leftHalf.add(lbl_plsSelect);
        pnl_leftHalf.add(Box.createRigidArea(new Dimension(0, 20)));

        // 2) Create JScrollpane with JTable inside
        scrollpane = new JScrollPane(tbl_words, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        pnl_leftHalf.add(scrollpane);

        // Define availability for BACK, MENU and LOGOUT buttons in footer and header
        this.backButton = true;
        this.menuButton = true;
        this.logoutButton = true;

        // Add all elements to main content panel
        gbc = new GridBagConstraints();
        pnl_content.add(pnl_allContent, gbc);

    }

    private int calculateColumnWidth(double shareInPercent, int tableSize)
    {

        return (int) (shareInPercent * tableSize);

    }

    public void updateTable(ArrayList<WordList> allWordLists)
    {

        // Clear table
        tblModel.getDataVector().removeAllElements();
        // For each entry in word list create one row in table
        for (int i = 0; i < allWordLists.size(); i++)
        {
            WordList list = allWordLists.get(i);
            String name = list.getName();
            int learned = list.getLearnedCount();
            int unlearned = list.getUnlearnedCount();
            int total = list.getTotalCount();
            tblModel.addRow(new Object[]
            {
                Boolean.FALSE, name, learned, unlearned, total
            });
        }
    }

    public ArrayList<WordList> getSelectedWordLists()
    {

        return tblModel.getSelectedWordLists();

    }

}

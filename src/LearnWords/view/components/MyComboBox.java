/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LearnWords.view.components;

import LearnWords.controller.ProgramParameters;
import java.awt.Dimension;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Daniel Bläcker (d.blaecker@gmail.com)
 */
public class MyComboBox extends JPanel
{

    private JComboBox txt_dropdown;
    private JLabel lbl_description;

    public MyComboBox(String description)
    {
        // setup this JPanel-object
        this.setBackground(ProgramParameters.COLOR_GUI_BACKGROUND);
        this.setPreferredSize(new Dimension(400, 40));
        this.setMinimumSize(new Dimension(400, 40));
        this.setLayout(null);
        //this.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // create elements 
        txt_dropdown = new JComboBox<>();
        lbl_description = new JLabel(description);

        // set size and position for each element
        lbl_description.setBounds(5, 5, 100, 20);
        txt_dropdown.setBounds(190, 5, 200, 20);

        // add elements
        this.add(txt_dropdown);
        this.add(lbl_description);

    }

    public MyComboBox(String description, boolean editable)
    {
        this(description);
        //if not editable then set input field to non-editable
        txt_dropdown.setEditable(false);
    }

    public String getInput()
    {
        return this.txt_dropdown.getSelectedItem().toString();
    }

    public void setText(String text)
    {
        Object o = (Object) text;
        this.txt_dropdown.setSelectedItem(o);
    }

    public void setListOfValues(String[] values)
    {
        // remove all items
        this.txt_dropdown.removeAllItems();
        // set new items
        for (String str : values)
        {
            // go through array and add itentifiers to dropdown value list
            this.txt_dropdown.addItem(str);
        }
    }
}

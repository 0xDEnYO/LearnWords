/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LearnWords.view.components;

import LearnWords.controller.ProgramParameters;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Daniel Bläcker (d.blaecker@gmail.com)
 */
public class MyTextfield extends JPanel
{

    private JTextField txt_input;
    private JLabel lbl_description;

    public MyTextfield(String description)
    {
        // setup this JPanel-object
        this.setBackground(ProgramParameters.COLOR_GUI_BACKGROUND);
        this.setPreferredSize(new Dimension(400, 40));
        this.setMinimumSize(new Dimension(400, 40));
        this.setLayout(null);
        //this.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // create elements 
        txt_input = new JTextField();
        lbl_description = new JLabel(description);

        // set size and position for each element
        lbl_description.setBounds(5, 5, 100, 20);
        txt_input.setBounds(190, 5, 200, 20);

        // add elements
        this.add(txt_input);
        this.add(lbl_description);

    }

    public MyTextfield(String description, boolean editable)
    {
        this(description);
        //if not editable then set input field to non-editable
        txt_input.setEditable(false);
    }

    public String getInput()
    {
        return this.txt_input.getText();
    }

    public void setText(String text)
    {
        this.txt_input.setText(text);
    }

}

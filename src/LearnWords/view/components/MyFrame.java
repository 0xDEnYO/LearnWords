/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LearnWords.view.components;

import javax.swing.JFrame;

public class MyFrame extends JFrame
{

    // --------------------------------------------------------------
    public MyFrame(String title)
    {
        super(title);
    }

    // --------------------------------------------------------------
    public void show(int x, int y, int width, int height)
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // pack();
        //setBounds(x, y, width, height);
        setSize(width, height);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    // --------------------------------------------------------------

}

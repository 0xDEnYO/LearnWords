/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LearnWords.view.components;

import LearnWords.controller.*;
import LearnWords.view.gui.*;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.border.BevelBorder;

/**
 *
 * @author Daniel Bläcker (d.blaecker@gmail.com)
 */
public class MyButton extends JButton //implements Runnable
{

    private MyButton(String actionCommand, Icon icon) {

        super(icon);

        if (actionCommand.equals("MENU") || actionCommand.equals("LOGOUT") || actionCommand.equals("BACK")
                || actionCommand.equals("VIEW_LEARNED") || actionCommand.equals("VIEW_UNLEARNED") || actionCommand.equals("VIEW_ALL")
                || actionCommand.equals("RESET_SELECTED") || actionCommand.equals("RESET_ALL") || actionCommand.equals("REQUEST_DELETION")
                || actionCommand.equals("CONNECT")) {
            this.setBorder(BorderFactory.createEmptyBorder());
        } else {
            this.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.GRAY, Color.DARK_GRAY));
        }

        this.setContentAreaFilled(false);
        this.setActionCommand(actionCommand);
        this.addActionListener(new ButtonControlActionListener());

    }

    public static MyButton getMyButton(String iconPath, String alternativeText, String actionCommand) {

        Icon icon = GUI.createImageIcon(iconPath, alternativeText);
        return new MyButton(actionCommand, icon);
    }

//    @Override
//    public void run()
//    {
////        // Set border colors the opposite way until timer runs off to create "button click" effect
////        this.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.DARK_GRAY, Color.GRAY));
////
//////        setVisible(false);
////        try
////        {
////            Thread.sleep(ProgramConstants.BUTTON_CLICK_ANIMATION_TIME);
////        } catch (InterruptedException ex)
////        {
////            Logger.getLogger(ViewLogin.class.getName()).log(Level.SEVERE, null, ex);
////        }
//////        setVisible(true);
////
////        if (getActionCommand().equals("MENU") || getActionCommand().equals("LOGOUT") || getActionCommand().equals("BACK")
////                || getActionCommand().equals("VIEW_LEARNED") || getActionCommand().equals("VIEW_UNLEARNED") || getActionCommand().equals("VIEW_ALL"))
////        {
////            this.setBorder(BorderFactory.createEmptyBorder());
////        } else
////        {
////            this.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.GRAY, Color.DARK_GRAY));
////        }
//
//    }
}

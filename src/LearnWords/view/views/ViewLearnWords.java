/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LearnWords.view.views;

import LearnWords.controller.ProgramParameters;
import LearnWords.view.components.MyButton;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.JButton;

/**
 *
 * @author Daniel Bläcker (d.blaecker@gmail.com)
 */
public class ViewLearnWords extends MyAbstractWordListView
{

    private final JButton btn_viewLearned, btn_viewUnlearned, btn_viewAll, btn_startSelected, btn_startAll, btn_resume;
    private static ViewLearnWords instance;

    private ViewLearnWords()
    {
        super();


//
        // Create menu elements

        // 2) Buttons (incl action listener)
        // Button "MyAbstractView learned..."
        pnl_leftHalf.add(Box.createRigidArea(new Dimension(0, 20)));
        btn_viewLearned = MyButton.getMyButton("../images/btn_viewLearned.gif",
                "View learned - with this button you can view all words that are marked as learned",
                "VIEW_LEARNED");
        pnl_leftHalf.add(btn_viewLearned);
        pnl_leftHalf.add(Box.createRigidArea(new Dimension(0, 5)));
        // Button "MyAbstractView unlearned..."
        btn_viewUnlearned = MyButton.getMyButton("../images/btn_viewUnlearned.gif",
                "View unlearned - with this button you can view all words that are not yet marked as learned",
                "VIEW_UNLEARNED");
        pnl_leftHalf.add(btn_viewUnlearned);
        pnl_leftHalf.add(Box.createRigidArea(new Dimension(0, 5)));
        // Button "MyAbstractView all..."
        btn_viewAll = MyButton.getMyButton("../images/btn_viewAll.gif",
                "View all - with this button you can view all words (learned and unlearned)",
                "VIEW_ALL");
        pnl_leftHalf.add(btn_viewAll);

        // --------
        // Populate right half
        pnl_rightHalf.add(Box.createRigidArea(new Dimension(0, 20)));

        // Button "MyAbstractView Start selected..."
        btn_startSelected = MyButton.getMyButton("../images/btn_startSelected.gif",
                "Start selected - with this button you can start a new learning session with the selected words",
                "START_SELECTED");
        pnl_rightHalf.add(btn_startSelected);
        pnl_rightHalf.add(Box.createRigidArea(new Dimension(0, 20)));

        // Button "MyAbstractView Start selected..."
        btn_startAll = MyButton.getMyButton("../images/btn_startAll.gif",
                "Start all - with this button you can start a new learning session with all words",
                "START_ALL");
        pnl_rightHalf.add(btn_startAll);
        pnl_rightHalf.add(Box.createRigidArea(new Dimension(0, 60)));

        // Button "Resume last..."
        btn_resume = MyButton.getMyButton("../images/btn_resume.gif",
                "Resume - with this button you can resume your last learning session",
                "RESUME");

        pnl_rightHalf.add(btn_resume);
        pnl_rightHalf.add(Box.createRigidArea(new Dimension(0, 5)));

        // Set title and subtitle
        this.title = ProgramParameters.VIEW_TITLE_LEARNWORDS;
        this.subtitle = ProgramParameters.VIEW_SUBTITLE_LEARNWORDS;


    }

        public static ViewLearnWords getView()
    {
        if (instance == null|| (!(instance instanceof ViewLearnWords)))
        {
            instance = new ViewLearnWords();
        }
        return (ViewLearnWords)instance;
    }



}

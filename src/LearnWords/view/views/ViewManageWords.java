/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LearnWords.view.views;

import LearnWords.controller.ProgramParameters;
import LearnWords.view.components.MyButton;
import static LearnWords.view.views.ViewLearningSession.instance;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.JButton;

/**
 *
 * @author Daniel Bläcker (d.blaecker@gmail.com)
 */
public class ViewManageWords extends MyAbstractWordListView {


    private final JButton btn_requestDeletion, btn_resetSelected, btn_resetAll, btn_uploadCSV, btn_startAll, btn_blank;
    private static ViewManageWords instance;


    private ViewManageWords() {

        // Create menu elements
        // 2) Buttons (incl action listener)   
        // Button "Reset Selected"
        pnl_leftHalf.add(Box.createRigidArea(new Dimension(0, 20)));
        btn_resetSelected = MyButton.getMyButton("../images/btn_resetSelected.gif",
                "Reset selected - with this button you can reset the selected words to status unlearned",
                "RESET_SELECTED");
        pnl_leftHalf.add(btn_resetSelected);
        pnl_leftHalf.add(Box.createRigidArea(new Dimension(0, 5)));

        // Button "Reset All"
        btn_resetAll = MyButton.getMyButton("../images/btn_resetAll.gif",
                "Reset all - with this button you can reset all words to status unlearned",
                "RESET_ALL");
        pnl_leftHalf.add(btn_resetAll);
        pnl_leftHalf.add(Box.createRigidArea(new Dimension(0, 5)));

        // Button "Request deletion"
        btn_requestDeletion = MyButton.getMyButton("../images/btn_requestDeletion.gif",
                "Request deletion - with this button you can send a request for deletion of the selected words to your teacher",
                "REQUEST_DELETION");
        pnl_leftHalf.add(btn_requestDeletion);
        pnl_leftHalf.add(Box.createRigidArea(new Dimension(0, 5)));

        // --------
        // Populate right half
        pnl_rightHalf.add(Box.createRigidArea(new Dimension(0, 20)));

        // Button "Upload CSV"
        btn_uploadCSV = MyButton.getMyButton("../images/btn_uploadCSV.gif",
                "Upload CSV - with this button you can upload new words from a CSV file",
                "UPLOAD_CSV");
        pnl_rightHalf.add(btn_uploadCSV);
        pnl_rightHalf.add(Box.createRigidArea(new Dimension(0, 20)));

        // Button "Upload Manual"
        btn_startAll = MyButton.getMyButton("../images/btn_uploadManual.gif",
                "Upload  Manual - with this button you can upload new words manually",
                "UPLOAD_MANUAL");
        pnl_rightHalf.add(btn_startAll);
        pnl_rightHalf.add(Box.createRigidArea(new Dimension(0, 200)));
//
//        // Button "Upload Manual2"
        btn_blank = MyButton.getMyButton("../images/btn_blank.gif",
                "",
                "");
//        pnl_rightHalf.add(btn_blank);
//        pnl_rightHalf.add(Box.createRigidArea(new Dimension(0, 5)));

        // Set title and subtitle
        this.title = ProgramParameters.VIEW_TITLE_MANAGEWORDS;
        this.subtitle = ProgramParameters.VIEW_SUBTITLE_MANAGEWORDS;



    }
    
        public static ViewManageWords getView()
    {
        if (instance == null  || (!(instance instanceof ViewManageWords)) )
        {
            instance = new ViewManageWords();
        }
        return (ViewManageWords)instance;
    }





}

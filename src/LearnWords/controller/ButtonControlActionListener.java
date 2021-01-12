/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LearnWords.controller;

import LearnWords.model.dataStorage.MyWorkSpace;
import LearnWords.model.functional.LearningSession;
import LearnWords.model.functional.WordList;
import LearnWords.view.components.MyButton;
import LearnWords.view.gui.GUI;
import LearnWords.view.views.ViewLearningSession;
import LearnWords.view.views.ViewLogin;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

/**
 *
 * @author dblae
 */
public class ButtonControlActionListener implements ActionListener, Runnable
{
    // Action listener that will manage all the interactions on the view

    @Override
    public void actionPerformed(ActionEvent ae)
    {

        // not sure if needed but I had trouble with flickering views
        GUI.getFrame().repaint();

        if (ae.getSource() instanceof JPanel)
        {

            switch (ae.getActionCommand())
            {
                case "FLASHCARD":
                    // Add action for Flashcard Panel
                    Controller.setErrorMessage("Flashcard clicked, ActionListener");
                    break;

                default:
                    Controller.setErrorMessage("Error in ActionListener (Switch)");

            }

        }

        if (ae.getSource() instanceof MyButton)
        {

//                // Show button click animation in new thread
//                Thread th = new Thread((MyButton) ae.getSource());
//                th.start();
            switch (ae.getActionCommand())
            {
                case "VIEW_LEARNED":
                    Controller.setErrorMessage("VIEW_LEARNED");
                    //GUI.getGUI().activateView(ProgramParameters.VIEW_TITLE_REQUESTS);
                    break;
                case "VIEW_UNLEARNED":
                    Controller.setErrorMessage("VIEW_UNLEARNED");
                    //GUI.getGUI().activateView(ProgramParameters.VIEW_TITLE_MAINTAINWORDS);
                    break;
                case "VIEW_ALL":
                    Controller.setErrorMessage("VIEW_ALL");
                    //GUI.getGUI().activateView(ProgramParameters.VIEW_TITLE_EVALUATE);
                    break;
                case "START_SELECTED":
                    // Get selected word lists
                    WordList[] wordLists = Controller.getSelectedWordListsFromMyWorkspace();
                    if (wordLists == null)
                    {
                        Controller.setErrorMessage("ERROR: No word lists selected");
                    } else
                    {

                        // Create LearningSession with selected word lists
                        LearningSession session = new LearningSession(wordLists);
                        // save LearningSession in myWorkSpace
                        Controller.updateLearningSession(session);
                        // Load data 
                        // Load current wordList into myWorkspace
                        Controller.setCurrentWordList(Controller.getCurrentLearningSession().getWordList());
                        // Load first wordPair into myWorkspace
                        Controller.setCurrentWordPair(Controller.getCurrentLearningSession().getWordList().iterator().next());
                        // Change view
                        GUI.getGUI().activateView(ProgramParameters.VIEW_TITLE_LEARNINGSESSION);
                        Controller.setErrorMessage("ENJOY YOUR LEARNING SESSION :)");
                    }
                    break;
                case "START_ALL":
                    // Create LearningSession with all word lists
                    LearningSession session = new LearningSession(Controller.getAllWordListsFromMyWorkspace());
                    // save LearningSession in myWorkSpace
                    Controller.updateLearningSession(session);
                    // Change view
                    GUI.getGUI().activateView(ProgramParameters.VIEW_TITLE_LEARNINGSESSION);
                    Controller.setErrorMessage("ENJOY YOUR LEARNING SESSION :)");
                    break;
                case "RESUME":
                    // save current myWorkspace state for rollback in error case

                    // load variables from saved session
                    // curre
                    //Re-create myworkspace object from database
                    // Load data of saved session into workspace
//                        Controller.resumeSession();
                    // Start session
                    //GUI.getGUI().activateView(ProgramParameters.VIEW_TITLE_EVALUATE);
                    break;
                case "RESET_SELECTED":
                    // Reset selected word lists
                    Controller.resetSelectedWordLists();
                    break;
                case "RESET_ALL":
                    Controller.resetAllWordLists();
                    break;
                case "REQUEST_DELETION":
                    Controller.setErrorMessage("REQUEST_DELETION");
                    Controller.setSelectedWordListsToLearned();

                    //GUI.getGUI().activateView(ProgramParameters.VIEW_TITLE_EVALUATE);
                    break;
                case "UPLOAD_CSV":
                    Controller.setErrorMessage("UPLOAD_CSV");
                    Controller.uploadWordListFromCSV();
                    //GUI.getGUI().activateView(ProgramParameters.VIEW_TITLE_EVALUATE);
                    break;
                case "UPLOAD_MANUAL":
                    Controller.setErrorMessage("UPLOAD_MANUAL");
                    //GUI.getGUI().activateView(ProgramParameters.VIEW_TITLE_EVALUATE);
                    break;
                case "PREVIOUS_WORD":
                    // check if previous word exists
                    if (Controller.getCurrentLearningSession().getCurrentIndex() == 1)
                    {
                        Controller.setErrorMessage("ERROR: There is no previous word");
                    } else
                    {
                        // Load previous wordPair into myWorkspace
                        Controller.setCurrentWordPair(Controller.getCurrentLearningSession().getWordList().iteratorLast());
                        // Update flashcard
                        Controller.showCurrentWordPairInFlashcard();
                    }
                    // Update heading in view
                    ViewLearningSession view = (ViewLearningSession) GUI.getCurrentView();
                    // Get current index of iterator
                    int currentIndex = Controller.getCurrentLearningSession().getWordList().getCurrentIndex();
                    // Get the number of words for this learning session
                    int wordCount = Controller.getCurrentLearningSession().getWordCount();
                    // update heading in view with current position
                    String tmp_string;
                    if (Controller.getDefaultLanguage() == MyWorkSpace.DEFAULT_LANGUAGE_GERMAN)
                    {
                        tmp_string = "WORT " + currentIndex + " VON " + wordCount;

                    } else
                    {
                        tmp_string = "WORD " + currentIndex + " OF " + wordCount;
                    }
                    view.setHeadingText(tmp_string);
                    break;
                case "NEXT_WORD":
                    // check if next word exists
                    if (Controller.getCurrentLearningSession().getWordList().getCurrentIndex() == Controller.getCurrentLearningSession().getWordCount())
                    {
                        Controller.setErrorMessage("ERROR: There is no next word");
                    } else
                    {
                        // Load next wordPair into myWorkspace
                        Controller.setCurrentWordPair(Controller.getCurrentLearningSession().getWordList().iterator().next());
                        // Update flashcard
                        Controller.showCurrentWordPairInFlashcard();
                    }
                    // Update heading in view
                    view = (ViewLearningSession) GUI.getCurrentView();
                    // Get current index of iterator
                    currentIndex = Controller.getCurrentLearningSession().getWordList().getCurrentIndex();
                    // Get the number of words for this learning session
                    wordCount = Controller.getCurrentLearningSession().getWordCount();
                    // update heading in view with current position
                    if (Controller.getDefaultLanguage() == MyWorkSpace.DEFAULT_LANGUAGE_GERMAN)
                    {
                        tmp_string = "WORT " + currentIndex + " VON " + wordCount;

                    } else
                    {
                        tmp_string = "WORD " + currentIndex + " OF " + wordCount;
                    }
                    view.setHeadingText(tmp_string);
                    break;
                case "CHANGE_DIRECTION":
                    Controller.changeDefaultLanguage();
                    Controller.setErrorMessage("Default language changed");
                    break;

                //--------------
                // MENU/NAVIGATION BUTTONS
                case "MENU":
                    Controller.setErrorMessage("MENU-Button pressed");
                    GUI.getGUI().activateView(ProgramParameters.VIEW_TITLE_MENU_MAIN);
                    break;
                case "CONNECT":

                    Controller.setErrorMessage("CONNECT-Button pressed");
                    {
                        try
                        {
                            Controller.connect();
                        } catch (SQLException ex)
                        {
                            Logger.getLogger(ButtonControlActionListener.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    // 
                    break;

                case "LOGOUT":
                    Controller.setErrorMessage("LOGOUT-Button pressed");
                    // TODO
                    //
                    // >>> ADD LOGOUT PROCEDURE
                    //
                    GUI.getGUI().activateView(ProgramParameters.VIEW_TITLE_LOGIN);
                    break;
                case "BACK":
                    //Go back to last view
                    GUI.getGUI().activateView(GUI.getLastView().getTitle());
                    break;
                default:
                    Controller.setErrorMessage("Error in ActionListener (Switch)");

            }
            // not sure if needed but I had trouble with flickering views
            GUI.getFrame().repaint();

            // Start timer and let error message disappear
            Thread th = new Thread(this);
            th.start();

        } else
        {
            Controller.setErrorMessage("\nUnknown Action Event in Class ViewLearnWords\n");
        }
    }

    @Override
    public void run()
    {
        try
        {
            Thread.sleep(ProgramParameters.ERROR_MSG_TIME);
        } catch (InterruptedException ex)
        {
            Logger.getLogger(ViewLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
        Controller.setErrorMessage("");
    }

}

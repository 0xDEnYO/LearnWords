// NEXT STEPS
// Herausfinden, wieso zwei Fenster geöffnet werden und die Übergabe der Daten nicht funktioniert
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LearnWords.view.gui;

import LearnWords.controller.Controller;
import LearnWords.view.views.ViewTeacherMenu;
import LearnWords.view.views.ViewMenu;
import LearnWords.view.views.ViewLearnWords;
import LearnWords.view.views.ViewLearningSession;
import LearnWords.view.views.ViewManageWords;
import LearnWords.view.views.MyAbstractView;
import LearnWords.view.views.ViewLogin;
import LearnWords.controller.ProgramParameters;
import LearnWords.view.views.ViewSettings;
import java.awt.GridBagConstraints;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Daniel Bläcker (d.blaecker@gmail.com)
 */
public class GUI
{

    private static GUI instance;
    private static GUI_Base base;
    private static MyAbstractView currentView;
    private static MyAbstractView lastView;
    private static JPanel pnl_main;
    private static GridBagConstraints gbc = new GridBagConstraints();

    private GUI()
    {

        // Create base GUI
        base = new GUI_Base();

        // Save main content panel in variable
        pnl_main = base.getMainPanel();

        // Load Login MyAbstractView
        currentView = ViewLogin.getView();

        // Include login view in base central panel
        pnl_main.add(currentView.getContentPane(), gbc);

        // Activate or deactivate MENU button depending on the view's settings
        base.setMenuButton(currentView.getMenuButton());
        // Activate or deactivate BACK button depending on the view's settings
        base.setBackButton(currentView.getBackButton());
        // Activate or deactivate LOGOUT button depending on the view's settings
        base.setLogoutButton(currentView.getLogoutButton());

        // Set view title and subtitle
        base.setViewTitle(currentView.getTitle());
        base.setViewSubtitle(currentView.getSubtitle());

        // Call GUI
        base.callGUI();

    }

    public static GUI getGUI()
    {
        if (instance == null)
        {
            instance = new GUI();
        }
        return instance;
    }

    public void activateView(String name)
    {

        lastView = currentView;

        // TODO Improve this code to be more generic (get class from String)
        switch (name)
        {

            case ProgramParameters.VIEW_TITLE_LOGIN:
                currentView = ViewLogin.getView();
                break;
            case ProgramParameters.VIEW_TITLE_MENU_MAIN:
                currentView = ViewMenu.getView();
                break;
            case ProgramParameters.VIEW_TITLE_MENU_TEACHER:
                currentView = ViewTeacherMenu.getView();
                break;
            case ProgramParameters.VIEW_TITLE_LEARNWORDS:
                currentView = ViewLearnWords.getView();
                Controller.updateWordListTable();
                break;

//            case ProgramParameters.VIEW_TITLE_SELECTWORDS:
//                currentView = ViewSelectWords.getView();
//                break;
//            case ProgramParameters.VIEW_TITLE_VIEWWORDS:
//                currentView = ViewViewWords.getView();
//                break;
//
            case ProgramParameters.VIEW_TITLE_LEARNINGSESSION:
                currentView = ViewLearningSession.getView();
                ViewLearningSession view = (ViewLearningSession) currentView;
                view.updateView(); 
                break;
            case ProgramParameters.VIEW_TITLE_MANAGEWORDS:
                currentView = (ViewManageWords) ViewManageWords.getView();
                Controller.updateWordListTable();
                break;
//            case ProgramParameters.VIEW_TITLE_MAINTAINWORDS:
//                currentView = ViewMaintainWords.getView();
//                break;
//            case ProgramParameters.VIEW_TITLE_REQUESTS:
//                currentView = ViewRequests.getView();
//                break;
//            case ProgramParameters.VIEW_TITLE_HISTORY:
//                currentView = ViewHistory.getView();
//                break;
            case ProgramParameters.VIEW_TITLE_SETTINGS:
                currentView = ViewSettings.getView();
                Controller.updateAllDataEntryFormsAndTables();
                break;
//            case ProgramParameters.VIEW_TITLE_EVALUATE:
//                currentView = ViewEvaluate.getView();
//                break;
//            case ProgramParameters.VIEW_TITLE_CSVUPLOAD:
//                currentView = ViewCSVUpload.getView();
//                break;
//            case ProgramParameters.VIEW_TITLE_MANUALUPLOAD:
//                currentView = ViewManualUpload.getView();
//                break;

            default:
                this.setErrorMessage("View not yet available");
        }

        // Set error message to default value
        //Controller.setErrorMessage(ProgramParameters.TXT_ERRORMESSAGE_DEFAULT);
        // Activate or deactivate MENU button depending on the view's settings
        base.setMenuButton(currentView.getMenuButton());
        // Activate or deactivate BACK button depending on the view's settings
        base.setBackButton(currentView.getBackButton());
        // Activate or deactivate LOGOUT button depending on the view's settings
        base.setLogoutButton(currentView.getLogoutButton());

        // Remove all elements from main content pane
        pnl_main.removeAll();

        // Load new view's content into existing main panel
        pnl_main.add(currentView.getContentPane());

        // Set view title and subtitle
        base.setViewTitle(currentView.getTitle());
        base.setViewSubtitle(currentView.getSubtitle());

        //not sure, if needed 
        pnl_main.repaint();

    }

    public static ImageIcon createImageIcon(String path, String description)
    {
        // Create an icon from an image file
        java.net.URL imgURL = GUI.class.getResource(path);
        if (imgURL != null)
        {
            return new ImageIcon(imgURL, description);
        } else
        {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    public void setErrorMessage(String text)
    {
        // Add line breaks above and below error message
        String tmp = "<html><br>" + text + "<br><br></html>";
        base.getFooter().setErrorMessage(tmp);
    }

    public static MyAbstractView getCurrentView()
    {
        return currentView;
    }

    public static MyAbstractView getLastView()
    {
        return lastView;
    }

    public static void updateLoginInformation()
    {
        base.getFooter().updateLoginInformation();
    }

    public static JFrame getFrame()
    {
        return GUI.base.getFrame();
    }

}

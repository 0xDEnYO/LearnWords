/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LearnWords.view.views;

import LearnWords.controller.Controller;
import LearnWords.controller.ProgramParameters;
import LearnWords.view.components.MyAbstractDataEntryForm;
import LearnWords.view.components.MyLoginDataEntryForm;
import LearnWords.view.components.MyPersonDataEntryForm;
import LearnWords.view.components.MyRoleDataEntryForm;
import LearnWords.view.components.MyTextfield;
import LearnWords.view.components.MyUserDataEntryForm;
import static LearnWords.view.views.ViewLearningSession.instance;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;

/**
 *
 * @author Daniel Bläcker (d.blaecker@gmail.com)
 */
public class ViewSettings extends MyAbstractView
{

    private JPanel pnl_settings;
    private MyAbstractDataEntryForm pnl_addLogin, pnl_addUser, pnl_addRole, pnl_addPerson;
    private HashMap<String, JCheckBox> settings;
    private JTabbedPane tabbedPane;

    private final int TAB_PERSONS = 1;
    private final int TAB_ROLES = 2;
    private final int TAB_USERS = 3;

    public ViewSettings()
    {
        this.settings = new HashMap<>();

        // Set up tabbed pane with tabs
        tabbedPane = new JTabbedPane();
        tabbedPane.setMinimumSize(new Dimension(900, 550));
        tabbedPane.setPreferredSize(new Dimension(900, 550));

        pnl_settings = new JPanel();
        pnl_settings.setBackground(ProgramParameters.COLOR_GUI_BACKGROUND);
        tabbedPane.addTab("General Settings", pnl_settings);
        
        pnl_addLogin = new MyLoginDataEntryForm();
        tabbedPane.addTab("Logins", pnl_addLogin);
        
        pnl_addPerson = new MyPersonDataEntryForm();
        tabbedPane.addTab("Persons", pnl_addPerson);

        pnl_addRole = new MyRoleDataEntryForm();  
        tabbedPane.addTab("Roles", pnl_addRole);

        pnl_addUser = new MyUserDataEntryForm();
        tabbedPane.addTab("Users", pnl_addUser);

        // TMP - REMOVE
        tabbedPane.setSelectedIndex(3);

        // Add change listener to tabbed pane to update date when switching between tabs
        tabbedPane.addChangeListener((ChangeEvent e) ->
        {
            // Update all tables in Settings
            Controller.updateAllDataEntryFormsAndTables();
        });

        // set up keyboard shortcuts
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
        tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);
        tabbedPane.setMnemonicAt(3, KeyEvent.VK_4);
        tabbedPane.setMnemonicAt(4, KeyEvent.VK_5);

//        // set up panel "Persons"
//        // set layout (gridbag)
//        // add myTextfield
//        pnl_addUser.add(new MyTextfield("First name"));

        // Define availability for BACK, MENU and LOGOUT buttons in footer and header
        this.backButton = true;
        this.menuButton = true;
        this.logoutButton = true;

        // add tabbed pane to main content panel
        pnl_content.add(tabbedPane);

    }

    public static MyAbstractView getView()
    {
        if (instance == null || (!(instance instanceof ViewSettings)))
        {
            instance = new ViewSettings();
        }
        return instance;
    }

    public MyAbstractDataEntryForm getDataEntryForm()
    {

        MyAbstractDataEntryForm form = null;
        int selectedTab = tabbedPane.getSelectedIndex();
        switch (selectedTab)
        {

            case TAB_PERSONS:
                form = pnl_addPerson;
                break;
            case TAB_ROLES:
                form = pnl_addRole;
                break;
            case TAB_USERS:
                form = pnl_addUser;
                break;
            default:
                Controller.setErrorMessage("ERROR: No suitable data entry form found");
                return form;

        }
        return form;
    }
    
        public MyAbstractDataEntryForm[] getAllDataEntryForms(){
            MyAbstractDataEntryForm[] forms = new MyAbstractDataEntryForm[4];
            forms[0] = pnl_addLogin;
            forms[1] = pnl_addPerson;
            forms[2] = pnl_addRole;
            forms[3] = pnl_addUser;
            return forms;
        }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LearnWords.controller;

import LearnWords.view.views.MyAbstractWordListView;
import LearnWords.model.administration.userManagement.*;
import LearnWords.model.administration.*;
import LearnWords.model.dataStorage.*;
import LearnWords.model.functional.LearningSession;
import LearnWords.model.functional.LearningSession;
import LearnWords.model.functional.WordList;
import LearnWords.model.functional.WordPair;
import LearnWords.view.components.MyAbstractDataEntryForm;
import LearnWords.view.components.MyPersonDataEntryForm;
import LearnWords.view.gui.GUI;
import LearnWords.view.views.MyAbstractView;
import LearnWords.view.views.ViewLearningSession;
import LearnWords.view.views.ViewLogin;
import LearnWords.view.views.ViewSettings;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JFrame;

/**
 *
 * @author Daniel Bläcker (d.blaecker@gmail.com)
 */
public class Controller
{

    private static MyWorkSpace myWorkspace = null;
    private static GUI gui = null;
    public static boolean databaseMirrorModeOn = true;

    public static void startUp()
    {

        // Read and save user directory
        ProgramParameters.updateUserDirectory();

        // Create workspace
        myWorkspace = new MyWorkSpace();

        // Start GUI
        gui = GUI.getGUI();
    }

    public static void connect() throws SQLException
    {

        switch (getSaveLocation())
        {
            case ProgramParameters.DROPDOWN_CONFIG_CSV:
                // Load objects from default CSV storage location
                // TODO
                // Deactivate database mirror mode so object changes will not get reflected in the db
                Controller.databaseMirrorModeOn = false;

                setErrorMessage(ProgramParameters.DROPDOWN_CONFIG_CSV);
                break;
            case ProgramParameters.DROPDOWN_CONFIG_DB:
                // CREATE CONNECTION TO DATABASE
                Database db = Database.getInstance();
                // Activate database mirror mode so object changes get reflected in the db
                Controller.databaseMirrorModeOn = true;

                // load data from database
                db.addTestDataInDatabase();
                // TODO
                // CREATE LOGIN OBJECTS
                //db.loadLoginObjectsFromDatabase();
                // CREATE ROLE OBJECTS
                // CREATE PERSON OBJECTS

                //setErrorMessage(ProgramParameters.DROPDOWN_CONFIG_DB);
                break;
            default:
                setErrorMessage("Error in Controller.connect()");
        }
        // Remove config box and show login box
        Controller.showLoginMask();
    }

    //---------------------------------------------------------------------------------------------------
    // USER MANAGEMENT
    public static boolean verifyUserLogin(String username, String password)
    {

        // Check if login exists
        Login login = Login.getLogin(username);
        // if not: send error message
        if (login == null)
        {

            setErrorMessage("Invalid Username: " + username + " :(");
            return false;
        }
        // if yes:  verify login against database
        if (login.verifyPassword(password))
        {
            // Login succesful
            //System.out.println("User Login successful :)");

            User user = User.getUser(username);
            if (user != null)
            {
                // save user as current user
                myWorkspace.setCurrentUser(user);

                // Load user data 
                loadUserData(user);

                // Update user information in footer
                GUI.updateLoginInformation();
            }

            setErrorMessage("Login Successful :)");
            return true;
        } else
        {
            setErrorMessage("Invalid Login :(");
        }

        return false;
    }

    public static Person createPerson(String firstName, String lastName, String emailAddress)
    {
        return Person.addPerson(firstName, lastName, emailAddress);
    }

    public static Role createRole(String roleTitle)
    {

        return Role.addRole(roleTitle);

    }

    public static boolean removeUser(User user)
    {

        return User.removeUser(user);
    }

    public static User getCurrentUser()
    {
        return myWorkspace.getCurrentUser();
    }

    //---------------------------------------------------------------------------------------------------
    // DATA MANAGEMENT
    public static boolean resumeSession()
    {
        // save current session as backup
        MyWorkSpace tmp = myWorkspace;

        myWorkspace.resumePreviousLearningSession();
        if (myWorkspace == null)
        {
            // error during session reload
            Controller.setErrorMessage("Error: Session could not be loaded. Previous session restored.");
            // roll back to old state
            myWorkspace = tmp;
            return false;
        }
        // successful
        Controller.setErrorMessage("Session successfully restored. :)");
        return true;
    }

    private static boolean loadUserData(User user)
    {
        // Check database for userdata

        // load user data into workspace
        // return result
        return false;
    }

    public static boolean saveAllObjectsToCSV()
    {

        // all WordLists from MyWorkspace
        ArrayList<WordList> allWordLists = myWorkspace.getAllWordLists();
        System.out.println("Print allWordLists: " + allWordLists.size());
        CSV.serializeObjectsToCSV(allWordLists, WordList.class);

        // WordPair (loop through above
        ArrayList<WordPair> allWordPairs = new ArrayList<>();
        // loop through all word lists
        for (WordList list : allWordLists)
        {
            // loop through each word list and add words to ArrayList
            for (WordPair pair : list)
            {
                // add WordPair
                allWordPairs.add(pair);
            }
        }
        System.out.println("Print allWordPairs: " + allWordPairs.size());
        CSV.serializeObjectsToCSV(allWordPairs, WordPair.class);

        // LearningSession
        ArrayList<LearningSession> allSessions = new ArrayList<>();
        allSessions.add(myWorkspace.getCurrentLearningSession());

        if (myWorkspace.getCurrentLearningSession() != null)
        {
            allSessions.add(myWorkspace.getPreviousLearningSession());
        }
        System.out.println("Print allLearningSessions: " + allSessions.size());
        CSV.serializeObjectsToCSV(allSessions, LearningSession.class);

        //TODO FINALIZE
        // MyWorkSpace
        // Login
        // Person
        // Role
        // User
        // UserList
        return false;
    }
//
//    public static final List<Class<?>> getClassesInPackage(String packageName) {
//        String path = packageName.replaceAll("\\.", File.separator);
//        List<Class<?>> classes = new ArrayList<>();
//        String[] classPathEntries = System.getProperty("java.class.path").split(
//                System.getProperty("path.separator")
//        );
//
//        String name;
//        for (String classpathEntry : classPathEntries) {
//            if (classpathEntry.endsWith(".jar")) {
//                File jar = new File(classpathEntry);
//                try {
//                    JarInputStream is = new JarInputStream(new FileInputStream(jar));
//                    JarEntry entry;
//                    while ((entry = is.getNextJarEntry()) != null) {
//                        name = entry.getName();
//                        if (name.endsWith(".class")) {
//                            if (name.contains(path) && name.endsWith(".class")) {
//                                String classPath = name.substring(0, entry.getName().length() - 6);
//                                classPath = classPath.replaceAll("[\\|/]", ".");
//                                classes.add(Class.forName(classPath));
//                            }
//                        }
//                    }
//                } catch (Exception ex) {
//                    // Silence is gold
//                }
//            } else {
//                try {
//                    File base = new File(classpathEntry + File.separatorChar + path);
//                    for (File file : base.listFiles()) {
//                        name = file.getName();
//                        if (name.endsWith(".class")) {
//                            name = name.substring(0, name.length() - 6);
//                            classes.add(Class.forName(packageName + "." + name));
//                        }
//                    }
//                } catch (Exception ex) {
//                    // Silence is gold
//                }
//            }
//        }
//
//        return classes;
//    }
    //---------------------------------------------------------------------------------------------------
    // GUI HANDLING

    public static void setErrorMessage(String text)
    {
        gui.setErrorMessage(text);
    }

    public static JFrame getCurrentFrame()
    {
        return GUI.getFrame();
    }

    public static void activateView(String viewTitle)
    {

        gui.activateView(viewTitle);
    }

    public static void showLoginMask()
    {
        MyAbstractView view = GUI.getCurrentView();
        if (view instanceof ViewLogin)
        {
            ViewLogin viewLogin = (ViewLogin) view;
            viewLogin.showLoginMask();
        } else
        {
            setErrorMessage("Error in Controller.showLoginMask()");
        }
    }

    //---------------------------------------------------------------------------------------------------
    //WORD LIST HANDLING
    public static void resetSelectedWordLists()
    {

        // Get selected word lists
        ArrayList<WordList> selectedWordLists = myWorkspace.getSelectedWordLists();
        // Iterate through all the lists and reset them
        selectedWordLists.forEach(list
                ->
        {
            list.resetAllWordsToUnlearned();
        });
        // Update Table
        updateWordListTable();
        // Send confirmation
        setErrorMessage("Selected word lists were reset successfully");

    }

    public static void resetAllWordLists()
    {

        // Get all word lists
        ArrayList<WordList> allWordLists = myWorkspace.getAllWordLists();
        // Iterate through all the lists and reset them
        allWordLists.forEach(list
                ->
        {
            list.resetAllWordsToUnlearned();
        });
        // Update Table
        updateWordListTable();
        // Send confirmation
        setErrorMessage("All word lists were reset successfully");

    }

    public static WordList findWordList(String name)
    {
        return myWorkspace.findWordListinWorkspace(name);
    }

    public static void uploadWordListFromCSV()
    {

        // Upload word list
        if (CSV.uploadWordList())
        {
            // Success
            Controller.setErrorMessage("File successfully uploaded :)");
        } else
        {
            // Error
            Controller.setErrorMessage("Error: File not uploaded successfully :(");
        }

        // Update table
        updateWordListTable();
    }

    public static void addWordList(WordList list)
    {
        myWorkspace.addWordList(list);
    }

    public static void updateWordListTable()
    {
        // Get current view and cast into MyAbstractWordListView object so table can be updated
        MyAbstractWordListView view = (MyAbstractWordListView) GUI.getCurrentView();
        // Get all word lists of current user/workspace
        ArrayList<WordList> allWordLists = myWorkspace.getAllWordLists();
        // populate Table with data
        view.updateTable(allWordLists);

    }

    public static WordList[] getSelectedWordListsFromMyWorkspace()
    {
        WordList[] words = new WordList[myWorkspace.getSelectedWordLists().size()];
        return myWorkspace.getSelectedWordLists().toArray(words);
    }

    public static void updateSelectedWordListsInMyWorkspace(ArrayList<WordList> selectedLists)
    {
        myWorkspace.setSelectedWordLists(selectedLists);
    }

    public static WordList[] getAllWordListsFromMyWorkspace()
    {
        WordList[] words = new WordList[myWorkspace.getAllWordLists().size()];
        return myWorkspace.getAllWordLists().toArray(words);
    }

    //---------------------------------------------------------------------------------------------------
    // METHODS FOR SETTINGS
//    public static void updatePersonListTable()
//    {
//        // Get current view and cast into MyAbstractWordListView object so table can be updated
//        ViewSettings view = (ViewSettings) GUI.getCurrentView();
//        // Get data entry form object
//        MyPersonDataEntryForm form = (MyPersonDataEntryForm) view.getDataEntryForm();
//        // Get all word lists of current user/workspace
//        ArrayList<Person> allPersons = Person.getAllPersons();
//        // populate Table with data
//        form.updateTable();
//    }
//    public static void updateDataEntryTable()
//    {
//        // Get current view and cast into MyAbstractWordListView object so table can be updated
//        ViewSettings view = (ViewSettings) GUI.getCurrentView();
//        // Get data entry form object
//        MyAbstractDataEntryForm form = (MyAbstractDataEntryForm) view.getDataEntryForm();
//        // populate Table with data
//        form.updateTable();
//    }   
    
    public static void updateAllDataEntryFormsAndTables()
    {
        // Get current view and cast into MyAbstractWordListView object so table can be updated
        ViewSettings view = (ViewSettings) GUI.getCurrentView();
            // Get all data entry form objects
        MyAbstractDataEntryForm[] allForms = view.getAllDataEntryForms();
        for(MyAbstractDataEntryForm form: allForms){
            form.updateTable();
            form.updateForm();
        }
    }

    //---------------------------------------------------------------------------------------------------
    // MYWORKSPACE ACCESS METHODS
    public static void addWordListToSelection(WordList list)
    {
        myWorkspace.getSelectedWordLists().add(list);
    }

    public static void removeWordListFromSelection(WordList list)
    {
        myWorkspace.getSelectedWordLists().remove(list);
    }

    public static String getSaveLocation()
    {
        return myWorkspace.getSaveLocation();
    }

    public static void setSaveLocation(String location)
    {
        myWorkspace.setSaveLocation(location);
    }

    public static void updateLearningSession(LearningSession session)
    {
        myWorkspace.updateLearningSession(session);
    }

    public static LearningSession getCurrentLearningSession()
    {
        return myWorkspace.getCurrentLearningSession();
    }

    public static LearningSession getPreviousLearningSession()
    {
        return myWorkspace.getCurrentLearningSession();
    }

    public static void showCurrentWordPairInFlashcard()
    {

        WordPair pair = myWorkspace.getCurrentWordPair();
        ViewLearningSession view = (ViewLearningSession) GUI.getCurrentView();
        if (Controller.getDefaultLanguage() == MyWorkSpace.DEFAULT_LANGUAGE_GERMAN)
        {
            // Default language is German, display German word
            view.setFlashcard(pair.getGerman());
        } else
        {
            // Default language is English, display English word
            view.setFlashcard(pair.getEnglish());

        }

    }

    public static void flipFlashcard()
    {
        WordPair pair = myWorkspace.getCurrentWordPair();
        // check which word is currently displayed and set the translation as new flashcard text
        ViewLearningSession view = (ViewLearningSession) GUI.getCurrentView();
        if (view.getFlashcard().equals(pair.getGerman()))
        {
            // German word is currently shown, display English word
            view.setFlashcard(pair.getEnglish());
        } else if (view.getFlashcard().equals(pair.getEnglish()))
        {
            // English word is currently shown, display German word
            view.setFlashcard(pair.getGerman());

        } else
        {
            Controller.setErrorMessage("ERROR: Error in Flashcard (Controller.flpFlashcard())");
        }
    }

    public static void setCurrentWordPair(WordPair pair)
    {
        myWorkspace.setCurrentWordPair(pair);
    }

    public static void setCurrentWordList(WordList list)
    {
        myWorkspace.setCurrentWordList(list);
    }

    public static WordPair getCurrentWordPair()
    {
        return myWorkspace.getCurrentWordPair();
    }

    public static int getDefaultLanguage()
    {
        return myWorkspace.getDefaultLanguage();
    }

    public static void changeDefaultLanguage()
    {
        myWorkspace.changeDefaultLanguage();
    }

//---------------------------------------------------------------------------------------------------
    //tmp
    public static void printSelection()
    {
        System.out.printf("\nSelection:\n%s", myWorkspace.getSelectedWordLists().toString());
    }

    public static void setSelectedWordListsToLearned()
    {
        ArrayList<WordList> selection = myWorkspace.getSelectedWordLists();
        for (WordList list : selection)
        {
            list.setAllToLearned();
        }
        updateWordListTable();
    }

    public static <T> T convertInstanceOfObject(Object o, Class<T> clazz)
    {
        try
        {
            return clazz.cast(o);
        } catch (ClassCastException e)
        {
            return null;
        }
    }

    public static void setCurrentUser(String user)
    {
        myWorkspace.setCurrentUser(User.getUser(user));
    }

//      public static User createUser(String username, String password, String roleTitle, String firstName, String lastName, String emailAddress) {
//
//        // Check if user already exists
//        if (User.getUser(username) != null) {
//            setErrorMessage("Error in Controller. createUser(): User aleady existing:" + username);
//            return null;
//        }
//        // Create user
//        User user = new User(username);
//        // Set user as currentUser
//        myWorkspace.setCurrentUser(user);
//
//        // Create login and add to user
//        Login login = new Login(username, password);
//        user.addLogin(login);
//        // Create person and add to user
//        Person person = new Person(firstName, lastName, emailAddress);
//        user.addPerson(person);
//        // Create role and add to user
//        Role role = new Role(roleTitle);
//        user.addRole(role);
//        // Return user
//        return user;
//    }
}

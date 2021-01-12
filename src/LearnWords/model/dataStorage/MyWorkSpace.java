/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LearnWords.model.dataStorage;

import LearnWords.controller.Controller;
import LearnWords.controller.ProgramParameters;
import LearnWords.model.administration.userManagement.*;
import LearnWords.model.functional.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Daniel Bläcker (d.blaecker@gmail.com)
 */
public class MyWorkSpace implements Serializable {

    // Variables to store various current objects
    private WordList currentWordList;
    private ArrayList<WordList> selectedWordLists = new ArrayList<>();
    private ArrayList<WordList> allWordLists = new ArrayList<>();
    private WordPair currentWordPair;
    private User currentUser;
    public static String selectedSaveLocation = ProgramParameters.DROPDOWN_CONFIG_DB;

    // variables for resume session 
    private LearningSession currentSession;
    private LearningSession previousSession;
    
    // Default language (to display WordPair)
    public static int DEFAULT_LANGUAGE_GERMAN = 1;
    public static int DEFAULT_LANGUAGE_ENGLISH= 2;
    private int defaultLanguage = DEFAULT_LANGUAGE_GERMAN;    

    public void addWordList(WordList list) {

        // save word list in "all words lists" variable
        allWordLists.add(list);

        // set new list as current word list
        currentWordList = list;
    }

    public WordList getCurrentWordList() {
        return currentWordList;
    }

    public void setCurrentWordList(WordList currentWordList) {
        this.currentWordList = currentWordList;
    }

    public ArrayList<WordList> getSelectedWordLists() {
        return selectedWordLists;
    }

    public void setSelectedWordLists(ArrayList<WordList> selectedWordLists) {
        this.selectedWordLists = selectedWordLists;
    }

    public ArrayList<WordList> getAllWordLists() {
        return this.allWordLists;
    }

    public WordPair getCurrentWordPair() {
        return currentWordPair;
    }

    public void setCurrentWordPair(WordPair currentWordPair) {
        this.currentWordPair = currentWordPair;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public LearningSession getCurrentLearningSession() {
        return this.currentSession;
    }

    public LearningSession getPreviousLearningSession() {
        return this.currentSession;
    }

    public void saveLearningSessionState() {

        // WRITE CODE TO STORE ALL VARIABLE VALUES SOMEWHERE (SERIALIZE OBJECTS HERE?)
        try {
            FileOutputStream fos = new FileOutputStream(ProgramParameters.dir_workspaceStorageLocation);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this);

        } catch (IOException e) {
            System.err.println("Error in MyWorkSpace.saveSession(): " + e);
        }

    }

    public void updateLearningSession(LearningSession session) {
        // save current as previous session
        this.previousSession = currentSession;
        // update current session
        this.currentSession = session;
    }

    public boolean resumePreviousLearningSession() {

        //check if previous session is available
        if (this.previousSession != null) {
            // save previousSession in currentSession and vice versa
            LearningSession tmp = this.previousSession;
            this.previousSession = this.currentSession;
            this.currentSession = tmp;
            return true;
        }
        Controller.setErrorMessage("Error in myWorkSpace.resumePreviousLearningSession(): no previous session available");
        return false;
        

    }

    public WordList findWordListinWorkspace(String name) {

        WordList result = null;

        for (WordList list : allWordLists) {
            if (list.getName().equals(name)) {
                result = list;
                break;
            }
        }

        return result;
    }

    public String getSaveLocation() {
        return selectedSaveLocation;
    }

    public void setSaveLocation(String location) {
        selectedSaveLocation = location;
    }
    
    public int getDefaultLanguage(){
        return this.defaultLanguage;
    }
    
    public void changeDefaultLanguage(){
        if(this.defaultLanguage == DEFAULT_LANGUAGE_GERMAN){
            this.defaultLanguage = DEFAULT_LANGUAGE_ENGLISH;
        }else{
            this.defaultLanguage = DEFAULT_LANGUAGE_GERMAN;
        }
    }
}

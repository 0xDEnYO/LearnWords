/*
 * This class represents a word pair. A word pair consists of a word and its translation into another language (curently: English<->German)
 */
package LearnWords.model.functional;

import LearnWords.controller.Controller;
import LearnWords.model.administration.userManagement.User;
import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author Daniel Bläcker (d.blaecker@gmail.com)
 */
public class WordPair implements Serializable{

    private final String inGerman, inEnglish;
    HashMap<String, Boolean> learnedPerUser;

    public WordPair(String german, String english) {
        this(german, english, Boolean.FALSE);
    }

    public WordPair(String german, String english, Boolean learned) {
        inGerman = german;
        inEnglish = english;
        learnedPerUser = new HashMap<>();
        this.learnedPerUser.put(Controller.getCurrentUser().getUsername(), learned);
    }

    public WordPair(String german, String english, Boolean learned, User user) {
        inGerman = german;
        inEnglish = english;
        learnedPerUser = new HashMap<>();
        System.out.printf("\nWordPair Constructor:\nGerman:%S\nEnglish:%S\nLearned:%S\nUser:%S", german, english, learned, user);
        this.learnedPerUser.put(user.getUsername(), learned);
    }

    public String getGerman() {
        return this.inGerman;
    }

    public String getEnglish() {
        return this.inEnglish;
    }

    public void setLearned(User user) {
        // Set learned to true for this user
        learnedPerUser.put(user.getUsername(), Boolean.TRUE);
    }

    public void setUnlearned(User user) {
        // 
        learnedPerUser.put(user.getUsername(), Boolean.FALSE);
    }

    public Boolean learnedByUser(User user) {
        // returns true, if the user has an entry which is set to TRUE
        return learnedPerUser.get(user.getUsername());
    }

}

/*
 * This class reprents a system user that connects a person with a login and a role
 */
package LearnWords.model.administration.userManagement;

import LearnWords.controller.Controller;
import LearnWords.model.administration.*;
import LearnWords.model.dataStorage.Database;
import LearnWords.model.functional.DataEntry;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Daniel Bläcker (d.blaecker@gmail.com)
 */
public class User implements Serializable, DataEntry {

    private String username;
    private Login login;
    private Person person;
    private Role role;
    private boolean isNewUser;
    private static ArrayList<User> allUsers = new ArrayList<>();

//    private ArrayList<WordPair> wordsLearned;
//    private ArrayList<WordPair> wordsUnlearned;
    //private Duration hoursSpentinProgram; 
    private User(String username) {

        //Set username
        this.username = username;
        // Set firstLogin to true
        isNewUser = true;
    }

    private User(Login login, Person person, Role role) {
        this(login.getUsername());
        this.login = login;
        this.person = person;
        this.role = role;
    }

    public boolean isNewUser() {
        return this.isNewUser;

    }

    public String getUsername() {
        return this.username;
    }

    public Login getLogin() {
        return this.login;
    }

    public Role getRole() {
        return this.role;
    }

    public void setPerson(Person person){
        this.person = person;
    }

    public void setRole(Role role){
        this.role = role;
    }
    public void addPerson(Person person) {
        this.person = person;
    }

    public Person getPerson() {
        return this.person;
    }

    public void addLogin(Login login) {
        this.login = login;
    }

    public void addRole(Role role) {
        this.role = role;
    }

    public boolean verifyPassword(String password) {
        return this.login.verifyPassword(password);
    }

    public boolean changePassword(String oldPassword, String newPassword) {

        return this.login.changePassword(this, oldPassword, newPassword);

    }

    //---------------------------------------------------------------------------------------------------
    // STATIC METHODS
    public static boolean checkUser(User user) {
        return allUsers.contains(user);
    }

    public static User addUser(Login login, Person person, Role role) {
        User user = null;
        // create user if does not exist yet
        if (getUser(login.getUsername()) == null) {
            user = new User(login, person, role);
            allUsers.add(user);
        } else {
            // IF USER ALREADY EXISTS
            user = getUser(login.getUsername());
        }

        // Check if database connection exists and data should be mirrored
        if (Controller.databaseMirrorModeOn) {
            //get db instance and add User to database
            Database db = Database.getInstance();
            db.addUserToDatabase(user);
            System.out.println("User.addUser(): User added to database:" + user.username);
        }
        return user;
    }

    public static void addUserFromDatabase(Login login, Person person, Role role) {
        // Call constructor
        User user = new User(login, person, role);

        // Add new user object to user list
        allUsers.add(user);
    }

    public static boolean removeUser(User user) {
        return allUsers.remove(user);
    }
    
    public static boolean removeUser(String username){
        return removeUser(getUser(username));
    }

    public static User getUser(String username) {
        User user = null;
        for (int i = 0; i < allUsers.size(); i++) {
            if (allUsers.get(i).getUsername().equals(username)) {
                user = allUsers.get(i);
            }
        }
        return user;
    }

        public static ArrayList<User> getAllUsers()
    {
        return allUsers;
    }
    
}

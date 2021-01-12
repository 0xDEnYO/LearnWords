/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LearnWords.model.administration;

import LearnWords.controller.Controller;
import LearnWords.model.administration.userManagement.User;
import LearnWords.model.dataStorage.Database;
import LearnWords.model.dataStorage.PasswordEncryptionService;
import LearnWords.model.functional.DataEntry;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Daniel Bläcker (d.blaecker@gmail.com)
 */
public class Login implements Serializable, DataEntry
{

    private String username;
    private byte[] encryptedPassword;
    // Salt for password encryption
    private byte[] salt = null;
    private final static ArrayList<Login> allLogins = new ArrayList<>();

    private Login(String username, String password)
    {

        // Generate salt for this login (for password encryption)
        try
        {
            this.salt = PasswordEncryptionService.generateSalt();
        } catch (NoSuchAlgorithmException ex)
        {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.username = username;
        this.encryptedPassword = encryptPassword(password);

    }

    public String getUsername()
    {
        return username;
    }

    public static Login getLogin(String username)
    {
        Login login = null;
        for (int i = 0; i < allLogins.size(); i++)
        {
            if (allLogins.get(i).getUsername().equals(username))
            {
                login = allLogins.get(i);
            }
        }
        return login;
    }

    public byte[] getEncryptedPassword()
    {
        return this.encryptedPassword;
    }

    public byte[] getSalt()
    {
        return this.salt;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public boolean verifyPassword(String passwordGuess)
    {

        // check if password is correct
        try
        {
            return PasswordEncryptionService.authenticate(passwordGuess, this.encryptedPassword, this.salt);
        } catch (NoSuchAlgorithmException ex)
        {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeySpecException ex)
        {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean changePassword(User user, String oldPassword, String newPassword)
    {
        // Check if old password is correct
        if (verifyPassword(oldPassword))
        {

            this.encryptedPassword = encryptPassword(newPassword);

            // TODO
            // UPDATE IN DATABASE
            return true;
        }
        return false;
    }

    public void changePasswordAuthorized(String newPassword)
    {
        this.encryptedPassword = encryptPassword(newPassword);
    }

    private byte[] encryptPassword(String password)
    {

        byte[] result = null;

        try
        {
            // Encrypt password
            result = PasswordEncryptionService.getEncryptedPassword(password, this.salt);
        } catch (NoSuchAlgorithmException ex)
        {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeySpecException ex)
        {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Return encrypted password
        return result;
    }
    // STATIC METHODS

    public static boolean checkLogin(Login login)
    {
        return allLogins.contains(login);
    }

    public static Login addLogin(String username, String password)
    {
        //reuse code from other method to determine if login already exists
        Login login = addLoginFromDatabase(username, password);

        // Check if database connection exists and data should be mirrored
        if (Controller.databaseMirrorModeOn)
        {
            //add Login to database
            Database db = Database.getInstance();
            db.addLoginToDatabase(login);
            System.out.println("Login.addLogin(): Login added to database;" + login.getUsername());
        }
        return login;
    }

    public static Login addLoginFromDatabase(String username, String passwords)
    {
        Login login = null;
        // create login if does not exist yet
        if (getLogin(username) == null)
        {
            login = new Login(username, passwords);
            allLogins.add(login);
        } else
        {
            // IF Login ALREADY EXISTS
            login = getLogin(username);
        }
        return login;
    }

    public static boolean removeLogin(String username)
    {
        // Get login object
        Login login = getLogin(username);
        if (login == null)
        {
            return false;
        }

        // Check if database connection exists and data should be mirrored
        if (Controller.databaseMirrorModeOn)
        {
            //remove Login from database
            Database db = Database.getInstance();
            db.removeLoginFromDatabase(login);
            System.out.println("Login.removeLogin(): Login removed from database;" + login.getUsername());
        }
        allLogins.contains(login);

        return allLogins.remove(login);
    }

    public static ArrayList<Login> getAllLogins()
    {
        return allLogins;
    }

}

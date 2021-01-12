/*
 * ....
 */
package LearnWords.model.dataStorage;

import LearnWords.controller.Controller;
import LearnWords.controller.ProgramParameters;
import LearnWords.model.administration.Login;
import LearnWords.model.administration.Person;
import LearnWords.model.administration.userManagement.Role;
import LearnWords.model.administration.userManagement.User;
import LearnWords.model.functional.WordList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Calendar;

/**
 * This class is realized as a singleton
 *
 */
public final class Database
{

    public static boolean debugmodeActive = true;
    private static Database instance = null;
    private static boolean back = true;

    // The connection to the database
    private Connection cn;
    // Variable used to store statements for manipulation of the database
    private Statement st;

    // --------------------------------------------------------------
    private Database()
    {

        try
        {
            // Embeds the driver, i.e. gets object of driver class and places it in virtual machine
            Class.forName(ProgramParameters.DB_DRIVER);

            // bereite mit dem Treiber eine DB-Verbindung vor (URL, Username, Passwort)
            cn = DriverManager.getConnection(ProgramParameters.DB_URL, ProgramParameters.DB_LOGIN_USER, ProgramParameters.DB_LOGIN_PW);

            // Activate auto-commit
            cn.setAutoCommit(true);
            // Endgueltige Verbindung zum Datenbankserver erzeugen
            st = cn.createStatement();

        } catch (ClassNotFoundException | SQLException ex)
        {
            Controller.setErrorMessage("Error in Database(): Server running, driver loaded?");
        } catch (Exception e)
        {
            System.out.println("FEHLER: " + e.getMessage());
            Controller.setErrorMessage("Error in Database(): unknown cause?");
        }
        Controller.setErrorMessage("You are now connected with the database :)");
        // Create database that will incorporate all the tables
        createDatabase();

        // Create all tables in database
        createTableStructureInDatabase();

    }

    // --------------------------------------------------------------
    // STATIC METHODS
    public static Database getInstance()
    {
        if (instance == null)
        {
            instance = new Database();
        }
        return instance;
    }

    // --------------------------------------------------------------
    // INSTANCE METHODS
    public void createDatabase()
    {

        // Delete existing database
        // TODO: REMOVE THIS AS IT WILL DELETE THE DATA LATER ON
        String sql = "DROP DATABASE " + ProgramParameters.DB_NAME;
        this.executeSQL(sql);

        sql = "CREATE DATABASE IF NOT EXISTS "
                + ProgramParameters.DB_NAME
                + " COLLATE utf8_german2_ci;";

        this.executeSQL(sql);
        this.executeSQL("USE " + ProgramParameters.DB_NAME + ";");

    }

    private boolean executeSQL(String sql)
    {
        back = true;

        if (debugmodeActive)
        {
            System.out.println(sql);
        }
        try
        {
            back = st.execute(sql);
        } catch (SQLException ex)
        {
            if (debugmodeActive)
            {
                ex.printStackTrace();
            }
        }
        return back;
    }

    public boolean createTable(String name)
    {
        back = true;
        String sql = "CREATE TABLE IF NOT EXISTS " + name + " ("
                + "idNo INT(64) NOT NULL AUTO_INCREMENT,"
                + "initials VARCHAR(2),"
                + "agentDate DATE,"
                + "agentCount INT(64), "
                + "PRIMARY KEY(idNo))";

        try
        {

            back = st.execute(sql);
            System.out.println("Table Created");
        } catch (SQLException e)
        {
            System.out.println("An error has occured on Table Creation");
            e.getMessage();
        }
        return back;
    }

    public boolean insertIntoTable(String name)
    {

        boolean back = true;

        // Prepare SQL Statement
        String sql = " insert into " + name + " (initials, agentDate, agentCount)"
                + " values (?, ?, ?)";

        try
        {

            // Create a prepared statement
            PreparedStatement preparedStmt = cn.prepareStatement(sql);

            // Fill values in prepared statement
            preparedStmt.setString(1, "DB");
            preparedStmt.setDate(2, new java.sql.Date(Calendar.getInstance().getTime().getTime()));
            preparedStmt.setInt(3, 5000);

            // Execute prepared statement
            back = preparedStmt.execute();

            System.out.println("Data inserted in Table");

        } catch (SQLException ex)
        {
            Controller.setErrorMessage("An error has occured on Table Insertion");
            System.out.println(ex.getMessage());
        }

        return back;
    }

    public boolean removeFromTable(String name)
    {

        back = true;

        // Prepare SQL Statement
        String sql = " insert into " + name + " (initials, agentDate, agentCount)"
                + " values (?, ?, ?)";

        try
        {

            // Create a prepared statement
            PreparedStatement preparedStmt = cn.prepareStatement(sql);

            // Fill values in prepared statement
            preparedStmt.setString(1, "DB");
            preparedStmt.setDate(2, new java.sql.Date(Calendar.getInstance().getTime().getTime()));
            preparedStmt.setInt(3, 5000);

            // Execute prepared statement
            back = preparedStmt.execute();

            System.out.println("Data inserted in Table");

        } catch (SQLException ex)
        {
            Controller.setErrorMessage("An error has occured on Table Insertion");
            System.out.println(ex.getMessage());
        }

        return back;
    }

    //---------------------------------------------------------------------------------------------------
    // Database mirror methods
    public void addLoginToDatabase(Login login)
    {

        String username = login.getUsername();
        String password = Arrays.toString(login.getEncryptedPassword());
        String salt = Arrays.toString(login.getSalt());

        // Prepare SQL Statement
        String query = String.format("insert into Login (username, password, salt) values ('%S', '%S', '%S')",
                username, password, salt);
        executeSQL(query);

    }

    public void addPersonToDatabase(Person person)
    {

        String firstName = person.getFirstName();
        String lastName = person.getLastName();
        String emailAddress = person.getEmailAddress();

        // Prepare SQL Statement
        String query = String.format("insert into Person (firstName, lastName, emailAddress) values ('%S', '%S', '%S')",
                firstName, lastName, emailAddress);
        executeSQL(query);
    }

    public void addRoleToDatabase(Role role)
    {

        // Prepare SQL Statement
        String query = String.format("insert into Role (roleTitle) values ('%S')",
                role.getRoleTitle());
        executeSQL(query);
    }

    public void addUserToDatabase(User user)
    {

        // Prepare SQL Statement
        String person = user.getPerson().getEmailAddress();
        String login = user.getLogin().getUsername();
        String role = user.getRole().getRoleTitle();

        String query = String.format(
                "insert into User (person, login, role) values ('%S','%S','%S')",
                person, login, role);
        executeSQL(query);
    }

    public void removeLoginFromDatabase(Login login)
    {
        String username = login.getUsername();
        // Prepare SQL Statement
        String query = String.format("DELETE FROM Login WHERE username = '%S'",
                username);
        executeSQL(query);
    }

    public void removePersonFromDatabase(Person person)
    {
        String emailAddress = person.getEmailAddress();
        // Prepare SQL Statement
        String query = String.format("DELETE FROM Person WHERE emailAddress = '%S'",
                emailAddress);
        executeSQL(query);

    }

    public void removeRoleFromDatabase(Role role)
    {
        String roleTitle = role.getRoleTitle();
        // Prepare SQL Statement
        String query = String.format("DELETE FROM Role WHERE username = '%S'",
                roleTitle);
        executeSQL(query);
    }

    public void loadLoginObjectsFromDatabase() throws SQLException
    {
        // TABLE LOGIN
        // Prepare SQL
        String query = "SELECT username, password FROM LOGIN";
        // Execute
        executeSQL(query);
        // go through ResultSet and create objects
        ResultSet set = st.getResultSet();
        while (set.next())
        {
            // Get informaation from database
            String username = set.getString(1);
            String password = set.getString(2);
            System.out.printf("\nLogin created:\nUsername: %S\nPassword:%S\n\n", username, password);
            // Create new login object (that is automatically stored in class Login)
            Login.addLoginFromDatabase(username, password);
        }

    }

    //---------------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------------
    // TMP METHODS (TESTDATA ETC.)
    public void createTableStructureInDatabase()
    {

        //------------------------------
        // Create table Person
        String sql = "CREATE TABLE IF NOT EXISTS PERSON ("
                + "firstName VARCHAR(50),"
                + "lastName VARCHAR(50),"
                + "emailAddress VARCHAR(50),"
                + "PRIMARY KEY(emailAddress))";

        executeSQL(sql);

        //------------------------------
        // Create Table Login
        sql = "CREATE TABLE IF NOT EXISTS LOGIN ("
                + "username VARCHAR(100),"
                + "password VARCHAR(200),"
                + "salt varchar(200),"
                + "PRIMARY KEY(username))";
        executeSQL(sql);

        //------------------------------
        // Create Table User
        sql = "CREATE TABLE IF NOT EXISTS USER ("
                + "person VARCHAR(40),"
                + "login VARCHAR(40),"
                + "role VARCHAR(30),"
                + "PRIMARY KEY(person, login, role))";
        executeSQL(sql);

        //------------------------------
        // Create Table WordPair
        sql = "CREATE TABLE IF NOT EXISTS WORDPAIR ("
                + "inGerman VARCHAR(50),"
                + "inEnglish VARCHAR(50),"
                + "userName VARCHAR(50),"
                + "learned VARCHAR(1),"
                + "PRIMARY KEY(inGerman, inEnglish, username))";
        executeSQL(sql);

        // Create Table WordList
        sql = "CREATE TABLE IF NOT EXISTS WORDLIST ("
                + "name VARCHAR (100),"
                + "xml MEDIUMTEXT,"
                + "PRIMARY KEY(name))";
        executeSQL(sql);

        //---------------------------------------------------------------------------------------------------
        // Create Table myWorkSpace
        sql = "CREATE TABLE IF NOT EXISTS MYWORKSPACE ("
                + "name VARCHAR (100),"
                + "xml MEDIUMTEXT,"
                + "PRIMARY KEY(name))";
        executeSQL(sql);
        //---------------------------------------------------------------------------------------------------
        // Create Table Role
        sql = "CREATE TABLE IF NOT EXISTS ROLE ("
                + "roleTitle VARCHAR (50),"
                + "PRIMARY KEY(roleTitle))";
        executeSQL(sql);
    }

    public void addTestDataInDatabase()
    {

        Login login1 = Login.addLogin("daniel.blaecker", "1234");
        Login login2 = Login.addLogin("gast", "1234");
        Person person1 = Person.addPerson("daniel", "blaecker", "d.blaecker@gmail.com");
        Person person2 = Person.addPerson("peter", "pan", "peter.pan@web.de");
        Role role1 = Role.addRole("administrator");
        Role role2 = Role.addRole("teacher");
        Role role3 = Role.addRole("student");
        User.addUser(login1, person1, role1);
        User.addUser(login2, person2, role2);

        // -----------
        //TMP -  REMOVE
        Controller.setCurrentUser("daniel.blaecker");
        // -----------

        //TODO
        // CHECK, IF CORRECT
        CSV.automaticUploadWordLists();

        // -----------
        //TMP -  REMOVE
        Controller.setCurrentUser(null);
        // -----------

    }

}

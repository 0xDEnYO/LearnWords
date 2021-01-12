/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LearnWords.model.administration;

import LearnWords.controller.Controller;
import LearnWords.model.dataStorage.Database;
import LearnWords.model.functional.DataEntry;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author Daniel Bläcker (d.blaecker@gmail.com)
 */
public class Person extends ArrayList<Person> implements Serializable, DataEntry
{

    private String firstName;
    private String lastName;
    private final String emailAddress;
    private static ArrayList<Person> allPersons = new ArrayList<>();

    private Person(String firstName, String lastName, String emailAddress)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public String getEmailAddress()
    {
        return emailAddress;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }
    
    public static Person getPerson(String emailAddress)
    {
        Person person = null;
        for (int i = 0; i < allPersons.size(); i++)
        {
            if (allPersons.get(i).getEmailAddress().equals(emailAddress))
            {
                person = allPersons.get(i);
            }
        }
        return person;
    }

    public static boolean removePerson(String emailAddress)
    {
        // Get person object
        Person person = getPerson(emailAddress);
        if (person == null)
        {
            return false;
        }

        // Check if database connection exists and data should be mirrored
        if (Controller.databaseMirrorModeOn)
        {
            //remove Person from database
            Database db = Database.getInstance();
            db.removePersonFromDatabase(person);
            System.out.println("Person.removePerson(): Person removed from database;" + person.getEmailAddress());
        }
        allPersons.contains(person);
        
        
        return allPersons.remove(person);
    }


    // STATIC METHODS
    public static boolean checkPerson(Person person)
    {
        return allPersons.contains(person);
    }

    public static Person addPerson(String firstName, String lastName, String emailAddress)
    {
        //reuse code from other method to determine if person already exists
        Person person = addPersonFromDatabase(firstName, lastName, emailAddress);

        // Check if database connection exists and data should be mirrored
        if (Controller.databaseMirrorModeOn)
        {
            //add Person to database
            Database db = Database.getInstance();
            db.addPersonToDatabase(person);
            System.out.println("Person.addPerson(): Person added to database;" + person.getEmailAddress());
        }
        return person;
    }

    public static Person addPersonFromDatabase(String firstName, String lastName, String emailAddress)
    {
        Person person = null;
        // create person if does not exist yet
        if (getPerson(emailAddress) == null)
        {
            person = new Person(firstName, lastName, emailAddress);
            allPersons.add(person);
        } else
        {
            // IF Person ALREADY EXISTS
            person = getPerson(emailAddress);
        }
        return person;
    }

    public static ArrayList<Person> getAllPersons()
    {
        return allPersons;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o instanceof Person)
        {
            Person person = (Person) o;
            return ((Person) o).getEmailAddress().equals(this.emailAddress);
        }
        return false;
    }

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.emailAddress);
        return hash;
    }

}

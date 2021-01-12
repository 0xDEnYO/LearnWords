/*
 * This class reprents a system user that connects a person with a login and a role
 */
package LearnWords.model.administration.userManagement;

import LearnWords.controller.Controller;
import LearnWords.model.dataStorage.Database;
import LearnWords.model.functional.DataEntry;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Daniel Bläcker (d.blaecker@gmail.com)
 */
public class Role implements Serializable, DataEntry
{

    private String roleTitle;
    private static ArrayList<Role> allRoles = new ArrayList<>();

    //private Duration hoursSpentinProgram; 
    public Role(String roleTitle)
    {
        this.roleTitle = roleTitle;

    }

    public String getRoleTitle()
    {
        return this.roleTitle;
    }

    public void setRoleTitle(String newTitle)
    {
        this.roleTitle = newTitle;
    }

    public static ArrayList<Role> getAllRoles()
    {
        return allRoles;
    }

    // STATIC METHODS
    public static boolean checkRole(Role role)
    {
        return allRoles.contains(role);
    }

    public static Role getRole(String roleTitle)
    {
        Role role = null;
        for (int i = 0; i < allRoles.size(); i++)
        {
            if (allRoles.get(i).getRoleTitle().equals(roleTitle))
            {
                role = allRoles.get(i);
            }
        }
        return role;
    }

    public static Role addRole(String roleTitle)
    {
        //reuse code from other method to determine if role already exists
        Role role = addRoleFromDatabase(roleTitle);

        // Check if database connection exists and data should be mirrored
        if (Controller.databaseMirrorModeOn)
        {
            //add Role to database
            Database db = Database.getInstance();
            db.addRoleToDatabase(role);
            System.out.println("Role.addRole(): Role added to database;" + role.getRoleTitle());
        }
        return role;
    }

    public static Role addRoleFromDatabase(String roleTitle)
    {
        Role role = null;
        // create role if does not exist yet
        if (getRole(roleTitle) == null)
        {
            role = new Role(roleTitle);
            allRoles.add(role);
        } else
        {
            // IF Role ALREADY EXISTS
            role = getRole(roleTitle);
        }
        return role;
    }

    public static boolean removeRole(Role role)
    {
        return allRoles.remove(role);
    }

    public static boolean removeRole(String roleTitle)
    {
        return removeRole(getRole(roleTitle));
    }

}

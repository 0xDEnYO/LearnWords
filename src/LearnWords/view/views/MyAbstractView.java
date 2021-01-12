/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LearnWords.view.views;

import LearnWords.view.gui.GUI_Base;
import java.awt.GridBagLayout;
import javax.swing.JPanel;

/**
 *
 * @author Daniel Bläcker (d.blaecker@gmail.com)
 */
public abstract class MyAbstractView extends JPanel
{
    // Saves the base GUI for easier access to its elements
    protected static GUI_Base base;

    // Variables that store which navigation buttons need to be active for a particular view
    protected boolean backButton;
    protected boolean menuButton;
    protected boolean logoutButton;

    // Title and subtitle variables
    protected String title;
    protected String subtitle;
    
    // JPanel that contains all the content of a particular view
    protected JPanel pnl_content =  new JPanel(new GridBagLayout());

    
    public String getTitle(){
        return this.title;
    }

    public String getSubtitle(){
        return this.subtitle;
    }

    public JPanel getContentPane(){
        return this.pnl_content;
    }

    public boolean getBackButton()
    {
        return this.backButton;
    }

    public boolean getMenuButton()
    {
        return this.menuButton;
    }

    public boolean getLogoutButton()
    {
        return this.logoutButton;
    }

}

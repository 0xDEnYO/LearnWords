/*
 * This class builds the interface between the program and the user. It creates the GUI_Base and its components.
 */
package LearnWords.view.gui;

import LearnWords.view.components.MyFrame;
import LearnWords.controller.ProgramParameters;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Daniel Bläcker (d.blaecker@gmail.com)
 */
public class GUI_Base
{
    
 

    private final MyFrame frame;
    private JPanel pnl_baseLayout_left, pnl_baseLayout_right, pnl_baseLayout_center, pnl_baseLayout_header, pnl_baseLayout_footer;
    private GUI_Footer footer;
    private GUI_Header header;

   
    public GUI_Base()
    {

        // Create frame
        frame = new MyFrame(ProgramParameters.PROGRAM_NAME);

        // Set up basic grid layout in frame
        setUpBaseLayout();

    }

    private void setUpBaseLayout()
    {
        // instantiate five panels
        pnl_baseLayout_left = new JPanel();
        pnl_baseLayout_right = new JPanel();
        pnl_baseLayout_header = new JPanel();
        pnl_baseLayout_footer = new JPanel();
        pnl_baseLayout_center = new JPanel(new GridBagLayout());

        // BorderLayout is set by default but just to make sure...
        frame.setLayout(new BorderLayout());

        // Assign a color to the panels
        pnl_baseLayout_left.setBackground(ProgramParameters.COLOR_GUI_BACKGROUND);
        pnl_baseLayout_right.setBackground(ProgramParameters.COLOR_GUI_BACKGROUND);
        pnl_baseLayout_center.setBackground(ProgramParameters.COLOR_GUI_FOREGROUND);
        pnl_baseLayout_header.setBackground(ProgramParameters.COLOR_GUI_BACKGROUND);
        pnl_baseLayout_footer.setBackground(ProgramParameters.COLOR_GUI_BACKGROUND);

        // Set up borders for center panel
        pnl_baseLayout_center.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Position panels on frame
        frame.add(pnl_baseLayout_left, BorderLayout.LINE_START);
        frame.add(pnl_baseLayout_center, BorderLayout.CENTER);
        frame.add(pnl_baseLayout_right, BorderLayout.LINE_END);

        // Create and incorporate header and footer
        header = new GUI_Header();
        frame.add(header.getMainPanel(), BorderLayout.PAGE_START);
        footer = new GUI_Footer();
        frame.add(footer.getMainPanel(), BorderLayout.PAGE_END);

        // Adjust dimensions of border layout
        pnl_baseLayout_left.setPreferredSize(new Dimension(100, 50));
        pnl_baseLayout_center.setPreferredSize(new Dimension(800, 400));
        pnl_baseLayout_right.setPreferredSize(new Dimension(100, 50));
        pnl_baseLayout_center.setMinimumSize(new Dimension(800, 400));
        pnl_baseLayout_header.setPreferredSize(new Dimension(100, 100));
//        pnl_baseLayout_footer.setPreferredSize(new Dimension(150, 300));
//        pnl_baseLayout_footer.setMinimumSize(new Dimension(150, 300));

    }

    protected void callGUI()
    {
        frame.show(500, 100, 1200, 800);
        //frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        //frame.pack();
    }

    public void setMenuButton(boolean state)
    {
        if (state) {
            footer.activateMenuButton();
        } else{
             footer.deactivateMenuButton();
        }
        
    }
    public void setBackButton(boolean state)
    {
        if (state) {
            header.activateBackButton();
        } else{
             header.deactivateBackButton();
        }
        
    }
    public void setLogoutButton(boolean state)
    {
        if (state) {
            footer.activateLogoutButton();
        } else{
             footer.deactivateLogoutButton();
        }
        
    }
 
    public void setErrorMessage(String text)
    {
        footer.setErrorMessage(text);

    }

    public JPanel getMainPanel()
    {
        return this.pnl_baseLayout_center;
    }

    public JFrame getFrame()
    {
        return this.frame;
    }

    public GUI_Footer getFooter()
    {
        return this.footer;
    }

    public GUI_Header getHeader()
    {
        return this.header;
    }

    public void setViewTitle(String title)
    {
        header.setViewTitle(title);
    }

    public void setViewSubtitle(String title)
    {
        header.setViewSubtitle(title);
    }

}

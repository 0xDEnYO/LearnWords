/*
 * This class builds the (generic) footer for the GUI of the program. It is unique and therefore only one instance of this class is allowed (singleton)
 */
package LearnWords.view.gui;

import LearnWords.view.components.MyButton;
import LearnWords.controller.ProgramParameters;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Daniel Bläcker (d.blaecker@gmail.com)
 */
public class GUI_Header extends JPanel
{

    private final JPanel pnl_header, pnl_header_left_top, pnl_header_left_bottom, pnl_header_center_top, pnl_header_center_bottom, pnl_header_right_top, pnl_header_right_bottom;
    private final JLabel lbl_header_programName, lbl_header_viewTitle, lbl_header_viewSubtitle;
    private final JButton btn_back;

    public GUI_Header()
    {

        // Create header main panel (that will incorporate all its elements
        pnl_header = new JPanel();

        // Create 6 panels to divide the header in three equal areas (by using GridLayout)
        pnl_header_left_top = new JPanel();
        pnl_header_left_bottom = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnl_header_center_top = new JPanel();
        pnl_header_center_bottom = new JPanel();
        pnl_header_right_top = new JPanel();
        pnl_header_right_bottom = new JPanel();
//        // Set up borders for center panel (tmp)
//        pnl_header_top_center.setBorder(BorderFactory.createLineBorder(Color.BLACK));
//        pnl_header_bottom_center.setBorder(BorderFactory.createLineBorder(Color.BLACK));

//        // Assign colors to all panels
        pnl_header_left_top.setBackground(ProgramParameters.COLOR_GUI_BACKGROUND);
        pnl_header_left_bottom.setBackground(ProgramParameters.COLOR_GUI_BACKGROUND);
        pnl_header_center_top.setBackground(ProgramParameters.COLOR_GUI_BACKGROUND);
        pnl_header_center_bottom.setBackground(ProgramParameters.COLOR_GUI_BACKGROUND);
        pnl_header_right_top.setBackground(ProgramParameters.COLOR_GUI_BACKGROUND);
        pnl_header_right_bottom.setBackground(ProgramParameters.COLOR_GUI_BACKGROUND);

        // Add header panels to the header area of the base layout
        pnl_header.add(pnl_header_left_top);
        pnl_header.add(pnl_header_center_top);
        pnl_header.add(pnl_header_right_top);
        pnl_header.add(pnl_header_left_bottom);
        pnl_header.add(pnl_header_center_bottom);
        pnl_header.add(pnl_header_right_bottom);
        pnl_header.setLayout(new GridLayout(2, 3));

        // Insert program name, view title and subtitle
        lbl_header_programName = new JLabel(ProgramParameters.PROGRAM_NAME);
        lbl_header_programName.setFont(ProgramParameters.FONT_TXT_SIZE_HEADING1);
        lbl_header_viewTitle = new JLabel("VIEW TITLE");
        lbl_header_viewTitle.setFont(ProgramParameters.FONT_TXT_SIZE_HEADING2);
        lbl_header_viewSubtitle = new JLabel("View Subtitle");
        lbl_header_viewSubtitle.setFont(ProgramParameters.FONT_TXT_SIZE_HEADING3);
        //lbl_header_viewSubtitle.setPreferredSize(new Dimension(600,20));

        // For all header center labels set horizontal alignment 
        lbl_header_programName.setHorizontalAlignment(JLabel.CENTER);
        lbl_header_viewTitle.setHorizontalAlignment(JLabel.CENTER);
        lbl_header_viewSubtitle.setHorizontalAlignment(JLabel.CENTER);

        // Add labels to header center
        pnl_header_center_top.add(lbl_header_programName);
        pnl_header_center_bottom.add(lbl_header_viewTitle);
        pnl_header_center_bottom.add(lbl_header_viewSubtitle);

        // Set header center panel to grid (2 rows, 1 column)
        pnl_header_center_bottom.setLayout(new GridLayout(2, 1));

        // Add "BACK" Button with icon in Header
        btn_back = MyButton.getMyButton("../images/btn_back.gif",
                "Back Button - clicking this button leads back to the last view",
                "BACK");
        btn_back.setPreferredSize(new Dimension(100,30));
        pnl_header_left_bottom.add(btn_back);

    }

    public void activateBackButton()
    {
        this.btn_back.setVisible(true);
    }

    public void deactivateBackButton()
    {
        this.btn_back.setVisible(false);

    }

    public void setViewTitle(String title)
    {
        lbl_header_viewTitle.setText(title);
    }

    public void setViewSubtitle(String title)
    {
        lbl_header_viewSubtitle.setText(title);
    }

    public JPanel getMainPanel()
    {
        return this.pnl_header;
    }

}

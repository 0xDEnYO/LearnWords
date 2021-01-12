/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LearnWords.view.views;

import LearnWords.controller.Controller;
import LearnWords.controller.ProgramParameters;
import LearnWords.model.dataStorage.MyWorkSpace;
import LearnWords.model.functional.WordPair;
import LearnWords.view.components.MyButton;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Daniel Bläcker (d.blaecker@gmail.com)
 */
public class ViewLearningSession extends MyAbstractView
{

    private final JPanel pnl_LearningSession, pnl_flashcard, pnl_horizontalStripe;
    private final JButton btn_previousWord, btn_nextWord, btn_changeDirection;
    private final JLabel lbl_heading;
    private JLabel lbl_word;

    private int counterLearningSession = 0;
    private int counterHorizontalStripe = 0;

    private GridBagConstraints gbc = new GridBagConstraints();
    // Saves the (only existing) instance of this class in this variable (Singleton)
    protected static MyAbstractView instance = null;

    private ViewLearningSession()
    {

        pnl_LearningSession = new JPanel();
        pnl_LearningSession.setBackground(ProgramParameters.COLOR_GUI_FOREGROUND);
        pnl_LearningSession.setPreferredSize(new Dimension(900, 550));
        pnl_LearningSession.setMinimumSize(new Dimension(900, 550));
        pnl_LearningSession.setLayout(new GridBagLayout());

        // Create menu elements
        // 0) JLabel for Text
        // Get current index of iterator
        int currentIndex = Controller.getCurrentLearningSession().getCurrentIndex();
        // Get the number of words for this learning session
        int wordCount = Controller.getCurrentLearningSession().getWordCount();
        // Create label
        lbl_heading = new JLabel("WORD " + currentIndex + " of " + wordCount);
        //lbl_heading = new JLabel("WORD ....");
        lbl_heading.setFont(ProgramParameters.FONT_TXT_SIZE_HEADING1);
        lbl_heading.setAlignmentX(Component.CENTER_ALIGNMENT);
        BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.red), lbl_heading.getBorder());
        // Adjust GridBagConstraints
        setGbc(0, counterLearningSession++, 1);
        pnl_LearningSession.add(Box.createRigidArea(new Dimension(0, 25)), gbc);
        setGbc(0, counterLearningSession++, 1);
        pnl_LearningSession.add(lbl_heading, gbc);
        setGbc(0, counterLearningSession++, 1);
        pnl_LearningSession.add(Box.createRigidArea(new Dimension(0, 20)), gbc);

        // Create panel that contains flashcard and  previous/next word buttons
        pnl_horizontalStripe = new JPanel();
        pnl_horizontalStripe.setBackground(ProgramParameters.COLOR_GUI_FOREGROUND);
        pnl_horizontalStripe.setMinimumSize(new Dimension(750, 350));
        pnl_horizontalStripe.setPreferredSize(new Dimension(750, 350));
        pnl_horizontalStripe.setLayout(new GridBagLayout());

        setGbc(0, counterLearningSession++, 1);
        pnl_LearningSession.add(pnl_horizontalStripe, gbc);

        pnl_flashcard = new JPanel();
        pnl_flashcard.setLayout(new GridBagLayout());
        pnl_flashcard.setBackground(Color.PINK);
        pnl_flashcard.setMinimumSize(new Dimension(350, 250));
        pnl_flashcard.setPreferredSize(new Dimension(350, 250));
        pnl_flashcard.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        pnl_flashcard.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e)
            {
                // Set background back to old color
                pnl_flashcard.setBackground(Color.WHITE);
                // Flip flashcard around (show other part of the word pair)
                Controller.flipFlashcard();
                pnl_flashcard.repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e)
            {
                // Set background back to old color
                pnl_flashcard.setBackground(Color.PINK);

            }

        });

        // create label to display the current word and add to flashcard
        WordPair wordPair = Controller.getCurrentWordPair();
        lbl_word = new JLabel(wordPair.getGerman());
        lbl_word.setFont(ProgramParameters.FONT_TXT_SIZE_FLASHCARD);
        lbl_word.setAlignmentY(Component.CENTER_ALIGNMENT);
        pnl_flashcard.add(lbl_word);

// 
        // Create button "Previous Word" and add to horizontal stripe panel
        pnl_flashcard.add(Box.createRigidArea(new Dimension(0, 20)));
        btn_previousWord = MyButton.getMyButton("../images/btn_previousWord.gif",
                "Previous Word - with this button you can move to the previous word pair",
                "PREVIOUS_WORD");
        setGbc(counterHorizontalStripe++, 0, 1);
        pnl_horizontalStripe.add(btn_previousWord);
        // Add spacing
        setGbc(counterHorizontalStripe++, 0, 1);
        pnl_horizontalStripe.add(Box.createRigidArea(new Dimension(20, 0)));

        // Add flashcard panel
        setGbc(counterHorizontalStripe++, 0, 1);
        pnl_horizontalStripe.add(pnl_flashcard);

        // Add spacing
        setGbc(counterHorizontalStripe++, 0, 1);
        pnl_horizontalStripe.add(Box.createRigidArea(new Dimension(20, 0)));

        // Button "Next Word"
        pnl_flashcard.add(Box.createRigidArea(new Dimension(0, 20)));
        btn_nextWord = MyButton.getMyButton("../images/btn_nextWord.gif",
                "Next Word - with this button you can move to the next word pair",
                "NEXT_WORD");
        setGbc(counterHorizontalStripe++, 0, 1);
        pnl_horizontalStripe.add(btn_nextWord);

        // Button "Change Direction"
        btn_changeDirection = MyButton.getMyButton("../images/btn_changeDirection.gif",
                "Change Direction - with this button you can change the language direction",
                "CHANGE_DIRECTION");
        setGbc(0, counterLearningSession++, 1);
        pnl_LearningSession.add(btn_changeDirection, gbc);
        setGbc(0, counterLearningSession++, 1);
        pnl_LearningSession.add(Box.createRigidArea(new Dimension(0, 10)), gbc);

        // Set title and subtitle
        this.title = ProgramParameters.VIEW_TITLE_LEARNINGSESSION;
        this.subtitle = ProgramParameters.VIEW_SUBTITLE_LEARNINGSESSION;

        // Define availability for BACK, MENU and LOGOUT buttons in footer and header
        this.backButton = true;
        this.menuButton = true;
        this.logoutButton = true;

        // Add all elements to main content panel
        gbc = new GridBagConstraints();
        pnl_content.add(pnl_LearningSession, gbc);

    }

    public static MyAbstractView getView()
    {
        // if instance is empty or references object of another view type
        if (instance == null || (!(instance instanceof ViewLearningSession)) )
        {
            instance = new ViewLearningSession();
        }
        return instance;
    }

    public void printSize()
    {
        System.out.println("pnl_content size: " + pnl_content.getSize());
        System.out.println("pnl_leftHalf size: " + pnl_flashcard.getSize());
    }

    public void setHeadingText(String text)
    {

        lbl_heading.setText(text);
    }

    private void setGbc(int gridX, int gridY, int gridwidthX)
    {
        gbc.gridx = gridX;
        gbc.gridy = gridY;
        gbc.weightx = 1;
        gbc.weighty = 1;
        //gbc.fill = GridBagConstraints.NONE;
        gbc.gridwidth = gridwidthX;
    }

    public void setFlashcard(String text)
    {
        lbl_word.setText(text);
    }

    public String getFlashcard()
    {
        return this.lbl_word.getText();
    }

    public void updateView()
    {
        // UPDATE HEADING
        // Get current index of iterator
        int currentIndex = Controller.getCurrentLearningSession().getCurrentIndex();
        // Get the number of words for this learning session
        int wordCount = Controller.getCurrentLearningSession().getWordCount();
        // Create label
        if (Controller.getDefaultLanguage() == MyWorkSpace.DEFAULT_LANGUAGE_GERMAN)
        {
            lbl_heading.setText("WORT " + currentIndex + " VON " + wordCount);
        } else
        {
            lbl_heading.setText("WORD " + currentIndex + " OF " + wordCount);
        }

        // UPDATE FLASHCARD
        Controller.showCurrentWordPairInFlashcard();
    }

}

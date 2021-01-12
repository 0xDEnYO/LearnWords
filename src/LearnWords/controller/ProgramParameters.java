/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LearnWords.controller;

import java.awt.Color;
import java.awt.Font;
import java.nio.file.Paths;

/**
 *
 * @author Daniel Bläcker (d.blaecker@gmail.com)
 */
public class ProgramParameters
{

    public static final String PROGRAM_NAME = "LearnWords_V1";
    public static final String AUTHOR = "Daniel Bläcker";
    public static final String AUTHOR_EMAIL = "d.blaecker@gmail.com";

    // Database
    public static final String DB_NAME = "TEST_DB";
    public static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String DB_URL = "jdbc:mysql://localhost:3306"
            + "?useUnicode=true"
            + "&useJDBCCompliantTimezoneShift=true"
            + "&useLegacyDatetimeCode=false"
            + "&serverTimezone=UTC";
    public static final String DB_LOGIN_USER = "root";
    public static final String DB_LOGIN_PW = "";

    // Program view titles
    public static final String VIEW_TITLE_LOGIN = "LOGIN";
    public static final String VIEW_TITLE_MENU_MAIN = "MAIN MENU";
    public static final String VIEW_TITLE_MENU_TEACHER = "TEACHER MENU";
    public static final String VIEW_TITLE_VIEWWORDS = "VIEW WORDS";
    public static final String VIEW_TITLE_LEARNWORDS = "LEARN WORDS";
    public static final String VIEW_TITLE_MANAGEWORDS = "MANAGE WORDS";
    public static final String VIEW_TITLE_MAINTAINWORDS = "MAINTAIN WORDS";
    public static final String VIEW_TITLE_REQUESTS = "REQUESTS";
    public static final String VIEW_TITLE_HISTORY = "HISTORY & STATISTICS";
    public static final String VIEW_TITLE_SETTINGS = "SETTINGS";
    public static final String VIEW_TITLE_EVALUATE = "EVALUATION";
    public static final String VIEW_TITLE_CSVUPLOAD = "CSV UPLOAD";
    public static final String VIEW_TITLE_MANUALUPLOAD = "MANUAL UPLOAD";
    public static final String VIEW_TITLE_LEARNINGSESSION = "LEARNING SESSION";

    // Program view subtitles
    public static final String VIEW_SUBTITLE_LOGIN = "";
    public static final String VIEW_SUBTITLE_MENU = "Please select a menu item";
    public static final String VIEW_SUBTITLE_LEARNWORDS = "Please select  words  to practice or resume your last session";
    public static final String VIEW_SUBTITLE_VIEWWORDS = "Please select the words you would like to view";
    public static final String VIEW_SUBTITLE_LEARNINGSESSION = "Enjoy your session :)";
    public static final String VIEW_SUBTITLE_MANAGEWORDS = "Add words, mark words unlearned or request deletion of word lists";

    // FORMATTING
    public static final int TXT_SIZE_FLASHCARD = 45;
    public static final int TXT_SIZE_HEADING1 = 30;
    public static final int TXT_SIZE_HEADING2 = 16;
    public static final int TXT_SIZE_HEADING3 = 12;
    public static final int TXT_SIZE_BODY = 11;
    public static final int TXT_SIZE_SMALL = 10;

    // FONTS
    public static final String FONT_NAME_HEADING = "TimesRoman";
    public static final Font FONT_TXT_SIZE_FLASHCARD = new Font(FONT_NAME_HEADING, Font.BOLD, TXT_SIZE_FLASHCARD);
    public static final Font FONT_TXT_SIZE_HEADING1 = new Font(FONT_NAME_HEADING, Font.BOLD, TXT_SIZE_HEADING1);
    public static final Font FONT_TXT_SIZE_HEADING2 = new Font(FONT_NAME_HEADING, Font.BOLD, TXT_SIZE_HEADING2);
    public static final Font FONT_TXT_SIZE_HEADING3 = new Font(FONT_NAME_HEADING, Font.BOLD, TXT_SIZE_HEADING3);
    public static final Font FONT_TXT_SIZE_BODY = new Font(FONT_NAME_HEADING, Font.PLAIN, TXT_SIZE_BODY);
    public static final Font FONT_TXT_SIZE_SMALL = new Font(FONT_NAME_HEADING, Font.PLAIN, TXT_SIZE_SMALL);
    public static final Font FONT_TXT_SIZE_ERROR = new Font(FONT_NAME_HEADING, Font.BOLD, TXT_SIZE_HEADING3);

    // Coloring
    public static final Color COLOR_GUI_BACKGROUND = new Color(217, 217, 217);
    public static final Color COLOR_GUI_FOREGROUND = new Color(255, 230, 153);

    // DEFAULT VALUES
    public static final String TXT_ERRORMESSAGE_DEFAULT = "<html><br><br><br></html>";
    public static final int ERROR_MSG_TIME = 2000;
    public static final int BUTTON_CLICK_ANIMATION_TIME = 100;

    // STORAGE LOCATIONS
    public static String dir_CSVStorageLocation = null;
    public static String dir_workspaceStorageLocation = null;
    public static String dir_testdataCSVUploadWordList = null;
    public static String dir_testdataCSVUploadWordList2 = null;
    public static String dir_testdata = null;
    public static String dir_userDirectory = null;

    // SELECTION VALUES
    public static final int SAVE_LOCATION_CSV = 1;
    public static final int SAVE_LOCATION_DATABASE = 2;
    public static final String DROPDOWN_CONFIG_CSV = "Local drive (CSV)";
    public static final String DROPDOWN_CONFIG_DB = "Database";

    public static void updateUserDirectory()
    {
        // Read current directory
        String path = Paths.get("")
                .toAbsolutePath()
                .toString();

        String tmp = path.replace("\\", "/");

        // Update user directory
        dir_userDirectory = tmp;

        // Update storage locations
        dir_CSVStorageLocation = dir_userDirectory + "/output/";
        dir_workspaceStorageLocation = dir_userDirectory + "/workspace/workspace.ser";
        dir_testdataCSVUploadWordList = dir_userDirectory + "/src/LearnWords/model/dataStorage/testData/wordList.csv";
        dir_testdataCSVUploadWordList2 = dir_userDirectory + "/src/LearnWords/model/dataStorage/testData/wordList2.csv";
        dir_testdata = dir_userDirectory + "/src/LearnWords/model/dataStorage/testData/Serialized/";

    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LearnWords;

import LearnWords.controller.*;
import LearnWords.model.dataStorage.*;
import LearnWords.model.functional.*;
import LearnWords.view.gui.GUI;
import LearnWords.view.views.ViewLogin;

/**
 *
 * @author Daniel
 */
public class Main {

    public static Database db;
    public static WordPair wordPair;
    public static WordList dictionary;
    private static GUI gui;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

//        wordPair = new WordPair("Auto", "car");
//        dictionary = new WordList();
//        dictionary.addWordPair(wordPair);
        // Create dummy user for test purposes


            Controller.startUp();
            //Controller.activateView(ProgramParameters.VIEW_TITLE_LEARNINGSESSION);
            ViewLogin viewLogin = (ViewLogin) GUI.getCurrentView();
//            viewLogin.clickConnect();
//            viewLogin.clickLogin();
//            Controller.activateView(ProgramParameters.VIEW_TITLE_SETTINGS);
            
//        User user1 = Controller.createUser(
//                "daniel.blaecker",
//                "1234",
//                "Administrator",
//                "Daniel",
//                "Blaecker",
//                ProgramParameters.AUTHOR_EMAIL);
//        
//        User user2 = Controller.createUser(
//                "gast",
//                "1234",
//                "Lehrer",
//                "Peter",
//                "Pan",
//                "peter.pan@web.de"
//        );




        //Controller.activateView(ProgramParameters.VIEW_TITLE_LEARNINGSESSION);


//        db.serializeObjectsToCSV(ser_users, User.class);
//        
//        //deSer_users = db.deserializeObjectsFromCSV(User.class);
//        deSer_users = db.deserializeObjectsFromCSV2(User.class);
//        
//        System.out.printf("Login of newUser: \nUsername0: %S\n",deSer_users.get(0).getLogin().getUsername());
//        System.out.printf("Login of newUser: \nUsername1: %S\n",deSer_users.get(1).getLogin().getUsername());

        //Controller.saveAllObjectsToCSV();
    }

    // --------------------------------------------------------------
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LearnWords.model.dataStorage;

import LearnWords.controller.*;
import LearnWords.model.administration.userManagement.User;
import LearnWords.model.functional.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;

/**
 *
 * @author Daniel Bläcker (d.blaecker@gmail.com)
 */
public class CSV
{

    private static PrintWriter printWriter = null;
    private static BufferedReader csvReader = null;
    private static WordList wordlist = null;
    private static boolean result = false;

    public static PrintWriter getPrintWriterDefaultLocation()
    {
        return getPrintWriter(ProgramParameters.dir_CSVStorageLocation + LocalDate.now().toString() + ".csv");
    }

    public static PrintWriter getPrintWriter(String path)
    {

        PrintWriter pw = null;
        try
        {
            FileWriter fw = new FileWriter(path, true);
            BufferedWriter bw = new BufferedWriter(fw);
            pw = new PrintWriter(bw);

        } catch (FileNotFoundException e)
        {
            System.out.println(e.getMessage());
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return pw;
    }

    public static <T extends Serializable> void serializeObjectsToCSV(ArrayList<T> objects, Class<T> clazz)
    {

        // Serialize Object
        OutputStream fos = null;

        try
        {
            // Create new file object with class name as file name
            File file = new File(ProgramParameters.dir_testdata + clazz.getName() + ".txt");
            // Check if actual file exists, otherwise create
            file.createNewFile();
            // Set up connection to CSV
            fos = new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(fos);
            // write object into file
            for (T object : objects)
            {
                // Cast item into object of its actual class
                T item = cast(object, clazz);
                if (item == null)
                {
                    System.out.println("Could not write item: " + object.toString());
                } else
                {
                    // serialize object into file
                    out.writeObject(item);
                }
            }
        } catch (IOException e)
        {
            System.err.println(e);
        } finally
        {
            try
            {
                fos.close();
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

//    public static <T extends Serializable> ArrayList<T> deserializeObjectsFromCSV(ArrayList<T> objects, Class<T> clazz) {
//
//        ArrayList<T> result = new ArrayList<>();
//
//        FileInputStream fis = null;
//        try {
//            // Open file
//            File file = new File(ProgramParameters.dir_testdata + objects.getClass().getName() + ".txt");
//            file.createNewFile();
//            fis = new FileInputStream(file);
//            ObjectInputStream in = new ObjectInputStream(fis);
//            // read objects from file
//            for (T object : objects) {
//                // Cast item into object of its actual class
//                T item = cast(object, clazz);
//                if (item != null) {
//                    System.out.println("Could not write item: " + object.toString());
//                } else {
//                    // create object from file
//                    result.add((T) in.readObject());
//                }
//            }
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(Database.class
//                    .getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(Database.class
//                    .getName()).log(Level.SEVERE, null, ex);
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(Database.class
//                    .getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            try {
//                fis.close();
//
//            } catch (IOException ex) {
//                Logger.getLogger(Database.class
//                        .getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//
//        // Cast object to its class
//        return result;
//    }
    public static <T extends Serializable> ArrayList<T> deserializeObjectsFromCSV2(Class<T> clazz)
    {

        ArrayList<T> result = new ArrayList<>();

        FileInputStream fis = null;
        try
        {
            // Open file
            File file = new File(ProgramParameters.dir_testdata + clazz.getName() + ".txt");
            file.createNewFile();
            fis = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fis);

            // read objects from file
            for (;;)
            {

                Object obj = in.readObject();
                // Read next object from file and try to cast it
                T item = cast(obj, clazz);
                // Cast item into object of its actual class

                if (item == null)
                {
                    System.out.println("Could not write item: " + obj.toString());
                } else
                {
                    // create object from file
                    result.add(item);
                }
            }
        } catch (FileNotFoundException ex)
        {
            Logger.getLogger(Database.class
                    .getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex)
        {
            Logger.getLogger(Database.class
                    .getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex)
        {
            Logger.getLogger(Database.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally
        {
            try
            {
                fis.close();

            } catch (IOException ex)
            {
                Logger.getLogger(Database.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }

        return result;
    }

    public static String escapeSpecialCharacters(String data)
    {
        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains(",") || data.contains("\"") || data.contains("'"))
        {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }

    private static File getCSVFileFromUserDialog()
    {
        int filePath;
        File selectedFile = new File("test.bla");
        try
        {

            // Open dialog to select file
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Please select a csv-file");
            fileChooser.setCurrentDirectory(new File(ProgramParameters.dir_testdata));
            do
            {
                filePath = fileChooser.showOpenDialog(Controller.getCurrentFrame());

                if (filePath == JFileChooser.APPROVE_OPTION)
                {
                    selectedFile = fileChooser.getSelectedFile();
                    //System.out.println("Selected file: " + selectedFile.getAbsolutePath());
                }
            } while (!selectedFile.getAbsolutePath().contains(".csv"));

        } catch (Exception e)
        {
            Logger.getLogger(CSV.class
                    .getName()).log(Level.SEVERE, null, e);
        }
        return new File(selectedFile.getAbsolutePath());
    }

    private static BufferedReader getCSVReader(String filePath)
    {

        csvReader = null;
        try
        {

            csvReader = new BufferedReader(new FileReader(filePath));

        } catch (FileNotFoundException ex)
        {
            Logger.getLogger(CSV.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return csvReader;
    }

    public static boolean uploadWordList()
    {
        BufferedReader csvReader = null;
        boolean result = false;
        File file;
        // Open dialog to select file
        file = getCSVFileFromUserDialog();
        // open printreader to read from CSV file
        csvReader = getCSVReader(file.getAbsolutePath());

        // Create new word list object
        WordList wordlist = new WordList(file.getName());
        String row;
        try
        {
            while ((row = csvReader.readLine()) != null)
            {
                String[] data = row.split(";");
                // Skip header row
                if (data[0].equals("GERMAN"))
                {
                    continue;
                }

                // Create word pairs and save in word list
                WordPair word = new WordPair(data[0], data[1], Boolean.valueOf(data[2]));
                wordlist.addWordPair(word);
                //System.out.printf("Data:\n0:%S\n1:%S\n2:%S\n", data[0], data[1], data[2]);
                //System.out.printf("WordPair created: \nGerman: %S\nEnglish:%S\n", data[0], data[1]);
                result = true;
            }
            csvReader.close();

        } catch (FileNotFoundException ex)
        {
            Logger.getLogger(CSV.class
                    .getName()).log(Level.SEVERE, null, ex);
        } catch (IOException e)
        {
            Logger.getLogger(CSV.class
                    .getName()).log(Level.SEVERE, null, e);
        } finally
        {
            try
            {
                csvReader.close();

            } catch (IOException ex)
            {
                Logger.getLogger(CSV.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
        // add word list to user's word lists
        Controller.addWordList(wordlist);

        // word list not uploaded successfully
        return result;
    }

    public static boolean automaticUploadWordLists()
    {
        try
        {
            csvReader = null;
            boolean result = false;
            // open printreader to read from CSV file
            csvReader = getCSVReader(ProgramParameters.dir_testdataCSVUploadWordList);

            // Create new word list object
            wordlist = new WordList("WordList.csv");
            String row;

            while ((row = csvReader.readLine()) != null)
            {
                String[] data = row.split(";");
                // Skip header row
                if (data[0].equals("GERMAN"))
                {
                    continue;
                }

                // Create word pairs and save in word list
                User user = User.getUser("daniel.blaecker");
                System.out.printf("WordList-Data:\nGerman:%S\nEnglish:%S\nLearned:%S\nUser:%S", data[0], data[1], Boolean.valueOf(data[2]), user);
                WordPair word = new WordPair(data[0], data[1], Boolean.valueOf(data[2]), user);
                wordlist.addWordPair(word);
                
                //System.out.printf("WordPair created: \nGerman: %S\nEnglish:%S\n", data[0], data[1]);
            }

            // add word list to user's word lists
            Controller.addWordList(wordlist);

            // open printreader to read from CSV file
            csvReader = getCSVReader(ProgramParameters.dir_testdataCSVUploadWordList2);

            // Create new word list object
            wordlist = new WordList("WordList2.csv");

            while ((row = csvReader.readLine()) != null)
            {
                String[] data = row.split(";");
                // Skip header row
                if (data[0].equals("GERMAN"))
                {
                    continue;
                }

                // Create word pairs and save in word list
                User user = User.getUser("daniel.blaecker");
                System.out.printf("WordList2-Data:\nGerman:%S\nEnglish:%S\nLearned:%S\nUser:%S", data[0], data[1], Boolean.valueOf(data[2]), user);
                WordPair word = new WordPair(data[0], data[1], Boolean.valueOf(data[2]), user);
                wordlist.addWordPair(word);

                //System.out.printf("Data:\n0:%S\n1:%S\n2:%S\n", data[0], data[1], data[2]);
                //System.out.printf("WordPair created: \nGerman: %S\nEnglish:%S\n", data[0], data[1]);
                result = true;
            }

        } catch (FileNotFoundException ex)
        {
            Logger.getLogger(CSV.class
                    .getName()).log(Level.SEVERE, null, ex);
        } catch (IOException e)
        {
            Logger.getLogger(CSV.class
                    .getName()).log(Level.SEVERE, null, e);
        } finally
        {
            try
            {
                csvReader.close();

            } catch (IOException ex)
            {
                Logger.getLogger(CSV.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
        // add word list to user's word lists

        Controller.addWordList(wordlist);

        // word list not uploaded successfully
        return result;
    }

    //---------------------------------------------------------------------------------------------------
    //Helper Methods
    public static <T> T cast(Object o, Class<T> clazz)
    {
        return clazz.isInstance(o) ? clazz.cast(o) : null;
    }

}

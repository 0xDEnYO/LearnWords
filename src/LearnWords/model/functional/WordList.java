/*
 * A WordList contains a selection of word pairs (German-English).
 */
package LearnWords.model.functional;

import LearnWords.controller.Controller;
import LearnWords.model.administration.userManagement.User;
import LearnWords.model.dataStorage.Database;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author Daniel Bläcker (d.blaecker@gmail.com)
 */
public class WordList implements Iterable<WordPair>, Serializable
{

    private final HashMap<String, String> words_DEtoEN, words_ENtoDE;
    private final ArrayList<WordPair> wordsList;
    private int wordsLearned, wordsUnlearned;
    private String name;
    public int currentIndex = 0;

    private class MyIterator implements Iterator
    {

        //int currentIndex = 0;
        @Override
        public boolean hasNext()
        {
            return wordsList.size() > currentIndex;
        }

        @Override
        public WordPair next()
        {
            return wordsList.get(currentIndex++);
        }
        public int getCurrentPosition()
        {
            return currentIndex;
        }

    }

    public WordList(String name)
    {
        wordsList = new ArrayList<>();
        words_DEtoEN = new HashMap<>();
        words_ENtoDE = new HashMap<>();
        wordsLearned = 0;
        wordsUnlearned = 0;
        this.name = name;
    }

    @Override
    public Iterator<WordPair> iterator()
    {
        return new MyIterator();
    }

    public WordPair iteratorLast()
    {
        return wordsList.get(--currentIndex);
    }

    public String getName()
    {
        return this.name;
    }

    public int getLearnedCount()
    {
        return this.wordsLearned;
    }

    public int getUnlearnedCount()
    {
        return this.wordsUnlearned;
    }

    public int getTotalCount()
    {
        return wordsLearned + wordsUnlearned;
    }

    public HashMap<String, String> getWordsDEtoEN()
    {
        return words_DEtoEN;
    }

    public HashMap<String, String> getWordsENtoDE()
    {
        return words_ENtoDE;
    }

    public void addWordPair(WordPair pair)
    {

        // Add word pair to dictionary with German Key
        words_DEtoEN.put(pair.getGerman(), pair.getEnglish());

        // Add word pair to dictionary with English Key
        words_ENtoDE.put(pair.getEnglish(), pair.getGerman());

        // Add word pair to object collection
        wordsList.add(pair);

        if (pair.learnedByUser(Controller.getCurrentUser()))
        {

            //Increase counter for learned words
            wordsLearned++;
        } else
        {
            // Increase counter for unlearned words
            wordsUnlearned++;

        }
    }

    public void removeWordPair(WordPair wordPair, User currentUser)
    {

        // Remove word pair from dictionary with German key
        words_DEtoEN.remove(wordPair.getGerman());

        // Add word pair to dictionary with English Key
        words_ENtoDE.remove(wordPair.getEnglish());

        // Remove word pair from object collection
        wordsList.remove(wordPair);

        // Find out if word pair was learned or unlearned for the current user
        if (wordPair.learnedByUser(currentUser))
        {
            // word was learned
            wordsLearned--;
        } else
        {
            //word was unlearned
            wordsUnlearned--;
        }
    }

    public WordPair getWordPair(String german)
    {
        // if key is found in hashmap
        if (this.words_DEtoEN.containsKey(german))
        {
            // go through all word pair objects in Arraylist
            for (WordPair pair : wordsList)
            {
                //if matching german key found, return pair
                if (pair.getGerman().equals(german))
                {
                    // found word pair
                    return pair;
                }
            }
        } else
        {
            Controller.setErrorMessage("Error in WordList.getWordPair():WordPair not found");
        }

        return null;
    }

    public void decreaseLearnedCounter()
    {
        // Decrease counter for learned words by 1
        this.wordsLearned--;
        // Increase counter for unlearned words by 1
        this.wordsUnlearned++;
    }

    public void increaseLearnedCounter()
    {
        // Increase counter for learned words by 1
        this.wordsLearned++;
        // Decrease counter for unlearned words by 1
        this.wordsUnlearned--;
    }

    public void resetAllWordsToUnlearned()
    {
        // store learnedCount in tmp variable, set learned to 0 and add tmp to unlearned words 
        int tmp = this.wordsLearned;
        this.wordsLearned = 0;
        wordsUnlearned += tmp;

    }

    public void setAllToLearned()
    {
        this.wordsLearned = this.getTotalCount();
        this.wordsUnlearned = 0;

    }

    public int getCurrentIndex()
    {
        return this.currentIndex;
    }
    
    public void resetIterator(){
        this.currentIndex = 0;
    }
    
}

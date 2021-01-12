/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LearnWords.model.functional;

import java.io.Serializable;

/**
 *
 * @author dblae
 */
public class LearningSession implements Serializable
{

    private final WordList words;

    public LearningSession(WordList... allWords)
    {
        words = new WordList("tmp_wordList");
        for (WordList list : allWords)
        {
            // go through the list and add each word pair to words variable
            // Reset iterator in list
            list.resetIterator();
            for (WordPair pair : list)
            {
                // word pair to words list of this session
                System.out.println("\nWordPair: " + pair.getGerman());
                words.addWordPair(pair);
            }
        }
        System.out.println("Wörter in Liste: " + words.getTotalCount());
    }

    public int getCurrentIndex()
    {
        return words.getCurrentIndex();
    }

    public int getWordCount()
    {
        return words.getTotalCount();
    }

    public WordList getWordList()
    {
        return words;
    }

}

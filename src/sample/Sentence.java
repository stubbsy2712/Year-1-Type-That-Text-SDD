package sample;
import java.util.ArrayList;

public class Sentence {
    private ArrayList<Word> words;
    private String[] structure;
    public void addWord(Word w)
    {
        words.add(w);
    }
    public void removeWord()
    {
        words.remove(words.size() -1 );
    }
    public boolean isValid(Word w)
    {
        Word pw = words.get(words.size() - 1);
        if (pw.type == "prefix" && pw.endsWithConsonant() && w.type == "noun" && w.startsWithConsonant())
        { return false; }
        if (pw.type == "prefix" && pw.endsWithVowel() && w.type == "noun" && w.startsWithVowel())
        { return false; }
        return true;
    }

    public Sentence()
    {
        words = new ArrayList<Word>();
        TextFileReader tfr = new TextFileReader("structures");
        structure = tfr.randomLine().split(" ");
        String nextWordType;
        Word newWord = new Word();
        for (int x = 0; x < structure.length; x++)
        {
            //x is the index of the word currently being added to the words ArrayList.
            nextWordType = structure[x];
            newWord = new Word(nextWordType);
            System.out.println(nextWordType);
            //System.out.println(newWord.word);
            while ( !wordValid(newWord, x-1))
            {
                //System.out.println("EPIC FAIL");
                newWord = new Word(nextWordType);
            }
            addWord(newWord);
        }
    }

    private boolean wordValid(Word w, int lastWordIndex)
    {
        Word lastWord;
        Word wordBeforeLast;
        if (words.size() > 2)
            wordBeforeLast = words.get(lastWordIndex - 1);
        else wordBeforeLast = new Word();
        if (words.size() > 2)
            System.out.println(wordBeforeLast.word);
        if (words.size() > 1)
            lastWord = words.get(lastWordIndex);
        else lastWord = new Word();
        if (words.size() > 1)
            System.out.println(lastWord.word);
        System.out.println(w.word);
        System.out.println(lastWordIndex);
        if (lastWordIndex == -1) {return true;}
        if (w.plural.equals("singular") && words.get(lastWordIndex).type.equals("determiner") && words.get(lastWordIndex).plural.equals("plural"))
        {return  false;}
        if (w.plural.equals("plural") && words.get(lastWordIndex).type.equals("determiner") && words.get(lastWordIndex).plural.equals("singular"))
        {return  false;}
        if (words.get(lastWordIndex).endsWithVowel() && words.get(lastWordIndex).type.equals("determiner") && w.startsWithVowel())
        {return  false;}

        return true;
    }

    public String toString(boolean punctuation)
    {
        String string = "";
        for (Word w: words)
        {
            string += " ";
            string += w.word;
        }
        String firstCharacter = string.substring(1, 2);
        //string.replace("_", " ");
        string = string.substring(2);
        if (punctuation)
        {
            firstCharacter = firstCharacter.toUpperCase();
            string += ".";
        }
        return (firstCharacter + string).replace("_", " ");
    }


}

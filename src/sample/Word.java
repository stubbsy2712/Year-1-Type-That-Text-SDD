package sample;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Word {
    public String word;
    public String type;
    public String plural;
    public boolean startsWithVowel()
    {
        if (word.matches("(a|e|i|o|u).*"))
        {return true;}
        return false;
    }
    public boolean endsWithConsonant()
    {
        if (! endsWithVowel())
        { return true;}
        return false;
    }
    public boolean startsWithConsonant()
    {
        if (! startsWithVowel())
        { return true;}
        return false;
    }
    public boolean endsWithVowel()
    {
        if (word.matches(".(a|e|i|o|u)") && ! findConsonantSounds().contains(word))
        {return true;}
        return false;
    }

    public ArrayList<String> findConsonantSounds()
    {

        TextFileReader tfr = new TextFileReader("endsInConsonantSound");
        return new ArrayList<String>(Arrays.asList(tfr.allLines()));
    }

    public Word(String word, String type, String plural)
    {
        this.word = word;
        this.type = type;
        this.plural = plural;
    }
    public Word(String type)
    {
        TextFileReader tfr = new TextFileReader(type);
        String[] line = tfr.randomLine().split(" ");
        this.type = type;
        this.word = line[0];
        this.plural = line[1];
    }
    public Word() {}
}

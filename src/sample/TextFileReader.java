package sample;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

public class TextFileReader {

    private String fullPath;
    private Random rng;
    private int numberOfLines;
    private File file;

    public String randomLine()
    {
        String line = "";
        try
        {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            try
            {
                while((line = br.readLine()) != null) {
                    numberOfLines += 1;
                }
                int randomNumber = rng.nextInt(numberOfLines);
                line = Files.readAllLines(Paths.get(fullPath)).get(randomNumber);
                br.close();
            }
            catch (IOException ioe)
            { }
        }
        catch (FileNotFoundException fnfe)
        { }
        return line;
    }

    public String[] findLines(String beginsWith)
    {
        ArrayList<String> lines = new ArrayList<String>();
        String currentLine;
        beginsWith += " ";
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(file));
            try
            {
                //System.out.println("a");
                while((currentLine = br.readLine()) != null) {
                    if (currentLine.matches(beginsWith+".*"))
                    {
                        //System.out.println(currentLine + "b");
                        lines.add(currentLine);
                    }
                }
                br.close();
            }
            catch (IOException ioe)
            { }
        }
        catch (FileNotFoundException fnfe)
        { }
        if (lines.size() == 0)
        {
            lines.add("empty");
            lines.add("empty");
            lines.add("empty");
            lines.add("extra");
        }//This is done so that something is returned, however it is larger than the 3
         //strings expected.
            String[] stringArray = new String[lines.size()];
            return lines.toArray(stringArray);
    }

    public String[] allLines()
    {
        ArrayList<String> lines = new ArrayList<String>();
        String currentLine;
        try
        {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            try
            {
                while((currentLine = br.readLine()) != null) {
                    lines.add(currentLine);
                }
                br.close();
            }
            catch (IOException ioe)
            { }
        }
        catch (FileNotFoundException fnfe)
        { }
        String[] stringArray = new String[lines.size()];
        return lines.toArray(stringArray);
    }

    public void deleteLines(String beginsWith)
    {
        deleteLines(beginsWith, false);
    }

    public void deleteLines(String beginsWith, boolean singleLine)
    {
        File newFile = new File("src/sample/Text Files/newScores.txt");
        String currentLine;
        beginsWith += " ";
        int newLineLimit = findLines(beginsWith).length;
        int lineNumberDown = allLines().length;
        try
        {
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                BufferedWriter bw = new BufferedWriter(new FileWriter(newFile));
                try
                {
                    while((currentLine = br.readLine()) != null) {
                        if (! currentLine.matches(beginsWith+".*")) {
                            bw.write(currentLine);
                            lineNumberDown--;
                            if (!singleLine) {
                                if (lineNumberDown >= newLineLimit)
                                { bw.newLine(); }
                            }
                            else
                                {
                                    if (lineNumberDown >= newLineLimit - 2)
                                    { bw.newLine(); }
                                }
                        }
                    }
                    br.close();
                    bw.close();
                    if (file.delete())
                    {
                        if (newFile.renameTo(file))
                            System.out.printf("a");
                        else
                            System.out.println("b");
                    }
                    else
                        System.out.println("c");
                }
                catch (IOException ioe) { }
            }
            catch (FileNotFoundException fnfe) { }
        }
        catch (IOException ioe) { }
    }

    public void addNewScores(String layoutName)
    {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
            //System.out.println("called");
            if (allLines().length != 0)
                bw.newLine();
            bw.write(layoutName +" Words 0");
            bw.newLine();
            bw.write(layoutName +" Sentences 0");
            bw.newLine();
            bw.write(layoutName +" Sentences_with_punctuation 0");
            bw.close();
        }
        catch (IOException ioe) {}
    }

    public void newHighScore(String beginsWith, int newPoints)
    {
        deleteLines(beginsWith, true);
        beginsWith += " ";
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
            //System.out.println("called");
            bw.newLine();
            bw.write(beginsWith + newPoints);
            bw.close();
        }
        catch (IOException ioe) {}
    }

    public TextFileReader(String filename)
    {
        rng = new Random();
        numberOfLines = 0;
        fullPath = "src/sample/Text Files/" + filename + ".txt";
        file = new File("src/sample/Text Files/" + filename + ".txt");
    }
}

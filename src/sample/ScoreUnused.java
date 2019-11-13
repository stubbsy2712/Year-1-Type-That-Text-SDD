package sample;

public class ScoreUnused {
    public String points;
    public String difficulty;
    public String layout;
    public ScoreUnused(String[] line)
    {
        layout = line[0];
        difficulty = line[1];
        points = line[2];
    }
}

package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Window;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class GameUIController {
    public AnchorPane anchorPane;
    public Label scoreLabel;
    public Label remainingTimeLabel;
    public Label sentenceLabel;
    public Label gameOverLabel;
    public TextField enterText;
    public Button backButton;
    public Button submitButton;
    public Button startButton;
    public String difficulty;
    public int highScore;
    public String layout;
    Timeline gameTimer;
    Timeline sentenceTimer;
    int timeLeft;
    int points;
    int secondsOnQuestion;
    void countGameTime()
    {
        timeLeft --;
        int secondsLeft = timeLeft % 60;
        int minutesLeft = timeLeft / 60;
        if (secondsLeft < 10)
            remainingTimeLabel.setText("Remaining Time: " + minutesLeft + " : 0" + secondsLeft);
        else
            remainingTimeLabel.setText("Remaining Time: " + minutesLeft + " : " + secondsLeft);
        if (timeLeft == 0)
        {
            gameTimer.stop();
            sentenceTimer.stop();
            endGame();
        }
    }
    @FXML
    void startGame()
    {
        gameTimer = new Timeline();
        //timeLeft = 11;
        timeLeft = 151;
        gameTimer.setDelay(new Duration(1000));
        points = 0;
        KeyFrame frame= new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                countGameTime();
                }
            });

        gameTimer.setCycleCount(Timeline.INDEFINITE);
        gameTimer.getKeyFrames().add(frame);
        gameTimer.play();

        sentenceTimer = new Timeline();
        sentenceTimer.setDelay(new Duration(1000));
        KeyFrame sentenceFrame = new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                secondsOnQuestion++;
            }
        });
        sentenceTimer.setCycleCount(Timeline.INDEFINITE);
        sentenceTimer.getKeyFrames().add(sentenceFrame);
        sentenceTimer.play();

        countGameTime();
        setPlaying(true);
        scoreLabel.setText("Score: " + points);
        setEnterAsButton();
        generateSentence();
    }
    @FXML
    void submitAnswer()
    {
        awardScore();
        generateSentence();
        enterText.setText("");
        scoreLabel.setText("Score: " + points);
    }
    @FXML
    void leaveGame() throws IOException
    {
        Window mainWindow = backButton.getScene().getWindow();
        Parent newRoot = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        mainWindow.getScene().setRoot(newRoot);
    }
    void awardScore()
    {
        int newScore = addPoints();
        newScore -= secondsOnQuestion;
        if (newScore > 0)
            points += newScore;
        secondsOnQuestion = 0;
    }
    int addPoints()
    {
        int userIndex = 0;
        int currentPoints = 0;
        for (char c : sentenceLabel.getText().toCharArray())
        {
            try {
                //System.out.println(c);
                //System.out.println(enterText.getText().toCharArray()[userIndex]);
                if (c == enterText.getText().toCharArray()[userIndex])
                {currentPoints += 1;}
            }
            catch (ArrayIndexOutOfBoundsException a) {}
            //If the user's input is shorter than the sentence or word they are presented with, an
            //ArrayOutOfBoundsException will be thrown, the program continues normally and the if statement simply
            //results false, so it works as intended.
            userIndex += 1;
        }
        return currentPoints;
    }
    void generateSentence()
    {
        secondsOnQuestion = 0;
        if (difficulty == "Sentences")
            sentenceLabel.setText(new Sentence().toString(false));
        else if (difficulty == "Sentences with punctuation")
            sentenceLabel.setText(new Sentence().toString(true));
        else
            generateWord();
    }
    void generateWord()
    {
        Random rng = new Random();
        ArrayList<String> wordTypes = new ArrayList<String>();
        wordTypes.add("verb");
        wordTypes.add("noun");
        wordTypes.add("conjunction");
        wordTypes.add("adverb");
        wordTypes.add("adjective");
        wordTypes.add("preposition");
        wordTypes.add("determiner");
        String words[] = new Word(wordTypes.get(rng.nextInt(wordTypes.size()))).word.split("_");
        sentenceLabel.setText(words[0]);
    }
    void setEnterAsButton(){
        enterText.setOnKeyPressed(
                event ->
                {
                    switch (event.getCode())
                    {
                        case ENTER:
                            submitAnswer();
                    }
                }
        );
    }
    void setPlaying(boolean playing)
    {
        scoreLabel.setDisable(!playing);
        remainingTimeLabel.setDisable(!playing);
        sentenceLabel.setDisable(!playing);
        enterText.setDisable(!playing);
        backButton.setDisable(!playing);
        submitButton.setDisable(!playing);
        startButton.setVisible(!playing);
        gameOverLabel.setVisible(!playing);
    }
    void endGame()
    {
        submitAnswer();
        setPlaying(false);
        if (points > highScore)
        {
            gameOverLabel.setText("Game over. You scored: " + points + " points.");
            TextFileReader tfr = new TextFileReader("scores");
            tfr.newHighScore(layout + " " + difficulty.replace(" ", "_"), points);
        }
        gameOverLabel.setText("Game over. You scored: " + points + " points.");
    }
}

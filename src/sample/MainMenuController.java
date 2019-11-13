package sample;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.Window;


import java.io.IOException;
import java.util.ArrayList;

public class MainMenuController {
    public Button minusLayoutButton;
    public Button addLayoutButton;
    public Button playButton;
    public Button quitButton;
    public Button helpButton;
    public ComboBox difficultyBox;
    public ComboBox keyboardBox;
    public Label errorMessage;
    public Label highScore;
    @FXML
    void addLayout() throws IOException
    {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddLayoutUI.fxml"));
        stage.setTitle("");
        Scene newScene = new Scene((Parent)loader.load());
        newScene.getStylesheets().add(getClass().getResource("Stylesheet.css").toExternalForm());
        stage.setScene(newScene);
        stage.show();
        AddLayoutUIController addLayoutUIController = loader.<AddLayoutUIController>getController();
        addLayoutUIController.setEnterAsSubmit();
    }
    @FXML
    void minusLayout()
    {
        if (keyboardBox.getValue() != null)
        {
            TextFileReader tfr = new TextFileReader("scores");
            tfr.deleteLines(keyboardBox.getValue().toString());
            keyboardBox.setItems(FXCollections.observableArrayList("Keyboard Layout"));
        }
    }
    @FXML
    void openHelp() throws IOException
    {
        Parent newRoot = FXMLLoader.load(getClass().getResource("HelpMenu.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Type That Text help");
        Scene newScene = new Scene(newRoot);
        newScene.getStylesheets().add(getClass().getResource("Stylesheet.css").toExternalForm());
        stage.setScene(newScene);
        stage.show();
    }
    @FXML
    void playGame() throws IOException
    {
        if ( difficultyBox.getValue() != null && keyboardBox.getValue() != null )
        {
            Window mainWindow = playButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("GameUI.fxml"));
            mainWindow.getScene().setRoot(loader.load());
            GameUIController gameUIController = loader.<GameUIController>getController();
            gameUIController.difficulty = difficultyBox.getValue().toString();
            gameUIController.layout = keyboardBox.getValue().toString();
            gameUIController.highScore = Integer.parseInt(highScore.getText().split(" ")[2]);
        }
        else
            {
                errorMessage.setVisible(true);
            }
    }
    @FXML
    void quitGame()
    {
        Platform.exit();
    }
    @FXML
    void findLayouts()
    {
        ArrayList<String> layouts = new ArrayList<>();
        TextFileReader tfr = new TextFileReader("scores");
        for (String scoreString:tfr.allLines()
             ) {
            if (! layouts.contains(scoreString.split(" ")[0]))
            {
                layouts.add(scoreString.split(" ")[0]);
            }
        }
        keyboardBox.setItems(FXCollections.observableArrayList(layouts));
    }
    @FXML
    void setDifficulties()
    {
        difficultyBox.setItems(FXCollections.observableArrayList("Words", "Sentences", "Sentences with punctuation"));
    }
    @FXML
    void findPoints()
    {
        if ( difficultyBox.getValue() != null && keyboardBox.getValue() != null )
        {
            TextFileReader tfr = new TextFileReader("scores");
            String[] fullLine = tfr.findLines(keyboardBox.getValue().toString() + " " + difficultyBox.getValue().toString().replace(" ", "_"));
            //System.out.println(keyboardBox.getValue().toString() + " " + difficultyBox.getValue().toString().replace(" ", "_"));
            //System.out.println(fullLine[0]);
            highScore.setText("High score: " + fullLine[0].split(" ")[2]);
        }
        //else System.out.println("ar");
    }
}

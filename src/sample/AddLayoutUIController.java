package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddLayoutUIController {
    public Button submitButton;
    public TextField newLayoutBox;
    public Label alreadyTakenLabel;
    public Label noSpacesLabel;
    public Label emptyLabel;
    public void setEnterAsSubmit()
    {
        newLayoutBox.setOnKeyPressed(
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
    @FXML
    void submitAnswer()
    {
        alreadyTakenLabel.setVisible(false);
        noSpacesLabel.setVisible(false);
        emptyLabel.setVisible(false);
        if (newLayoutBox.getText().contains(" "))
        {
            noSpacesLabel.setVisible(true);
        }
        if (newLayoutBox.getText().equals(""))
        {
            emptyLabel.setVisible(true);
        }
        if (! newLayoutBox.getText().contains(" ") && ! newLayoutBox.getText().equals(""))
        {
            TextFileReader tfr = new TextFileReader("scores");
            try {
                if (tfr.findLines(newLayoutBox.getText())[3] == "extra")
                {
                    tfr.addNewScores(newLayoutBox.getText());
                    Stage stage = (Stage) submitButton.getScene().getWindow();
                    stage.close();
                }
            }
            catch (ArrayIndexOutOfBoundsException oob)
            {
                alreadyTakenLabel.setVisible(true);
            }
        }
    }
}

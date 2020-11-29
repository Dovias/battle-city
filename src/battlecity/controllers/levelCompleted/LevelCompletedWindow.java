package battlecity.controllers.levelCompleted;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class LevelCompletedWindow {
    public Label scoreLabel;
    public Label levelLabel;

    public void navigateToMainMenu(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../mainMenu/MainMenuWindow.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) scoreLabel.getScene().getWindow();

        stage.setScene(new Scene(root));
        stage.show();
    }

    public void setScore(int score) {
        scoreLabel.setText("Score: " + score);
    }

    public void setLevel(int level) {
        levelLabel.setText("Level " + level + " completed");
    }
}

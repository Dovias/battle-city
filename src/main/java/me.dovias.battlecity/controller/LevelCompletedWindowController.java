package me.dovias.battlecity.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class LevelCompletedWindowController {
    public Label scoreLabel;
    public Label levelLabel;

    public void navigateToMainMenu() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/jfx/window/MainMenuWindow.fxml"));
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

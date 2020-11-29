package battlecity.controllers.deathWindow;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class DeathWindow {
    public Label scoreLabel;

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
}

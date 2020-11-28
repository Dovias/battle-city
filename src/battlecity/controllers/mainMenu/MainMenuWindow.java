package battlecity.controllers.mainMenu;

import battlecity.Main;
import battlecity.model.Game;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainMenuWindow {
    public Button startGameBtn;

    private Game game = new Game();

    public void startGame(ActionEvent actionEvent) {
        Parent root = game.createContent();

        Stage stage = (Stage) startGameBtn.getScene().getWindow();

        stage.setScene(new Scene(root));
        stage.show();
    }
}

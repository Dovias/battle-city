package battlecity.controllers.mainMenu;

import battlecity.model.Game;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainMenuWindow {
    public void startGame(ActionEvent actionEvent) {
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();

        Game game = new Game(true);
        Scene scene = game.createContent();

        stage.setScene(scene);
        stage.show();
    }
}

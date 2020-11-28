package battlecity.controllers.mainMenu;

import battlecity.model.Game;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainMenuWindow {
    private Game game;

    public void startGame(ActionEvent actionEvent) {
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();

        Scene scene = game.createContent();

        stage.setScene(scene);
        stage.show();
    }

    public void setGame(Game game) {
        this.game = game;
    }
}

package me.dovias.battlecity.controller;

import me.dovias.battlecity.model.Game;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainMenuWindowController {
    public void startGame(ActionEvent actionEvent) {
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();

        Game game = new Game(true);
        Scene scene = game.createContent();

        stage.setScene(scene);
        stage.show();
    }
}

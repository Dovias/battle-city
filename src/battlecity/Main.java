package battlecity;

import battlecity.controllers.mainMenu.MainMenuWindow;
import battlecity.model.Game;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private Game game = new Game(false);

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = showMainMenu();

        stage.setTitle("Battle city");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private Parent showMainMenu() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("controllers/mainMenu/MainMenuWindow.fxml"));
        Parent root = loader.load();

        MainMenuWindow mainMenuWindow = loader.getController();
        mainMenuWindow.setGame(game);

        return root;
    }
}

package battlecity.model;

import javafx.scene.Parent;
import javafx.scene.layout.Pane;

public class Game {
    private Pane root = new Pane();

    public Parent createContent() {
        root.setPrefSize(800, 800);

        return root;
    }

    public Pane getRoot() {
        return root;
    }

    public void setRoot(Pane root) {
        this.root = root;
    }
}

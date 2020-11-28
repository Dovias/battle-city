package battlecity.model;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Game {
    private Pane root = new Pane();
    private boolean isRunning;
    private Tank player = new Tank(350, 700, 30, 30, "player", Color.BLACK);

    public Game() {
    }

    public Game(boolean isRunning) {
        this.isRunning = isRunning;
    }

    public Scene createContent() {
        isRunning = true;

        root.setPrefSize(800, 800);
        root.getChildren().add(player);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };

        timer.start();

        for (int i = 0; i < 5; i++) {
            Tank t = new Tank(100 + i * 100, 0, 30, 30, "enemy", Color.RED);
            root.getChildren().add(t);
        }

        Scene scene = new Scene(root);

        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            switch (event.getCode()) {
                case A:
                    player.moveLeft();
                    break;
                case S:
                    player.moveDown();
                    break;
                case D:
                    player.moveRight();
                    break;
                case W:
                    player.moveUp();
                    break;
                case SPACE:
                    break;
            }
        });

        return scene;
    }

    private void update() {

    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }
}

package battlecity.model;

import javafx.animation.AnimationTimer;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.stream.Collectors;

public class Game {
    private final Pane root = new Pane();
    private boolean isRunning;
    private Tank player;
    private double t = 0;
    private final int level = 1;
    private final Map map = new Map();

    public Game(boolean isRunning) {
        this.isRunning = isRunning;
    }

    public Scene createContent() {
        isRunning = true;

        root.setPrefSize(GameSettings.applicationWidth, GameSettings.applicationHeight);
        root.setMaxSize(GameSettings.applicationWidth, GameSettings.applicationHeight);
        root.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));

        player = map.loadLevelOne(root);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };

        timer.start();

        Scene scene = new Scene(root);

        // Key inputs
        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            switch (event.getCode()) {
                case A:
                case LEFT:
                    player.moveLeft();
                    break;
                case S:
                case DOWN:
                    player.moveDown();
                    break;
                case D:
                case RIGHT:
                    player.moveRight();
                    break;
                case W:
                case UP:
                    player.moveUp();
                    break;
                case SPACE:
                    shoot(player);
                    break;
            }
        });

        return scene;
    }

    private void shoot(Tank shooter) {
        Bullet bullet = new Bullet(shooter.getTranslateX(), shooter.getTranslateY(), shooter.type, shooter.getDirection());
        root.getChildren().add(bullet);
    }

    private List<Bullet> bullets() {
        return root.getChildren()
                .stream()
                .filter(node -> node.getClass() == Bullet.class)
                .map(node -> (Bullet) node)
                .collect(Collectors.toList());
    }

    private List<Tank> tanks() {
        return root.getChildren()
                .stream()
                .filter(node -> node.getClass() == Tank.class)
                .map(node -> (Tank) node)
                .collect(Collectors.toList());
    }

    private void update() {
        t += 0.016;

        bullets().forEach(bullet -> {
            switch (bullet.type) {
                case "player":
                    bullet.move();
                    tanks().stream().filter(tank -> tank.type.equals("enemy")).forEach(enemy -> {
                        if (bullet.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
                            enemy.dead = true;
                            bullet.dead = true;
                        }
                    });
                    break;
                case "enemy":
                    break;
            }
        });

        root.getChildren().removeIf(node -> {
            if (node instanceof Tank) {
                Tank t = (Tank) node;
                return t.dead;
            } else if (node instanceof Bullet) {
                Bullet b = (Bullet) node;
                return b.dead || isNotInBounds(b.getBoundsInParent());
            }
            return false;
        });
    }

    private boolean isNotInBounds(Bounds bounds) {
        return !bounds.intersects(0, 0, 800, 800);
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }
}

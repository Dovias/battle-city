package me.dovias.battlecity.model;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import me.dovias.battlecity.controller.DeathWindowController;
import me.dovias.battlecity.controller.LevelCompletedWindowController;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Game {
    private final Pane root = new Pane();
    private boolean isRunning;
    private Player player;
    private double t = 0;
    private int tick = GameSettings.minTick;
    private final Map map = new Map();
    private AnimationTimer timer;
    private int enemiesLeft = 0;
    private int enemiesSpawned = 1;

    public Game(boolean isRunning) {
        this.isRunning = isRunning;
    }

    public Scene createContent() {
        isRunning = true;
        enemiesLeft = 5;

        root.setPrefSize(GameSettings.applicationWidth, GameSettings.applicationHeight);
        root.setMaxSize(GameSettings.applicationWidth, GameSettings.applicationHeight);
        root.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));

        player = map.loadLevelOne(root);

        timer = new AnimationTimer() {
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
                case W:
                case UP:
                    player.setMovingDirection(Direction.UP);
                    break;
                case D:
                case RIGHT:
                    player.setMovingDirection(Direction.RIGHT);
                    break;
                case S:
                case DOWN:
                    player.setMovingDirection(Direction.DOWN);
                    break;
                case A:
                case LEFT:
                    player.setMovingDirection(Direction.LEFT);
                    break;
                case SPACE:
                    player.shoot(root, tick);
                    break;
            }
        });

        // Key inputs
        scene.addEventFilter(KeyEvent.KEY_RELEASED, event -> {
            switch (event.getCode()) {
                case W:
                case UP:
                case D:
                case RIGHT:
                case S:
                case DOWN:
                case A:
                case LEFT:
                    player.setMovingDirection(null);
                    break;
            }
        });

        return scene;
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
                .filter(node -> node instanceof Tank)
                .map(node -> (Tank) node)
                .collect(Collectors.toList());
    }

    private List<Player> players() {
        return root.getChildren()
                .stream()
                .filter(node -> node.getClass() == Player.class)
                .map(node -> (Player) node)
                .collect(Collectors.toList());
    }

    private List<Enemy> enemies() {
        return root.getChildren()
                .stream()
                .filter(node -> node.getClass() == Enemy.class)
                .map(node -> (Enemy) node)
                .collect(Collectors.toList());
    }

    private List<Block> blocks() {
        return root.getChildren()
                .stream()
                .filter(node -> node.getClass() == Block.class)
                .map(node -> (Block) node)
                .collect(Collectors.toList());
    }

    private void update() {
        t += 0.016;
        tick += 1;

        if (t > 5 && enemiesSpawned < 5) {
            t = 0;

            // get available spawns
            List<Coordinates> availableSpawns = map.getEnemySpawns().stream().filter(
                    cord -> Collision.isEnemyTankSpawnAvailable(cord, tanks())
            ).toList();

            // select random spawn and spawn enemy if spawn available
            if (!availableSpawns.isEmpty()) {
                int randomNum = ThreadLocalRandom.current().nextInt(0, availableSpawns.size());
                Enemy enemy = new Enemy(availableSpawns.get(randomNum));
                root.getChildren().add(enemy);
                enemiesSpawned += 1;
            }
        }

        tanks().forEach(tank -> {
            if (tick % 6 == 0) {
                // spawn tanks
                if (tank.isSpawning()) {
                    tank.spawn(t);
                }
            }

            // Reset all tanks shooting ability every n ticks
            tank.canShoot(tick);
        });


        if (tick % 6 == 0) {
            players().forEach(player -> { // move player tank
                if (!player.isSpawning()) {
                    if (player.getMovingDirection() != null) {
                        player.move(tanks(), blocks());
                    }
                }
            });

            enemies().forEach(enemy -> { // enemy tanks move or shoot
                if (!enemy.isSpawning()) {
                    if (Collision.canMove(enemy, tanks(), blocks())) {
                        int randNum = ThreadLocalRandom.current().nextInt(0, 5);
                        if (enemy.canShoot(tick) && randNum == 0) {
                            enemy.shoot(root, tick);
                        } else {
                            enemy.move(tanks(), blocks());
                        }
                    } else {
                        enemy.shouldChangeDirection(tanks(), blocks());
                        enemy.move(tanks(), blocks());
                    }
                }
            });
        }

        // bullet update
        bullets().forEach(bullet -> {
            bullet.move();
            switch (bullet.getType()) {
                case "player":
                    enemies().forEach(enemy -> {
                        if (bullet.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
                            if (!enemy.isInvincible()) {
                                enemy.setDead(true);
                                enemyKilled();
                            }
                            bullet.setDead(true);
                        }
                    });
                    break;
                case "enemy":
                    players().forEach(player -> {
                        if (bullet.getBoundsInParent().intersects(player.getBoundsInParent())) {
                            if (!player.isInvincible()) {
                                player.setDead(true);
                                isRunning = false;
                            }
                            bullet.setDead(true);
                        }
                    });
                    break;
            }

            blocks().stream().filter(block -> !block.isBush()).forEach(block -> {
                if (bullet.getBoundsInParent().intersects(block.getBoundsInParent())) {
                    bullet.setDead(true);
                }
            });
        });

        // remove dead things from game
        root.getChildren().removeIf(node -> {
            if (node instanceof Tank tank) {
                return tank.isDead();
            } else if (node instanceof Bullet bullet) {
                return bullet.isDead() || !bullet.getBoundsInParent().intersects(0, 0, 800, 800);
            }
            return false;
        });

        if (tick == GameSettings.maxTick) {
            tick = GameSettings.minTick;
        }

        if (!isRunning) {
            timer.stop();
            playerKilled();
        }

        if (enemiesLeft == 0) {
            timer.stop();
            playerWon();
        }
    }

    private void enemyKilled() {
        player.addScore(100);
        enemiesLeft -= 1;
    }

    private void playerWon() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/jfx/window/LevelCompletedWindow.fxml"));
            Parent r = loader.load();

            LevelCompletedWindowController controller = loader.getController();
            controller.setScore(player.getScore());
            controller.setLevel(1);

            Stage stage = (Stage) root.getScene().getWindow();

            stage.setScene(new Scene(r));
            stage.show();
            r.requestFocus();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void playerKilled() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/jfx/window/DeathWindow.fxml"));
            Parent r = loader.load();

            DeathWindowController controller = loader.getController();
            controller.setScore(player.getScore());

            Stage stage = (Stage) root.getScene().getWindow();

            stage.setScene(new Scene(r));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

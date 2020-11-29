package battlecity.model;

import battlecity.controllers.deathWindow.DeathWindow;
import battlecity.controllers.levelCompleted.LevelCompletedWindow;
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

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Game {
    private final Pane root = new Pane();
    private boolean isRunning;
    private Tank player;
    private double t = 0;
    private int tick = 0;
    private final int level = 1;
    private final Map map = new Map();
    private int score = 0;
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
                case A:
                case LEFT:
                    player.move(Direction.LEFT, tanks(), blocks());
                    break;
                case S:
                case DOWN:
                    player.move(Direction.DOWN, tanks(), blocks());
                    break;
                case D:
                case RIGHT:
                    player.move(Direction.RIGHT, tanks(), blocks());
                    break;
                case W:
                case UP:
                    player.move(Direction.UP, tanks(), blocks());
                    break;
                case SPACE:
                    shoot(player);
                    break;
            }
        });

        return scene;
    }

    private void shoot(Tank shooter) {
        if (shooter.nextShootTick == -1 && !shooter.spawning) {
            Bullet bullet = new Bullet(shooter.getTranslateX(), shooter.getTranslateY(), shooter.type, shooter.getDirection());
            root.getChildren().add(bullet);
            if (shooter.type.equals("player")) {
                shooter.nextShootTick = tick + GameSettings.playerShootDelay;
            } else {
                shooter.nextShootTick = tick + GameSettings.enemyShootDelay;
            }
        }
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

        if (t > 5 && enemiesSpawned <= 5) {
            t = 0;

            // get available spawns
            List<Coordinates> availableSpawns = map.getEnemySpawns().stream().filter(
                    cord -> Collision.isEnemyTankSpawnAvailable(cord, tanks())
            ).collect(Collectors.toList());

            // select random spawn and spawn enemy if spawn available
            if (availableSpawns.size() != 0) {
                int randomNum = ThreadLocalRandom.current().nextInt(0, availableSpawns.size());
                Tank enemy = new Tank(availableSpawns.get(randomNum), "enemy");
                root.getChildren().add(enemy);
                enemiesSpawned += 1;
            }
        }

        tanks().forEach(tank -> {
            if (tick % 6 == 0) {
                // spawn tanks
                if (tank.spawning) {
                    tank.spawn(t);
                }

                // enemy tanks move or shoot
                if (tank.type.equals("enemy") && !tank.spawning) {
                    // tank is stuck
                    // move to some direction
                    if (!Collision.canMove(tank, tanks(), blocks(), tank.getDirection().getDirection())) {
                        boolean moved = false;
                        String oppositeDir = tank.getDirection().getOppositeDirection();
                        String initialDir = tank.getDirection().getDirection();

                        int randNum = ThreadLocalRandom.current().nextInt(0, 2);
                        if (initialDir.equals(Direction.UP) || initialDir.equals(Direction.DOWN)) {
                            if (randNum == 0 && Collision.canMove(tank, tanks(), blocks(), Direction.LEFT)) {
                                tank.move(Direction.LEFT, tanks(), blocks());
                                moved = true;
                            } else if (Collision.canMove(tank, tanks(), blocks(), Direction.RIGHT)) {
                                tank.move(Direction.RIGHT, tanks(), blocks());
                                moved = true;
                            }
                        } else {
                            if (randNum == 0 && Collision.canMove(tank, tanks(), blocks(), Direction.UP)) {
                                tank.move(Direction.UP, tanks(), blocks());
                                moved = true;
                            } else if (Collision.canMove(tank, tanks(), blocks(), Direction.DOWN)) {
                                tank.move(Direction.DOWN, tanks(), blocks());
                                moved = true;
                            }
                        }

                        if (!moved) {
                            tank.move(oppositeDir, tanks(), blocks());
                        }
                    } else {
                        int randNum = ThreadLocalRandom.current().nextInt(0, 5);
                        if (tank.nextShootTick == -1 && randNum == 0) {
                            shoot(tank);
                        } else {
                            tank.move(tank.getDirection().getDirection(), tanks(), blocks());
                        }
                    }
                }
            }

            // Reset all tanks shooting ability every 30 ticks
            if (tank.nextShootTick != -1) {
                tank.canShoot(tick);
            }
        });

        // bullet update
        bullets().forEach(bullet -> {
            bullet.move();
            switch (bullet.type) {
                case "player":
                    tanks().stream().filter(tank -> tank.type.equals("enemy")).forEach(enemy -> {
                        if (bullet.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
                            if (!enemy.invincible) {
                                enemy.dead = true;
                                enemyKilled();
                            }
                            bullet.dead = true;
                        }
                    });
                    break;
                case "enemy":
                    tanks().forEach(tank -> {
                        if (bullet.getBoundsInParent().intersects(tank.getBoundsInParent())) {
                            if (!tank.invincible && tank.type.equals("player")) {
                                tank.dead = true;
                                isRunning = false;
                            }
                            bullet.dead = true;
                        }
                    });
                    break;
            }

            blocks().stream().filter(block -> !block.type.equals("bush")).forEach(block -> {
                if (bullet.getBoundsInParent().intersects(block.getBoundsInParent())) {
                    bullet.dead = true;
                }
            });
        });

        // remove dead things from game
        root.getChildren().removeIf(node -> {
            if (node instanceof Tank) {
                Tank t = (Tank) node;
                return t.dead;
            } else if (node instanceof Bullet) {
                Bullet b = (Bullet) node;
                return b.dead || !b.getBoundsInParent().intersects(0, 0, 800, 800);
            }
            return false;
        });

        if (tick == 125) {
            tick = 1;
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
        score += 100;
        enemiesLeft -= 1;
    }

    private void playerWon() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../controllers/levelCompleted/LevelCompletedWindow.fxml"));
            Parent r = loader.load();

            LevelCompletedWindow levelCompletedWindow = loader.getController();
            levelCompletedWindow.setScore(score);
            levelCompletedWindow.setLevel(level);

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../controllers/deathWindow/DeathWindow.fxml"));
            Parent r = loader.load();

            DeathWindow deathWindow = loader.getController();
            deathWindow.setScore(score);

            Stage stage = (Stage) root.getScene().getWindow();

            stage.setScene(new Scene(r));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

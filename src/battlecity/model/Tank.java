package battlecity.model;

import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

import java.io.File;
import java.util.List;

public class Tank extends Rectangle {
    boolean dead = false;
    boolean invincible = true;
    boolean spawning = true;
    int spawnState = 2;
    final String type;
    private final Direction direction;

    boolean movingLeft = false;
    boolean movingRight = false;
    boolean movingUp = false;
    boolean movingDown = false;

    int nextShootTick = -1;

    public Tank(Coordinates coordinates, String type) {
        super(GameSettings.tankSize, GameSettings.tankSize);
        this.type = type;
        setTranslateX(coordinates.getX());
        setTranslateY(coordinates.getY());

        direction = new Direction(Direction.UP);

        String file = new File("src/battlecity/assets/spawn1.png").toURI().toString();
        Image img = new Image(file);
        setFill(new ImagePattern(img));
    }

    public boolean move(String direction, List<Tank> tanks, List<Block> blocks) {
        changeDirection(direction);
        if (Collision.canMove(this, tanks, blocks, direction)) {
            switch (direction) {
                case Direction.LEFT:
                    moveLeft();
                    break;
                case Direction.RIGHT:
                    moveRight();
                    break;
                case Direction.UP:
                    moveUp();
                    break;
                case Direction.DOWN:
                    moveDown();
                    break;
            }
            return true;
        }
        return false;
    }

    private void moveLeft() {
        setTranslateX(getTranslateX() - GameSettings.moveSize);
    }

    private void moveRight() {
        setTranslateX(getTranslateX() + GameSettings.moveSize);
    }

    private void moveUp() {
        setTranslateY(getTranslateY() - GameSettings.moveSize);
    }

    private void moveDown() {
        setTranslateY(getTranslateY() + GameSettings.moveSize);
    }

    public Direction getDirection() {
        return direction;
    }

    private boolean changeDirection(String direction) {
        if (!this.direction.getDirection().equals(direction)) {
            this.direction.setDirection(direction);
            getTransforms().clear();
            Rotate rotate = new Rotate(this.direction.getAngle(), GameSettings.tankPivotPoint, GameSettings.tankPivotPoint);
            getTransforms().add(rotate);
            return true;
        }
        return false;
    }

    public void spawn(double t) {
        if (t > 2) {
            spawning = false;
            invincible = false;

            // sets asset to tank
            String file = new File("src/battlecity/assets/" + type + "Tank.png").toURI().toString();
            Image img = new Image(file);
            setFill(new ImagePattern(img));
            getTransforms().add(new Rotate(direction.getAngle(), 0, 0));
        } else {
            String file = new File("src/battlecity/assets/spawn" + spawnState + ".png").toURI().toString();
            Image img = new Image(file);
            setFill(new ImagePattern(img));
            spawnState = spawnState == 4 ? 1 : (spawnState + 1);
        }
    }

    public boolean canShoot(int tick) {
        if (nextShootTick > GameSettings.maxTick) {
            int shootDelay = type.equals("player") ? GameSettings.playerShootDelay : GameSettings.enemyShootDelay;
            if (tick > shootDelay + 10) {
                return false;
            } else {
                if (nextShootTick - GameSettings.maxTick < tick) {
                    nextShootTick = -1;
                    return true;
                }
            }
        }

        if (nextShootTick < tick || nextShootTick == -1) {
            nextShootTick = -1;
            return true;
        }

        return false;
    }
}

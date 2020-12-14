package battlecity.model;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

import java.io.File;
import java.util.List;

public class Tank extends Rectangle {
    private boolean dead = false;
    private boolean invincible = true;
    private boolean spawning = true;
    private int spawnState = 2;

    private final String type;
    protected final Direction direction;

    private String movingDirection = null;

    protected int shotDelay = GameSettings.enemyShootDelay;
    private int nextShootTick = -1;

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

    public void move(List<Tank> tanks, List<Block> blocks) {
        changeDirection();
        if (Collision.canMove(this, tanks, blocks)) {
            switch (direction.getDirection()) {
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
        }
    }

    protected void moveLeft() {
        setTranslateX(getTranslateX() - GameSettings.moveSize);
    }

    protected void moveRight() {
        setTranslateX(getTranslateX() + GameSettings.moveSize);
    }

    protected void moveUp() {
        setTranslateY(getTranslateY() - GameSettings.moveSize);
    }

    protected void moveDown() {
        setTranslateY(getTranslateY() + GameSettings.moveSize);
    }

    public Direction getDirection() {
        return direction;
    }

    private boolean changeDirection() {
        if (movingDirection != null && !direction.getDirection().equals(movingDirection)) {
            direction.setDirection(movingDirection);
            getTransforms().clear();
            Rotate rotate = new Rotate(direction.getAngle(), GameSettings.tankPivotPoint, GameSettings.tankPivotPoint);
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

    public void shoot(Pane root, int tick) {
        if (nextShootTick == -1 && !spawning) {
            Bullet bullet = new Bullet(getTranslateX(), getTranslateY(), type, getDirection());
            root.getChildren().add(bullet);
            setNextShootTick(tick + shotDelay);
        }
    }

    public int getNextShootTick() {
        return nextShootTick;
    }

    public void setNextShootTick(int nextShootTick) {
        this.nextShootTick = nextShootTick;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public boolean isInvincible() {
        return invincible;
    }

    public boolean isSpawning() {
        return spawning;
    }

    public String getType() {
        return type;
    }

    public String getMovingDirection() {
        return movingDirection;
    }

    public void setMovingDirection(String movingDirection) {
        this.movingDirection = movingDirection;
    }
}

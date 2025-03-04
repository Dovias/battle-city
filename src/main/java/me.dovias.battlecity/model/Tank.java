package me.dovias.battlecity.model;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

import java.io.InputStream;
import java.util.List;

public class Tank extends Rectangle {
    private boolean dead = false;
    private boolean invincible = true;
    private boolean spawning = true;
    private int spawnState = 2;

    private final String type;
    protected final Direction direction = new Direction(Direction.UP);

    private String movingDirection = null;

    protected int shotDelay = GameSettings.enemyShootDelay;
    private int nextShootTick = -1;

    public Tank(Coordinates coordinates, String type) {
        super(GameSettings.tankSize, GameSettings.tankSize);

        this.type = type;
        setTranslateX(coordinates.getX());
        setTranslateY(coordinates.getY());
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

    private void changeDirection() {
        if (movingDirection != null && !direction.getDirection().equals(movingDirection)) {
            direction.setDirection(movingDirection);
            rotateTankImage();
        }
    }

    public void spawn(double t) {
        if (t > 2) {
            spawning = false;
            invincible = false;

            setTankImage(this.getClass().getResourceAsStream("/assets/" + type + "Tank.png"));
            rotateTankImage();
        } else {
            setTankImage(this.getClass().getResourceAsStream("/assets/spawn" + spawnState + ".png"));
            spawnState = spawnState == 4 ? 1 : (spawnState + 1);
        }
    }

    public boolean canShoot(int tick) {
        if (spawning) {
            return false;
        }

        if (nextShootTick == -1) {
          return true;
        }

        if (nextShootTick < tick && (nextShootTick > GameSettings.maxTick - shotDelay || tick < GameSettings.maxTick - shotDelay)) {
            nextShootTick = -1;
            return true;
        }

        return false;
    }

    public void shoot(Pane root, int tick) {
        if (canShoot(tick)) {
            nextShootTick = calculateNextShootTick(tick, shotDelay);

            Bullet bullet = new Bullet(getTranslateX(), getTranslateY(), type, getDirection());
            root.getChildren().add(bullet);
        }
    }

    private int calculateNextShootTick(int tick, int shotDelay) {
        if (tick + shotDelay >= GameSettings.maxTick) {
            return tick + shotDelay - GameSettings.maxTick;
        }
        return tick + shotDelay;
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

    public String getMovingDirection() {
        return movingDirection;
    }

    public void setMovingDirection(String movingDirection) {
        this.movingDirection = movingDirection;
    }

    private void setTankImage(InputStream stream) {
        this.setFill(new ImagePattern(
            new Image(stream)
        ));
    }

    private void rotateTankImage() {
        getTransforms().clear();
        Rotate rotate = new Rotate(direction.getAngle(), GameSettings.tankPivotPoint, GameSettings.tankPivotPoint);
        getTransforms().add(rotate);
    }
}

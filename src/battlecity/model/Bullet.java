package battlecity.model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Bullet extends Circle {
    private final String type;
    private final Direction direction;

    private boolean dead = false;

    public Bullet(double centerX, double centerY, String type, Direction direction) {
        super(centerX, centerY, GameSettings.bulletSize, Color.WHITE);
        this.type = type;
        this.direction = new Direction(direction);
        switch (this.direction.getDirection()) {
            case Direction.UP:
                setTranslateX(getTranslateX() + GameSettings.tankPivotPoint - GameSettings.bulletPivotPoint);
                break;
            case Direction.DOWN:
                setTranslateX(getTranslateX() + GameSettings.tankPivotPoint - GameSettings.bulletPivotPoint);
                setTranslateY(getTranslateY() + GameSettings.tankSize);
                break;
            case Direction.LEFT:
                setTranslateY(getTranslateY() + GameSettings.tankPivotPoint - GameSettings.bulletPivotPoint);
                break;
            case Direction.RIGHT:
                setTranslateX(getTranslateX() + GameSettings.tankSize);
                setTranslateY(getTranslateY() + GameSettings.tankPivotPoint - GameSettings.bulletPivotPoint);
                break;
        }
    }

    public void move() {
        switch (direction.getDirection()) {
            case Direction.UP:
                setTranslateY(getTranslateY() - GameSettings.bulletSpeed);
                break;
            case Direction.DOWN:
                setTranslateY(getTranslateY() + GameSettings.bulletSpeed);
                break;
            case Direction.LEFT:
                setTranslateX(getTranslateX() - GameSettings.bulletSpeed);
                break;
            case Direction.RIGHT:
                setTranslateX(getTranslateX() + GameSettings.bulletSpeed);
                break;
        }
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public String getType() {
        return type;
    }
}

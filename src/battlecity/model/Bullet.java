package battlecity.model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Bullet extends Circle {
    final String type;
    boolean dead = false;
    final private Direction direction;

    public Bullet(double centerX, double centerY, String type, Direction direction) {
        super(centerX, centerY, 3, Color.WHITE);
        this.type = type;
        this.direction = new Direction(direction);
        switch (this.direction.getDirection()) {
            case Direction.UP:
                setTranslateX(getTranslateX() + 12);
                break;
            case Direction.DOWN:
                setTranslateX(getTranslateX() + 12);
                setTranslateY(getTranslateY() + 26);
                break;
            case Direction.LEFT:
                setTranslateY(getTranslateY() + 12);
                break;
            case Direction.RIGHT:
                setTranslateX(getTranslateX() + 26);
                setTranslateY(getTranslateY() + 12);
                break;
        }
    }

    public void move() {
        switch (direction.getDirection()) {
            case Direction.UP:
                setTranslateY(getTranslateY() - 5);
                break;
            case Direction.DOWN:
                setTranslateY(getTranslateY() + 5);
                break;
            case Direction.LEFT:
                setTranslateX(getTranslateX() - 5);
                break;
            case Direction.RIGHT:
                setTranslateX(getTranslateX() + 5);
                break;
        }
    }
}

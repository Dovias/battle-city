package battlecity.model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tank extends Rectangle {
    boolean dead = false;
    final String type;
    private final Direction direction;

    public Tank(double x, double y, double width, double height, String type, Color color) {
        super(width, height, color);
        this.type = type;
        setTranslateX(x);
        setTranslateY(y);
        direction = new Direction(Direction.UP);
    }

    public void moveLeft() {
        setTranslateX(getTranslateX() - 10);
        direction.setDirection(Direction.LEFT);
    }

    public void moveRight() {
        setTranslateX(getTranslateX() + 10);
        direction.setDirection(Direction.RIGHT);
    }

    public void moveUp() {
        setTranslateY(getTranslateY() - 10);
        direction.setDirection(Direction.UP);
    }

    public void moveDown() {
        setTranslateY(getTranslateY() + 10);
        direction.setDirection(Direction.DOWN);
    }

    public Direction getDirection() {
        return direction;
    }
}

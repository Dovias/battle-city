package battlecity.model;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

import java.io.File;

public class Tank extends Rectangle {
    boolean dead = false;
    final String type;
    private final Direction direction;

    public Tank(double x, double y, String type, Color color) {
        super(26, 26, color);
        this.type = type;
        setTranslateX(x);
        setTranslateY(y);

        direction = new Direction(Direction.UP);

        // sets asset to tank
        String file = new File("src/battlecity/assets/" + type + "Tank.png").toURI().toString();
        Image img = new Image(file);
        setFill(new ImagePattern(img));
        getTransforms().add(new Rotate(direction.getAngle(), 0, 0));
    }

    public void moveLeft() {
        if (!changeDirection(Direction.LEFT)) {
            setTranslateX(getTranslateX() - 8);
        }
    }

    public void moveRight() {
        if (!changeDirection(Direction.RIGHT)) {
            setTranslateX(getTranslateX() + 8);
        }
    }

    public void moveUp() {
        if (!changeDirection(Direction.UP)) {
            setTranslateY(getTranslateY() - 8);
        }
    }

    public void moveDown() {
        if (!changeDirection(Direction.DOWN)) {
            setTranslateY(getTranslateY() + 8);
        }
    }

    public Direction getDirection() {
        return direction;
    }

    private boolean changeDirection(String direction) {
        if (!this.direction.getDirection().equals(direction)) {
            this.direction.setDirection(direction);
            getTransforms().clear();
            getTransforms().add(new Rotate(this.direction.getAngle(), 13, 13));
            return true;
        }
        return false;
    }
}

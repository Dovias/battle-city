package battlecity.model;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

import java.io.File;

public class Tank extends Rectangle {
    boolean dead = false;
    final String type;
    private final Direction direction;

    public Tank(Coordinates coordinates, String type) {
        super(GameSettings.tankSize, GameSettings.tankSize);
        this.type = type;
        setTranslateX(coordinates.getX());
        setTranslateY(coordinates.getY());

        direction = new Direction(Direction.UP);

        // sets asset to tank
        String file = new File("src/battlecity/assets/" + type + "Tank.png").toURI().toString();
        Image img = new Image(file);
        setFill(new ImagePattern(img));
        getTransforms().add(new Rotate(direction.getAngle(), 0, 0));
    }

    public void moveLeft(boolean canMove) {
        changeDirection(Direction.LEFT);
        if (canMove) {
            setTranslateX(getTranslateX() - GameSettings.moveSize);
        }
    }

    public void moveRight(boolean canMove) {
        changeDirection(Direction.RIGHT);
        if (canMove) {
            setTranslateX(getTranslateX() + GameSettings.moveSize);
        }
    }

    public void moveUp(boolean canMove) {
        changeDirection(Direction.UP);
        if (canMove) {
            setTranslateY(getTranslateY() - GameSettings.moveSize);
        }
    }

    public void moveDown(boolean canMove) {
        changeDirection(Direction.DOWN);
        if (canMove) {
            setTranslateY(getTranslateY() + GameSettings.moveSize);
        }
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
}

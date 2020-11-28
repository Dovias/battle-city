package battlecity.model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tank extends Rectangle {
    boolean dead = false;
    final String type;

    public Tank(double x, double y, double width, double height, String type, Color color) {
        super(width, height, color);
        this.type = type;
        setTranslateX(x);
        setTranslateY(y);
    }

    public void moveLeft() {
        setTranslateX(getTranslateX() - 5);
    }

    public void moveRight() {
        setTranslateX(getTranslateX() + 5);
    }

    public void moveUp() {
        setTranslateY(getTranslateY() - 5);
    }

    public void moveDown() {
        setTranslateY(getTranslateY() + 5);
    }
}

package me.dovias.battlecity.model;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Block extends Rectangle {
    private final String type;

    public Block(Coordinates coordinates, String type) {
        super(coordinates.getX(), coordinates.getY(), GameSettings.blockSize, GameSettings.blockSize);
        this.type = type;

        // sets asset to block
        this.setFill(new ImagePattern(
            new Image(this.getClass().getResourceAsStream("/assets/" + type + ".png"))
        ));
    }

    public boolean isBush() {
        return type.equals("bush");
    }
}

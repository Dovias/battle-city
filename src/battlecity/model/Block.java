package battlecity.model;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.File;

public class Block extends Rectangle {
    private final String type;

    public Block(Coordinates coordinates, String type) {
        super(coordinates.getX(), coordinates.getY(), GameSettings.blockSize, GameSettings.blockSize);
        this.type = type;

        // sets asset to block
        String file = new File("src/battlecity/assets/" + type + ".png").toURI().toString();
        Image img = new Image(file);
        setFill(new ImagePattern(img));
    }

    public boolean isBush() {
        return type.equals("bush");
    }
}

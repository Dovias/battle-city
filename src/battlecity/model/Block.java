package battlecity.model;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

import java.io.File;

public class Block extends Rectangle {
    final String type;
    boolean dead = false;

    public Block(int x, int y, String type) {
        super(x, y, GameSettings.blockSize, GameSettings.blockSize);
        this.type = type;

        // sets asset to block
        String file = new File("src/battlecity/assets/" + type + ".png").toURI().toString();
        Image img = new Image(file);
        setFill(new ImagePattern(img));
    }
}

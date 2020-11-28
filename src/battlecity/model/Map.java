package battlecity.model;

import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class Map {
    private ArrayList<Block> brickWalls = new ArrayList<>();

    private void setLevelOneData() {
        brickWalls.add(new Block(26, 800-26,  "wall"));
        brickWalls.add(new Block(26, 800-2*26, "wall"));
    }

    public void loadLevelOne(Pane root) {
        setLevelOneData();

        brickWalls.forEach(block -> root.getChildren().add(block));
    };
}

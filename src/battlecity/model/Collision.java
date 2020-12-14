package battlecity.model;

import javafx.scene.shape.Rectangle;

import java.util.List;
import java.util.stream.Collectors;

public class Collision {

    private static boolean outOfBounds(double x, double y, double w, double h) {
        Rectangle rect = new Rectangle(
                GameSettings.tankSize - 1,
                GameSettings.tankSize - 1,
                GameSettings.applicationWidth - 2 * GameSettings.tankSize + 2,
                GameSettings.applicationHeight - 2 * GameSettings.tankSize + 2
        );
        return !rect.getBoundsInParent().intersects(x, y, w, h);
    }

    public static boolean canMove(Tank tank, List<Tank> tanks, List<Block> blocks, String direction) {
        if (tank.isSpawning()) {
            return false;
        }

        double x = tank.getTranslateX() + 1;
        double y = tank.getTranslateY() + 1;
        double w = GameSettings.tankSize - 2;
        double h = GameSettings.tankSize - 2;
        switch (direction) {
            case Direction.RIGHT:
                x += GameSettings.moveSize;
                break;
            case Direction.LEFT:
                x -= GameSettings.moveSize;
                break;
            case Direction.UP:
                y -= GameSettings.moveSize;
                break;
            case Direction.DOWN:
                y += GameSettings.moveSize;
                break;
        }

        for (Tank t : tanks) {
            if (t.getBoundsInParent().intersects(x, y, w, h) && tank != t) {
                return false;
            }
        }

        blocks = blocks.stream().filter(block -> !block.type.equals("bush")).collect(Collectors.toList());
        for (Block b : blocks) {
            if (b.getBoundsInParent().intersects(x, y, w, h)) {
                return false;
            }
        }

        return !outOfBounds(x, y, w, h);
    }

    public static boolean isEnemyTankSpawnAvailable(Coordinates cord, List<Tank> tanks) {
        double x = cord.getX() + 1;
        double y = cord.getY() + 1;
        double w = GameSettings.tankSize - 2;
        double h = GameSettings.tankSize - 2;

        for (Tank t : tanks) {
            if (t.getBoundsInParent().intersects(x, y, w, h)) {
                return false;
            }
        }

        return !outOfBounds(x, y, w, h);
    }
}

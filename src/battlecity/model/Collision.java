package battlecity.model;

import java.util.List;
import java.util.stream.Collectors;

public class Collision {

    public static boolean canMove(Tank tank, List<Tank> tanks, List<Block> blocks, String direction) {
        double x = tank.getTranslateX() + 1;
        double y = tank.getTranslateY() + 1;
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
            if (t.getBoundsInParent().intersects(x, y, GameSettings.tankSize, GameSettings.tankSize) && tank != t) {
                return false;
            }
        }

        blocks = blocks.stream().filter(block -> !block.type.equals("bush")).collect(Collectors.toList());
        for (Block b: blocks) {
            if (b.getBoundsInParent().intersects(x, y, GameSettings.tankSize - 2, GameSettings.tankSize - 2)) {
                return false;
            }
        }

        return true;
    }
}

package battlecity.model;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Enemy extends Tank {

    public Enemy(Coordinates coordinates) {
        super(coordinates, "enemy");
    }

    public void shouldChangeDirection(List<Tank> tanks, List<Block> blocks) {
        String oppositeDir = direction.getOppositeDirection();
        String initialDir = direction.getDirection();

        int randNum = ThreadLocalRandom.current().nextInt(0, 2);
        if (initialDir.equals(Direction.UP) || initialDir.equals(Direction.DOWN)) {
            if (randNum == 0 && Collision.canMove(this, tanks, blocks, Direction.LEFT)) {
                setMovingDirection(Direction.LEFT);
                return;
            } else if (Collision.canMove(this, tanks, blocks, Direction.RIGHT)) {
                setMovingDirection(Direction.RIGHT);
                return;
            }
        } else {
            if (randNum == 0 && Collision.canMove(this, tanks, blocks, Direction.UP)) {
                setMovingDirection(Direction.UP);
                return;
            } else if (Collision.canMove(this, tanks, blocks, Direction.DOWN)) {
                setMovingDirection(Direction.DOWN);
                return;
            }
        }

        System.out.println("setting opposite direction: " + oppositeDir);
        setMovingDirection(oppositeDir);
    }
}

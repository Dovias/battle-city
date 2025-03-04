package me.dovias.battlecity.model;

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
            if (randNum == 0) {
                setMovingDirection(Direction.LEFT);
                if (Collision.canMove(this, tanks, blocks)) {
                    return;
                } else {
                    setMovingDirection(Direction.RIGHT);
                    if (Collision.canMove(this, tanks, blocks)) {
                        return;
                    }
                }
            } else {
                setMovingDirection(Direction.RIGHT);
                if (Collision.canMove(this, tanks, blocks)) {
                    return;
                } else {
                    setMovingDirection(Direction.LEFT);
                    if (Collision.canMove(this, tanks, blocks)) {
                        return;
                    }
                }
            }
        } else {
            if (randNum == 0) {
                setMovingDirection(Direction.UP);
                if (Collision.canMove(this, tanks, blocks)) {
                    return;
                } else {
                    setMovingDirection(Direction.DOWN);
                    if (Collision.canMove(this, tanks, blocks)) {
                        return;
                    }
                }
            } else {
                setMovingDirection(Direction.DOWN);
                if (Collision.canMove(this, tanks, blocks)) {
                    return;
                } else {
                    setMovingDirection(Direction.UP);
                    if (Collision.canMove(this, tanks, blocks)) {
                        return;
                    }
                }
            }
        }

        setMovingDirection(oppositeDir);
    }
}

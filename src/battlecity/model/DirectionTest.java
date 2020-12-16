package battlecity.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DirectionTest {
    @Test
    public void directionIsUp() {
        Direction tester = new Direction(Direction.UP);
        assertEquals(Direction.UP, tester.getDirection());
    }
    @Test
    public void directionIsUpAngleIsCorrect() {
        Direction tester = new Direction(Direction.UP);
        assertEquals(0, tester.getAngle());
    }
    @Test
    public void directionIsUpOppositeDirectionIsCorrect() {
        Direction tester = new Direction(Direction.UP);
        assertEquals(Direction.DOWN, tester.getOppositeDirection());
    }

    @Test
    public void directionIsDown() {
        Direction tester = new Direction(Direction.DOWN);
        assertEquals(Direction.DOWN, tester.getDirection());
    }
    @Test
    public void directionIsDownAngleIsCorrect() {
        Direction tester = new Direction(Direction.DOWN);
        assertEquals(180, tester.getAngle());
    }
    @Test
    public void directionIsDownOppositeDirectionIsCorrect() {
        Direction tester = new Direction(Direction.DOWN);
        assertEquals(Direction.UP, tester.getOppositeDirection());
    }

    @Test
    public void directionIsRight() {
        Direction tester = new Direction(Direction.RIGHT);
        assertEquals(Direction.RIGHT, tester.getDirection());
    }
    @Test
    public void directionIsRightAngleIsCorrect() {
        Direction tester = new Direction(Direction.RIGHT);
        assertEquals(90, tester.getAngle());
    }
    @Test
    public void directionIsRightOppositeDirectionIsCorrect() {
        Direction tester = new Direction(Direction.RIGHT);
        assertEquals(Direction.LEFT, tester.getOppositeDirection());
    }

    @Test
    public void directionIsLeft() {
        Direction tester = new Direction(Direction.LEFT);
        assertEquals(Direction.LEFT, tester.getDirection());
    }
    @Test
    public void directionIsLeftAngleIsCorrect() {
        Direction tester = new Direction(Direction.LEFT);
        assertEquals(270, tester.getAngle());
    }
    @Test
    public void directionIsLeftOppositeDirectionIsCorrect() {
        Direction tester = new Direction(Direction.LEFT);
        assertEquals(Direction.RIGHT, tester.getOppositeDirection());
    }
}
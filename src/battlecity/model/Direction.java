package battlecity.model;

public final class Direction {
    public static final String UP = "UP";
    public static final String DOWN = "DOWN";
    public static final String LEFT = "LEFT";
    public static final String RIGHT = "RIGHT";
    private String direction;
    private String oppositeDirection;
    private int angle;

    public Direction(Direction direction) {
        this.direction = direction.getDirection();
        setAngle();
    }

    public Direction(String direction) {
        this.direction = direction;
        setAngle();
    }

    private void setAngle() {
        switch (direction) {
            case UP:
                angle = 0;
                oppositeDirection = DOWN;
                break;
            case DOWN:
                angle = 180;
                oppositeDirection = UP;
                break;
            case RIGHT:
                angle = 90;
                oppositeDirection = LEFT;
                break;
            case LEFT:
                angle = 270;
                oppositeDirection = RIGHT;
                break;
        }
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
        setAngle();
    }

    public int getAngle() {
        return angle;
    }

    public String getOppositeDirection() {
        return oppositeDirection;
    }
}

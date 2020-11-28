package battlecity.model;

public final class Direction {
    public static final String UP = "UP";
    public static final String DOWN = "DOWN";
    public static final String LEFT = "LEFT";
    public static final String RIGHT = "RIGHT";
    private String direction;
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
                break;
            case DOWN:
                angle = 180;
                break;
            case RIGHT:
                angle = 90;
                break;
            case LEFT:
                angle = 270;
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
}

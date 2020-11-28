package battlecity.model;

public final class Direction {
    public static final String UP = "UP";
    public static final String DOWN = "DOWN";
    public static final String LEFT = "LEFT";
    public static final String RIGHT = "RIGHT";
    private String direction;

    public Direction(Direction direction) {
        this.direction = direction.getDirection();
    }

    public Direction(String direction) {
        this.direction = direction;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}

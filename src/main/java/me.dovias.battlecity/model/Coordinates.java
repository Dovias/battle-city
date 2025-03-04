package me.dovias.battlecity.model;

public class Coordinates {
    private final int blockXCoordinate;
    private final int blockYCoordinate;

    public Coordinates(int blockXCoordinate, int blockYCoordinate) {
        this.blockXCoordinate = blockXCoordinate;
        this.blockYCoordinate = blockYCoordinate;
    }

    public int getX() {
        return blockXCoordinate * GameSettings.blockSize;
    }

    public int getY() {
        return blockYCoordinate * GameSettings.blockSize;
    }
}

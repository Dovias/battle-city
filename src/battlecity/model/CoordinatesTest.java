package battlecity.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoordinatesTest {

    @Test
    public void getX_bothPositiveNumbers_success() {
        Coordinates tester = new Coordinates(2, 2);
        assertEquals(2 * GameSettings.blockSize, tester.getX());
    }

    @Test
    public void getX_bothNegativeNumbers_success() {
        Coordinates tester = new Coordinates(-2, -2);
        assertEquals(-2 * GameSettings.blockSize, tester.getX());
    }

    @Test
    public void getX_zero_success() {
        Coordinates tester = new Coordinates(0, 0);
        assertEquals(0, tester.getX());
    }

}
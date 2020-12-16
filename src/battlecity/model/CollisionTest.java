package battlecity.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CollisionTest {

    @Test
    public void isEnemyTankSpawnAvailable_emptyCoordinates_success() {
        Coordinates coordinates = new Coordinates(0, 0);
        List<Tank> tanks = new ArrayList<Tank>() {{
            add(new Tank(new Coordinates(1,1), "enemy"));
            add(new Tank(new Coordinates(2,2), "enemy"));
            add(new Tank(new Coordinates(3,3), "enemy"));
        }};

        assertTrue(Collision.isEnemyTankSpawnAvailable(coordinates, tanks));
    }

    @Test
    public void isEnemyTankSpawnAvailable_tankInCoordinates_fail() {
        Coordinates coordinates = new Coordinates(0, 0);
        List<Tank> tanks = new ArrayList<Tank>() {{
            add(new Tank(coordinates, "enemy"));
            add(new Tank(new Coordinates(2,2), "enemy"));
            add(new Tank(new Coordinates(3,3), "enemy"));
        }};

        assertFalse(Collision.isEnemyTankSpawnAvailable(coordinates, tanks));
    }

}
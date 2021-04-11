package battlecity.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TankTest {
    private final Tank tank = new Tank(new Coordinates(0, 0), "player");

    @Test
    public void createdTankIsSpawning() {
        assertTrue(tank.isSpawning());
    }

    @Test
    public void spawningTankIsInvincible() {
        assertTrue(tank.isSpawning() && tank.isInvincible());
    }
}
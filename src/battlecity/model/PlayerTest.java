package battlecity.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    private final Player player = new Player(new Coordinates(0, 0));

    @Test
    public void playerAddScore() {
        player.addScore(100);
        player.addScore(200);
        player.addScore(300);
        assertEquals(600, player.getScore());
    }
}
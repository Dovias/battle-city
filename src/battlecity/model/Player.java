package battlecity.model;

public class Player extends Tank {
    private int score = 0;

    public Player(Coordinates coordinates) {
        super(coordinates, "player");
        shotDelay = GameSettings.playerShootDelay;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int score) {
        this.score += score;
    }
}

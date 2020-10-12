package battlecity;

public class GameLevel {

    private GameMap gameMap;

    private Player player;

    private boolean render = true;

    public GameLevel(GameMap map, Player player) {
        setGameMap(map);
        setPlayer(player);
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    public void setGameMap(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public boolean getRender() {
        return render;
    }

    public void setRender(boolean render) {
        this.render = render;
    }
}


package battlecity;

public class GameRules {

    private GameLevel level;

    private boolean gameOver = false;

    public GameRules(GameLevel level) {
        this.level = level;
    }

    public void processUserInput(int key) {
        switch (key) {
            case 'a':
                if (level.getGameMap().isAvailable(level.getPlayer().getPosition().left())) {
                    System.out.println(level.getGameMap().isAvailable(level.getPlayer().getPosition().left()));
                    level.getPlayer().setGun(level.getPlayer().getGun().left());
                    level.getPlayer().setPosition(level.getPlayer().getPosition().left());
                } else {
                    level.setRender(false);
                    System.out.println("Invalid move!");
                }
                break;
            case 'q':
                level.setRender(false);
                System.out.println("Game over!");
                setGameOver(true);
                break;
            default:
                level.setRender(false);
                System.out.println("Invalid move!");
        }
    }

//		case 'd':
//			if (level.getGameMap().isAvailable(level.getPacman().getPosition().right()))
//				level.getPacman().setPosition( level.getPacman().getPosition().right());
//			break;
//		case 'w':
//			if (level.getGameMap().isAvailable(level.getPacman().getPosition().up()))
//				level.getPacman().setPosition( level.getPacman().getPosition().up());
//			break;
//		case 's':
//			if (level.getGameMap().isAvailable(level.getPacman().getPosition().down()))
//				level.getPacman().setPosition( level.getPacman().getPosition().down());
//			break;

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }
}

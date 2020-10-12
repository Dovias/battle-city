package battlecity;

public class GameRendered {

	public void renderMap(GameLevel gameLevel) {
		if (gameLevel.getRender()) {
			for (int y = 0; y < gameLevel.getGameMap().width(); y++) {
				for (int x = 0; x < gameLevel.getGameMap().height(); x++) {
					if (gameLevel.getPlayer().getPosition().at(x, y))
						System.out.print("O");
					else if (gameLevel.getPlayer().getGun().at(x, y))
						System.out.print("-");
					else if (gameLevel.getGameMap().isWall(y, x))
						System.out.print("#");
					else if (gameLevel.getGameMap().isAvailable(y, x))
						System.out.print(" ");
				}
				System.out.println();
			}
		} else {
			gameLevel.setRender(true);
		}
	}
}

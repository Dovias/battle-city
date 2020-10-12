package battlecity;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        GameMap map = new GameMap();

        Player player = new Player( new Position(3,5) );

        GameLevel level = new GameLevel(map, player);

        GameRendered renderer = new GameRendered();

        GameRules gameRules = new GameRules(level);

        ConsoleInput consoleInput = new ConsoleInput();

        while (!gameRules.isGameOver()) {

            renderer.renderMap(level);

            int key = consoleInput.readConsoleInput();

            gameRules.processUserInput(key);

//            gameRules.moveGhosts();

            System.out.println(key);
            System.out.println(gameRules.isGameOver());

//            gameRules.setGameOver(true);

        }

        System.out.println("Press any key to continue");
        System.in.read();
    }
}

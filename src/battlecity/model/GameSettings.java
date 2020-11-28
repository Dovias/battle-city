package battlecity.model;

public class GameSettings {
    private boolean isRunning;

    public GameSettings(boolean isRunning) {
        this.isRunning = isRunning;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }
}

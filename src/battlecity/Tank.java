package battlecity;

public class Tank {

    private Position position;
    private Position gun;

    public Tank(Position pos) {
        this.position = pos;
        this.gun = new Position(pos.getX() + 1, pos.getY());
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Position getGun() {
        return gun;
    }

    public void setGun(Position gun) {
        this.gun = gun;
    }
}

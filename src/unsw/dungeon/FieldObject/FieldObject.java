package unsw.dungeon.FieldObject;

import unsw.dungeon.Entity;

public abstract class FieldObject extends Entity {
    private boolean isObstacle;

    public FieldObject(int x, int y, boolean isObstacle) {
        super(x, y);
        this.isObstacle = isObstacle;
    }

    public boolean isObstacle() {
        return isObstacle;
    }

    public void setObstacle(boolean obstacle) {
        isObstacle = obstacle;
    }
}

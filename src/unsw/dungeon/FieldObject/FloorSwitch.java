package unsw.dungeon.FieldObject;

import unsw.dungeon.Character.Character;
import unsw.dungeon.Entity;
import unsw.dungeon.Observer;

public class FloorSwitch extends FieldObject implements Observer {
    private boolean isTriggered;

    public FloorSwitch(int x, int y) {
        super(x, y, false);
        this.isTriggered = false;
    }

    private void trigger() {
        this.isTriggered = true;
    }

    private void deactivate() {
        this.isTriggered = false;
    }

    public boolean isTriggered() {
        return isTriggered;
    }

    @Override
    public void interact(Character character) {

    }

    @Override
    public void update(Entity entity) {
        if (entity instanceof Boulder) {
            if (entity.isSamePosition(getX(), getY()))
                trigger();
            else if (this.isTriggered)
                deactivate();
        }
    }
}

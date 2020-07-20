package unsw.dungeon.FieldObject;

import unsw.dungeon.Character.Character;
import unsw.dungeon.Dungeon;
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
        int[] pos = character.getFacingPosition();
        for (Entity entity : character.getDungeon().getEntities(getX(), getY())) {
            if (entity instanceof Boulder && !((Boulder) entity).isObstacle() && isSamePosition(pos[0], pos[1])) {
                deactivate();
            }
        }
    }

    @Override
    public void update(Entity entity) {
        if (entity instanceof Boulder) {
            if (entity.isSamePosition(getX(), getY()))
                trigger();
            for (Observer observer : ((Boulder) entity).getObservers()) {
                if (observer instanceof Dungeon)
                    observer.update(this);
            }
        }
    }
}

package unsw.dungeon.FieldObject;

import unsw.dungeon.Character.Character;
import unsw.dungeon.Character.Player;
import unsw.dungeon.Entity;
import unsw.dungeon.Observer;

// Sample for testing DungeonLoader and stop IDE for complaining
public class Portal extends FieldObject implements Observer {
    private Portal associatedPortal;
    private boolean isFunctioning;

    public Portal(int x, int y) {
        super(x, y, false);
        isFunctioning = true;
    }

    @Override
    public void interact(Character character) {

    }

    private void teleport(Character character) {
        if (!isFunctioning)
            return;
        int oldX = character.getX(), oldY = character.getY();
        associatedPortal.setFunctioning(false);
        character.x().set(associatedPortal.getX());
        character.y().set(associatedPortal.getY());
        character.getDungeon().changeEntityPosition(oldX, oldY, character);
    }

    public Portal getAssociatedPortal() {
        return associatedPortal;
    }

    public void setAssociatedPortal(Portal associatedPortal) {
        this.associatedPortal = associatedPortal;
    }

    public void setFunctioning(boolean functioning) {
        isFunctioning = functioning;
    }

    @Override
    public void update(Entity entity) {
        if (entity instanceof Character && entity.isSamePosition(getX(), getY()))
            teleport((Character) entity);
        else
            isFunctioning = true;
    }
}

package unsw.dungeon.FieldObject;

import unsw.dungeon.Character.Character;
import unsw.dungeon.Character.Enemy;
import unsw.dungeon.Entity;
import unsw.dungeon.Enum.CharacterStatus;
import unsw.dungeon.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class Portal extends FieldObject implements Observer {
    private Portal associatedPortal;
    private boolean isFunctioning;

    public Portal(int x, int y) {
        super(x, y, false);
        isFunctioning = true;
    }

    @Override
    public void interact(Character character) {
        if (character instanceof Enemy)
            ((Enemy) character).clearPath();
    }

    private void teleport(Character character) {
        if (!isFunctioning)
            return;
        int oldX = character.getX(), oldY = character.getY();
        associatedPortal.setFunctioning(false);
        character.setPosition(associatedPortal.getX(), associatedPortal.getY());
        character.getDungeon().changeEntityPosition(oldX, oldY, character);
        if (character instanceof Enemy)
            ((Enemy) character).trackPlayer();
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
        if (entity instanceof Character && entity.isSamePosition(getX(), getY())) {
            interact((Character) entity);
            teleport((Character) entity);
        } else {
            isFunctioning = true;
        }
    }
}

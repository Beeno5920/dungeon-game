package unsw.dungeon.Character;

import org.w3c.dom.CharacterData;
import unsw.dungeon.Dungeon;
import unsw.dungeon.Entity;
import unsw.dungeon.Enum.CharacterStatus;
import unsw.dungeon.Enum.Orientation;
import unsw.dungeon.FieldObject.FieldObject;
import unsw.dungeon.Observer;

import java.util.List;
import java.util.ArrayList;

public abstract class Character extends Entity {
    private Dungeon dungeon;
    private CharacterStatus characterStatus;
    private Orientation orientation;

    public Character(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.dungeon = dungeon;
        characterStatus = CharacterStatus.NORMAL;
        orientation = Orientation.DOWN;
    }

    public boolean canMoveTo(int x, int y) {
        if (x < 0 || x >= dungeon.getWidth() || y < 0 || y >= dungeon.getHeight() || characterStatus.equals(CharacterStatus.DEAD))
            return false;
        boolean canMove = true;
        for (Entity entity : dungeon.getEntities(x, y)) {
            if (entity instanceof FieldObject)
                ((FieldObject) entity).interact(this);
            if (entity instanceof FieldObject && ((FieldObject) entity).isObstacle()) {
                canMove = false;
                break;
            }
        }
        return canMove;
    }

    public abstract void notifyAllObservers();

    public void moveUp() {
        orientation = Orientation.UP;
        int y = getY();
        if (canMoveTo(getX(), getY() - 1)) {
            y().set(getY() - 1);
            dungeon.changeEntityPosition(getX(), y, this);
        }
        notifyAllObservers();
    }

    public void moveDown() {
        orientation = Orientation.DOWN;
        int y = getY();
        if (canMoveTo(getX(), getY() + 1)) {
            y().set(getY() + 1);
            dungeon.changeEntityPosition(getX(), y, this);
        }
        notifyAllObservers();
    }

    public void moveLeft() {
        orientation = Orientation.LEFT;
        int x = getX();
        if (canMoveTo(getX() - 1, getY())) {
            x().set(getX() - 1);
            dungeon.changeEntityPosition(x, getY(), this);
        }
        notifyAllObservers();
    }

    public void moveRight() {
        orientation = Orientation.RIGHT;
        int x = getX();
        if (canMoveTo(getX() + 1, getY())) {
            x().set(getX() + 1);
            dungeon.changeEntityPosition(x, getY(), this);
        }
        notifyAllObservers();
    }

    public int[] getPosition() {
        return new int[] {getX(), getY()};
    }

    public int[] getFacingPosition() {
        int[] facingPosition = getPosition();
        if (orientation == Orientation.UP)
            facingPosition[1]--;
        else if (orientation == Orientation.DOWN)
            facingPosition[1]++;
        else if (orientation == Orientation.LEFT)
            facingPosition[0]--;
        else
            facingPosition[0]++;
        return facingPosition;
    }

    public Dungeon getDungeon() {
        return dungeon;
    }

    public CharacterStatus getCharacterStatus() {
        return characterStatus;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setCharacterStatus(CharacterStatus characterStatus) {
        this.characterStatus = characterStatus;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }
}

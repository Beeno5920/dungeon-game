package unsw.dungeon.FieldObject;

import unsw.dungeon.Character.Character;
import unsw.dungeon.Character.Player;
import unsw.dungeon.Item.Key;

import java.util.Objects;

// Sample for testing DungeonLoader and stop IDE for complaining
public class Door extends FieldObject {
    private Key key;

    public Door(int x, int y) {
        super(x, y, true);
    }

    @Override
    public void interact(Character character) {

    }

    public boolean unlock(Key key) {
        if (!key.equals(this.key))
            return false;
        setObstacle(false);
        return true;
    }

    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }
}

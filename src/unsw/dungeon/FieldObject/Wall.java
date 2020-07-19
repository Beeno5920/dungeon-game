package unsw.dungeon.FieldObject;

import unsw.dungeon.Character.Character;
import unsw.dungeon.Entity;

public class Wall extends FieldObject {

    public Wall(int x, int y) {
        super(x, y, true);
    }

    @Override
    public void interact(Character character) {

    }
}

package unsw.dungeon.FieldObject;

import unsw.dungeon.Character.Character;

public class Exit extends FieldObject {
    public Exit(int x, int y) {
        super(x, y, false);
    }

    @Override
    public void interact(Character character) {

    }
}

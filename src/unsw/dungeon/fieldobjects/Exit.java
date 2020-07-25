package unsw.dungeon.fieldobjects;

import unsw.dungeon.characters.Character;

public class Exit extends FieldObject {
    public Exit(int x, int y) {
        super(x, y, false);
    }

    @Override
    public void interact(Character character) {

    }
}

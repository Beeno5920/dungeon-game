package unsw.dungeon.fieldobjects;

import unsw.dungeon.characters.Character;

public class Wall extends FieldObject {

    public Wall(int x, int y) {
        super(x, y, true);
    }

    @Override
    public void interact(Character character) {

    }
}

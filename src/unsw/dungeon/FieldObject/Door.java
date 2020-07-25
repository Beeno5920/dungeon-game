package unsw.dungeon.FieldObject;

import javafx.scene.image.Image;
import unsw.dungeon.Character.Character;
import unsw.dungeon.Item.Key;

import java.io.File;

public class Door extends FieldObject {
    private Key key;
    private final Image openedDoorImage = new Image((new File("images/open_door.png").toURI().toString()));

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
        setImage(openedDoorImage);
        return true;
    }

    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }
}

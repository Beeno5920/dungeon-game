package test;

import org.junit.jupiter.api.Test;
import unsw.dungeon.Enum.ItemCategory;
import unsw.dungeon.FieldObject.Door;
import unsw.dungeon.Item.Key;

import static org.junit.jupiter.api.Assertions.*;

public class DoorTest extends EntityTest {
    @Test
    void testDoor() {
        int x = player.getX(), y = player.getY();
        Door door = new Door(x + 1, y);
        Key key = new Key(x, y);
        door.setKey(key);
        key.setDoor(door);
        dungeon.addEntity(x + 1, y, door);
        dungeon.addEntity(x, y, key);

        player.pickUp();
        player.moveRight();
        assertTrue(player.isSamePosition(x, y));

        player.useItem(ItemCategory.KEY);
        player.moveRight();
        assertTrue(player.isSamePosition(door.getX(), door.getY()));
    }
}

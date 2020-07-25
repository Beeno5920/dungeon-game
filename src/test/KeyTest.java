package test;

import unsw.dungeon.Enum.ItemCategory;
import unsw.dungeon.FieldObject.Door;
import unsw.dungeon.Item.Key;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class KeyTest extends EntityTest {
    @Test
    void testUseKeyOnCorrectDoor() {
        int x = player.getX(), y = player.getY();
        Key key = new Key(x, y);
        Door door = new Door(x + 1, y);
        key.setDoor(door);
        door.setKey(key);

        dungeon.addEntity(x, y, key);
        dungeon.addEntity(x + 1, y, door);

        assertNotNull(player.pickUp());
        assertTrue(player.useItem(ItemCategory.KEY));
        assertTrue(door.isObstacle());

        player.moveRight();
        assertTrue(player.useItem(ItemCategory.KEY));
        assertFalse(door.isObstacle());
        assertFalse(player.useItem(ItemCategory.KEY));
    }

    @Test
    void testUseKeyOnIncorrectDoor() {
        int x = player.getX(), y = player.getY();
        // key1 <-> door1; key2 <-> door2
        Key key1 = new Key(x, y);
        Key key2 = new Key(x + 1, y + 1);
        Door door1 = new Door(x + 2, y + 1);
        Door door2 = new Door(x + 1, y);
        key1.setDoor(door1);
        key2.setDoor(door2);
        door1.setKey(key1);
        door2.setKey(key2);

        dungeon.addEntity(x, y, key1);
        dungeon.addEntity(x + 1, y + 1, key2);
        dungeon.addEntity(x + 2, y + 1, door1);
        dungeon.addEntity(x + 1, y, door2);
        player.moveRight();

        assertTrue(player.isSamePosition(x, y));
        assertNotNull(player.pickUp());
        assertTrue(door2.isObstacle());
        assertTrue(player.useItem(ItemCategory.KEY));
        assertTrue(door2.isObstacle());
        assertTrue(player.useItem(ItemCategory.KEY));
    }
}

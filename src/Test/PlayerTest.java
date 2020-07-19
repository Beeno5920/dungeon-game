package unsw.dungeon.Test;

import unsw.dungeon.Enum.ItemCategory;
import unsw.dungeon.Item.InvincibilityPotion;
import unsw.dungeon.Item.Key;
import unsw.dungeon.Item.Sword;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest extends CharacterTest {
    @Test
    void testItemPickUp() {
        int x = player.getX(), y = player.getY();

        dungeon.addEntity(x, y, new Sword(x, y));
        assertNotNull(player.pickUp());

        dungeon.addEntity(x + 1, y, new Key(x + 1, y));
        player.moveRight();
        assertNotNull(player.pickUp());

        dungeon.addEntity(x + 1, y + 1, new InvincibilityPotion(x + 1, y + 1));
        player.moveDown();
        assertNotNull(player.pickUp());
    }

    @Test
    void pickMoreThanLimit() {
        int x = player.getX(), y = player.getY();

        dungeon.addEntity(x, y, new Sword(x, y));
        assertNotNull(player.pickUp());
        assertEquals(dungeon.getEntities(x, y).size(), 1);

        dungeon.addEntity(x, y, new Sword(x, y));
        assertNull(player.pickUp());
        assertEquals(dungeon.getEntities(x, y).size(), 2);

        player.moveRight();
        x = player.getX();
        y = player.getY();

        dungeon.addEntity(x, y, new Key(x, y));
        assertNotNull(player.pickUp());
        assertEquals(dungeon.getEntities(x, y).size(), 1);

        dungeon.addEntity(x, y, new Key(x, y));
        assertNull(player.pickUp());
        assertEquals(dungeon.getEntities(x, y).size(), 2);
    }

    @Test
    void testUseItem() {
        int x = player.getX(), y = player.getY();

        player.discardAllItems();

        dungeon.addEntity(x, y, new Sword(x, y));
        player.pickUp();
        assertTrue(player.useItem(ItemCategory.SWORD));

        dungeon.addEntity(x, y, new Key(x, y));
        player.pickUp();
        assertTrue(player.useItem(ItemCategory.KEY));
    }
}

package test;

import org.junit.jupiter.api.Test;
import unsw.dungeon.characters.Enemy;
import unsw.dungeon.enums.CharacterStatus;
import unsw.dungeon.enums.ItemCategory;
import unsw.dungeon.items.Sword;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SwordTest extends EntityTest {
    @Test
    void testKillAnEnemy() {
        int x = player.getX(), y = player.getY();
        Enemy enemy = new Enemy(dungeon, x, y + 1);

        dungeon.addEntity(x, y, new Sword(x, y));
        dungeon.addEntity(x, y + 1, enemy);

        int[] position = player.getFacingPosition();
        assertEquals(enemy.getX(), position[0]);
        assertEquals(enemy.getY(), position[1]);

        assertNotNull(player.pickUp());
        assertTrue(player.useItem(ItemCategory.SWORD));
        assertEquals(CharacterStatus.DEAD, enemy.getCharacterStatus());
        assertFalse(dungeon.getEntities(position[0], position[1]).contains(enemy));
    }

    @Test
    void testHitNothingTenTimes() {
        int x = player.getX(), y = player.getY();

        dungeon.addEntity(x, y, new Sword(x, y));
        assertNotNull(player.pickUp());

        for (int i = 0; i < 10; i++)
            assertTrue(player.useItem(ItemCategory.SWORD));
    }

    @Test
    void testKillFiveEnemies() {
        // Assumed that the player can kill at most one enemy per hit
        int x = player.getX(), y = player.getY();
        List<Enemy> enemies = new ArrayList<>();

        dungeon.addEntity(x, y, new Sword(x, y));
        for (int i = 0; i < 5; i++) {
            Enemy enemy = new Enemy(dungeon, x, y + 1);
            dungeon.addEntity(x, y + 1, enemy);
            enemies.add(enemy);
        }

        assertNotNull(player.pickUp());

        for (int i = 0; i < 5; i++) {
            assertTrue(player.useItem(ItemCategory.SWORD));
            assertEquals(CharacterStatus.DEAD, enemies.get(i).getCharacterStatus());
        }

        assertEquals(dungeon.getEntities(x, y + 1).size(), 0);
        assertFalse(player.useItem(ItemCategory.SWORD));
    }
}

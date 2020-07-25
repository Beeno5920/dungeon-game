package test;

import unsw.dungeon.Enum.CharacterStatus;
import unsw.dungeon.Item.InvincibilityPotion;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InvincibilityPotionTest extends EntityTest {
    @Test
    void testOnPick() {
        int x = player.getX(), y = player.getY();

        InvincibilityPotion invincibilityPotion = new InvincibilityPotion(x, y);
        dungeon.addEntity(x, y, invincibilityPotion);
        assertEquals(CharacterStatus.NORMAL, player.getCharacterStatus());
        assertNotNull(player.pickUp());
        for (int i = 0; i < 5; i++) {
            assertEquals(CharacterStatus.INVINCIBLE, player.getCharacterStatus());
            invincibilityPotion.tick((i));
        }
        invincibilityPotion.tick(5);
        assertEquals(CharacterStatus.NORMAL, player.getCharacterStatus());
    }
}

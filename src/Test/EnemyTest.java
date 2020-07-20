package Test;

import unsw.dungeon.Character.Enemy;
import unsw.dungeon.Enum.CharacterStatus;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EnemyTest extends CharacterTest {
    Enemy enemy;

    public EnemyTest() {
        int[] playerPosition = player.getPosition();
        int x = playerPosition[0] + 5, y = playerPosition[1];

        enemy = new Enemy(dungeon, x, y);
        dungeon.addEntity(x, y, enemy);
        player.addObserver(enemy);
    }

    @Test
    void testTrackPlayer() throws InterruptedException {
        int[] playerPosition = player.getPosition();

        player.moveDown();
        playerPosition = enemy.getPlayerPosition();
        assertEquals(playerPosition[0], player.getX());
        assertEquals(playerPosition[1], player.getY());

        player.moveRight();
        playerPosition = enemy.getPlayerPosition();
        assertEquals(playerPosition[0], player.getX());
        assertEquals(playerPosition[1], player.getY());

        player.moveUp();
        playerPosition = enemy.getPlayerPosition();
        assertEquals(playerPosition[0], player.getX());
        assertEquals(playerPosition[1], player.getY());

        player.moveLeft();
        playerPosition = enemy.getPlayerPosition();
        assertEquals(playerPosition[0], player.getX());
        assertEquals(playerPosition[1], player.getY());
    }

    @Test
    void testKilledByInvinciblePlayer() {
        player.setCharacterStatus(CharacterStatus.INVINCIBLE);
        for (int i = 0; i < 5; i++)
            player.moveRight();
        assertTrue(player.isSamePosition(enemy.getX(), enemy.getY()));
        assertEquals(enemy.getCharacterStatus(), CharacterStatus.DEAD);
        assertFalse(dungeon.getEntities(enemy.getX(), enemy.getY()).contains(enemy));
    }

    @Test
    void testKillNormalFormPlayer() {
        for (int i = 0; i < 5; i++) {
            player.moveRight();
        }
        assertTrue(player.isSamePosition(enemy.getX(), enemy.getY()));
        assertEquals(CharacterStatus.DEAD, player.getCharacterStatus());
    }
}

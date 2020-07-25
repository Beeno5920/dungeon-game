package test;

import org.junit.jupiter.api.Test;
import unsw.dungeon.fieldobjects.Boulder;

import static org.junit.jupiter.api.Assertions.*;

public class BoulderTest extends EntityTest {
    @Test
    void testBoulderMoveSuccess() {
        int x = player.getX(), y = player.getY();

        Boulder boulder = new Boulder(x + 1, y);
        dungeon.addEntity(x + 1, y, boulder);
        player.addObserver(boulder);

        player.moveRight();
        assertFalse(boulder.isObstacle());
        assertTrue(boulder.isSamePosition(x + 2, y));
    }

    @Test
    void testBoulderMoveFail() {
        int x = player.getX(), y = player.getY();

        Boulder boulder1 = new Boulder(x + 1, y);
        Boulder boulder2 = new Boulder(x + 2, y);
        dungeon.addEntity(x + 1, y, boulder1);
        dungeon.addEntity(x + 2, y, boulder2);
        player.addObserver(boulder1);
        player.addObserver(boulder2);

        player.moveRight();
        assertTrue(boulder1.isObstacle());
        assertTrue(player.isSamePosition(x, y));
        assertTrue(boulder1.isSamePosition(x + 1, y));
        assertTrue(boulder2.isSamePosition(x + 2, y));
    }
}

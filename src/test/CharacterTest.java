package test;

import unsw.dungeon.Enum.Orientation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class CharacterTest extends EntityTest {

    @Test
    void testBasicMovement() {
        int prevX = player.getX(), prevY = player.getY();

        player.moveRight();
        assertTrue(player.isSamePosition(prevX + 1, prevY));

        prevX = player.getX();
        prevY = player.getY();
        player.moveDown();
        assertTrue(player.isSamePosition(prevX, prevY + 1));

        prevX = player.getX();
        prevY = player.getY();
        player.moveLeft();
        assertTrue(player.isSamePosition(prevX - 1, prevY));

        prevX = player.getX();
        prevY = player.getY();
        player.moveUp();
        assertTrue(player.isSamePosition(prevX, prevY - 1));
    }

    @Test
    void testMoveToWall() {
        int prevX = player.getX(), prevY = player.getY();

        player.moveUp();
        assertTrue(player.isSamePosition(prevX, prevY));

        player.moveLeft();
        assertTrue(player.isSamePosition(prevX, prevY));
    }

    @Test
    void testOrientation() {
        player.moveRight();
        assertEquals(player.getOrientation(), Orientation.RIGHT);

        player.moveDown();
        assertEquals(player.getOrientation(), Orientation.DOWN);

        player.moveLeft();
        assertEquals(player.getOrientation(), Orientation.LEFT);

        player.moveUp();
        assertEquals(player.getOrientation(), Orientation.UP);
    }
}

package unsw.dungeon.Test;

import unsw.dungeon.Character.Player;
import unsw.dungeon.Dungeon;
import unsw.dungeon.Enum.Orientation;
import unsw.dungeon.FieldObject.Wall;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class CharacterTest {
    protected int WIDTH = 50;
    protected int HEIGHT = 50;

    protected Dungeon dungeon;
    protected Player player;

    public CharacterTest() {
        this.dungeon = new Dungeon(WIDTH, HEIGHT);
        this.player = new Player(this.dungeon, 1, 1);
        this.dungeon.setPlayer(this.player);
        this.dungeon.addEntity(1, 1, player);

        for (int i = 0; i < WIDTH; i++) {
            dungeon.addEntity(i, 0, new Wall(i, 0));
            dungeon.addEntity(i, HEIGHT - 1, new Wall(i, HEIGHT - 1));
        }

        for (int i = 1; i < HEIGHT - 1; i++) {
            dungeon.addEntity(0, i, new Wall(0, i));
            dungeon.addEntity(WIDTH - 1, i, new Wall(WIDTH - 1, i));
        }
    }

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

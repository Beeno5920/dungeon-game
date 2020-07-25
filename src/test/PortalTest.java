package test;

import org.junit.jupiter.api.Test;
import unsw.dungeon.FieldObject.Portal;

import static org.junit.jupiter.api.Assertions.*;

public class PortalTest extends EntityTest {
    @Test
    void testPortalOneWay() {
        int x = player.getX(), y = player.getY();

        Portal portalA = new Portal(x + 1, y);
        Portal portalB = new Portal(x, y + 10);
        portalA.setAssociatedPortal(portalB);
        portalB.setAssociatedPortal(portalA);
        dungeon.addEntity(x + 1, y, portalA);
        dungeon.addEntity(x, y + 10, portalB);

        player.addObserver(portalA);
        player.addObserver(portalB);

        player.moveRight();
        assertTrue(player.isSamePosition(portalB.getX(), portalB.getY()));
    }

    @Test
    void testPortalTwoWay() {
        int x = player.getX(), y = player.getY();

        Portal portalA = new Portal(x + 1, y);
        Portal portalB = new Portal(x, y + 10);
        portalA.setAssociatedPortal(portalB);
        portalB.setAssociatedPortal(portalA);
        dungeon.addEntity(x + 1, y, portalA);
        dungeon.addEntity(x, y + 10, portalB);

        player.addObserver(portalA);
        player.addObserver(portalB);

        player.moveRight();
        assertTrue(player.isSamePosition(portalB.getX(), portalB.getY()));

        player.moveRight();
        assertTrue(player.isSamePosition(portalB.getX() + 1, portalB.getY()));
        player.moveLeft();
        assertTrue(player.isSamePosition(portalA.getX(), portalA.getY()));
        player.moveLeft();
        assertTrue(player.isSamePosition(x, y));
    }
}

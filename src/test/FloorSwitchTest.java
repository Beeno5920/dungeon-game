package test;

import unsw.dungeon.FieldObject.Boulder;
import unsw.dungeon.FieldObject.FloorSwitch;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FloorSwitchTest extends EntityTest {
    @Test
    void testFloorSwitch() {
        int x = player.getX(), y = player.getY();
        Boulder boulder = new Boulder(x + 1, y);
        FloorSwitch floorSwitch = new FloorSwitch(x + 2, y);
        dungeon.addEntity(x + 1, y, boulder);
        dungeon.addEntity(x + 2, y, floorSwitch);

        player.addObserver(boulder);
        player.addObserver(floorSwitch);
        boulder.addObserver(floorSwitch);

        assertFalse(floorSwitch.isTriggered());
        player.moveRight();
        assertTrue(floorSwitch.isTriggered());
        player.moveRight();
        assertFalse(floorSwitch.isTriggered());
    }
}

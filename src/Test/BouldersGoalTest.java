package Test;

import unsw.dungeon.FieldObject.Boulder;
import unsw.dungeon.FieldObject.FloorSwitch;
import unsw.dungeon.Goal.BouldersGoal;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BouldersGoalTest extends GoalTest {
    @Test
    void testBouldersGoal() {
        int x = player.getX(), y = player.getY();
        BouldersGoal bouldersGoal = new BouldersGoal(dungeon);
        Boulder boulder = new Boulder(x + 1, y);
        FloorSwitch floorSwitch = new FloorSwitch(x + 2, y);
        dungeon.addEntity(x + 1, y, boulder);
        dungeon.addEntity(x + 2, y, floorSwitch);
        mainGoal.addSubGoal(bouldersGoal);

        player.addObserver(boulder);
        player.addObserver(floorSwitch);
        boulder.addObserver(floorSwitch);

        assertFalse(bouldersGoal.checkSelf());
        assertFalse(mainGoal.checkSelf());

        player.moveRight();
        assertTrue(bouldersGoal.checkSelf());
        assertTrue(mainGoal.checkSelf());
    }
}

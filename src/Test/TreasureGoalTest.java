package Test;

import unsw.dungeon.Goal.TreasureGoal;
import unsw.dungeon.Item.Treasure;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TreasureGoalTest extends GoalTest {
    @Test
    void testTreasureGoalSuccess() {
        int x = player.getX(), y = player.getY();
        TreasureGoal treasureGoal = new TreasureGoal(dungeon);

        dungeon.addEntity(x, y, new Treasure(x, y));
        dungeon.addEntity(x, y + 1, new Treasure(x, y + 1));
        player.pickUp();
        player.moveDown();
        player.pickUp();

        assertTrue(treasureGoal.checkSelf());
        assertTrue(treasureGoal.checkSubGoals());

        mainGoal.addSubGoal(treasureGoal);
        assertTrue(mainGoal.checkSelf());
    }

    @Test
    void testTreasureGoalFail() {
        int x = player.getX(), y = player.getY();
        TreasureGoal treasureGoal = new TreasureGoal(dungeon);

        dungeon.addEntity(x, y, new Treasure(x, y));
        dungeon.addEntity(x, y + 1, new Treasure(x, y + 1));
        player.pickUp();

        assertFalse(treasureGoal.checkSelf());
        assertTrue(treasureGoal.checkSubGoals());

        mainGoal.addSubGoal(treasureGoal);
        assertFalse(mainGoal.checkSelf());
    }
}

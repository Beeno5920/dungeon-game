package Test;

import unsw.dungeon.FieldObject.Exit;
import unsw.dungeon.Goal.ExitGoal;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ExitGoalTest extends GoalTest {
    @Test
    void testExitGoalSuccess() {
        int x = player.getX(), y = player.getY();
        ExitGoal exitGoal = new ExitGoal(dungeon);

        dungeon.addEntity(x + 1, y, new Exit(x + 1, y));
        player.moveRight();

        assertTrue(exitGoal.checkSelf());
        assertTrue(exitGoal.checkSubGoals());

        mainGoal.addSubGoal(exitGoal);
        assertTrue(mainGoal.checkSelf());
    }

    @Test
    void testExitGoalFail() {
        int x = player.getX(), y = player.getY();
        ExitGoal exitGoal = new ExitGoal(dungeon);

        dungeon.addEntity(x + 1, y, new Exit(x + 1, y));

        assertFalse(exitGoal.checkSelf());
        assertTrue(exitGoal.checkSubGoals());

        mainGoal.addSubGoal(exitGoal);
        assertFalse(mainGoal.checkSelf());
    }
}

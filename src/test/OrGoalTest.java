package test;

import unsw.dungeon.Character.Enemy;
import unsw.dungeon.Enum.ItemCategory;
import unsw.dungeon.FieldObject.Exit;
import unsw.dungeon.Goal.*;
import unsw.dungeon.Item.Sword;
import unsw.dungeon.Item.Treasure;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OrGoalTest extends GoalTest {
    @Test
    void testOrGoal() {
        int x = player.getX(), y = player.getY();

        OrGoal orGoal = new OrGoal();
        ExitGoal exitGoal = new ExitGoal(dungeon);
        KillAllEnemiesGoal killAllEnemiesGoal = new KillAllEnemiesGoal(dungeon);
        TreasureGoal treasureGoal = new TreasureGoal(dungeon);
        orGoal.addSubGoal(exitGoal);
        orGoal.addSubGoal(killAllEnemiesGoal);
        orGoal.addSubGoal(treasureGoal);
        mainGoal.addSubGoal(orGoal);

        dungeon.addEntity(x, y, new Treasure(x, y));
        dungeon.addEntity(x, y + 1, new Enemy(dungeon, x, y + 1));
        dungeon.addEntity(x, y + 2, new Exit(x, y + 2));

        assertFalse(orGoal.checkSelf());

        player.pickUp();
        assertTrue(treasureGoal.checkSelf());
        assertTrue(orGoal.checkSelf());
        assertTrue(mainGoal.checkSelf());

        dungeon.addEntity(x, y, new Sword(x, y));
        player.pickUp();
        player.useItem(ItemCategory.SWORD);
        assertTrue(killAllEnemiesGoal.checkSelf());
        assertTrue(orGoal.checkSelf());
        assertTrue(mainGoal.checkSelf());

        player.moveDown();
        player.moveDown();
        assertTrue(exitGoal.checkSelf());
        assertTrue(orGoal.checkSelf());
        assertTrue(mainGoal.checkSelf());

        assertEquals(orGoal.checkSelf(), orGoal.checkSubGoals());
    }
}

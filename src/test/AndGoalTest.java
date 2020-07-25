package test;

import unsw.dungeon.characters.Enemy;
import unsw.dungeon.enums.ItemCategory;
import unsw.dungeon.fieldobjects.Exit;
import unsw.dungeon.goals.AndGoal;
import unsw.dungeon.goals.ExitGoal;
import unsw.dungeon.goals.KillAllEnemiesGoal;
import unsw.dungeon.goals.TreasureGoal;
import unsw.dungeon.items.Sword;
import unsw.dungeon.items.Treasure;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AndGoalTest extends GoalTest {
    @Test
    void testAndGoal() {
        int x = player.getX(), y = player.getY();

        AndGoal andGoal = new AndGoal();
        ExitGoal exitGoal = new ExitGoal(dungeon);
        KillAllEnemiesGoal killAllEnemiesGoal = new KillAllEnemiesGoal(dungeon);
        TreasureGoal treasureGoal = new TreasureGoal(dungeon);
        andGoal.addSubGoal(exitGoal);
        andGoal.addSubGoal(killAllEnemiesGoal);
        andGoal.addSubGoal(treasureGoal);

        dungeon.addEntity(x, y, new Treasure(x, y));
        dungeon.addEntity(x, y + 1, new Enemy(dungeon, x, y + 1));
        dungeon.addEntity(x, y + 2, new Exit(x, y + 2));

        assertFalse(andGoal.checkSelf());

        player.pickUp();
        assertTrue(treasureGoal.checkSelf());
        assertFalse(andGoal.checkSelf());

        dungeon.addEntity(x, y, new Sword(x, y));
        player.pickUp();
        player.useItem(ItemCategory.SWORD);
        assertTrue(killAllEnemiesGoal.checkSelf());
        assertFalse(andGoal.checkSelf());

        player.moveDown();
        player.moveDown();
        assertTrue(exitGoal.checkSelf());
        assertTrue(andGoal.checkSelf());

        assertEquals(andGoal.checkSelf(), andGoal.checkSubGoals());

        mainGoal.addSubGoal(andGoal);
        assertTrue(mainGoal.checkSelf());
    }
}

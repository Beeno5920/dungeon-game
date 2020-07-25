package test;

import unsw.dungeon.characters.Enemy;
import unsw.dungeon.enums.ItemCategory;
import unsw.dungeon.goals.KillAllEnemiesGoal;
import unsw.dungeon.items.Sword;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class KillAllEnemiesGoalTest extends GoalTest {
    @Test
    void testKillAllEnemiesGoalSuccess() {
        int x = player.getX(), y = player.getY();
        KillAllEnemiesGoal killAllEnemiesGoal = new KillAllEnemiesGoal(dungeon);

        assertTrue(killAllEnemiesGoal.checkSelf());
        assertTrue(killAllEnemiesGoal.checkSubGoals());

        dungeon.addEntity(x, y, new Sword(x, y));
        dungeon.addEntity(x, y + 1, new Enemy(dungeon, x, y + 1));

        assertFalse(killAllEnemiesGoal.checkSelf());
        player.pickUp();
        player.useItem(ItemCategory.SWORD);
        assertTrue(killAllEnemiesGoal.checkSelf());
        assertTrue(killAllEnemiesGoal.checkSubGoals());

        mainGoal.addSubGoal(killAllEnemiesGoal);
        assertTrue(mainGoal.checkSelf());
    }

    @Test
    void testKillAllEnemiesGoalFail() {
        int x = player.getX(), y = player.getY();
        KillAllEnemiesGoal killAllEnemiesGoal = new KillAllEnemiesGoal(dungeon);

        assertTrue(killAllEnemiesGoal.checkSelf());
        assertTrue(killAllEnemiesGoal.checkSubGoals());

        dungeon.addEntity(x, y, new Sword(x, y));
        dungeon.addEntity(x, y + 1, new Enemy(dungeon, x, y + 1));
        dungeon.addEntity(x + 1, y, new Enemy(dungeon, x + 1, y));

        assertFalse(killAllEnemiesGoal.checkSelf());
        player.pickUp();
        player.useItem(ItemCategory.SWORD);
        assertFalse(killAllEnemiesGoal.checkSelf());
        assertTrue(killAllEnemiesGoal.checkSubGoals());

        mainGoal.addSubGoal(killAllEnemiesGoal);
        assertFalse(mainGoal.checkSelf());
    }
}

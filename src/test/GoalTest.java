package test;

import unsw.dungeon.characters.Enemy;
import unsw.dungeon.characters.Player;
import unsw.dungeon.Dungeon;
import unsw.dungeon.fieldobjects.Exit;
import unsw.dungeon.fieldobjects.Wall;
import unsw.dungeon.goals.*;
import unsw.dungeon.items.Treasure;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GoalTest {
    protected int WIDTH = 50;
    protected int HEIGHT = 50;

    protected Dungeon dungeon;
    protected Player player;

    protected Goal mainGoal;

    public GoalTest() {
        this.dungeon = new Dungeon(WIDTH, HEIGHT);
        this.player = new Player(this.dungeon, 1, 1);
        this.mainGoal = new MainGoal(this.dungeon);

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
    void testCompositeGoal() {
        int x = player.getX(), y = player.getY();

        AndGoal andGoal = new AndGoal();
        OrGoal orGoal = new OrGoal();
        ExitGoal exitGoal = new ExitGoal(dungeon);
        KillAllEnemiesGoal killAllEnemiesGoal = new KillAllEnemiesGoal(dungeon);
        TreasureGoal treasureGoal = new TreasureGoal(dungeon);

        orGoal.addSubGoal(killAllEnemiesGoal);
        orGoal.addSubGoal(treasureGoal);
        andGoal.addSubGoal(exitGoal);
        andGoal.addSubGoal(orGoal);
        mainGoal.addSubGoal(andGoal);

        dungeon.addEntity(x, y, new Treasure(x, y));
        dungeon.addEntity(x, y + 1, new Enemy(dungeon, x, y + 1));
        dungeon.addEntity(x, y + 2, new Exit(x, y + 2));

        assertFalse(orGoal.checkSelf());
        assertFalse(andGoal.checkSelf());
        assertFalse(mainGoal.checkSelf());

        player.pickUp();
        assertTrue(treasureGoal.checkSelf());
        assertTrue(orGoal.checkSelf());
        assertFalse(andGoal.checkSelf());
        assertFalse(mainGoal.checkSelf());

        player.moveRight();
        player.moveDown();
        player.moveDown();
        player.moveLeft();
        assertFalse(killAllEnemiesGoal.checkSelf());
        assertTrue(orGoal.checkSelf());
        assertTrue(exitGoal.checkSelf());
        assertTrue(andGoal.checkSelf());
        assertTrue(mainGoal.checkSelf());
    }
}

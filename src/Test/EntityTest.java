package Test;

import unsw.dungeon.Character.Player;
import unsw.dungeon.Dungeon;
import unsw.dungeon.FieldObject.Wall;

public class EntityTest {
    protected int WIDTH = 50;
    protected int HEIGHT = 50;

    protected Dungeon dungeon;
    protected Player player;

    public EntityTest() {
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
}

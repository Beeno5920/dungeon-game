package unsw.dungeon.characters;

import unsw.dungeon.Dungeon;

public class EnemyFactory {
    public final Dungeon dungeon;

    public EnemyFactory(Dungeon dungeon) {
        this.dungeon = dungeon;
    }

    public Enemy createDeepElf(int x, int y) {
        return new Enemy(dungeon, x, y);
    }

    public Enemy createGhost(int x, int y) {
        return new Ghost(dungeon, x, y);
    }

    public Enemy createDragon(int x, int y) {
        return new Dragon(dungeon, x, y);
    }
}

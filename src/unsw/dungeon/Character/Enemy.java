package unsw.dungeon.Character;

import unsw.dungeon.Dungeon;
import unsw.dungeon.Entity;
import unsw.dungeon.Observer;

public class Enemy extends Character implements Observer {
    int speed;

    public Enemy(Dungeon dungeon, int x, int y) {
        super(dungeon, x, y);
        this.speed = 5;
    }

    public void trackPlayer() {
        //TODO
    }

    @Override
    public void update(Entity entity) {
        //TODO
    }
}

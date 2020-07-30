package unsw.dungeon.characters;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import unsw.dungeon.Dungeon;
import unsw.dungeon.enums.CharacterStatus;
import unsw.dungeon.util.PathFinder;
import unsw.dungeon.util.algorithms.Greedy;

public class Ghost extends Enemy {
    public Ghost(Dungeon dungeon, int x, int y) {
        super(dungeon, x, y, 10);
        this.pathFinder = new PathFinder(new Greedy(this, getPosition(), getPlayerPosition()));
    }

    @Override
    public boolean canMoveTo(int x, int y) {
        return !characterStatusEquals(CharacterStatus.DEAD);
    }
}

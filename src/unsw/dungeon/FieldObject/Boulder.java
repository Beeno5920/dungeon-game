package unsw.dungeon.FieldObject;

import unsw.dungeon.Character.Character;
import unsw.dungeon.Dungeon;
import unsw.dungeon.Entity;
import unsw.dungeon.Enum.Orientation;
import unsw.dungeon.Observable;
import unsw.dungeon.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Boulder extends FieldObject implements Observable, Observer {
    private List<Observer> observers;

    public Boulder(int x, int y) {
        super(x, y, true);
        this.observers = new ArrayList<>();
    }

    private boolean canMove(Character character) {
        int[][] dirs = new int[][] {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};
        int idx = character.getOrientation().ordinal();
        int x = getX() + dirs[idx][0], y = getY() + dirs[idx][1];
        for (Entity entity : character.getDungeon().getEntities(x, y)) {
            if (entity instanceof Character || entity instanceof Boulder ||
                    (entity instanceof FieldObject && ((FieldObject) entity).isObstacle()))
                return false;
        }
        return true;
    }

    private void move(Character character) {
        int oldX = getX(), oldY = getY();
        int[][] dirs = new int[][] {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};
        Orientation orientation = character.getOrientation();

        x().set(oldX + dirs[orientation.ordinal()][0]);
        y().set(oldY + dirs[orientation.ordinal()][1]);
        character.getDungeon().changeEntityPosition(oldX, oldY, this);
        notifyAllObservers();
    }

    @Override
    public void interact(Character character) {
        if (character.isSamePosition(getX(), getY()))
            move(character);
        setObstacle(!canMove(character));
    }

    @Override
    public void notify(Observer object) {
        object.update(this);
    }

    @Override
    public void notifyAllObservers() {
        for (Observer observer : observers)
            notify(observer);
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void update(Entity entity) {
        if (entity instanceof Character)
            interact((Character) entity);
    }
}

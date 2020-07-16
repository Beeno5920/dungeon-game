package unsw.dungeon.Character;

import unsw.dungeon.Dungeon;
import unsw.dungeon.Entity;
import unsw.dungeon.Observable;
import unsw.dungeon.Observer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Enemy extends Character implements Observer, Observable {
    int speed;
    int[] playerPosition;
    Set<Observer> observers;

    public Enemy(Dungeon dungeon, int x, int y) {
        super(dungeon, x, y);
        this.speed = 5;
        this.playerPosition = dungeon.getPlayer().getPosition();
        this.observers = new HashSet<>();
    }

    public void trackPlayer() {
        //TODO
    }

    @Override
    public void update(Entity entity) {
        if (entity instanceof Player)
            playerPosition = ((Player) entity).getPosition();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Enemy enemy = (Enemy) o;
        return speed == enemy.speed &&
                Arrays.equals(playerPosition, enemy.playerPosition);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(speed);
        result = 31 * result + Arrays.hashCode(playerPosition);
        return result;
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
}

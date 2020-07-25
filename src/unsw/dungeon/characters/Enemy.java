package unsw.dungeon.characters;

import unsw.dungeon.Dungeon;
import unsw.dungeon.Entity;
import unsw.dungeon.enums.CharacterStatus;
import unsw.dungeon.fieldobjects.FieldObject;
import unsw.dungeon.Observable;
import unsw.dungeon.Observer;

import javafx.animation.Timeline;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.util.Duration;
import unsw.dungeon.util.PathFinder;
import unsw.dungeon.util.algorithms.Astar;

import java.util.*;

public class Enemy extends Character implements Observer, Observable {
    private int speed;
    private int[] playerPosition;
    private Set<Observer> observers;

    private PathFinder pathFinder;
    private List<PathFinder.Node> path;

    private Timeline moveTimeline, searchTimeline;
    private boolean isSearching;

    public Enemy(Dungeon dungeon, int x, int y) {
        super(dungeon, x, y);
        this.speed = 5;
        this.playerPosition = dungeon.getPlayer().getPosition();
        this.observers = new HashSet<>();
        this.pathFinder = new PathFinder(new Astar(this, getPosition(), playerPosition));
        this.path = new ArrayList<>();
        this.moveTimeline = new Timeline();
        this.searchTimeline = new Timeline();
        this.isSearching = false;

        moveTimeline.getKeyFrames().add(new KeyFrame(Duration.seconds(speed / 10.0), e -> move()));
        searchTimeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), e -> trackPlayer()));
        moveTimeline.setCycleCount(Animation.INDEFINITE);
        searchTimeline.setCycleCount(Animation.INDEFINITE);
        moveTimeline.playFromStart();
        searchTimeline.playFromStart();
    }


    public boolean canMoveTo(int x, int y) {
        if (x < 0 || x >= getDungeon().getWidth() || y < 0 || y >= getDungeon().getHeight()
                || characterStatusEquals(CharacterStatus.DEAD))
            return false;
        for (Entity entity : getDungeon().getEntities(x, y)) {
            if (entity instanceof FieldObject && ((FieldObject) entity).isObstacle())
                return false;
        }
        return true;
    }

    private void move() {
        if (path.isEmpty())
            return;

        int x = getX(), y = getY();
        if (getDungeon().getPlayer().getCharacterStatus().equals(CharacterStatus.INVINCIBLE)
                && isSamePosition(playerPosition))
            die();

        PathFinder.Node next = path.remove(0);
        if (!canMoveTo(next.x, next.y)) {
            path.clear();
            return;
        }
        setPosition(next.x, next.y);
        getDungeon().changeEntityPosition(x, y, this);
        if (isSamePosition(playerPosition))
            getDungeon().getPlayer().setCharacterStatus(CharacterStatus.DEAD);
        if (getDungeon().getPlayer().characterStatusEquals(CharacterStatus.DEAD))
            stopTimelines();
        notifyAllObservers();
    }

    public void trackPlayer() {
        if (!isSearching)
            return;
        if (getDungeon().getPlayer().characterStatusEquals(CharacterStatus.INVINCIBLE))
            pathFinder.setNegation(true);
        else
            pathFinder.setNegation(false);
        path = pathFinder.findPath();
    }

    public void clearPath() {
        path.clear();
    }

    public void die() {
        stopTimelines();
        setCharacterStatus(CharacterStatus.DEAD);
        getDungeon().getPlayer().removeObserver(this);
        getDungeon().removeEntity(getX(), getY(), this);
        return;
    }

    public void stopTimelines() {
        moveTimeline.stop();
        searchTimeline.stop();
    }

    public int[] getPlayerPosition() {
        return playerPosition;
    }

    @Override
    public void update(Entity entity) {
        if (entity instanceof Player) {
            playerPosition = entity.getPosition();
            pathFinder.setEndPoint(playerPosition);
            if (entity.isSamePosition(getPosition())) {
                if (((Player) entity).getCharacterStatus().equals(CharacterStatus.INVINCIBLE))
                    die();
                else
                    ((Player) entity).setCharacterStatus(CharacterStatus.DEAD);
                return;
            }
            isSearching = true;
            trackPlayer();
        }
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

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        if (speed > 0)
            this.speed = speed;
    }
}

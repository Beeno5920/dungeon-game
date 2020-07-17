package unsw.dungeon.Character;

import unsw.dungeon.Dungeon;
import unsw.dungeon.Entity;
import unsw.dungeon.FieldObject.FieldObject;
import unsw.dungeon.Observable;
import unsw.dungeon.Observer;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.*;

public class Enemy extends Character implements Observer, Observable {
    private int speed;
    private int[] playerPosition;
    private Set<Observer> observers;

    private final int[][] dirs = new int[][] {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> a[0] - b[0]);
    private Timeline timeline;

    public Enemy(Dungeon dungeon, int x, int y) {
        super(dungeon, x, y);
        this.speed = 5;
        this.playerPosition = dungeon.getPlayer().getPosition();
        this.observers = new HashSet<>();
        this.timeline = new Timeline();

        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), e -> {
            minHeap.clear();
            try {
                trackPlayer();
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.playFromStart();
    }

    private boolean canMove(int x, int y) {
        if (x < 0 || x >= getDungeon().getWidth() || y < 0 || y >= getDungeon().getHeight())
            return false;
        for (Entity entity : getDungeon().getEntities(x, y)) {
            if (entity instanceof FieldObject && ((FieldObject) entity).isObstacle())
                return false;
        }
        return true;
    }

    private int getHeuristic(int x, int y) {
        return (int) (Math.pow(x - playerPosition[0], 2) + Math.pow(y - playerPosition[1], 2));
    }

    private void Astar() throws InterruptedException {
        minHeap.add(new int[] {1  + getHeuristic(getX(), getY()), getX(), getY()});
        while (!minHeap.isEmpty()) {
            int[] curr = minHeap.poll();
            System.out.println(Arrays.toString(curr));
            if (curr[1] == playerPosition[0] && curr[2] == playerPosition[1])
                return;
            x().set(curr[1]);
            y().set(curr[2]);
            Thread.sleep(2000);
            for (int[] dir : dirs) {
                int x = curr[1] + dir[0], y = curr[2] + dir[1];
                if (canMove(x, y))
                    minHeap.add(new int[]{curr[0] + getHeuristic(x, y), x, y});
            }
        }
    }

    public void trackPlayer() throws InterruptedException {
        //TODO
        Astar();
        timeline.stop();
    }

    @Override
    public void update(Entity entity) {
        if (entity instanceof Player) {
            playerPosition = ((Player) entity).getPosition();
        }
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

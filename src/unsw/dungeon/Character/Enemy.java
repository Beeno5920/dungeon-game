package unsw.dungeon.Character;

import unsw.dungeon.Dungeon;
import unsw.dungeon.Entity;
import unsw.dungeon.Enum.CharacterStatus;
import unsw.dungeon.FieldObject.FieldObject;
import unsw.dungeon.Observable;
import unsw.dungeon.Observer;

import javafx.animation.Timeline;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.util.Duration;

import java.util.*;

public class Enemy extends Character implements Observer, Observable {
    private int speed;
    private Player player;
    private int[] playerPosition;
    private Set<Observer> observers;

    private final int[][] dirs = new int[][] {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    private List<Node> path = new ArrayList<>();

    private Timeline timeline;

    public Enemy(Dungeon dungeon, int x, int y) {
        super(dungeon, x, y);
        this.speed = 5;
        this.player = dungeon.getPlayer();
        this.playerPosition = player.getPosition();
        this.observers = new HashSet<>();
        this.timeline = new Timeline();

        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(speed / 10.0), e -> move()));
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

    private void constructPath(Node end) {
        while (end.prev != null) {
            path.add(0, end);
            end = end.prev;
        }
    }

    private void Astar() {
        if (player.getCharacterStatus().equals(CharacterStatus.DEAD))
            return;

        PriorityQueue<Node> toExplore = new PriorityQueue<>((a, b) -> (int) (a.dist - b.dist));
        boolean escape = player.getCharacterStatus().equals(CharacterStatus.INVINCIBLE);
        if (escape)
            toExplore = new PriorityQueue<>((a, b) -> (int) (b.dist - a.dist));
        toExplore.add(new Node(getX(), getY(), getHeuristic(playerPosition[0], playerPosition[1])));

        while (!toExplore.isEmpty()) {
            Node curr = toExplore.poll();
            if (curr.x == playerPosition[0] && curr.y == playerPosition[1] || (escape && toExplore.size() > 20)) {
                constructPath(curr);
                return;
            }
            for (int[] dir : dirs) {
                int x = curr.x + dir[0], y = curr.y + dir[1];
                if (canMove(x, y)) {
                    Node next = new Node(x, y, curr.dist + getHeuristic(x, y));
                    next.prev = curr;
                    toExplore.add(next);
                }
            }
        }
    }

    private void move() {
        if (path.isEmpty())
            return;

        int x = getX(), y = getY();
        if (playerPosition[0] == x && playerPosition[1] == y)
            die();

        Node next = path.remove(0);
        x().set(next.x);
        y().set(next.y);
        getDungeon().changeEntityPosition(x, y, this);
        if (getX() == player.getX() && getY() == player.getY())
            player.setCharacterStatus(CharacterStatus.DEAD);
    }

    public void trackPlayer() {
        path.clear();
        Astar();
    }

    public void die() {
        timeline.stop();
        setCharacterStatus(CharacterStatus.DEAD);
        player.removeObserver(this);
        getDungeon().removeEntity(getX(), getY(), this);
        return;
    }

    public int[] getPlayerPosition() {
        return playerPosition;
    }

    @Override
    public void update(Entity entity) {
        if (entity instanceof Player) {
            playerPosition = ((Player) entity).getPosition();
            if (((Player) entity).isSamePosition(getX(), getY())) {
                if (((Player) entity).getCharacterStatus().equals(CharacterStatus.INVINCIBLE))
                    die();
                else
                    ((Player) entity).setCharacterStatus(CharacterStatus.DEAD);
                return;
            }
            trackPlayer();
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

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        if (speed > 0)
            this.speed = speed;
    }

    private class Node {
        int x, y;
        long dist;
        Node prev;

        public Node(int x, int y, long dist) {
            this.x = x;
            this.y = y;
            this.dist = dist;
            this.prev = null;
        }
    }
}

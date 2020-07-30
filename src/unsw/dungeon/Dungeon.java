/**
 *
 */
package unsw.dungeon;

import unsw.dungeon.characters.Player;
import unsw.dungeon.goals.Goal;
import unsw.dungeon.goals.MainGoal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * A dungeon in the interactive dungeon player.
 *
 * A dungeon can contain many entities, each occupy a square. More than one
 * entity can occupy the same square.
 *
 * @author Robert Clifton-Everest
 *
 */
public class Dungeon implements Observer {

    private int width, height;
    private Map<String, List<Entity>> entities;
    private Player player;
    private Goal goal;
    private SceneSelector sceneSelector = null;

    public Dungeon(int width, int height) {
        this.width = width;
        this.height = height;
        this.entities = new HashMap<>();
        this.player = null;
        this.goal = null;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        player.addObserver(this);
        this.player = player;
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
        try {
            isFinished();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MainGoal getGoal() {
        return (MainGoal) goal;
    }

    private String constructKey(int x, int y) {
        return x + " " + y;
    }

    public void addEntity(int x, int y, Entity entity) {
        String key = constructKey(x, y);
        entities.putIfAbsent(key, new ArrayList<>());
        entities.get(key).add(entity);
        entity.setVisibility(true);
    }

    public List<Entity> getEntities(int x, int y) {
        String key = constructKey(x, y);
        return entities.getOrDefault(key, new ArrayList<>());
    }

    public void removeEntity(int x, int y, Entity entity) {
        entities.getOrDefault(constructKey(x, y), new ArrayList<>()).remove(entity);
        entity.setVisibility(false);
    }

    public void removeAllSameEntities(int x, int y, Entity entity) {
        entities.getOrDefault(constructKey(x, y), new ArrayList<>()).removeIf(entity::equals);
    }

    public void changeEntityPosition(int oldX, int oldY, Entity entity) {
        removeEntity(oldX, oldY, entity);
        addEntity(entity.getX(), entity.getY(), entity);
    }

    public void assignObservers() {
        Map<Class, List<Entity>> classifiedEntities = classifyEntities();
        List<Entity> observers = classifiedEntities.getOrDefault(Observer.class, new ArrayList<>());
        List<Entity> subjects = classifiedEntities.getOrDefault(Observable.class, new ArrayList<>());

        for (Entity subject : subjects) {
            for (Entity observer : observers) {
                if (subject == observer)
                    continue;
                ((Observable) subject).addObserver((Observer) observer);
            }
        }
    }

    private Map<Class, List<Entity>> classifyEntities() {
        Map<Class, List<Entity>> groups = new HashMap<>();

        for (String key : entities.keySet()) {
            for (Entity entity : entities.get(key)) {
                if (entity instanceof Observer) {
                    groups.putIfAbsent(Observer.class, new ArrayList<>());
                    groups.get(Observer.class).add(entity);
                }
                if (entity instanceof Observable) {
                    groups.putIfAbsent(Observable.class, new ArrayList<>());
                    groups.get(Observable.class).add(entity);
                }
            }
        }

        return groups;
    }

    public void startAllTimelines() {
        for (String key : entities.keySet()) {
            for (Entity entity : entities.get(key)) {
                if (entity instanceof TimeDependent)
                    ((TimeDependent) entity).start();
            }
        }
    }

    public void stopAllTimelines() {
        for (String key : entities.keySet()) {
            for (Entity entity : entities.get(key)) {
                if (entity instanceof TimeDependent)
                    ((TimeDependent) entity).stop();
            }
        }
    }

    public boolean isFinished() throws IOException {
        if (goal.checkSelf()) {
            stopAllTimelines();
            entities.clear();
            sceneSelector.loadNextLevel();
            return true;
        }
        return false;
    }

    public void gameOver() {
        stopAllTimelines();
        sceneSelector.gameOver();
    }

    public void setSceneSelector(SceneSelector sceneSelector) {
        this.sceneSelector = sceneSelector;
    }

    @Override
    public void update(Entity entity) {
        try {
            isFinished();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

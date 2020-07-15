/**
 *
 */
package unsw.dungeon;

import unsw.dungeon.Character.Enemy;
import unsw.dungeon.Character.Player;

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
public class Dungeon {

    private int width, height;
    private Map<String, List<Entity>> entities;
    private Player player;

    public Dungeon(int width, int height) {
        this.width = width;
        this.height = height;
        this.entities = new HashMap<>();
        this.player = null;
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
        this.player = player;
    }

    private String constructKey(int x, int y) {
        return x + " " + y;
    }

    public void addEntity(int x, int y, Entity entity) {
        String key = constructKey(x, y);
        entities.putIfAbsent(key, new ArrayList<>());
        entities.get(key).add(entity);
    }

    public List<Entity> getEntities(int x, int y) {
        String key = constructKey(x, y);
        return entities.getOrDefault(key, new ArrayList<>());
    }

    public void removeEntity(int x, int y, Entity entity) {
        entities.getOrDefault(constructKey(x, y), new ArrayList<>()).removeIf(entity::equals);
    }
}

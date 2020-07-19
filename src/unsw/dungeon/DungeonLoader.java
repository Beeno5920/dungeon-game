package unsw.dungeon;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import unsw.dungeon.Character.Enemy;
import unsw.dungeon.Character.Player;
import unsw.dungeon.Enum.ItemCategory;
import unsw.dungeon.FieldObject.Door;
import unsw.dungeon.FieldObject.Portal;
import unsw.dungeon.FieldObject.Wall;
import unsw.dungeon.Item.InvincibilityPotion;
import unsw.dungeon.Item.Key;
import unsw.dungeon.Item.Sword;

/**
 * Loads a dungeon from a .json file.
 *
 * By extending this class, a subclass can hook into entity creation. This is
 * useful for creating UI elements with corresponding entities.
 *
 * @author Robert Clifton-Everest
 *
 */
public abstract class DungeonLoader {

    private JSONObject json;
    private Map<Integer, KeyDoorPair> keyDoorPairMap;
    private Map<Integer, PortalPair> portalPairMap;

    public DungeonLoader(String filename) throws FileNotFoundException {
        json = new JSONObject(new JSONTokener(new FileReader("dungeons/" + filename)));
        keyDoorPairMap = new HashMap<>();
        portalPairMap = new HashMap<>();
    }

    /**
     * Parses the JSON to create a dungeon.
     * @return
     */
    public Dungeon load() {
        int width = json.getInt("width");
        int height = json.getInt("height");

        Dungeon dungeon = new Dungeon(width, height);

        JSONArray jsonEntities = json.getJSONArray("entities");

        for (int i = 0; i < jsonEntities.length(); i++) {
            loadEntity(dungeon, jsonEntities.getJSONObject(i));
        }
        //TODO Assign observers
        dungeon.assignObservers();
        return dungeon;
    }

    private void loadEntity(Dungeon dungeon, JSONObject json) {
        String type = json.getString("type");
        int x = json.getInt("x");
        int y = json.getInt("y");
        int id = -1;

        Entity entity = null;
        switch (type) {
            case "player":
                Player player = new Player(dungeon, x, y);
                dungeon.setPlayer(player);
                onLoad(player);
                entity = player;
                break;
            case "wall":
                Wall wall = new Wall(x, y);
                onLoad(wall);
                entity = wall;
                break;
        // TODO Handle other possible entities
            case "door":
                Door door = new Door(x, y, true);
                id = json.getInt("id");
                if (!keyDoorPairMap.containsKey(id))
                    keyDoorPairMap.put(id, new KeyDoorPair(door));
                else
                    keyDoorPairMap.get(id).addDoor(door);
                entity = door;
                break;
            case "key":
                Key key = new Key(x, y);
                id = json.getInt("id");
                if (!keyDoorPairMap.containsKey(id))
                    keyDoorPairMap.put(id, new KeyDoorPair(key));
                else
                    keyDoorPairMap.get(id).addKey(key);
                entity = key;
                break;
            case "portal":
                Portal portal = new Portal(x, y, false);
                id = json.getInt("id");
                if (!portalPairMap.containsKey(id))
                    portalPairMap.put(id, new PortalPair(portal));
                else
                    portalPairMap.get(id).addPortal(portal);
                entity = portal;
                break;
            case "enemy":
                Enemy enemy = new Enemy(dungeon, x, y);
                onLoad(enemy);
                entity = enemy;
                break;
            case "sword":
                Sword sword = new Sword(x, y);
                onLoad(sword);
                entity = sword;
                break;
            case "invincibility":
                InvincibilityPotion invincibilityPotion = new InvincibilityPotion(x, y);
                onLoad(invincibilityPotion);
                entity = invincibilityPotion;
                break;
        }
        dungeon.addEntity(x, y, entity);
    }

    public abstract void onLoad(Entity player);

    public abstract void onLoad(Wall wall);

    // TODO Create additional abstract methods for the other entities
    public abstract void onLoad(Key key);

    public abstract void onLoad(Enemy enemy);

    public abstract void onLoad(Sword sword);

    public abstract void onLoad(InvincibilityPotion invincibilityPotion);

    private class KeyDoorPair {
        private Key key;
        private Door door;

        public KeyDoorPair(Key key) {
            this.key = key;
        }

        public KeyDoorPair(Door door) {
            this.door = door;
        }

        public void addKey(Key key) {
            assert door != null && key == null;
            door.setKey(key);
            key.setDoor(door);
            this.key = key;
        }

        public void addDoor(Door door) {
            assert key != null && door == null;
            door.setKey(key);
            key.setDoor(door);
            this.door = door;
        }
    }

    private class PortalPair {
        private Portal portal1;
        private Portal portal2;

        public PortalPair(Portal portal) {
            portal1 = portal;
        }

        public void addPortal(Portal portal) {
            assert portal1 != null && portal2 == null;
            portal2 = portal;
            portal1.setAssociatedPortal(portal2);
            portal2.setAssociatedPortal(portal1);
        }
    }
}

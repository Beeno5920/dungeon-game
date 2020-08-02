package unsw.dungeon;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import unsw.dungeon.characters.Enemy;
import unsw.dungeon.characters.EnemyFactory;
import unsw.dungeon.characters.Player;
import unsw.dungeon.enums.LayerLevel;
import unsw.dungeon.fieldobjects.*;
import unsw.dungeon.goals.*;
import unsw.dungeon.items.*;

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
    private EnemyFactory enemyFactory;

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
        enemyFactory = new EnemyFactory(dungeon);

        JSONArray jsonEntities = json.getJSONArray("entities");

        for (int i = 0; i < jsonEntities.length(); i++) {
            loadEntity(dungeon, jsonEntities.getJSONObject(i));
        }

        MainGoal mainGoal =  new MainGoal(dungeon);
        mainGoal.addSubGoal(constructGoal(dungeon, json.getJSONObject("goal-condition")));
        dungeon.setGoal(mainGoal);

        dungeon.assignObservers();
        keyDoorPairMap.clear();
        portalPairMap.clear();
        return dungeon;
    }

    private Goal constructGoal(Dungeon dungeon, JSONObject jsonObject) {
        Goal goal = null;

        if (jsonObject.has("subgoals")) {
            switch (jsonObject.getString("goal")) {
                case "AND":
                    goal = new AndGoal();
                    break;
                case "OR":
                    goal = new OrGoal();
                    break;
            }
            JSONArray subgoals = jsonObject.getJSONArray("subgoals");
            for (int i = 0; i < subgoals.length(); i++)
                goal.addSubGoal(constructGoal(dungeon, subgoals.getJSONObject(i)));
        } else {
            switch (jsonObject.getString("goal")) {
                case "exit":
                    goal = new ExitGoal(dungeon);
                    break;
                case "enemies":
                    goal = new KillAllEnemiesGoal(dungeon);
                    break;
                case "treasure":
                    goal = new TreasureGoal(dungeon);
                    break;
                case "boulders":
                    goal = new BouldersGoal(dungeon);
                    break;
            }
        }

        return goal;
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
                player.setLayerLevel(LayerLevel.TOP);
                dungeon.setPlayer(player);
                onLoad(player);
                entity = player;
                break;
            case "wall":
                Wall wall = new Wall(x, y);
                onLoad(wall);
                entity = wall;
                break;
            case "exit":
                Exit exit = new Exit(x, y);
                onLoad(exit);
                entity = exit;
                break;
            case "treasure":
                Treasure treasure = new Treasure(x, y);
                treasure.setLayerLevel(LayerLevel.MIDDLE);
                onLoad(treasure);
                entity = treasure;
                break;
            case "door":
                Door door = new Door(x, y);
                id = json.getInt("id");
                if (!keyDoorPairMap.containsKey(id))
                    keyDoorPairMap.put(id, new KeyDoorPair(door));
                else
                    keyDoorPairMap.get(id).addDoor(door);
                onLoad(door);
                entity = door;
                break;
            case "key":
                Key key = new Key(x, y);
                key.setLayerLevel(LayerLevel.MIDDLE);
                id = json.getInt("id");
                if (!keyDoorPairMap.containsKey(id))
                    keyDoorPairMap.put(id, new KeyDoorPair(key));
                else
                    keyDoorPairMap.get(id).addKey(key);
                onLoad(key);
                entity = key;
                break;
            case "boulder":
                Boulder boulder = new Boulder(x, y);
                boulder.setLayerLevel(LayerLevel.MIDDLE);
                onLoad(boulder);
                entity = boulder;
                break;
            case "switch":
                FloorSwitch floorSwitch = new FloorSwitch(x, y);
                onLoad(floorSwitch);
                entity = floorSwitch;
                break;
            case "portal":
                Portal portal = new Portal(x, y);
                id = json.getInt("id");
                if (!portalPairMap.containsKey(id))
                    portalPairMap.put(id, new PortalPair(portal));
                else
                    portalPairMap.get(id).addPortal(portal);
                onLoad(portal);
                entity = portal;
                break;
            case "enemy":
                Enemy enemy = enemyFactory.createDeepElf(x, y);
                enemy.setLayerLevel(LayerLevel.TOP);
                onLoad(enemy);
                entity = enemy;
                break;
            case "ghost":
                Enemy ghost = enemyFactory.createGhost(x, y);
                ghost.setLayerLevel(LayerLevel.TOP);
                onLoad(ghost);
                entity = ghost;
                break;
            case "dragon":
                Enemy dragon = enemyFactory.createDragon(x, y);
                dragon.setLayerLevel(LayerLevel.TOP);
                onLoad(dragon);
                entity = dragon;
                break;
            case "sword":
                Sword sword = new Sword(x, y);
                sword.setLayerLevel(LayerLevel.MIDDLE);
                onLoad(sword);
                entity = sword;
                break;
            case "invincibility":
                InvincibilityPotion invincibilityPotion = new InvincibilityPotion(x, y);
                invincibilityPotion.setLayerLevel(LayerLevel.MIDDLE);
                onLoad(invincibilityPotion);
                entity = invincibilityPotion;
                break;
            case "bow":
                Bow bow = new Bow(x, y);
                bow.setLayerLevel(LayerLevel.MIDDLE);
                onLoad(bow);
                entity = bow;
                break;
            case "arrow":
                Arrow arrow = new Arrow(x, y);
                arrow.setLayerLevel(LayerLevel.MIDDLE);
                onLoad(arrow);
                entity = arrow;
                break;
        }
        dungeon.addEntity(x, y, entity);
    }

    public abstract void onLoad(Player player);

    public abstract void onLoad(Wall wall);

    public abstract void onLoad(Exit exit);

    public abstract void onLoad(Treasure treasure);

    public abstract void onLoad(Door door);

    public abstract void onLoad(Key key);

    public abstract void onLoad(Boulder boulder);

    public abstract void onLoad(FloorSwitch floorSwitch);

    public abstract void onLoad(Portal portal);

    public abstract void onLoad(Enemy enemy);

    public abstract void onLoad(Sword sword);

    public abstract void onLoad(InvincibilityPotion invincibilityPotion);

    public abstract void onLoad(Bow bow);

    public abstract void onLoad(Arrow arrow);

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

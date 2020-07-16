package unsw.dungeon.Character;

import unsw.dungeon.Dungeon;
import unsw.dungeon.Entity;
import unsw.dungeon.Enum.ItemCategory;
import unsw.dungeon.Item.Item;
import unsw.dungeon.Observable;
import unsw.dungeon.Observer;

import java.util.*;

/**
 * The player entity
 * @author Robert Clifton-Everest
 *
 */
public class Player extends Character implements Observable {
    private Map<ItemCategory, List<Item>> items = new EnumMap<ItemCategory, List<Item>>(ItemCategory.class);
    private Set<Observer> observers;
    /**
     * Create a player positioned in square (x,y)
     * @param x
     * @param y
     */
    public Player(Dungeon dungeon, int x, int y) {
        super(dungeon, x, y);
        observers = new HashSet<>();
    }

    public Item pickUp() {
        List<Entity> entities = getDungeon().getEntities(getX(), getY());
        for (Entity entity : entities) {
            if (entities instanceof Item) {
                ItemCategory itemCategory = ((Item) entity).getItemCategory();
                ((Item) entity).onPick(this);
                items.putIfAbsent(itemCategory, new ArrayList<>());
                items.get(itemCategory).add((Item) entity);
                return (Item) entity;
            }
        }
        return null;
    }

    public boolean useItem(ItemCategory itemCategory) {
        if (!items.containsKey(itemCategory) || items.getOrDefault(itemCategory, new ArrayList<>()).size() == 1)
            return false;
        items.get(itemCategory).get(0).use(this);
        return true;
    }

    public boolean useItem(Item item) {
        ItemCategory itemCategory = item.getItemCategory();
        if (!items.containsKey(itemCategory) || items.getOrDefault(itemCategory, new ArrayList<>()).size() == 1)
            return false;
        for (Item ownedItem : items.get(itemCategory)) {
            if (ownedItem.equals(item)) {
                ownedItem.use(this);
                return true;
            }
        }
        return false;
    }

    public void discardItem(Item item) {
        ItemCategory itemCategory = item.getItemCategory();
        if (!items.containsKey(itemCategory) || items.getOrDefault(itemCategory, new ArrayList<>()).size() == 1)
            return;
        items.get(itemCategory).removeIf(i -> i.equals(item));
    }

    public void discardAllItems(ItemCategory itemCategory) {
        if (!items.containsKey(itemCategory))
            return;
        items.remove(itemCategory);
    }

    public void discardAllItems() {
        items.clear();
    }

    public List<Entity> getEntities(int x, int y) {
        return getDungeon().getEntities(x, y);
    }

    public void removeEntity(int x, int y, Entity entity) {
        getDungeon().removeEntity(x, y, entity);
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(items, player.items) &&
                Objects.equals(observers, player.observers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(items, observers);
    }
}

package unsw.dungeon.Character;

import unsw.dungeon.Dungeon;
import unsw.dungeon.Entity;
import unsw.dungeon.Enum.ItemCategory;
import unsw.dungeon.Enum.Orientation;
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
    private Map<ItemCategory, List<Item>> items;
    private Map<ItemCategory, Integer> itemLimit;
    private Set<Observer> observers;
    /**
     * Create a player positioned in square (x,y)
     * @param x
     * @param y
     */
    public Player(Dungeon dungeon, int x, int y) {
        super(dungeon, x, y);
        observers = new HashSet<>();
        this.items= new EnumMap<ItemCategory, List<Item>>(ItemCategory.class);
        this.itemLimit = new EnumMap<ItemCategory, Integer>(ItemCategory.class);

        initItemLimit();
    }

    private void initItemLimit() {
        itemLimit.put(ItemCategory.SWORD, 1);
        itemLimit.put(ItemCategory.KEY, 1);
    }

    public Item pickUp() {
        List<Entity> entities = getDungeon().getEntities(getX(), getY());
        for (Entity entity : entities) {
            if (entity instanceof Item) {
                ItemCategory itemCategory = ((Item) entity).getItemCategory();
                ((Item) entity).onPick(this);
                items.putIfAbsent(itemCategory, new ArrayList<>());
                if (itemLimit.containsKey(itemCategory) && itemLimit.get(itemCategory) == items.get(itemCategory).size())
                    continue;
                items.get(itemCategory).add((Item) entity);
                getDungeon().removeEntity(entity.getX(), entity.getY(), entity);
                return (Item) entity;
            }
        }
        return null;
    }

    public boolean useItem(ItemCategory itemCategory) {
        if (!items.containsKey(itemCategory) || items.getOrDefault(itemCategory, new ArrayList<>()).size() == 0)
            return false;
        items.get(itemCategory).get(0).use(this);
        return true;
    }

    public boolean useItem(Item item) {
        ItemCategory itemCategory = item.getItemCategory();
        if (!items.containsKey(itemCategory) || items.getOrDefault(itemCategory, new ArrayList<>()).size() == 0)
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
        if (!items.containsKey(itemCategory) || items.getOrDefault(itemCategory, new ArrayList<>()).size() == 0)
            return;
        items.get(itemCategory).remove(item);
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

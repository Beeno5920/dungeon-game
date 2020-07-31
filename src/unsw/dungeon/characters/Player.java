package unsw.dungeon.characters;

import unsw.dungeon.Dungeon;
import unsw.dungeon.Entity;
import unsw.dungeon.enums.ItemCategory;
import unsw.dungeon.items.Item;
import unsw.dungeon.Observable;
import unsw.dungeon.Observer;

import java.io.IOException;
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
    private List<Observer> observersToRemove;
    private Item currItem;

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
        this.observersToRemove = new ArrayList<>();
        this.currItem = null;

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
                System.out.println("You picked up a " + itemCategory);
                ((Item) entity).onPick(this);
                items.putIfAbsent(itemCategory, new ArrayList<>());
                if (itemLimit.containsKey(itemCategory) && itemLimit.get(itemCategory) == items.get(itemCategory).size())
                    continue;
                items.get(itemCategory).add((Item) entity);
                getDungeon().removeEntity(entity.getX(), entity.getY(), entity);
                notifyAllObservers();
                return (Item) entity;
            }
        }
        return null;
    }

    public boolean useItem(ItemCategory itemCategory) {
        if (!items.containsKey(itemCategory) || items.getOrDefault(itemCategory, new ArrayList<>()).size() == 0)
            return false;
        items.get(itemCategory).get(0).use(this);
        notifyAllObservers();
        return true;
    }

    public boolean useItem(Item item) {
        if (item == null)
            return false;
        ItemCategory itemCategory = item.getItemCategory();
        if (!items.containsKey(itemCategory) || items.getOrDefault(itemCategory, new ArrayList<>()).size() == 0)
            return false;
        for (Item ownedItem : items.get(itemCategory)) {
            if (ownedItem.equals(item)) {
                ownedItem.use(this);
                return true;
            }
        }
        notifyAllObservers();
        return false;
    }

    public boolean hasItem(Item item) {
        if (item == null)
            return false;
        return items.get(item.getItemCategory()).contains(item);
    }

    public boolean hasItem(ItemCategory itemCategory) {
        return items.containsKey(itemCategory) && !items.get(itemCategory).isEmpty();
    }

    public void discardItem(Item item) {
        if (!hasItem(item))
            return;
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

    public Map<ItemCategory, List<Item>> getItems() {
        return items;
    }

    public List<Item> getItems(ItemCategory itemCategory) {
        return items.getOrDefault(itemCategory, new ArrayList<>());
    }

    public void setCurrItem(Item currItem) {
        this.currItem = currItem;
    }

    public Item getCurrItem() {
        return currItem;
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
        observers.removeAll(observersToRemove);
        for (Observer observer : observers)
            notify(observer);
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        // observers.remove(observer);
        observersToRemove.add(observer);
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

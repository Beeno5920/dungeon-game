package unsw.dungeon.Item;

import unsw.dungeon.Enum.ItemCategory;

public class Treasure extends Item {
    public Treasure(int x, int y) {
        super(ItemCategory.TREASURE, x, y);
    }
}

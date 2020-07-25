package unsw.dungeon.items;

import unsw.dungeon.enums.ItemCategory;

public class Treasure extends Item {
    public Treasure(int x, int y) {
        super(ItemCategory.TREASURE, x, y);
    }
}

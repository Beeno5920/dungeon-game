package unsw.dungeon.Item;

import unsw.dungeon.Character.Player;
import unsw.dungeon.Entity;
import unsw.dungeon.Enum.ItemCategory;

import java.util.Objects;

public abstract class Item extends Entity {
    private ItemCategory itemCategory;

    public Item(ItemCategory itemCategory, int x, int y) {
        super(x, y);
        this.itemCategory = itemCategory;
    }

    public void onPick(Player player) {

    };

    public void use(Player player) {

    };

    public ItemCategory getItemCategory() {
        return itemCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return itemCategory == item.itemCategory;
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemCategory);
    }
}

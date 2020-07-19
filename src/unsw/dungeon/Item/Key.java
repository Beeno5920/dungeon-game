package unsw.dungeon.Item;

import unsw.dungeon.Character.Player;
import unsw.dungeon.Enum.ItemCategory;
import unsw.dungeon.FieldObject.Door;

import java.util.Objects;

public class Key extends Item {
    private Door door;

    public Key(int x, int y) {
        super(ItemCategory.KEY, x, y);
    }

    @Override
    public void use(Player player) {
        if (door != null && door.unlock(this))
            player.discardItem(this);
    }

    public Door getDoor() {
        return door;
    }

    public void setDoor(Door door) {
        this.door = door;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Key key = (Key) o;
        return Objects.equals(door, key.door);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), door);
    }
}

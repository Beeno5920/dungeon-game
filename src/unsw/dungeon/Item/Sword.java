package unsw.dungeon.Item;

import unsw.dungeon.Character.Enemy;
import unsw.dungeon.Character.Player;
import unsw.dungeon.Entity;
import unsw.dungeon.Enum.ItemCategory;

import java.util.Arrays;

public class Sword extends Item {
    private int durability = 5;

    public Sword(int x, int y) {
        super(ItemCategory.SWORD, x, y);
    }

    @Override
    public void use(Player player) {
        int[] position = player.getFacingPosition();
        for (Entity entity : player.getEntities(position[0], position[1])) {
            if (entity instanceof Enemy) {
                player.removeEntity(position[0], position[1], entity);
                ((Enemy) entity).die();
                //TODO Remove the image of the enemy
                break;
            }
        }
        if (--durability == 0)
            player.discardItem(this);
    }
}

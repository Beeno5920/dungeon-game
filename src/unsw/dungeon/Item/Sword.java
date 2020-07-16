package unsw.dungeon.Item;

import unsw.dungeon.Character.Enemy;
import unsw.dungeon.Character.Player;
import unsw.dungeon.Entity;
import unsw.dungeon.Enum.ItemCategory;

public class Sword extends Item {
    private int durability = 5;

    public Sword(ItemCategory itemCategory, int x, int y) {
        super(itemCategory, x, y);
    }

    @Override
    public void use(Player player) {
        int[] position = player.getFacingPosition();
        for (Entity entity : player.getEntities(position[0], position[1])) {
            if (entity instanceof Enemy) {
                player.removeEntity(position[0], position[1], entity);
                //TODO Remove the image of the enemy
            }
        }
        if (--durability == 0)
            player.discardItem(this);
    }
}

package unsw.dungeon.Item;

import unsw.dungeon.Character.Enemy;
import unsw.dungeon.Character.Player;
import unsw.dungeon.Entity;
import unsw.dungeon.Enum.ItemCategory;

import java.util.Arrays;
import java.util.List;

public class Sword extends Item {
    private int durability = 5;

    public Sword(int x, int y) {
        super(ItemCategory.SWORD, x, y);
    }

    @Override
    public void use(Player player) {
        int[] position = player.getFacingPosition();
        System.out.println("Attacking " + player.getOrientation());
        for (Entity entity : player.getEntities(position[0], position[1])) {
            if (entity instanceof Enemy) {
                ((Enemy) entity).die();
                if (--durability == 0)
                    player.discardItem(this);
                break;
            }
        }
    }
}

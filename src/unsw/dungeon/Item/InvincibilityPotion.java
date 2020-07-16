package unsw.dungeon.Item;

import unsw.dungeon.Character.Player;
import unsw.dungeon.Enum.CharacterStatus;
import unsw.dungeon.Enum.ItemCategory;

public class InvincibilityPotion extends Item {
    public InvincibilityPotion(ItemCategory itemCategory, int x, int y) {
        super(itemCategory, x, y);
    }

    @Override
    public void onPick(Player player) {
        player.setCharacterStatus(CharacterStatus.INVINCIBLE);
    }
}

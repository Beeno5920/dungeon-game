package unsw.dungeon.items;

import javafx.scene.image.ImageView;
import unsw.dungeon.Images;
import unsw.dungeon.characters.Player;
import unsw.dungeon.enums.ItemCategory;

public class Bow extends Item {
    public static final int attackPower = 1;

    public Bow(int x, int y) {
        super(ItemCategory.BOW, x, y);
    }

    @Override
    public void onPick(Player player) {
        player.attachEffect(this, new ImageView(Images.holdingBowImage));
    }

    @Override
    public void use(Player player) {
        if (!player.hasItem(ItemCategory.ARROW))
            return;

        Arrow arrow = (Arrow) player.getItems(ItemCategory.ARROW).get(0);
        arrow.shoot(player.getDungeon(), player.getOrientation(), attackPower);
        player.discardItem(arrow);
    }
}

package unsw.dungeon.items;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import unsw.dungeon.Consumable;
import unsw.dungeon.Images;
import unsw.dungeon.characters.Enemy;
import unsw.dungeon.characters.Player;
import unsw.dungeon.Entity;
import unsw.dungeon.enums.ItemCategory;

public class Sword extends Item implements Consumable {
    private int durability = 5;
    private ImageView effect;
    public static final int attackPower = 2;

    public Sword(int x, int y) {
        super(ItemCategory.SWORD, x, y);
        this.effect = new ImageView(Images.holdingSwordImage);
    }

    @Override
    public void onPick(Player player) {
        player.attachEffect(this, effect);
    }

    @Override
    public void use(Player player) {
        int[] position = player.getFacingPosition();

        ImageView slashEffect = new ImageView(Images.slashImage);
        player.displayEffect(slashEffect, position[0], position[1]);
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), e -> player.removeEffect(slashEffect)));
        timeline.playFromStart();

        System.out.println("Attacking " + player.getOrientation());
        for (Entity entity : player.getEntities(position[0], position[1])) {
            if (entity instanceof Enemy) {
                ((Enemy) entity).takeDamage(attackPower);
                if (decreaseAvailability(1) == 0) {
                    player.discardItem(this);
                    player.getDungeon().removeEffect(effect);
                }
                break;
            }
        }
    }

    @Override
    public int getAvailability() {
        return durability;
    }

    @Override
    public void setAvailability(int i) {
        durability = i;
    }

    @Override
    public int increaseAvailability(int i) {
        durability += i;
        return durability;
    }

    @Override
    public int decreaseAvailability(int i) {
        durability -= i;
        return durability;
    }
}

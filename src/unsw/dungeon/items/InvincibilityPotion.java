package unsw.dungeon.items;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import unsw.dungeon.characters.Player;
import unsw.dungeon.enums.CharacterStatus;
import unsw.dungeon.enums.ItemCategory;

public class InvincibilityPotion extends Item {
    private Player player;
    private Timeline timeline;

    public InvincibilityPotion(int x, int y) {
        super(ItemCategory.POTION, x, y);
        this.timeline = new Timeline();

        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(5), e -> tick(5)));
    }

    public void tick(double seconds) {
        if (seconds >= 5.0) {
            player.setCharacterStatus(CharacterStatus.NORMAL);
            System.out.println("You are back to normal");
        }
    }

    @Override
    public void onPick(Player player) {
        this.player = player;
        player.setCharacterStatus(CharacterStatus.INVINCIBLE);
        player.notifyAllObservers();
        timeline.playFromStart();
        System.out.println("You now are invincible!");
    }
}

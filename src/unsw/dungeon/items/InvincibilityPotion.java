package unsw.dungeon.items;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import unsw.dungeon.TimeDependent;
import unsw.dungeon.characters.Player;
import unsw.dungeon.enums.CharacterStatus;
import unsw.dungeon.enums.ItemCategory;

public class InvincibilityPotion extends Item implements TimeDependent {
    private Player player;
    private Timeline timeline;

    public InvincibilityPotion(int x, int y) {
        super(ItemCategory.POTION, x, y);
        this.player = null;
        this.timeline = new Timeline();

        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(5), e -> tick(5)));
    }

    public void tick(double seconds) {
        if (seconds >= 5.0 && player != null && player.hasItem(this)) {
            player.setCharacterStatus(CharacterStatus.NORMAL);
            player.discardItem(this);
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

    @Override
    public void start() {
        timeline.playFromStart();
    }

    @Override
    public void stop() {
        timeline.stop();
    }
}

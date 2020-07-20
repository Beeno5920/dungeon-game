package unsw.dungeon.Item;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import unsw.dungeon.Character.Character;
import unsw.dungeon.Character.Player;
import unsw.dungeon.Enum.CharacterStatus;
import unsw.dungeon.Enum.ItemCategory;

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

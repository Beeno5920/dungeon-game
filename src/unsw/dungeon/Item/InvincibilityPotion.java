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

    public InvincibilityPotion(ItemCategory itemCategory, int x, int y) {
        super(itemCategory, x, y);
        this.timeline = new Timeline();

        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(5), e -> player.setCharacterStatus(CharacterStatus.NORMAL)));
    }

    @Override
    public void onPick(Player player) {
        this.player = player;
        player.setCharacterStatus(CharacterStatus.INVINCIBLE);
        player.notifyAllObservers();
        timeline.playFromStart();
    }
}

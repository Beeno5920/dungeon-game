package unsw.dungeon.items;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import unsw.dungeon.Images;
import unsw.dungeon.TimeDependent;
import unsw.dungeon.characters.Player;
import unsw.dungeon.enums.CharacterStatus;
import unsw.dungeon.enums.ItemCategory;

public class InvincibilityPotion extends Item implements TimeDependent {
    private Player player;
    private final Timeline timeline, visualEffect;
    private int timestamp;
    private ImageView effect;

    public InvincibilityPotion(int x, int y) {
        super(ItemCategory.POTION, x, y);
        this.player = null;
        this.timeline = new Timeline();
        this.visualEffect = new Timeline();
        this.timestamp = 0;
        this.effect = new ImageView(Images.haloImage);

        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), e -> tick(1)));
        timeline.setCycleCount(5);
        visualEffect.getKeyFrames().add(new KeyFrame(Duration.seconds(0.1), e -> {
            player.setVisibility(!player.isVisible());
        }));
        visualEffect.setCycleCount(50);
        visualEffect.setOnFinished(e -> player.setVisibility(true));
    }

    public void tick(double seconds) {
        timestamp += seconds;
        if (timestamp >= 5.0 && player != null && player.hasItem(this)) {
            player.setCharacterStatus(CharacterStatus.NORMAL);
            player.discardItem(this);
            timeline.stop();
            System.out.println("You are back to normal");
        }
    }

    @Override
    public void onPick(Player player) {
        this.player = player;
        player.setCharacterStatus(CharacterStatus.INVINCIBLE);
        player.notifyAllObservers();
        timeline.playFromStart();
        //visualEffect.playFromStart();
        player.attachEffect(this, effect);
        player.displayEffect(this);
        System.out.println("You now are invincible!");
    }

    @Override
    public void start() {
        if (player == null || !player.hasItem(this))
            return;
        timeline.playFromStart();
        visualEffect.playFromStart();
    }

    @Override
    public void stop() {
        timeline.stop();
        visualEffect.stop();
    }
}

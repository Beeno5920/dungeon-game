package unsw.dungeon.characters;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import unsw.dungeon.Dungeon;
import unsw.dungeon.enums.CharacterStatus;

public class Dragon extends Enemy {
    private int hp;

    public Dragon(Dungeon dungeon, int x, int y) {
        super(dungeon, x, y, 8);
        this.hp = 3;
    }

    @Override
    public void takeDamage(int damage) {
        Timeline effect = new Timeline(new KeyFrame(Duration.millis(100), e -> setVisibility(!getVisibility().get())));
        effect.setCycleCount(4);
        effect.setOnFinished(e -> setVisibility(!getCharacterStatus().equals(CharacterStatus.DEAD)));
        effect.playFromStart();
        
        hp -= damage;
        if (hp <= 0)
            die();
    }
}

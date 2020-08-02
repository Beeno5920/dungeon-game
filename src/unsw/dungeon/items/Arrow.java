package unsw.dungeon.items;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import unsw.dungeon.Dungeon;
import unsw.dungeon.Entity;
import unsw.dungeon.Images;
import unsw.dungeon.characters.Enemy;
import unsw.dungeon.enums.ItemCategory;
import unsw.dungeon.enums.Orientation;
import unsw.dungeon.fieldobjects.FieldObject;


public class Arrow extends Item {
    private final Images images;
    private Timeline timeline;

    public Arrow(int x, int y) {
        super(ItemCategory.ARROW, x, y);
        this.images = new Images();
    }

    public void shoot(Dungeon dungeon, Orientation orientation, int attackPower) {
        setPosition(dungeon.getPlayer().getX(), dungeon.getPlayer().getY());
        changeImage(orientation);
        setVisibility(true);
        timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(100), e -> move(dungeon, orientation, attackPower)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.playFromStart();
    }

    private boolean check(Dungeon dungeon, int[] pos, int attackPower) {
        for (Entity entity : dungeon.getEntities(pos[0], pos[1])) {
            if (entity instanceof Enemy || entity instanceof FieldObject) {
                if (entity instanceof Enemy)
                    ((Enemy) entity).takeDamage(attackPower);
                setVisibility(false);
                timeline.stop();
                return true;
            }
        }

        return false;
    }

    private void move(Dungeon dungeon, Orientation orientation, int attackPower) {
        int[] pos = getPosition();
        if (check(dungeon, pos, attackPower))
            return;

        if (orientation.equals(Orientation.UP)) pos[1]--;
        else if (orientation.equals(Orientation.DOWN)) pos[1]++;
        else if (orientation.equals(Orientation.LEFT)) pos[0]--;
        else pos[0]++;

        if (check(dungeon, pos, attackPower))
            return;

        setPosition(pos[0], pos[1]);
    }

    private void changeImage(Orientation orientation) {
        switch (orientation) {
            case UP:
                getImageView().setImage(images.arrowUpImage);
                break;
            case DOWN:
                getImageView().setImage(images.arrowDownImage);
                break;
            case LEFT:
                getImageView().setImage(images.arrowLeftImage);
                break;
            case RIGHT:
                getImageView().setImage(images.arrowRightImage);
                break;
        }
    }
}

package unsw.dungeon;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import unsw.dungeon.enums.LayerLevel;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

/**
 * An entity in the dungeon.
 * @author Robert Clifton-Everest
 *
 */
public class Entity {

    // IntegerProperty is used so that changes to the entities position can be
    // externally observed.
    private IntegerProperty x, y;
    private BooleanProperty visible;
    private LayerLevel layerLevel;
    private ImageView imageView;

    /**
     * Create an entity positioned in square (x,y)
     * @param x
     * @param y
     */
    public Entity(int x, int y) {
        this.x = new SimpleIntegerProperty(x);
        this.y = new SimpleIntegerProperty(y);
        this.visible = new SimpleBooleanProperty(true);
        this.layerLevel = LayerLevel.BASE;
    }

    public IntegerProperty x() {
        return x;
    }

    public IntegerProperty y() {
        return y;
    }

    public int getY() {
        return y().get();
    }

    public int getX() {
        return x().get();
    }

    public int[] getPosition() {
        return new int[] {getX(), getY()};
    }

    public void setPosition(int x, int y) {
        this.x.set(x);
        this.y.set(y);
    }

    public boolean isSamePosition(int[] position) {
        return getX() == position[0] && getY() == position[1];
    }

    public boolean isSamePosition(int x, int y) {
        return getX() == x && getY() == y;
    }

    public BooleanProperty getVisibility() {
        return visible;
    }

    public void setVisibility(boolean visibility) {
        visible.set(visibility);
    }

    public boolean isVisible() {
        return visible.get();
    }

    public LayerLevel getLayerLevel() {
        return layerLevel;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setLayerLevel(LayerLevel layerLevel) {
        this.layerLevel = layerLevel;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public void setImage(Image image) {
        imageView.setImage(image);
    }
}

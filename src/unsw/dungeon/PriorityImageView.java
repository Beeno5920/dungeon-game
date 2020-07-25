package unsw.dungeon;

import javafx.scene.image.ImageView;

public class PriorityImageView {
    private ImageView imageView;
    private int priority;

    public PriorityImageView(ImageView imageView, int priority) {
        this.imageView = imageView;
        this.priority = priority;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public int getPriority() {
        return priority;
    }
}

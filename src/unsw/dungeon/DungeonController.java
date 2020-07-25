package unsw.dungeon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import unsw.dungeon.Character.Player;
import unsw.dungeon.Enum.ItemCategory;

import java.io.File;

/**
 * A JavaFX controller for the dungeon.
 * @author Robert Clifton-Everest
 *
 */
public class DungeonController {

    @FXML
    private GridPane squares;

    private List<PriorityImageView> initialEntities;

    private Player player;

    private Dungeon dungeon;

    public DungeonController(Dungeon dungeon, List<PriorityImageView> initialEntities) {
        this.dungeon = dungeon;
        this.player = dungeon.getPlayer();
        this.initialEntities = new ArrayList<>(initialEntities);
    }

    @FXML
    public void initialize() {
        Image ground = new Image((new File("images/dirt_0_new.png")).toURI().toString());

        // Add the ground first so it is below all other entities
        for (int x = 0; x < dungeon.getWidth(); x++) {
            for (int y = 0; y < dungeon.getHeight(); y++) {
                squares.add(new ImageView(ground), x, y);
            }
        }

        Collections.sort(initialEntities, (a, b) -> a.getPriority() - b.getPriority());
        for (PriorityImageView priorityImageView : initialEntities)
            squares.getChildren().add(priorityImageView.getImageView());

    }

    @FXML
    public void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
        case UP:
            player.moveUp();
            break;
        case DOWN:
            player.moveDown();
            break;
        case LEFT:
            player.moveLeft();
            break;
        case RIGHT:
            player.moveRight();
            break;
        case F:
            player.useItem(ItemCategory.SWORD);
        case G:
            player.useItem(ItemCategory.KEY);
        case SPACE:
            player.pickUp();
        default:
            break;
        }
    }

}


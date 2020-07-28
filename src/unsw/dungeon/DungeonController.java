package unsw.dungeon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Pair;
import unsw.dungeon.characters.Player;
import unsw.dungeon.enums.ItemCategory;

import java.io.File;

/**
 * A JavaFX controller for the dungeon.
 * @author Robert Clifton-Everest
 *
 */
public class DungeonController {

    @FXML
    private GridPane squares;

    @FXML
    private GridPane goalsPane;

    private List<PriorityImageView> initialEntities;

    private Player player;

    private Dungeon dungeon;

    private SceneSelector sceneSelector = null;

    public DungeonController(Dungeon dungeon, List<PriorityImageView> initialEntities) {
        this.dungeon = dungeon;
        this.player = dungeon.getPlayer();
        this.initialEntities = new ArrayList<>(initialEntities);
    }

    public void setSceneSelector(SceneSelector sceneSelector) {
        this.sceneSelector = sceneSelector;
        this.dungeon.setSceneSelector(sceneSelector);
    }

    public Player getPlayer() {
        return player;
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

        Pair<String, List<CheckBox>> goalComponents = dungeon.getGoal().constructDisplayComponent();
        String[] goalTexts = goalComponents.getKey().split("\n");
        List<CheckBox> goalStatus = goalComponents.getValue();
        for (int i = 0; i < goalTexts.length; i++) {
            goalsPane.add(new Label(goalTexts[i]), 0, i);
            goalsPane.add(goalStatus.get(i), 1, i);
        }
    }

    @FXML
    public void handleKeyPress(KeyEvent event) throws IOException {
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
            player.useItem(player.getCurrItem());
            break;
        case SPACE:
            player.pickUp();
            break;
        case G:
            dungeon.stopAllTimelines();
            sceneSelector.openInventory(player);
            break;
        default:
            break;
        }
    }

}


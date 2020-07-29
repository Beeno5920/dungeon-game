package unsw.dungeon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
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
    private StackPane stack;

    @FXML
    private GridPane squares;

    @FXML
    private GridPane goalsPane;

    private List<PriorityImageView> initialEntities;

    private Player player;

    private Dungeon dungeon;

    private SceneSelector sceneSelector;

    private boolean isMenuOpened;

    private boolean isGameOver;

    public DungeonController(Dungeon dungeon, List<PriorityImageView> initialEntities) {
        this.dungeon = dungeon;
        this.player = dungeon.getPlayer();
        this.initialEntities = new ArrayList<>(initialEntities);
        this.sceneSelector = null;
        this.isMenuOpened = false;
        this.isGameOver = false;
    }

    public void setSceneSelector(SceneSelector sceneSelector) {
        this.sceneSelector = sceneSelector;
        this.dungeon.setSceneSelector(sceneSelector);
    }

    public Player getPlayer() {
        return player;
    }

    private void blur() {
        squares.setEffect(new GaussianBlur(10));
    }

    private VBox constructMenu(Text title) {
        String buttonStyle = "-fx-text-fill: #006464;\n" +
                "    -fx-background-color: #DFB951;\n" +
                "    -fx-border-radius: 20;\n" +
                "    -fx-background-radius: 20;\n" +
                "    -fx-font-size: 24;\n" +
                "    -fx-padding: 5;";

        VBox menu = new VBox();
        menu.setAlignment(Pos.CENTER);
        menu.setSpacing(10);
        menu.setPrefWidth(300);
        menu.getChildren().add(title);

        if (!isGameOver) {
            Button continueButton = new Button("Continue (C)");
            continueButton.setStyle(buttonStyle);
            continueButton.setOnMouseClicked(e -> sceneSelector.setScene("dungeon"));
            continueButton.setPrefWidth(menu.getPrefWidth());
            menu.getChildren().add(continueButton);
        }

        Button restartButton = new Button("Restart (R)");
        restartButton.setStyle(buttonStyle);
        restartButton.setOnMouseClicked(e -> {
            try {
                sceneSelector.reloadCurrLevel();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        restartButton.setPrefWidth(menu.getPrefWidth());
        menu.getChildren().add(restartButton);

        Button exitButton = new Button("Exit (E)");
        exitButton.setStyle(buttonStyle);
        exitButton.setPrefWidth(menu.getPrefWidth());
        menu.getChildren().add(exitButton);

        return menu;
    }

    private void openMenu() {
        isMenuOpened = true;
        blur();

        Text title = new Text("Menu");
        title.setFill(Color.WHITE);
        title.setFont(Font.font(null, FontWeight.BOLD, 70));

        stack.getChildren().add(constructMenu(title));
    }

    private void closeMenu() {
        isMenuOpened = false;
        squares.setEffect(null);
        stack.getChildren().removeIf(node -> node instanceof VBox);
    }

    public void win() {
        isGameOver = true;
        blur();

        Text title = new Text("You Pass All the levels!");
        title.setFill(Color.YELLOW);
        title.setFont(Font.font(null, FontWeight.BOLD, 70));

        stack.getChildren().add(constructMenu(title));
    }

    public void gameOver() {
        isGameOver = true;
        blur();

        Text title = new Text("Game Over");
        title.setFill(Color.RED);
        title.setFont(Font.font(null, FontWeight.BOLD, 70));

        stack.getChildren().add(constructMenu(title));
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
            if (!isGameOver && !isMenuOpened) {
                dungeon.stopAllTimelines();
                sceneSelector.openInventory(player);
            }
            break;
        case P:
            if (!isMenuOpened && !isGameOver) {
                dungeon.stopAllTimelines();
                openMenu();
            } else if (isMenuOpened) {
                closeMenu();
            }
            break;
        case C:
            if (isMenuOpened && !isGameOver) {
                sceneSelector.setScene("dungeon");
                dungeon.startAllTimelines();
                closeMenu();
            }
            break;
        case R:
            if (isMenuOpened || isGameOver) {
                sceneSelector.reloadCurrLevel();
                closeMenu();
            }
            break;
        case E:
            if (isMenuOpened || isGameOver) {
                sceneSelector.loadStartingScene();
                sceneSelector.setCurrLevelIdx(0);
                closeMenu();
            }
        default:
            break;
        }
    }

}


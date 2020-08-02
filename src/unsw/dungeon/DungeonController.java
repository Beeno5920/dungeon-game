package unsw.dungeon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

    private boolean isLevelsMenuOpened;

    public DungeonController(Dungeon dungeon, List<PriorityImageView> initialEntities) {
        this.dungeon = dungeon;
        this.player = dungeon.getPlayer();
        this.initialEntities = new ArrayList<>(initialEntities);
        this.sceneSelector = null;
        this.isMenuOpened = false;
        this.isGameOver = false;
        this.isLevelsMenuOpened = false;
    }

    public void setSceneSelector(SceneSelector sceneSelector) {
        this.sceneSelector = sceneSelector;
        this.dungeon.setSceneSelector(sceneSelector);
    }

    public Player getPlayer() {
        return player;
    }

    private void blur() {
        for (Node node : stack.getChildren())
            node.setEffect(new GaussianBlur(10));
    }

    private VBox constructMenu(Text title) {
        VBox menu = new VBox();
        menu.setAlignment(Pos.CENTER);
        menu.setSpacing(10);
        menu.setPrefWidth(300);
        menu.getChildren().add(title);

        if (!isGameOver) {
            Button continueButton = new Button("Continue (C)");
            continueButton.setOnMouseClicked(e -> sceneSelector.setScene("dungeon"));
            menu.getChildren().add(continueButton);
        }

        Button restartButton = new Button("Restart (R)");
        restartButton.setId("button");
        restartButton.setOnMouseClicked(e -> {
            try {
                sceneSelector.loadCurrentLevel();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        menu.getChildren().add(restartButton);

        Button levelsButton = new Button("Choose level (L)");
        levelsButton.setOnAction(e -> displayLevelsMenu());
        menu.getChildren().add(levelsButton);

        Button exitButton = new Button("Exit (E)");
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

    private void displayLevelsMenu() {
        isMenuOpened = false;
        isLevelsMenuOpened = true;
        blur();
        GridPane levelsMenu = new GridPane();
        levelsMenu.setVgap(10);
        levelsMenu.setHgap(10);
        levelsMenu.setAlignment(Pos.CENTER);

        int size = 3;
        int i = 0;
        for (File file : sceneSelector.getLevels()) {
            if (i >= size * size)
                break;
            int x = i % size;
            int y = i / size;
            Button button = new Button(file.getName().split("\\.")[0]);
            button.setMaxWidth(200);
            int finalI = i;
            button.setOnAction(e -> {
                sceneSelector.setCurrLevelIdx(finalI);
                try {
                    sceneSelector.loadCurrentLevel();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            });
            levelsMenu.add(button, x, y);
            i++;
        }
        stack.getChildren().add(levelsMenu);
    }

    private void closeLevelsMenu() {
        isMenuOpened = true;
        isLevelsMenuOpened = false;
        stack.getChildren().remove(stack.getChildren().size() - 1);
        stack.getChildren().get(stack.getChildren().size() - 1).setEffect(null);
    }

    public void displayEffect(ImageView effect, int x, int y) {
        GridPane.setColumnIndex(effect, player.getX());
        GridPane.setRowIndex(effect, player.getY());
        player.x().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                                Number oldValue, Number newValue) {
                GridPane.setColumnIndex(effect, newValue.intValue());
            }
        });
        player.y().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                                Number oldValue, Number newValue) {
                GridPane.setRowIndex(effect, newValue.intValue());
            }
        });
        squares.add(effect, x, y);
    }

    public void removeEffect(ImageView effect) {
        squares.getChildren().remove(effect);
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
        int w = Math.max(dungeon.getWidth(), StartingViewController.prefWidth);
        int h = Math.max(dungeon.getHeight(), StartingViewController.prefHeight);
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                squares.add(new ImageView(Images.groundImage), x, y);
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
                if (isLevelsMenuOpened)
                    return;
                dungeon.stopAllTimelines();
                sceneSelector.openInventory(player);
            }
            break;
        case P:
            if (!isMenuOpened && !isGameOver) {
                if (isLevelsMenuOpened)
                    return;
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
                sceneSelector.loadCurrentLevel();
                closeMenu();
            }
            break;
        case E:
            if (isMenuOpened || isGameOver) {
                sceneSelector.loadStartingScene();
                sceneSelector.setCurrLevelIdx(-1);
                closeMenu();
            }
            break;
        case L:
            if (isLevelsMenuOpened) {
                closeLevelsMenu();
            } else if (isMenuOpened) {
                displayLevelsMenu();
            }
        default:
            break;
        }
    }

}


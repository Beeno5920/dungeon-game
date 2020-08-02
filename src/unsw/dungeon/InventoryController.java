package unsw.dungeon;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;

import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Pair;
import unsw.dungeon.characters.Player;
import unsw.dungeon.items.Item;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class InventoryController {
    @FXML
    private GridPane inventory;

    @FXML
    private GridPane goalsPane;

    private Player player;

    private List<Pair<Item, ImageView>> initialEntities;

    private Item[][] items;

    private SceneSelector sceneSelector = null;

    private int[] cursorPosition;
    private Rectangle cursor;

    public InventoryController(Player player, List<Pair<Item, ImageView>> initialEntities) {
        this.player = player;
        this.initialEntities = initialEntities;
        this.cursorPosition = new int[] {0, 0};
        this.items = new Item[StartingViewController.prefWidth][StartingViewController.prefHeight];
        this.cursor = new Rectangle(30, 30);
        cursor.setArcWidth(10);
        cursor.setArcHeight(10);
        cursor.setStroke(Color.BLUE);
        cursor.setFill(Color.TRANSPARENT);
    }

    public void setSceneSelector(SceneSelector sceneSelector) {
        this.sceneSelector = sceneSelector;
    }

    private void cursorUp() {
        if (cursorPosition[1] == 0)
            return;
        inventory.getChildren().remove(cursor);
        cursorPosition[1]--;
        inventory.add(cursor, cursorPosition[0], cursorPosition[1]);
    }

    private void cursorDown() {
        if (cursorPosition[1] == 9)
            return;
        inventory.getChildren().remove(cursor);
        cursorPosition[1]++;
        inventory.add(cursor, cursorPosition[0], cursorPosition[1]);
    }

    private void cursorLeft() {
        if (cursorPosition[0] == 0)
            return;
        inventory.getChildren().remove(cursor);
        cursorPosition[0]--;
        inventory.add(cursor, cursorPosition[0], cursorPosition[1]);
    }

    private void cursorRight() {
        if (cursorPosition[0] == 9)
            return;
        inventory.getChildren().remove(cursor);
        cursorPosition[0]++;
        inventory.add(cursor, cursorPosition[0], cursorPosition[1]);
    }

    private void selectItem() {
        int x = cursorPosition[0], y = cursorPosition[1];
        System.out.println("You selected " + items[y][x]);
        player.setCurrItem(items[y][x]);
        closeInventory();
    }

    private void discardItem() {
        int x = cursorPosition[0], y = cursorPosition[1];
        if (items[y][x] == null)
            return;

        System.out.println("You discarded " + items[y][x]);
        player.discardItem(items[y][x]);
        for (Pair<Item, ImageView> pair : initialEntities) {
            if (items[y][x].equals(pair.getKey())) {
                inventory.getChildren().remove(pair.getValue());
                items[y][x] = null;
                break;
            }
        }
    }

    private void closeInventory() {
        sceneSelector.setScene("dungeon");
        player.getDungeon().startAllTimelines();
    }

    @FXML
    public void initialize() {
        // Add the ground first so it is below all other entities
        for (int x = 0; x < StartingViewController.prefWidth; x++) {
            for (int y = 0; y < StartingViewController.prefHeight; y++) {
                Rectangle border = new Rectangle(30, 30);
                border.setStroke(Color.GRAY);
                border.setFill(Color.TRANSPARENT);
                inventory.add(new ImageView(Images.groundImage), x, y);
                inventory.add(border, x, y);
            }
        }

        int x = 0, y = 0;
        for (int i = 0; i < initialEntities.size(); i++) {
            x = i / StartingViewController.prefWidth;
            y = i % StartingViewController.prefWidth;
            inventory.add(initialEntities.get(i).getValue(), y, x);
            items[x][y] = initialEntities.get(i).getKey();
            if (items[x][y] instanceof Consumable) {
                Text durability = new Text(Integer.toString(((Consumable) items[x][y]).getAvailability()));
                durability.setFill(Color.WHITE);
                inventory.add(durability, y, x);
            }
        }
        inventory.add(cursor, 0, 0);

        Pair<String, List<CheckBox>> goalComponents = player.getDungeon().getGoal().constructDisplayComponent();
        String[] goalTexts = goalComponents.getKey().split("\n");
        List<CheckBox> goalStatus = goalComponents.getValue();
        for (int i = 0; i < goalTexts.length; i++) {
            goalsPane.add(new Label(goalTexts[i]), 0, i);
            goalsPane.add(goalStatus.get(i), 1, i);
        }
    }

    @FXML
    public void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
            case UP:
                cursorUp();
                break;
            case DOWN:
                cursorDown();
                break;
            case LEFT:
                cursorLeft();
                break;
            case RIGHT:
                cursorRight();
                break;
            case SPACE:
                selectItem();
                break;
            case B:
                discardItem();
                break;
            case G:
                closeInventory();
                break;
            default:
                break;
        }
    }
}

package unsw.dungeon;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;

import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;
import unsw.dungeon.characters.Player;
import unsw.dungeon.items.Item;

import java.io.IOException;
import java.util.List;

public class InventoryController {
    @FXML
    private GridPane inventory;

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
        this.items = new Item[10][10];
        this.cursor = new Rectangle(40, 40);
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
    }

    @FXML
    public void initialize() {
        int x = 0, y = 0;
        for (int i = 0; i < initialEntities.size(); i++) {
            x = i / 10;
            y = i % 10;
            inventory.add(initialEntities.get(i).getValue(), y, x);
            items[y][x] = initialEntities.get(i).getKey();
        }
        inventory.add(cursor, 0, 0);
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
            case B:
                sceneSelector.setScene("dungeonScene");
                player.getDungeon().startAllTimelines();
                break;
            default:
                break;
        }
    }
}

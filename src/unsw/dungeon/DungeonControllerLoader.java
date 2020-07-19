package unsw.dungeon;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import unsw.dungeon.Character.Enemy;
import unsw.dungeon.FieldObject.Wall;
import unsw.dungeon.Item.InvincibilityPotion;
import unsw.dungeon.Item.Key;
import unsw.dungeon.Item.Sword;

import java.io.File;

/**
 * A DungeonLoader that also creates the necessary ImageViews for the UI,
 * connects them via listeners to the model, and creates a controller.
 * @author Robert Clifton-Everest
 *
 */
public class DungeonControllerLoader extends DungeonLoader {

    private List<ImageView> entities;

    //Images
    private final Image playerImage;
    private final Image wallImage;
    private final Image exitImage;
    private final Image treasureImage;
    private final Image closesDoorImage;
    private final Image openedDoorImage;
    private final Image keyImage;
    private final Image boulderImage;
    private final Image floorSwitchImage;
    private final Image portalImage;
    private final Image enemyImage;
    private final Image swordImage;
    private final Image invincibilityPotionImage;

    public DungeonControllerLoader(String filename)
            throws FileNotFoundException {
        super(filename);
        entities = new ArrayList<>();
        playerImage = new Image((new File("images/human_new.png")).toURI().toString());
        wallImage = new Image((new File("images/brick_brown_0.png")).toURI().toString());
        exitImage = new Image((new File("images/exit.png").toURI().toString()));
        treasureImage = new Image((new File("images/gold_pile.png").toURI().toString()));
        closesDoorImage = new Image((new File("images/closed_door.png").toURI().toString()));
        openedDoorImage = new Image((new File("images/open_door.png").toURI().toString()));
        keyImage = new Image((new File("images/key.png").toURI().toString()));
        boulderImage = new Image((new File("images/boulder.png").toURI().toString()));
        floorSwitchImage = new Image((new File("images/pressure_plate.png").toURI().toString()));
        portalImage = new Image((new File("images/portal.png").toURI().toString()));
        enemyImage = new Image((new File("images/deep_elf_master_archer.png").toURI().toString()));
        swordImage = new Image((new File("images/greatsword_1_new.png").toURI().toString()));
        invincibilityPotionImage = new Image((new File("images/brilliant_blue_new.png").toURI().toString()));
    }

    @Override
    public void onLoad(Entity player) {
        ImageView view = new ImageView(playerImage);
        addEntity(player, view);
    }

    @Override
    public void onLoad(Wall wall) {
        ImageView view = new ImageView(wallImage);
        addEntity(wall, view);
    }

    @Override
    public void onLoad(Key key) {
        ImageView view = new ImageView(keyImage);
        addEntity(key, view);
    }

    @Override
    public void onLoad(Enemy enemy) {
        ImageView view = new ImageView(enemyImage);
        addEntity(enemy, view);
    }

    @Override
    public void onLoad(Sword sword) {
        ImageView view = new ImageView(swordImage);
        addEntity(sword, view);
    }

    @Override
    public void onLoad(InvincibilityPotion invincibilityPotion) {
        ImageView view = new ImageView(invincibilityPotionImage);
        addEntity(invincibilityPotion, view);
    }

    private void addEntity(Entity entity, ImageView view) {
        trackPosition(entity, view);
        entities.add(view);
    }

    /**
     * Set a node in a GridPane to have its position track the position of an
     * entity in the dungeon.
     *
     * By connecting the model with the view in this way, the model requires no
     * knowledge of the view and changes to the position of entities in the
     * model will automatically be reflected in the view.
     * @param entity
     * @param node
     */
    private void trackPosition(Entity entity, Node node) {
        GridPane.setColumnIndex(node, entity.getX());
        GridPane.setRowIndex(node, entity.getY());
        entity.x().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                GridPane.setColumnIndex(node, newValue.intValue());
            }
        });
        entity.y().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                GridPane.setRowIndex(node, newValue.intValue());
            }
        });
    }

    /**
     * Create a controller that can be attached to the DungeonView with all the
     * loaded entities.
     * @return
     * @throws FileNotFoundException
     */
    public DungeonController loadController() throws FileNotFoundException {
        return new DungeonController(load(), entities);
    }


}

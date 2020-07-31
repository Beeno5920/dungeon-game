package unsw.dungeon;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import unsw.dungeon.characters.Enemy;
import unsw.dungeon.characters.Ghost;
import unsw.dungeon.characters.Player;
import unsw.dungeon.fieldobjects.*;
import unsw.dungeon.items.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * A DungeonLoader that also creates the necessary ImageViews for the UI,
 * connects them via listeners to the model, and creates a controller.
 * @author Robert Clifton-Everest
 *
 */
public class DungeonControllerLoader extends DungeonLoader {

    private List<PriorityImageView> entities;
    private Images images;

    public DungeonControllerLoader(String filename)
            throws FileNotFoundException {
        super(filename);
        entities = new ArrayList<>();
        images = new Images();
    }

    @Override
    public void onLoad(Player player) {
        ImageView view = new ImageView(images.playerImage);
        addEntity(player, view);
    }

    @Override
    public void onLoad(Wall wall) {
        ImageView view = new ImageView(images.wallImage);
        addEntity(wall, view);
    }

    @Override
    public void onLoad(Exit exit) {
        ImageView view = new ImageView(images.exitImage);
        addEntity(exit, view);
    }

    @Override
    public void onLoad(Treasure treasure) {
        ImageView view = new ImageView(images.treasureImage);
        addEntity(treasure, view);
    }

    @Override
    public void onLoad(Door door) {
        ImageView view = new ImageView(images.closesDoorImage);
        if (!door.isObstacle())
            view = new ImageView(images.openedDoorImage);
        addEntity(door, view);
    }

    @Override
    public void onLoad(Key key) {
        ImageView view = new ImageView(images.keyImage);
        addEntity(key, view);
    }

    @Override
    public void onLoad(Boulder boulder) {
        ImageView view = new ImageView(images.boulderImage);
        addEntity(boulder, view);
    }

    @Override
    public void onLoad(FloorSwitch floorSwitch) {
        ImageView view = new ImageView(images.floorSwitchImage);
        addEntity(floorSwitch, view);
    }

    @Override
    public void onLoad(Portal portal) {
        ImageView view = new ImageView(images.portalImage);
        addEntity(portal, view);
    }

    @Override
    public void onLoad(Enemy enemy) {
        ImageView view = new ImageView(images.enemyImage);
        if (enemy instanceof Ghost)
            view = new ImageView(images.ghostImage);
        addEntity(enemy, view);
    }

    @Override
    public void onLoad(Sword sword) {
        ImageView view = new ImageView(images.swordImage);
        addEntity(sword, view);
    }

    @Override
    public void onLoad(InvincibilityPotion invincibilityPotion) {
        ImageView view = new ImageView(images.invincibilityPotionImage);
        addEntity(invincibilityPotion, view);
    }

    @Override
    public void onLoad(Bow bow) {
        ImageView view = new ImageView(images.bowImage);
        addEntity(bow, view);
    }

    @Override
    public void onLoad(Arrow arrow) {
        ImageView view = new ImageView(images.arrowLeftImage);
        arrow.setImageView(view);
        addEntity(arrow, view);
    }

    private void addEntity(Entity entity, ImageView view) {
        trackPosition(entity, view);
        entity.setImageView(view);
        entities.add(new PriorityImageView(view, entity.getLayerLevel().ordinal()));
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
        entity.getVisibility().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue,
                                Boolean aBoolean, Boolean t1) {
                node.setVisible(entity.isVisible());
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

package unsw.dungeon;

import javafx.util.Pair;
import unsw.dungeon.characters.Player;
import unsw.dungeon.enums.ItemCategory;
import unsw.dungeon.items.*;

import javafx.scene.image.ImageView;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InventoryControllerLoader {
    private Player player;
    private List<Pair<Item, ImageView>> imageViews;

    public InventoryControllerLoader(Player player) {
        this.player = player;
        this.imageViews = new ArrayList<>();
        extractItems();
    }

    private void extractItems() {
        Map<ItemCategory, List<Item>> items = player.getItems();
        for (ItemCategory itemCategory : items.keySet()) {
            for (Item item : items.get(itemCategory))
                addImage(itemCategory, item);
        }
    }

    private void addImage(ItemCategory itemCategory, Item item) {
        switch (itemCategory) {
            case SWORD:
                addImage((Sword) item);
                break;
            case BOW:
                addImage((Bow) item);
                break;
            case ARROW:
                addImage((Arrow) item);
                break;
            case KEY:
                addImage((Key) item);
                break;
            case POTION:
                addImage((InvincibilityPotion) item);
                break;
        }
    }

    private void addImage(Sword sword) {
        ImageView view = new ImageView(Images.swordImage);
        imageViews.add(new Pair<>(sword, view));
    }

    private void addImage(Key key) {
        ImageView view = new ImageView(Images.keyImage);
        imageViews.add(new Pair<>(key, view));
    }

    private void addImage(InvincibilityPotion invincibilityPotion) {
        ImageView view = new ImageView(Images.invincibilityPotionImage);
        imageViews.add(new Pair<>(invincibilityPotion, view));
    }

    private void addImage(Bow bow) {
        ImageView view = new ImageView(Images.bowImage);
        imageViews.add(new Pair<>(bow, view));
    }

    private void addImage(Arrow arrow) {
        ImageView view = new ImageView(Images.arrowLeftImage);
        imageViews.add(new Pair<>(arrow, view));
    }

    public InventoryController loadController() {
        return new InventoryController(player, imageViews);
    }
}

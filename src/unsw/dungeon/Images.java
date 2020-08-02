package unsw.dungeon;

import javafx.scene.image.Image;

import java.io.File;

public class Images {
    public static final Image groundImage = new Image((new File("images/dirt_0_new.png")).toURI().toString());
    public static final Image playerImage = new Image((new File("images/human_new.png")).toURI().toString());
    public static final Image wallImage = new Image((new File("images/brick_brown_0.png")).toURI().toString());
    public static final Image exitImage = new Image((new File("images/exit.png").toURI().toString()));
    public static final Image treasureImage = new Image((new File("images/gold_pile.png").toURI().toString()));
    public static final Image closesDoorImage = new Image((new File("images/closed_door.png").toURI().toString()));
    public static final Image openedDoorImage = new Image((new File("images/open_door.png").toURI().toString()));
    public static final Image keyImage = new Image((new File("images/key.png").toURI().toString()));
    public static final Image boulderImage = new Image((new File("images/boulder.png").toURI().toString()));
    public static final Image floorSwitchImage = new Image((new File("images/pressure_plate.png").toURI().toString()));
    public static final Image portalImage = new Image((new File("images/portal.png").toURI().toString()));
    public static final Image enemyImage = new Image((new File("images/deep_elf_master_archer.png").toURI().toString()));
    public static final Image swordImage = new Image((new File("images/greatsword_1_new.png").toURI().toString()));
    public static final Image invincibilityPotionImage = new Image((new File("images/brilliant_blue_new.png").toURI().toString()));
    public static final Image dragonImage = new Image(new File("images/dragon.png").toURI().toString());
    public static final Image ghostImage = new Image(new File("images/ghost.png").toURI().toString());
    public static final Image bowImage = new Image(new File("images/bow.png").toURI().toString());
    public static final Image arrowUpImage = new Image(new File("images/arrow_up.png").toURI().toString());
    public static final Image arrowDownImage = new Image(new File("images/arrow_down.png").toURI().toString());
    public static final Image arrowLeftImage = new Image(new File("images/arrow_left.png").toURI().toString());
    public static final Image arrowRightImage = new Image(new File("images/arrow_right.png").toURI().toString());
    public static final Image holdingSwordImage = new Image(new File("images/holding_sword.png").toURI().toString());
    public static final Image holdingBowImage = new Image(new File("images/holding_bow.png").toURI().toString());
    public static final Image haloImage = new Image(new File("images/halo_player.png").toURI().toString());
    public static final Image slashImage = new Image(new File("images/slash.png").toURI().toString());
}

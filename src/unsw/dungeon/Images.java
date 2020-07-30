package unsw.dungeon;

import javafx.scene.image.Image;

import java.io.File;

public class Images {
    public final Image groundImage;
    public final Image playerImage;
    public final Image wallImage;
    public final Image exitImage;
    public final Image treasureImage;
    public final Image closesDoorImage;
    public final Image openedDoorImage;
    public final Image keyImage;
    public final Image boulderImage;
    public final Image floorSwitchImage;
    public final Image portalImage;
    public final Image enemyImage;
    public final Image swordImage;
    public final Image invincibilityPotionImage;
    public final Image dragonImage;
    public final Image ghostImage;

    public Images() {
        groundImage = new Image((new File("images/dirt_0_new.png")).toURI().toString());
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
        dragonImage = new Image(new File("images/dragon.png").toURI().toString());
        ghostImage = new Image(new File("images/ghost.png").toURI().toString());
    }
}

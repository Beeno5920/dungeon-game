package unsw.dungeon;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import unsw.dungeon.characters.Player;

public class DungeonApplication extends Application {
    private SceneSelector sceneSelector;

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Dungeon");
        sceneSelector = new SceneSelector(primaryStage);

        primaryStage.setScene(initializeDungeonScene());
        primaryStage.show();
    }

    private Scene initializeDungeonScene() throws IOException {
        DungeonControllerLoader dungeonLoader = new DungeonControllerLoader("mazeTest.json");

        DungeonController controller = dungeonLoader.loadController();
        controller.setSceneSelector(sceneSelector);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("DungeonView.fxml"));
        loader.setController(controller);
        Parent root = loader.load();
        Scene dungeonScene = new Scene(root);
        root.requestFocus();
        sceneSelector.putScene("dungeonScene", dungeonScene);

        initializeInventoryScene(controller.getPlayer());

        return dungeonScene;
    }

    private Scene initializeInventoryScene(Player player) throws IOException {
        InventoryControllerLoader loader = new InventoryControllerLoader(player);
        InventoryController controller = loader.loadController();
        controller.setSceneSelector(sceneSelector);

        FXMLLoader inventoryLoader = new FXMLLoader(getClass().getResource("InventoryView.fxml"));
        inventoryLoader.setController(controller);
        Parent root2 = inventoryLoader.load();
        Scene inventoryScene = new Scene(root2);
        root2.requestFocus();
        sceneSelector.putScene("inventoryScene", inventoryScene);

        return inventoryScene;
    }

    public static void main(String[] args) {
        launch(args);
    }
}

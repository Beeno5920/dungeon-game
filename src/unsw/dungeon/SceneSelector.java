package unsw.dungeon;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import unsw.dungeon.characters.Player;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SceneSelector {
    private Stage stage;
    private Map<String, Scene> sceneMap;

    public SceneSelector(Stage stage) {
        this.stage = stage;
        this.sceneMap = new HashMap<>();
    }

    public void putScene(String sceneName, Scene scene) {
        sceneMap.put(sceneName, scene);
    }

    public void setScene(String sceneName) {
        if (!sceneMap.containsKey(sceneName)) {
            System.out.println("Unable to find scene " + sceneName);
            return;
        }
        stage.setScene(sceneMap.get(sceneName));
    }

    public void openInventory(Player player) throws IOException {
        InventoryControllerLoader loader = new InventoryControllerLoader(player);
        InventoryController controller = loader.loadController();
        controller.setSceneSelector(this);

        FXMLLoader inventoryLoader = new FXMLLoader(getClass().getResource("InventoryView.fxml"));
        inventoryLoader.setController(controller);
        Parent root2 = inventoryLoader.load();
        Scene inventoryScene = new Scene(root2);
        root2.requestFocus();

        stage.setScene(inventoryScene);
    }
}

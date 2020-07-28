package unsw.dungeon;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import unsw.dungeon.characters.Player;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SceneSelector {
    private Stage stage;
    private Map<String, Scene> sceneMap;
    private File[] levels;
    private int currLevelIdx;

    public SceneSelector(Stage stage) {
        this.stage = stage;
        this.sceneMap = new HashMap<>();

        String basePath = new File("").getAbsolutePath();
        this.levels = new File(basePath + "/dungeons").listFiles();
        this.currLevelIdx = -1;
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

    private Scene loadFxml(String fxmlName, Object controller) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlName));
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        root.requestFocus();

        return scene;
    }

    public void reloadCurrLevel() throws IOException {
        loadDungeonScene();
    }

    public void loadNextLevel() throws IOException {
        if (currLevelIdx + 1 >= levels.length)
            return;
        currLevelIdx++;
        loadDungeonScene();
    }

    private void loadDungeonScene() throws IOException {
        DungeonControllerLoader dungeonLoader = new DungeonControllerLoader(levels[currLevelIdx].getName());

        DungeonController controller = dungeonLoader.loadController();
        controller.setSceneSelector(this);

        Scene dungeonScene = loadFxml("DungeonView.fxml", controller);
        putScene("dungeonScene", dungeonScene);

        stage.setScene(dungeonScene);
    }

    private void loadInventoryScene(Player player) throws IOException {
        InventoryControllerLoader loader = new InventoryControllerLoader(player);
        InventoryController controller = loader.loadController();
        controller.setSceneSelector(this);

        Scene inventoryScene = loadFxml("InventoryView.fxml", controller);
        putScene("inventoryScene", inventoryScene);
    }

    public void openInventory(Player player) throws IOException {
        loadInventoryScene(player);
        setScene("inventoryScene");
    }
}

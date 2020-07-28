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
        sceneSelector.loadNextLevel();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

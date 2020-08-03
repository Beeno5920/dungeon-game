package unsw.dungeon;

import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import unsw.dungeon.characters.Player;

public class DungeonApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Dungeon");

        primaryStage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });

        SceneSelector sceneSelector = new SceneSelector(primaryStage);
        sceneSelector.loadStartingScene();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

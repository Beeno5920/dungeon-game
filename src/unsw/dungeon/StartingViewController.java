package unsw.dungeon;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class StartingViewController {
    @FXML
    private StackPane stack;

    @FXML
    private GridPane squares;

    @FXML
    private VBox menu;

    public final static int prefWidth = 25;

    public final static int prefHeight = 18;

    private SceneSelector sceneSelector;

    public StartingViewController(SceneSelector sceneSelector) {
        this.sceneSelector = sceneSelector;
    }

    private void blur() {
        for (Node node : stack.getChildren())
            node.setEffect(new GaussianBlur(10));
    }

    private void displayLevelsMenu() {
        if (stack.getChildren().size() == 3) {
            closeLevelsMenu();
            return;
        }
        blur();
        GridPane levelsMenu = new GridPane();
        levelsMenu.setVgap(10);
        levelsMenu.setHgap(10);
        levelsMenu.setAlignment(Pos.CENTER);
        int size = 3;
        int i = 0;
        for (File file : sceneSelector.getLevels()) {
            if (i >= size * size)
                break;
            int x = i % size;
            int y = i / size;
            Button button = new Button(file.getName().split("\\.")[0]);
            button.setMaxWidth(200);
            int finalI = i;
            button.setOnAction(e -> {
                sceneSelector.setCurrLevelIdx(finalI);
                try {
                    sceneSelector.loadCurrentLevel();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            });
            levelsMenu.add(button, x, y);
            i++;
        }
        stack.getChildren().add(levelsMenu);
    }

    private void closeLevelsMenu() {
        stack.getChildren().remove(stack.getChildren().size() - 1);
        for (Node node : stack.getChildren())
            node.setEffect(null);
    }

    @FXML
    public void initialize() {

        for (int i = 0; i < prefHeight; i++) {
            for (int j = 0; j < prefWidth; j++)
                squares.add(new ImageView(Images.groundImage), j, i);
        }

        for (int i = 0; i < prefHeight; i++) {
            squares.add(new ImageView(Images.wallImage), 0, i);
            squares.add(new ImageView(Images.wallImage), prefWidth - 1, i);
        }

        for (int i = 0; i < prefWidth; i++) {
            squares.add(new ImageView(Images.wallImage), i, 0);
            squares.add(new ImageView(Images.wallImage), i, prefHeight - 1);
        }

        Text title = new Text("The Dungeon Game");
        title.setFill(Color.WHITE);
        title.setFont(Font.font(null, FontWeight.BOLD, 70));

        Button startButton = new Button("Start (S)");
        startButton.setId("button");
        startButton.setOnMouseClicked(e -> {
            try {
                sceneSelector.loadNextLevel();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        Button levelButton = new Button("Choose level (L)");
        levelButton.setId("button");
        levelButton.setOnAction(e -> displayLevelsMenu());

        Button exitButton = new Button("Exit (E)");
        exitButton.setId("button");

        menu.getChildren().addAll(title, startButton, levelButton, exitButton);
    }

    @FXML
    public void handleKeyPress(KeyEvent event) throws IOException {
        switch (event.getCode()) {
            case S:
                sceneSelector.loadNextLevel();
                break;
            case L:
                displayLevelsMenu();
                break;
            case E:
                if (stack.getChildren().size() == 3)
                    closeLevelsMenu();
                else
                    sceneSelector.close();
                break;
            default:
                break;
        }
    }
}

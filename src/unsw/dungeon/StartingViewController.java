package unsw.dungeon;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.IOException;

public class StartingViewController {
    @FXML
    private StackPane stack;

    @FXML
    private GridPane squares;

    private final int width = 25;

    private final int height = 18;

    private Images images;

    private SceneSelector sceneSelector;

    public StartingViewController(SceneSelector sceneSelector) {
        this.images = new Images();
        this.sceneSelector = sceneSelector;
    }

    @FXML
    public void initialize() {

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++)
                squares.add(new ImageView(images.groundImage), j, i);
        }

        for (int i = 0; i < height; i++) {
            squares.add(new ImageView(images.wallImage), 0, i);
            squares.add(new ImageView(images.wallImage), width - 1, i);
        }

        for (int i = 0; i < width; i++) {
            squares.add(new ImageView(images.wallImage), i, 0);
            squares.add(new ImageView(images.wallImage), i, height - 1);
        }

        VBox menu = new VBox();
        menu.setAlignment(Pos.CENTER);
        menu.setSpacing(10);
        menu.setPrefWidth(300);

        Text title = new Text("The Dungeon Game");
        title.setFill(Color.WHITE);
        title.setFont(Font.font(null, FontWeight.BOLD, 70));

        String buttonStyle = "-fx-text-fill: #006464;\n" +
                "    -fx-background-color: #DFB951;\n" +
                "    -fx-border-radius: 20;\n" +
                "    -fx-background-radius: 20;\n" +
                "    -fx-font-size: 24;\n" +
                "    -fx-padding: 5;";

        Button startButton = new Button("Start (S)");
        startButton.setStyle(buttonStyle);
        startButton.setOnMouseClicked(e -> {
            try {
                sceneSelector.loadNextLevel();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        Button exitButton = new Button("Exit (E)");
        exitButton.setStyle(buttonStyle);

        menu.getChildren().addAll(title, startButton, exitButton);

        stack.getChildren().add(menu);
    }

    public void handleKeyPress(KeyEvent event) throws IOException {
        switch (event.getCode()) {
            case S:
                sceneSelector.loadNextLevel();
                break;
            case E:
                sceneSelector.close();
                break;
            default:
                break;
        }
    }
}

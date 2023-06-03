package main.java.view;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import main.java.controller.ViewController;
import main.java.model.Game;
import main.java.pattern.Observable;
import main.java.pattern.Observer;

public class GameScoreView extends VBox implements Observer {
    private static final int PADDING = 10;

    private ViewController view;

    public GameScoreView(final ViewController view) {
        this.view = view;
        this.update();
        Observable.addObserver(Game.class, this);
        this.setPadding(new Insets(PADDING));
        this.setSpacing(10);
    }

    @Override
    public void update() {
        this.getChildren().clear();
        StackPane currentRound = new StackPane();
        currentRound
                .setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        currentRound.setPadding(new Insets(PADDING));
        currentRound.setStyle("-fx-border-color: black; -fx-border-width: 2px;");

        Text roundText = new Text("Ronde: " + view.getCurrentRound());
        roundText.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        currentRound.getChildren().add(roundText);

        this.getChildren().add(currentRound);

        view.getScores().forEach(score -> {
            StackPane playerScore = new StackPane();

            playerScore
                    .setBackground(new Background(new BackgroundFill(Color.valueOf(score.get("color")), null, null)));
            playerScore.setPadding(new Insets(PADDING));
            playerScore.setStyle("-fx-border-color: black; -fx-border-width: 2px;");

            Text text = new Text(score.get("username") + ": " + score.get("score"));
            text.setFill(Color.WHITE);
            text.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-stroke: black; -fx-stroke-width: 0.5px;");

            playerScore.getChildren().add(text);

            this.getChildren().add(playerScore);
        });
    }
}

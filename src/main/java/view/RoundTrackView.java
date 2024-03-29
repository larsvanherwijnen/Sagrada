package main.java.view;

import java.util.ArrayList;
import java.util.Map;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import main.java.controller.ViewController;
import main.java.model.Game;
import main.java.pattern.Observable;
import main.java.pattern.Observer;

public class RoundTrackView extends StackPane implements Observer {
    private static final int PADDING = 5;
    private static final int BASESIZE = 50;
    private static final int TEXTPADDING = 2;
    private static final int ROUNDS = 10;
    private static final int ROUNDING = 20;
    private static final double GLOBALSCALING = 1.2;
    private static final int WIDTH = 570;
    private static final int HEIGHT = 85;
    private static final int CHANGETO3X3 = 4;
    private static final int EXPANDEDDICEDISPLAYSIZE = 3;
    private static final int SIZE = (int) Math.round(BASESIZE * GLOBALSCALING);

    private final ArrayList<Group> roundGroups;
    private final ViewController view;

    public RoundTrackView(final ViewController view) {
        this.roundGroups = new ArrayList<>();
        this.view = view;

        this.setMaxSize(WIDTH, HEIGHT);
        this.setBackground(new Background(new BackgroundFill(Color.MAROON, new CornerRadii(ROUNDING), null)));

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(PADDING));
        gridPane.setVgap(PADDING);
        gridPane.setHgap(PADDING);

        for (int i = 0; i < ROUNDS; i++) {
            VBox vbox = new VBox();
            Group group = new Group();
            GridPane diceDisplay = new GridPane();
            diceDisplay.setMaxWidth(SIZE);
            diceDisplay.setMaxHeight(SIZE);
            Rectangle diceBackground = new Rectangle(SIZE, SIZE);
            diceBackground.setFill(Color.BEIGE);
            group.getChildren().add(0, diceBackground);
            group.getChildren().add(1, diceDisplay);
            roundGroups.add(group);

            Text roundNumber = new Text(Integer.toString(i + 1));
            roundNumber.setFill(Color.WHITE);
            TextFlow roundNumberFlow = new TextFlow(roundNumber);
            roundNumberFlow.setPadding(new Insets(TEXTPADDING));
            roundNumberFlow.setTextAlignment(TextAlignment.CENTER);
            vbox.setAlignment(Pos.CENTER);
            vbox.getChildren().addAll(group, roundNumberFlow);
            gridPane.add(vbox, i, 0);
        }
        StackPane.setMargin(gridPane, (new Insets(PADDING)));
        this.getChildren().add(gridPane);

        Observable.addObserver(Game.class, this);

        update();
    }

    @Override
    public void update() {
        int previousRoundtrack = -1;
        for (Map<String, String> die : view.getRoundTrack()) {
            int currentRoundTrack = Integer.parseInt(die.get("roundtrack")) - 1;
            Group currentDiceGroup = roundGroups.get((currentRoundTrack) / 2);
            GridPane diceDisplay = (GridPane) currentDiceGroup.getChildren().get(1);

            if (previousRoundtrack != currentRoundTrack) {
                diceDisplay.getChildren().clear();
            }

            DieView newDice = new DieView(this.view, Integer.parseInt(die.get("eyes")), Color.web(die.get("color")),
                Integer.parseInt(die.get("number")), false);

            int alreadyPlacedDice = diceDisplay.getChildren().size();
            if (alreadyPlacedDice >= 1) {
                if (alreadyPlacedDice == CHANGETO3X3) {
                    diceDisplay.add(newDice, 2, 0);
                } else if (alreadyPlacedDice >= CHANGETO3X3 + 1) {
                    diceDisplay.add(newDice, alreadyPlacedDice % EXPANDEDDICEDISPLAYSIZE, alreadyPlacedDice / EXPANDEDDICEDISPLAYSIZE);
                } else {
                    diceDisplay.add(newDice, alreadyPlacedDice % 2, alreadyPlacedDice / 2);
                }
            } else {
                diceDisplay.add(newDice, 0, 0);
            }
            previousRoundtrack = currentRoundTrack;
        }

        for (Group diceGroup : roundGroups) {
            GridPane diceDisplay = (GridPane) diceGroup.getChildren().get(1);
            for (Node dieNode : diceDisplay.getChildren()) {
                DieView die = (DieView) dieNode;
                int scaledown = 1;
                if (diceDisplay.getChildren().size() > 1) {
                    scaledown = 2;
                    if (diceDisplay.getChildren().size() > CHANGETO3X3) {
                        scaledown = EXPANDEDDICEDISPLAYSIZE;
                    }
                }
                switch (scaledown) {
                    case 2:
                        GridPane.setMargin(die, new Insets(-BASESIZE / scaledown / scaledown));
                        break;
                    case EXPANDEDDICEDISPLAYSIZE:
                        GridPane.setMargin(die, new Insets(-BASESIZE / scaledown));
                        break;
                    default:
                        break;
                }
                diceDisplay.setPadding(new Insets((SIZE - BASESIZE) / 2));
                diceDisplay.setTranslateY(scaledown * -1 + 1);
                diceDisplay.setTranslateX(scaledown * -1 + 1);
                die.setScaleX(GLOBALSCALING / (double) scaledown);
                die.setScaleY(GLOBALSCALING / (double) scaledown);
            }
        }
    }
}

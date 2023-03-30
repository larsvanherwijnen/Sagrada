package view;

import controller.ViewController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class MenuView extends VBox {

    private ViewController view;

    private Text textGreet;

    private Button buttonGames;
    private Button buttonStats;
    private Button buttonLogout;

    private int buttonHeight = 25;
    private int buttonWidth = 200;

    private int padding = 200;
    private int spacing = 15;

    public MenuView(ViewController view) {
        this.view = view;
        this.setBackground(view.getBackground());

        this.setAlignment(Pos.CENTER);

        this.textGreet = new Text("Welkom " + view.getAccountController().getAccount().getUsername() + "!");
        this.textGreet.setStyle("-fx-font-size: 20px;");
        this.textGreet.setWrappingWidth(this.buttonWidth);
        this.textGreet.setTextAlignment(TextAlignment.CENTER);
        this.textGreet.setFill(Color.WHITE);

        this.buttonGames = new Button("Spellen");
        this.buttonGames.setPrefSize(this.buttonWidth, this.buttonHeight);
        this.buttonGames.setOnAction(e -> view.openGamesView());

        this.buttonStats = new Button("Statistieken");
        this.buttonStats.setPrefSize(this.buttonWidth, this.buttonHeight);
        this.buttonStats.setOnAction(e -> view.openStatsView());

        this.buttonLogout = new Button("Uitloggen");
        this.buttonLogout.setPrefSize(this.buttonWidth, this.buttonHeight);
        this.buttonLogout.setOnAction(e -> this.logout());

        this.setSpacing(this.spacing);
        this.setPadding(new Insets(0, this.padding, 0, this.padding));

        this.getChildren().addAll(view.getLogo(), this.textGreet, this.buttonGames, this.buttonStats, this.buttonLogout);
    }

    private void logout() {
        view.getAccountController().logoutAccount();
        view.openLoginView();
    }
}

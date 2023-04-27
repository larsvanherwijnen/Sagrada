package main.java.view;

import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class DieDropTarget extends StackPane {

    public DieDropTarget() {
        this.setOnDragOver(event -> {
            if (event.getGestureSource() instanceof DieView) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });

        this.setOnDragEntered(event -> {
            if (event.getGestureSource() instanceof DieView) {
                GridPane gridPane = (GridPane) this.getParent();
                System.out.println("Y" + gridPane.getColumnIndex(this));
                System.out.println("X" + gridPane.getRowIndex(this));

                this.setStyle("-fx-border-color: blue;");
            }
        });

        this.setOnDragExited(event -> {
            this.setStyle("");
        });

        this.setOnDragDropped(event -> {
            if (event.getGestureSource() instanceof DieView) {
                DieView dieView = (DieView) event.getGestureSource();
                this.getChildren().add(dieView);
            }

            event.setDropCompleted(true);
            event.consume();
        });
    }
}
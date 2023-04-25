package main.java.view;

import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class DieView extends Group {

    private static final int DOTRADIUS = 3;
    private static final int RECTANGLE = 50;

    private static final int POSITIONLOW = 15;
    private static final int POSITIONMEDIUM = 25;
    private static final int POSITIONHIGH = 35;

    private final int radius = 10;
    private final double scale = 0.8;

    private int value;

    private double dragStartX;
    private double dragStartY;

    public DieView(final int value, final Color color, final Boolean isDraggable) {
        this.value = value;

        Rectangle rectangle = new Rectangle(RECTANGLE, RECTANGLE);
        rectangle.setFill(Color.rgb(0, 0, 0, 0));

        Rectangle die = new Rectangle(RECTANGLE, RECTANGLE);
        die.setFill(color);
        die.setStroke(Color.BLACK);

        die.setArcHeight(this.radius);
        die.setArcWidth(this.radius);

        die.setScaleX(scale);
        die.setScaleY(scale);
        die.setTranslateX((rectangle.getWidth() - die.getWidth()) / 2);

        if (isDraggable) {
            this.setOnDragDetected(event -> {
                Dragboard db = this.startDragAndDrop(TransferMode.ANY);
                ClipboardContent content = new ClipboardContent();
                Image image = this.snapshot(new SnapshotParameters(), null);
                content.putImage(image);
                db.setContent(content);
                event.consume();
            });

            this.setOnDragDone(event -> {
                if (event.getTransferMode() == TransferMode.MOVE) {
                    this.getParent().getChildrenUnmodifiable().remove(this);
                }
                event.consume();
            });
        }

        die.setTranslateX((rectangle.getWidth() - die.getWidth()) / 2);

        this.getChildren().addAll(rectangle, die, this.addDotsToDie());
    }

    public DieView(final int value) {
        this.value = value;

        Rectangle rectangle = new Rectangle(RECTANGLE, RECTANGLE);
        rectangle.setFill(Color.WHITE);

        this.getChildren().addAll(rectangle, this.addDotsToDie());
    }

    private Group addDotsToDie() {
        ArrayList<Circle> dots = new ArrayList<Circle>();

        switch (Integer.toString(this.value)) {
            case "1":
                dots.add(createDot(POSITIONMEDIUM, POSITIONMEDIUM));
                break;
            case "2":
                dots.add(createDot(POSITIONLOW, POSITIONLOW));
                dots.add(createDot(POSITIONHIGH, POSITIONHIGH));
                break;
            case "3":
                dots.add(createDot(POSITIONLOW, POSITIONLOW));
                dots.add(createDot(POSITIONMEDIUM, POSITIONMEDIUM));
                dots.add(createDot(POSITIONHIGH, POSITIONHIGH));
                break;
            case "4":
                dots.add(createDot(POSITIONLOW, POSITIONLOW));
                dots.add(createDot(POSITIONHIGH, POSITIONLOW));
                dots.add(createDot(POSITIONLOW, POSITIONHIGH));
                dots.add(createDot(POSITIONHIGH, POSITIONHIGH));
                break;
            case "5":
                dots.add(createDot(POSITIONLOW, POSITIONLOW));
                dots.add(createDot(POSITIONHIGH, POSITIONLOW));
                dots.add(createDot(POSITIONMEDIUM, POSITIONMEDIUM));
                dots.add(createDot(POSITIONLOW, POSITIONHIGH));
                dots.add(createDot(POSITIONHIGH, POSITIONHIGH));
                break;
            case "6":
                dots.add(createDot(POSITIONLOW, POSITIONLOW));
                dots.add(createDot(POSITIONHIGH, POSITIONLOW));
                dots.add(createDot(POSITIONLOW, POSITIONMEDIUM));
                dots.add(createDot(POSITIONHIGH, POSITIONMEDIUM));
                dots.add(createDot(POSITIONLOW, POSITIONHIGH));
                dots.add(createDot(POSITIONHIGH, POSITIONHIGH));
                break;
            default:
                break;
        }

        Group dotsGroup = new Group();
        dotsGroup.getChildren().addAll(dots);

        return dotsGroup;
    }

    private Circle createDot(final double x, final double y) {
        return new Circle(x, y, DOTRADIUS, Color.BLACK);
    }
}

package main.java.model;

import java.util.Map;

import javafx.scene.paint.Color;

public class PatternCardField {

    private Color color;
    private Integer value;

    public Color getColor() {
        return this.color;
    }

    public Integer getValue() {
        return this.value;
    }

    public void setColor(final String color) {
        if (color == null) {
            this.color = Color.web("#F8F8F8");
            return;
        }
        switch (color) {
            case "red":
                this.color = Color.web("#F44336");
                return;
            case "blue":
                this.color = Color.web("#2196F3");
                return;
            case "green":
                this.color = Color.web("#4CAF50");
                return;
            case "yellow":
                this.color = Color.web("#FFEB3B");
                return;
            case "purple":
                this.color = Color.web("#9C27B0");
                return;
            default:
                this.color = Color.web("#F8F8F8");
        }
    }

    public void setValue(final String value) {
        this.value = value != null ? Integer.parseInt(value) : null;
    }

    public static PatternCardField mapToPatternCardField(final Map<String, String> map) {
        PatternCardField field = new PatternCardField();

        field.setColor(map.get("color"));
        field.setValue(map.get("value"));

        return field;
    }
}

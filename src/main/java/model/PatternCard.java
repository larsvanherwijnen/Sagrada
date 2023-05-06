package main.java.model;

import java.util.ArrayList;
import java.util.Map;

import javafx.scene.paint.Color;
import main.java.db.PatternCardDB;

public class PatternCard {
    private int idPatternCard;

    private String name;
    private int difficulty;

    private Boolean standard;

    private static final int ROWS = 4;
    private static final int COLUMNS = 5;

    private PatternCardField[][] fields = new PatternCardField[ROWS][COLUMNS];

    public static PatternCard get(final int idPatternCard) {
        return mapToPatternCard(PatternCardDB.get(idPatternCard));
    }

    public static ArrayList<PatternCard> getDefaultCards() {
        ArrayList<PatternCard> defaultCards = new ArrayList<PatternCard>();
        for (Map<String, String> cardInfo : PatternCardDB.getAllStandard()) {
            PatternCard card = mapToPatternCard(cardInfo);
            defaultCards.add(card);
        }
        return defaultCards;
    }

    public static PatternCard mapToPatternCard(final Map<String, String> patternCardMap) {
        PatternCard patternCard = new PatternCard();

        patternCard.idPatternCard = Integer.parseInt(patternCardMap.get("idpatterncard"));
        patternCard.name = patternCardMap.get("name");
        patternCard.difficulty = Integer.parseInt(patternCardMap.get("difficulty"));
        patternCard.standard = Boolean.parseBoolean(patternCardMap.get("standard"));

        for (Map<String, String> map : PatternCardDB.getAllFieldsForCard(patternCard.idPatternCard)) {
            int row = Integer.parseInt(map.get("position_y"));
            int col = Integer.parseInt(map.get("position_x"));

            patternCard.fields[row - 1][col - 1] = PatternCardField.mapToPatternCardField(map);
        }

        return patternCard;
    }

    public int getIdPatternCard() {
        return this.idPatternCard;
    }

    public String getName() {
        return this.name;
    }

    public int getDifficulty() {
        return this.difficulty;
    }

    public Boolean getStandard() {
        return this.standard;
    }

    public PatternCardField getField(final int row, final int column) {
        return fields[row - 1][column - 1];
    }

    public ArrayList<int[]> getPossibleMoves(final Board board, final int dieValue, final Color dieColor) {
        ArrayList<int[]> possibleMoves = new ArrayList<int[]>();
        for (int row = 1; row <= ROWS; row++) {
            for (int col = 1; col <= COLUMNS; col++) {
                if (validateMove(board, dieValue, dieColor, col, row)) {
                    int[] move = {row, col};
                    possibleMoves.add(move);
                }
            }
        }

        return possibleMoves;
    }

    public boolean validateMove(final Board board, final int dieValue, final Color dieColor, final int columnIndex,
            final int rowIndex) {

        if (board.getField(rowIndex, columnIndex) != null) {
            return false;
        }

        if (board.isEmpty() && !isOnSideOrCorner(rowIndex, columnIndex)) {
            return false;
        }

        if (this.getField(rowIndex, columnIndex).getColor() != null
                && !dieColor.equals(this.getField(rowIndex, columnIndex).getColor())) {
            return false;
        }

        if (this.getField(rowIndex, columnIndex).getValue() != null
                && dieValue != this.getField(rowIndex, columnIndex).getValue()) {

            return false;
        }

        if (!validateAgainstAdjacentFields(rowIndex, columnIndex, dieValue,
                dieColor)) {
            return false;
        }

        return true;
    }

    private boolean isOnSideOrCorner(final int row, final int col) {
        return row == 1 || row == ROWS || col == 1 || col == COLUMNS;
    }

    private boolean validateAgainstAdjacentFields(
            final int rowIndex,
            final int columnIndex,
            final int dieValue,
            final Color dieColor) {
        ArrayList<int[]> neighbors = getNeighbors(rowIndex, columnIndex);

        for (int[] neighbor : neighbors) {
            PatternCardField neighborField = this.getField(neighbor[0], neighbor[1]);

            if (neighborField.getColor() != null && dieColor.equals(neighborField.getColor())) {
                return false;
            }

            if (neighborField.getValue() != null && dieValue == neighborField.getValue()) {
                return false;
            }
        }

        return true;
    }

    public ArrayList<int[]> getNeighbors(final int row, final int col) {
        ArrayList<int[]> neighbors = new ArrayList<>();
        int[][] offsets = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        for (int[] offset : offsets) {
            int neighborRow = row + offset[0];
            int neighborCol = col + offset[1];

            if (neighborRow > 0 && neighborRow < ROWS && neighborCol > 0 && neighborCol < COLUMNS + 1) {
                int[] neighbor = {neighborRow, neighborCol};
                neighbors.add(neighbor);
            }
        }

        return neighbors;
    }

    // public ArrayList<int[]> getNeighbors(final int row, final int col) {
    //     ArrayList<int[]> neighbors = new ArrayList<>();
    //     int rowStart = Math.max(row - 1, 0);
    //     int rowEnd = Math.min(row + 1, ROWS - 1);
    //     int colStart = Math.max(col - 1, 0);
    //     int colEnd = Math.min(col + 1, COLUMNS - 1);

    //     for (int curRow = rowStart; curRow <= rowEnd; curRow++) {
    //         for (int curCol = colStart; curCol <= colEnd; curCol++) {
    //             if (curRow != row || curCol != col) {
    //                 neighbors.add(new int[] {curRow, curCol});
    //             }
    //         }
    //     }

    //     return neighbors;
    // }
}

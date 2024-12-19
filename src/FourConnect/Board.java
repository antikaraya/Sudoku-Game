package FourConnect;

import java.awt.*;

public class Board {
    public static final int ROWS = 6;
    public static final int COLS = 7;

    public static final int CANVAS_WIDTH = Cell.SIZE * COLS;
    public static final int CANVAS_HEIGHT = Cell.SIZE * ROWS;

    Cell[][] cells;

    public Board() {
        initGame();
    }

    public void initGame() {
        cells = new Cell[ROWS][COLS];
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                cells[row][col] = new Cell(row, col);
            }
        }
    }

    public State stepGame(Seed player, int row, int col) {
        cells[row][col].content = player;

        if (hasWon(player, row, col)) {
            return (player == Seed.CROSS) ? State.CROSS_WON : State.NOUGHT_WON;
        }
        return isDraw() ? State.DRAW : State.PLAYING;
    }

    private boolean hasWon(Seed player, int row, int col) {
        return checkLine(player, row, col, 0, 1)  // Horizontal
                || checkLine(player, row, col, 1, 0)  // Vertical
                || checkLine(player, row, col, 1, 1)  // Diagonal
                || checkLine(player, row, col, 1, -1); // Anti-diagonal
    }

    private boolean checkLine(Seed player, int row, int col, int deltaRow, int deltaCol) {
        int count = 0;
        for (int d = -3; d <= 3; d++) {
            int r = row + d * deltaRow;
            int c = col + d * deltaCol;
            if (r >= 0 && r < ROWS && c >= 0 && c < COLS && cells[r][c].content == player) {
                if (++count == 4) return true;
            } else {
                count = 0;
            }
        }
        return false;
    }

    private boolean isDraw() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (cells[row][col].content == Seed.NO_SEED) {
                    return false;
                }
            }
        }
        return true;
    }

    public void paint(Graphics g, int cellSize) {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                cells[row][col].paint(g, cellSize);
            }
        }
    }
}

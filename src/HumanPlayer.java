import edu.macalester.graphics.CanvasWindow;

public class HumanPlayer extends Player {
    protected int[][] marks;

    public HumanPlayer(int playerNumber, Game game) {
        super(playerNumber, game);
        marks = new int[game.boardSize][game.boardSize];
    }

    @Override
    public boolean addMark(CanvasWindow canvas, int row, int column) {
        marks[row][column] = 1;
        Mark newMark = new Mark(imagePath, row, column);
        newMark.setMaxSize(game.squareSize * 0.8);
        newMark.setCenter(game.MARGIN + game.squareSize * column + game.squareSize / 2,
            game.MARGIN + game.squareSize * row + game.squareSize / 2);
        canvas.add(newMark.getSymbol());
        numOfMoves++;
        if (numOfMoves >= game.winCondition) {
            return checkWin(row, column);
        }
        return false;
    }

    public boolean checkWin(int row, int col) {
        int lrow, rrow, ucol, dcol, upright, dleft, upleft, dright;
        lrow = rrow = ucol = dcol = upright = dleft = upleft = dright = 1;
        lrow = checkWin(row, col - 1, lrow, LEFT);
        rrow = checkWin(row, col + 1, rrow, RIGHT);
        ucol = checkWin(row - 1, col, ucol, UP);
        dcol = checkWin(row + 1, col, dcol, DOWN);
        upright = checkWin(row - 1, col + 1, upright, UP_RIGHT);
        dleft = checkWin(row + 1, col - 1, dleft, DOWN_LEFT);
        upleft = checkWin(row - 1, col - 1, upleft, UP_LEFT);
        dright = checkWin(row + 1, col + 1, dright, DOWN_RIGHT);
        if (lrow + rrow - 1 == game.winCondition || ucol + dcol - 1 == game.winCondition ||
            upright + dleft - 1 == game.winCondition || upleft + dright - 1 == game.winCondition) {
            return true;
        }
        return false;
    }

    private int checkWin(int row, int col, int count, int pattern) {
        if (row < marks.length && row >= 0 && col < marks.length && col >= 0) { // assume that marks is a square array
            if (marks[row][col] == 1) {
                count = count + 1;
                if (pattern == LEFT)
                    return checkWin(row, col - 1, count, pattern);
                if (pattern == RIGHT)
                    return checkWin(row, col + 1, count, pattern);
                if (pattern == UP)
                    return checkWin(row - 1, col, count, pattern);
                if (pattern == DOWN)
                    return checkWin(row + 1, col, count, pattern);
                if (pattern == UP_RIGHT)
                    return checkWin(row - 1, col + 1, count, pattern);
                if (pattern == DOWN_LEFT)
                    return checkWin(row + 1, col - 1, count, pattern);
                if (pattern == UP_LEFT)
                    return checkWin(row - 1, col - 1, count, pattern);
                if (pattern == DOWN_RIGHT)
                    return checkWin(row + 1, col + 1, count, pattern);
            }
        }
        return count;
    }

    public int[][] getMarks() {
        return marks;
    }
}

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Point;

public class SinglePlayerGame extends Game {
    private AIPlayer2 aiPlayer;
    private HumanPlayer humanPlayer;

    public SinglePlayerGame(CanvasWindow canvas) {
        super(canvas);
        this.winCondition = 3;
        this.numberOfPlayers = 2;
        this.boardSize = 3;
        this.squareSize = ((CANVAS_SIZE - 2 * MARGIN) / boardSize);
    }

    @Override
    public void play() {
        board = new int[boardSize][boardSize];
        humanPlayer = new HumanPlayer(1, this);
        players.add(humanPlayer);
        aiPlayer = new AIPlayer2(2, this);
        players.add(aiPlayer);
        canvas.onClick(e -> {
            Point pos = e.getPosition();
            if (playMode && clickInRange(pos)) {
                int column = (int) Math.floor((pos.getX() - MARGIN) / squareSize);
                int row = (int) Math.floor((pos.getY() - MARGIN) / squareSize);
                if (board[row][column] != 0) {
                    turnDisplay.setText("Cannot add moves in a filled cell.");
                } else {
                    // human's turn
                    board[row][column] = 1;
                    if (humanPlayer.addMark(canvas, row, column)) {
                        currentPlayer = players.get(0);
                        playMode = false;
                        askToPlayAgain(true);
                    }
                    ;
                    turnDisplay.setText("Next turn: player " + ((turn + 1) % numberOfPlayers + 1));
                    canvas.draw();
                    turn++;
                    movesCount++;

                    // AI's turn
                    int[] bestMove = aiPlayer.takeBestMove(board);
                    if (board[bestMove[0]][bestMove[1]] == 0) {
                        board[bestMove[0]][bestMove[1]] = 2;
                        aiPlayer.addMark(canvas);
                    }

                    turnDisplay.setText("Next turn: player " + ((turn + 1) % numberOfPlayers + 1));
                    turn++;
                    movesCount++;

                    int winner = checkWinner();
                    if (winner != -1) {
                        playMode = false;
                        currentPlayer = players.get(winner - 1);
                        askToPlayAgain(true);
                    } else if (movesCount >= boardSize * boardSize) {
                        playMode = false;
                        askToPlayAgain(false);
                    }
                }

            }
        });
    }

    private int checkWinner() {
        int winner = -1;
        for (int i = 0; i < board.length; i++) {
            if (equals3(board[i][0], board[i][1], board[i][2])) {
                winner = board[i][1];
            }
            if (equals3(board[0][i], board[1][i], board[2][i])) {
                winner = board[0][i];
            }
        }
        if (equals3(board[0][0], board[1][1], board[2][2])) {
            winner = board[0][0];
        }
        if (equals3(board[0][2], board[1][1], board[2][0])) {
            winner = board[0][2];
        }
        return winner;
    }

    private boolean equals3(int a, int b, int c) {
        if (a != 0 && b != 0 && c != 0) {
            return (a == b && b == c);
        }
        return false;
    }
}

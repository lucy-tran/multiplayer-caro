import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Point;

public class MultiplayerGame extends Game {
    public MultiplayerGame(CanvasWindow canvas) {
        super(canvas);
    }

    @Override
    public void play() {
        board = new int[boardSize][boardSize];
        for (int i = 0; i < numberOfPlayers; i++) {
            players.add(new HumanPlayer(i + 1, this));
        }
        canvas.onClick(e -> {
            currentPlayer = players.get(turn % numberOfPlayers);
            Point pos = e.getPosition();
            if (playMode && currentPlayer != null && clickInRange(pos)) {
                int column = (int) Math.floor((pos.getX() - MARGIN) / squareSize);
                int row = (int) Math.floor((pos.getY() - MARGIN) / squareSize);
                if (board[row][column] != 0) {
                    turnDisplay.setText("Cannot add moves in a filled cell.");
                } else {
                    movesCount++;
                    board[row][column] = currentPlayer.playerNumber;
                    if (currentPlayer.addMark(canvas, row, column)) {
                        playMode = false;
                        askToPlayAgain(true);
                    } else if (movesCount == boardSize * boardSize) {
                        playMode = false;
                        askToPlayAgain(false);
                    }
                    turnDisplay.setText("Next turn: player " + ((turn + 1) % numberOfPlayers + 1));
                    turn += 1;
                }
            }
        });
    }

    // ---------------------------- Setters and Getters ---------------------------------
    public void setNumOfPlayers(int num) {
        this.numberOfPlayers = num;
    }

    public void setBoardSize(int num) {
        this.boardSize = num;
    }

    public void setWinCondition(int num) {
        this.winCondition = num;
    }

}

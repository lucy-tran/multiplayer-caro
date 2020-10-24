import edu.macalester.graphics.*;

public class Player {
    protected String imagePath;
    protected int numOfMoves;
    protected int playerNumber;
    protected Game game;
    protected int LEFT = 1;
    protected int RIGHT = 2;
    protected int UP = 3;
    protected int DOWN = 4;
    protected int UP_RIGHT = 5;
    protected int DOWN_LEFT = 6;
    protected int UP_LEFT = 7;
    protected int DOWN_RIGHT = 8;

    public Player(int playerNumber, Game game) {
        this.playerNumber = playerNumber;
        this.game = game;
        numOfMoves = 0;
        switch (playerNumber) {
            case 1:
                imagePath = "red o.png";
                break;
            case 2:
                imagePath = "black x.png";
                break;
            case 3:
                imagePath = "black o.png";
                break;
            case 4:
                imagePath = "red x.png";
                break;
            case 5:
                imagePath = "red check.png";
                break;
            case 6:
                imagePath = "black check.png";
                break;
            default:
                imagePath = "black x.png";
        }
    }

    public boolean addMark(CanvasWindow canvas, int row, int column) {
        // implemented in subclasses
        return false;
    }

    public String notifyWin() {
        return "Player " + playerNumber + " wins the game!";
    }
}

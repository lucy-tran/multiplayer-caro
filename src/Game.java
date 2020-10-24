import edu.macalester.graphics.*;
import edu.macalester.graphics.Rectangle;
import edu.macalester.graphics.ui.Button;
import edu.macalester.graphics.Point;
import edu.macalester.graphics.Image;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class Game {
    protected CanvasWindow canvas;
    public int winCondition;
    public int numberOfPlayers;
    public int boardSize;
    public double squareSize;
    public int movesCount;
    public int[][] board;

    protected final double CANVAS_SIZE;
    protected final double MARGIN = 50;
    protected GraphicsText turnDisplay;
    protected int turn;
    protected boolean playMode;
    protected Player currentPlayer;
    protected List<Player> players;

    public Game(CanvasWindow canvas) {
        this.canvas = canvas;
        this.CANVAS_SIZE = canvas.getHeight();
        players = new ArrayList<>();
        movesCount = 0;
    }

    public void resetGame() {
        turn = 0;
        setBackground();
        drawLines();
        turnDisplay = new GraphicsText();
        turnDisplay.setText("Next turn: player 1");
        turnDisplay.setPosition(50, 35);
        turnDisplay.setFontSize(25);
        canvas.add(turnDisplay);
        playMode = true;
        play();
    }

    protected void play() {
        // implemented in the subclasses
    }

    protected void askToPlayAgain(boolean someoneWins) {
        Rectangle rectangle = new Rectangle(0, 0, 250, 150);
        rectangle.setCenter(CANVAS_SIZE * 0.5, CANVAS_SIZE * 0.5);
        rectangle.setFillColor(new Color(255, 246, 249, 236));

        GraphicsText ask = new GraphicsText();
        ask.setText("Do you want to play again?");
        ask.setCenter(CANVAS_SIZE * 0.5, CANVAS_SIZE * 0.5 - 10);

        GraphicsText notifier = new GraphicsText();
        if (!someoneWins) {
            notifier.setText("That was a tie!");
        } else {
            notifier.setText(currentPlayer.notifyWin());
        }
        notifier.setCenter(CANVAS_SIZE * 0.5 - 10, CANVAS_SIZE * 0.5 - 40);
        notifier.setFont(FontStyle.BOLD, 15);
        notifier.setFillColor(Color.RED);

        canvas.add(rectangle);
        canvas.add(ask);
        canvas.add(notifier);

        Button yes = new Button("Yes    ");
        yes.setCenter(CANVAS_SIZE * 0.5 - 50, CANVAS_SIZE * 0.5 + 45);
        Button no = new Button("No    ");
        no.setCenter(CANVAS_SIZE * 0.5 + 50, CANVAS_SIZE * 0.5 + 45);
        canvas.add(yes);
        canvas.add(no);
        yes.onClick(() -> {
            canvas.removeAll();
            players = new ArrayList<>();
            currentPlayer = null;
            new WelcomeWindow(canvas);
            canvas.draw();
        });
        no.onClick(() -> {
            canvas.closeWindow();
        });
    }

    protected boolean clickInRange(Point pos) {
        return (pos.getX() <= CANVAS_SIZE - MARGIN && pos.getX() >= MARGIN && pos.getY() <= CANVAS_SIZE - MARGIN
            && pos.getY() >= MARGIN);
    }

    protected void drawLines() {
        squareSize = ((CANVAS_SIZE - 2 * MARGIN) / boardSize);
        double x = MARGIN;
        double y = MARGIN;

        // Creates rows
        for (int j = 0; j < boardSize + 1; j++) {
            Line line = new Line(x, y, MARGIN + boardSize * squareSize, y);
            line.setStrokeColor(Color.BLACK);
            canvas.add(line);
            y = y + squareSize;
        }

        // Creates columns
        x = MARGIN;
        y = MARGIN;

        for (int j = 0; j < boardSize + 1; j++) {
            Line line = new Line(x, y, x, MARGIN + boardSize * squareSize);
            line.setStrokeColor(Color.BLACK);
            canvas.add(line);
            x += squareSize;
        }
    }

    private void setBackground() {
        Image background = new Image("background1.jpg");
        background.setMaxHeight(canvas.getHeight());
        canvas.add(background);
    }

}

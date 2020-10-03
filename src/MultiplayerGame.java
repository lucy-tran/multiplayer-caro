import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsObject;
import edu.macalester.graphics.*;
import edu.macalester.graphics.Point;
import edu.macalester.graphics.Rectangle;
import edu.macalester.graphics.ui.Button;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MultiplayerGame {
    private CanvasWindow canvas;
    private GraphicsGroup playBoard;
    private Player currentPlayer;
    private final double CANVAS_SIZE = 600;
    private final int MARGIN = 50;
    private double squareSize;
    private int turn;
    private List<Player> players;
    private boolean playMode; // true: game in play
    public static int rectangleCount;

    public MultiplayerGame(CanvasWindow canvas) {
        this.canvas = canvas;
        playBoard = new GraphicsGroup();
        canvas.add(playBoard);
        players = new ArrayList<>();
        squareSize = Math.round((CANVAS_SIZE - 2 * MARGIN) / WelcomeWindow.boardSize);
        for (int i = 0; i < WelcomeWindow.numberOfPlayers; i++) {
            players.add(new Player(i + 1));
        }
        resetGame();
    }

    private void resetGame() {
        canvas.removeAll();
        playBoard.removeAll();
        rectangleCount = 0;
        turn = 0;
        drawRectangle();
        drawLine();
        canvas.add(playBoard);
        playMode = true;
        play();

    }

    private void play() {
        canvas.onClick(e -> {
            if (!players.isEmpty()) {
                currentPlayer = players.get(turn % WelcomeWindow.numberOfPlayers);
            }
            if (playMode && isRectangle(e.getPosition()) != null && currentPlayer != null) {
                currentPlayer.addMark(playBoard, Objects.requireNonNull(isRectangle(e.getPosition())));
                canvas.draw();
                if (currentPlayer.checkWin()) {
                    playMode = false;
                    askToPlayAgain(true);
                } else if (rectangleCount == 0) {
                    playMode = false;
                    askToPlayAgain(false);
                }
                turn += 1;
            }
        });

    }

    private void askToPlayAgain(boolean win) {
        Rectangle rectangle = new Rectangle(0, 0, 250, 150);
        rectangle.setCenter(300, 300);
        rectangle.setFillColor(new Color(255, 246, 249, 236));
        GraphicsText ask = new GraphicsText();
        GraphicsText notifier = new GraphicsText();
        if (!win) {
            notifier.setText("It was a draw game.");
        } else {
            notifier.setText(currentPlayer.notifyWin());
        }
        ask.setText("Do you want to play again?");
        notifier.setCenter(290, 255);
        notifier.setFont(FontStyle.BOLD, 15);
        notifier.setFillColor(Color.RED);
        ask.setCenter(300, 285);
        canvas.add(rectangle);
        canvas.add(ask);
        canvas.add(notifier);
        Button yes = new Button("Yes");
        yes.setCenter(250, 345);
        Button no = new Button("No");
        no.setCenter(350, 345);
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

    private BoardSquare isRectangle(Point location) {
        GraphicsObject obj = playBoard.getElementAt(location);
        if (obj instanceof Rectangle) {
            return (BoardSquare) obj;
        }
        return null;
    }

    private void drawRectangle() {
        int x = MARGIN;

        for (int i = 0; i < WelcomeWindow.boardSize; i++) {
            int y = MARGIN;
            for (int j = 0; j < WelcomeWindow.boardSize; j++) {
                BoardSquare square = new BoardSquare(x, y, squareSize, squareSize, j, i);
                square.setFillColor(Color.WHITE);
                playBoard.add(square);
                y += squareSize;
                rectangleCount++;
            }
            x += squareSize;
        }
    }

    private void drawLine() {
        int x = MARGIN;
        int y = MARGIN;

        // Creates lines in row
        for (int j = 0; j < WelcomeWindow.boardSize + 1; j++) {
            Line line = new Line(x, y, MARGIN + WelcomeWindow.boardSize * squareSize, y);
            line.setStrokeColor(Color.BLACK);
            playBoard.add(line);
            y += squareSize;
        }

        // Creates lines in column
        x = MARGIN;
        y = MARGIN;

        for (int j = 0; j < WelcomeWindow.boardSize + 1; j++) {
            Line line = new Line(x, y, x, MARGIN + WelcomeWindow.boardSize * squareSize);
            line.setStrokeColor(Color.BLACK);
            playBoard.add(line);
            x += squareSize;
        }
    }


    public static void main(String[] args) {
        CanvasWindow canvas = new CanvasWindow("Caro Game!", 600, 600);
        new WelcomeWindow(canvas);
    }

}

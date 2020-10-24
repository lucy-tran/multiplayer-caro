import edu.macalester.graphics.*;
import edu.macalester.graphics.Image;
import edu.macalester.graphics.ui.Button;

import edu.macalester.graphics.ui.TextField;

import java.awt.*;
import java.util.function.Consumer;

public class WelcomeWindow {
    private CanvasWindow canvas;
    private final double CANVAS_WIDTH;
    private final double CANVAS_HEIGHT;
    private GraphicsText welcomeText;
    private GraphicsText errorText;
    private TextField playersField, sizeField, conditionField;
    private MultiplayerGame multiplayerGame;
    private SinglePlayerGame singlePlayerGame;
    private Button readyButton, playWithAI;
    private GraphicsGroup multiplayerSettings;
    private GraphicsGroup singlePlayerSettings;

    public WelcomeWindow(CanvasWindow canvas) {
        this.canvas = canvas;
        this.CANVAS_WIDTH = canvas.getWidth();
        this.CANVAS_HEIGHT = canvas.getHeight();
        Image background = new Image("background.png");
        background.setMaxHeight(CANVAS_HEIGHT);
        canvas.add(background);

        this.multiplayerGame = new MultiplayerGame(canvas);
        this.singlePlayerGame = new SinglePlayerGame(canvas);

        // Welcome Text
        welcomeText = new GraphicsText();
        welcomeText.setText("Welcome to Caro Game!");
        welcomeText.setFont(FontStyle.BOLD, 30);
        welcomeText.setCenter(CANVAS_WIDTH * 0.5, CANVAS_HEIGHT * 0.15);
        canvas.add(welcomeText);
        canvas.draw();

        GraphicsText or = new GraphicsText();
        or.setText("- OR -");
        or.setCenter(CANVAS_WIDTH * 0.6, CANVAS_HEIGHT * 0.5);
        canvas.add(or);

        // -------------------- Settings for Multiplayer Game --------------------
        this.multiplayerSettings = new GraphicsGroup();
        multiplayerSettings.setPosition(0, 0);
        canvas.add(multiplayerSettings);

        GraphicsText modeDisplay1 = new GraphicsText();
        modeDisplay1.setText("Multiplayer Mode");
        modeDisplay1.setFont(FontStyle.BOLD, 20);
        modeDisplay1.setCenter(CANVAS_WIDTH * 0.3, CANVAS_HEIGHT * 0.275);
        multiplayerSettings.add(modeDisplay1);

        // Error Text
        errorText = new GraphicsText();
        errorText.setFont(FontStyle.ITALIC, 15);
        errorText.setFillColor(Color.RED);
        errorText.setCenter(CANVAS_WIDTH * 0.3, CANVAS_HEIGHT * 0.3);
        multiplayerSettings.add(errorText);

        // Create an input field for the number of players:
        playersField = addComponentField("Number of players: ", CANVAS_HEIGHT * 0.06);

        // Create an input field for the board size:
        sizeField = addComponentField("Size of the board: ", CANVAS_HEIGHT * 0.18);

        // Create an input field for the win condition:
        conditionField = addComponentField("Number of marks to win: ", CANVAS_HEIGHT * 0.3);

        readyButton = new Button("Start game!");
        readyButton.setCenter(CANVAS_WIDTH * 0.4, CANVAS_HEIGHT * 0.8);
        canvas.add(readyButton);

        // ----------------- Settings for Singleplayer Game (with AI player) ---------------
        this.singlePlayerSettings = new GraphicsGroup();
        singlePlayerSettings.setPosition(CANVAS_WIDTH * 0.6, 0);
        canvas.add(singlePlayerSettings);

        GraphicsText modeDisplay2 = new GraphicsText();
        modeDisplay2.setText("Singleplayer Mode");
        modeDisplay2.setFont(FontStyle.BOLD, 20);
        modeDisplay2.setCenter(CANVAS_WIDTH * 0.2, CANVAS_HEIGHT * 0.275);
        singlePlayerSettings.add(modeDisplay2);

        playWithAI = new Button("Play with the computer");
        playWithAI.setCenter(CANVAS_WIDTH * 0.2, CANVAS_HEIGHT * 0.5);
        singlePlayerSettings.add(playWithAI);

        listenToInputs();
    }

    // -------------------------- Helper methods for inputs ----------------------------
    private TextField addComponentField(String label, double margin) {
        double y = CANVAS_HEIGHT / 3 + margin;

        GraphicsText labelGraphics = new GraphicsText(label);
        labelGraphics.setFontStyle(FontStyle.BOLD);
        labelGraphics.setPosition(CANVAS_WIDTH / 20, y);
        multiplayerSettings.add(labelGraphics);

        TextField field = new TextField();
        field.setPosition(CANVAS_WIDTH / 2.8, y);
        multiplayerSettings.add(field);

        labelGraphics.setCenter(labelGraphics.getCenter().getX(), field.getCenter().getY());
        return field;
    }

    private void updateNumberOfPlayersFromField() {
        readIntField(playersField, (newNumber) -> {
            multiplayerGame.setNumOfPlayers(newNumber);
        });
    }

    private void updateBoardSizeFromField() {
        readIntField(sizeField, (newNumber) -> {
            multiplayerGame.setBoardSize(newNumber);
        });
    }

    private void updateWinConditionFromField() {
        readIntField(conditionField, (newNumber) -> {
            multiplayerGame.setWinCondition(newNumber);
        });
    }

    private void readIntField(TextField field, Consumer<Integer> updateAction) {
        try {
            updateAction.accept(
                Integer.parseInt(
                    field.getText()));
            errorText.setText("");
            errorText.setCenter(CANVAS_WIDTH * 0.3, CANVAS_HEIGHT * 0.33);
        } catch (NumberFormatException e) {
            errorText.setText("Invalid value. Please type an integer.");
            errorText.setCenter(CANVAS_WIDTH * 0.3, CANVAS_HEIGHT * 0.33);
        }
    }

    private void listenToInputs() {
        playersField.onChange((text) -> updateNumberOfPlayersFromField());
        sizeField.onChange((text) -> updateBoardSizeFromField());
        conditionField.onChange((text) -> updateWinConditionFromField());

        readyButton.onClick(() -> {
            if (multiplayerGame.numberOfPlayers < 2) {
                errorText.setText("For the number of players, please type an integer at least 2.");
                errorText.setCenter(CANVAS_WIDTH * 0.3, CANVAS_HEIGHT * 0.33);
            } else if (multiplayerGame.boardSize < 3) {
                errorText.setText("For the board size, please type an integer at least 3.");
                errorText.setCenter(CANVAS_WIDTH * 0.3, CANVAS_HEIGHT * 0.33);
            } else if (multiplayerGame.boardSize < multiplayerGame.winCondition) {
                errorText.setText("The board size must be at least the number of marks to win.");
                errorText.setCenter(CANVAS_WIDTH * 0.3, CANVAS_HEIGHT * 0.33);
            } else {
                canvas.removeAll();
                multiplayerGame.resetGame();
            }
        });

        playWithAI.onClick(() -> {
            canvas.removeAll();
            singlePlayerGame.resetGame();
        });
    }

    public static void main(String[] args) {
        CanvasWindow canvas = new CanvasWindow("Caro Game!", 600, 600);
        new WelcomeWindow(canvas);
    }

}

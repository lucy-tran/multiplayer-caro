import edu.macalester.graphics.*;
import edu.macalester.graphics.Rectangle;
import edu.macalester.graphics.ui.Button;

import edu.macalester.graphics.ui.TextField;

import java.awt.*;
import java.util.function.Consumer;

public class WelcomeWindow {
    private CanvasWindow canvas;
    private GraphicsText welcomeText;
    private GraphicsText errorText;
    private Rectangle typeDisplay;
    private TextField playersField, sizeField, conditionField;
    public static int numberOfPlayers;
    public static int boardSize;
    public static int winCondition;
    private Button readyButton;

    public WelcomeWindow(CanvasWindow canvas) {
        this.canvas = canvas;

        // Setting the welcome Text
        welcomeText = new GraphicsText();
        welcomeText.setText("Welcome to Caro Game!");
        welcomeText.setFont(FontStyle.BOLD, 20);
        welcomeText.setCenter(300, 150);
        canvas.add(welcomeText);
        canvas.draw();

        errorText = new GraphicsText();
        errorText.setFont(FontStyle.ITALIC, 15);
        errorText.setFillColor(Color.RED);
        canvas.add(errorText);

        // Create a rectangle to type:
        typeDisplay = new Rectangle(200, 200, 10, 10);

        //  Create a field to type for players:
        playersField = addComponentField("Number of players: ", typeDisplay, 10);

        // Create a field to type for size:
        sizeField = addComponentField("Size of the board: ", typeDisplay, 110);

        // Create a field to type for win condition:
        conditionField = addComponentField("Number of marks to win: ", typeDisplay, 210);

        readyButton = new Button("Start game!");
        readyButton.setCenter(400, 500);
        canvas.add(readyButton);
        playersField.onChange((text) -> updateNumberOfPlayersFromField());
        sizeField.onChange((text) -> updateBoardSizeFromField());
        conditionField.onChange((text) -> updateWinConditionFromField());

        readyButton.onClick(() -> {
            if (numberOfPlayers < 2) {
                errorText.setText("For the number of players, please type an integer at least 2.");
                errorText.setCenter(300, 180);
            } else if (boardSize < 5){
                errorText.setText("For the board size, please type an integer at least 5.");
                errorText.setCenter(300, 180);
            } else if (boardSize < winCondition){
                errorText.setText("The board size must be at least the number of marks to win.");
                errorText.setCenter(300, 180);
            }
            else {
                canvas.removeAll();
                new MultiplayerGame(canvas);
            }
        });
    }

    private TextField addComponentField(String label, GraphicsObject positionAfter, int margin) {
        double y = positionAfter.getBounds().getMaxY() + margin;

        GraphicsText labelGraphics = new GraphicsText(label);
        labelGraphics.setPosition(140, y);
        canvas.add(labelGraphics);

        TextField field = new TextField();
        field.setPosition(320, y);
        canvas.add(field);

        labelGraphics.setCenter(labelGraphics.getCenter().getX(), field.getCenter().getY());
        return field;
    }

    private void updateNumberOfPlayersFromField() {
        readIntField(playersField, (newNumber) -> {
            numberOfPlayers = newNumber;
        });
    }

    private void updateBoardSizeFromField() {
        readIntField(sizeField, (newNumber) -> {
            boardSize = newNumber;
        });
    }

    private void updateWinConditionFromField() {
        readIntField(conditionField, (newNumber) -> {
            winCondition = newNumber;
        });
    }

    private void readIntField(TextField field, Consumer<Integer> updateAction) {
        try {
            updateAction.accept(
                    Integer.parseInt(
                            field.getText()));
        } catch (NumberFormatException e) {
            errorText.setText("Invalid value. Please type an integer.");
            errorText.setCenter(300, 180);
        }
    }

}

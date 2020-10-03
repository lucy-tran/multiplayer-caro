import java.util.ArrayList;

import java.util.List;

import edu.macalester.graphics.*;


public class Player {
    private String imagePath;
    private List<Mark> marks;
    private int playerNumber;

    public Player(int playerNumber){
        this.playerNumber = playerNumber;
        marks = new ArrayList<>();
        switch (playerNumber) {
            case 1:
                imagePath = "black x.jpg";
                break;
            case 2:
                imagePath = "red o.png";
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
        }
    }

    public void addMark(GraphicsGroup board, BoardSquare square){
        Mark newMark = new Mark(imagePath, square.getColumn(), square.getRow());
        newMark.setMaxSize(square.getHeight() * 0.8);
        newMark.setCenter(square.getCenter());
        board.remove(square);
        board.add(newMark.getSymbol());
        marks.add(newMark);
        MultiplayerGame.rectangleCount--;
    }

    public boolean checkWin(){
        if (checkWinInRow()) return true;
        if (checkWinInColumn()) return true;
        if (checkWinInDiagonalUpRight()) return true;
        if (checkWinInDiagonalUpLeft()){
            return true;
        }
        return false;
    }

    private boolean checkWinInRow(){
        for (Mark m: marks){
            int count = 1;
            for (int i = 1; i < WelcomeWindow.winCondition; i ++) {
                if (isMarkInPosition(m.getColumn(), m.getRow() + i)) count += 1;
            }
            if (count == WelcomeWindow.winCondition) return true;
        }
        return false;
    }

    private boolean checkWinInColumn(){
        for (Mark m: marks){
            int count = 1;
            for (int i = 1; i < WelcomeWindow.winCondition; i ++) {
                if (isMarkInPosition(m.getColumn() + i, m.getRow())) count += 1;
            }
            if (count == WelcomeWindow.winCondition) return true;
        }
        return false;
    }

    private boolean checkWinInDiagonalUpRight(){
        for (Mark m: marks){
            int count = 1;
           for (int i = 1; i < WelcomeWindow.winCondition; i ++) {
                if (isMarkInPosition(m.getColumn() + i, m.getRow() + i)) count += 1;
            }
            if (count == WelcomeWindow.winCondition) return true;
        }
        return false;
    }

    private boolean checkWinInDiagonalUpLeft(){
        for (Mark m: marks){
            int count = 1;
            for (int i = 1; i < WelcomeWindow.winCondition; i ++) {
                if (isMarkInPosition(m.getColumn() + i, m.getRow() - i)) count += 1;
            }
            if (count == WelcomeWindow.winCondition) return true;
        }
        return false;
    }

    public String notifyWin(){
        return "Player " + playerNumber + " wins the game!";
    }


    private boolean isMarkInPosition(int column, int row){
        for (Mark m: marks){
            if (m.getRow() == row && m.getColumn() == column){
                return true;
            }
        }
        return false;
    }
}

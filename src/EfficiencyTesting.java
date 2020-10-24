import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.macalester.graphics.CanvasWindow;

public class EfficiencyTesting {
    Random rand = new Random();
    CanvasWindow canvas = new CanvasWindow("Testing", 100, 100);
    AIPlayer minimax = new AIPlayer(2, new SinglePlayerGame(canvas));
    AIPlayer2 AbPruning = new AIPlayer2(2, new SinglePlayerGame(canvas));

    public List<int[][]> generateBoards() {
        List<int[][]> listOfBoards = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            int[][] board = new int[3][3];
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    board[j][k] = rand.nextInt(3);
                }
            }
            listOfBoards.add(board);
        }
        return listOfBoards;
    }

    public long timeMinimax(List<int[][]> listOfBoards) {
        long sum = 0;
        for (int[][] board : listOfBoards) {
            long startTime = System.nanoTime();
            minimax.Minimax(board, 0, true);
            long endTime = System.nanoTime();
            sum = sum + (endTime - startTime);
        }
        return sum / 100;
    }

    public long timeAbPruning(List<int[][]> listOfBoards) {
        long sum = 0;
        for (int[][] board : listOfBoards) {
            long startTime = System.nanoTime();
            AbPruning.Minimax(board, 0, true, Integer.MIN_VALUE, Integer.MAX_VALUE);
            long endTime = System.nanoTime();
            sum = sum + (endTime - startTime);
        }
        return sum / 100;
    }

    public static void main(String[] args) {
        EfficiencyTesting tester = new EfficiencyTesting();
        String minimaxTitle = "Minimax's average time over 100 boards: ";
        String abTitle = "Alpha-beta pruning's average time over 100 boards: ";

        List<int[][]> boardList1 = tester.generateBoards();
        long minimaxResult = tester.timeMinimax(boardList1);
        long abPruningResult = tester.timeAbPruning(boardList1);
        System.out.println("------------ Minimax first ------------");
        System.out.println(minimaxTitle + minimaxResult);
        System.out.println(abTitle + abPruningResult);
        System.out.println();

        List<int[][]> boardList2 = tester.generateBoards();
        long abPruningResult2 = tester.timeAbPruning(boardList2);
        long minimaxResult2 = tester.timeMinimax(boardList2);
        System.out.println("------- Alpha-beta pruning first -------");
        System.out.println(minimaxTitle + minimaxResult2);
        System.out.println(abTitle + abPruningResult2);
        System.out.println();

        List<int[][]> boardList3 = tester.generateBoards();
        List<int[][]> boardList4 = tester.generateBoards();
        long minimaxResult3 = tester.timeMinimax(boardList3);
        long abPruningResult3 = tester.timeAbPruning(boardList4);
        System.out.println("---------- On different boards ----------");
        System.out.println(minimaxTitle + minimaxResult3);
        System.out.println(abTitle + abPruningResult3);
    }
}

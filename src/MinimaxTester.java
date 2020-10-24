import java.util.Arrays;

public class MinimaxTester {
    private static int[] bestMove = new int[2];
    private static int[] scores = { 0, -1, 1 };

    private static int Minimax(int[][] board, int depth, boolean isMaximizer) {
        bestMove = new int[2];
        Integer winner = checkWinner(board);
        if (winner != null) {
            return scores[winner];
        }
        if (isMaximizer) {
            int bestVal = Integer.MIN_VALUE;
            for (int r = 0; r < board.length; r++) {
                for (int c = 0; c < board.length; c++) {
                    if (board[r][c] == 0) {
                        board[r][c] = 2;
                        int value = Minimax(board, depth - 1, false);
                        board[r][c] = 0;
                        if (value > bestVal) {
                            bestVal = value;
                            if (depth == 0) {
                                bestMove[0] = r;
                                bestMove[1] = c;
                            }
                        }
                    }
                }
            }
            return bestVal;
        } else {
            int bestVal = Integer.MAX_VALUE;
            for (int r = 0; r < board.length; r++) {
                for (int c = 0; c < board.length; c++) {
                    if (board[r][c] == 0) {
                        board[r][c] = 1;
                        int value = Minimax(board, depth - 1, true);
                        board[r][c] = 0;
                        if (value < bestVal) {
                            bestVal = value;
                        }
                    }
                }
            }
            return bestVal;
        }
    }

    private static Integer checkWinner(int[][] board) {
        Integer winner = null;
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
        if (winner == null && isBoardFilled(board)) {
            return 0;
        } else {
            return winner;
        }
    }

    private static boolean isBoardFilled(int[][] board) {
        int availSpot = 9;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] != 0)
                    availSpot--;
            }
        }
        return (availSpot == 0);
    }

    private static boolean equals3(int a, int b, int c) {
        return (a == b && b == c && a == c && a != 0);
    }

    public static void main(String[] args) {
        int[][] board = new int[3][3];
        // board[0][1] = board[1][1] = board[1][2] = 2;
        // board[1][0] = board[2][1] = board[2][2] = 1;
        board[0][1] = 2;
        board[1][0] = board[1][1] = 1;
        Minimax(board, 0, true);
        System.out.println("------------------");
        System.out.println(Arrays.toString(bestMove));
        board[bestMove[0]][bestMove[1]] = 2;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + "   ");
            }
            System.out.println();
        }
    }

}


import java.util.*;

public class Main {

    static Scanner scanner = new Scanner(System.in);
    static int n = Integer.parseInt(scanner.nextLine());
    static int nSquared = n * n;
    static int[][] sudokuBoxes = new int[nSquared][nSquared]; // box, position in box
    static int[][] sudokuMatrix = new int[nSquared][nSquared]; // row, column
    static Set<Integer> sudokuBoxesCheck = new LinkedHashSet<>();
    static Set<Integer> sudokuMatrixCheck = new LinkedHashSet<>();
    static boolean solved = true;

    public static void main(String[] args) {
        readInput();
        hashSetCheck(); // checking through sets (repetition in boxes)
        nestedLoopCheck(); // checking through loops (specifically columns)

        System.out.println(solved ? "YES" : "NO");
    }

    static void readInput() {
        int row = 0;
        int column = 0;
        int box = 0;
        int position = 0; // position inside box
        int boxColumn = 0; // 0 to n - 1 limit, for transitioning from one box to another

        for (int i = 0; i < nSquared * nSquared; i++) {
            int input = scanner.nextInt();
            if (input > nSquared || input <= 0) {
                solved = false;
                return;
            }

            sudokuMatrix[row][column++] = input;
            sudokuBoxes[box][position++] = input;
            boxColumn++;

            if (boxColumn == n) {
                boxColumn = 0;
                if (column == nSquared) {
                    column = 0;
                    row++;
                    if (row % n == 0) {
                        position = 0;
                        box++;
                    } else {
                        box = box - n + 1;
                    }
                } else {
                    position = position - n;
                    box++;
                }
            }
        }
    }

    static void hashSetCheck() {
        for (int i = 0; i < nSquared; i++) {
            for (int j = 0; j < nSquared; j++) {
                sudokuBoxesCheck.add(sudokuBoxes[i][j]);
                sudokuMatrixCheck.add(sudokuMatrix[i][j]);
            }

            if (sudokuBoxesCheck.size() != nSquared || sudokuMatrixCheck.size() != nSquared) {
                solved = false;
                return;
            }

            sudokuBoxesCheck.clear();
            sudokuMatrixCheck.clear();
        }
    }

    static void nestedLoopCheck() {
        for (int i = 0; i < nSquared; i++) {
            for (int j = 0; j < nSquared; j++) {
                for (int k = 0; k < nSquared; k++) {
                    if (i == k) {
                        continue;
                    }

                    if (sudokuMatrix[i][j] == sudokuMatrix[k][j]) {
                        solved = false;
                        return;
                    }
                }
            }
        }
    }
}
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[][] colorMatrix = new String[4][4];
        boolean pretty = true;

        for (int i = 0; i < colorMatrix.length; i++) {
            colorMatrix[i] = scanner.nextLine().split("");
        }

        outerLoop:
        for (int i = 0; i < colorMatrix.length - 1; i++) {
            for (int j = 0; j < colorMatrix.length - 1; j++) {
                if (colorMatrix[i][j].equals(colorMatrix[i][j + 1])
                        && colorMatrix[i][j].equals(colorMatrix[i + 1][j])
                        && colorMatrix[i][j].equals(colorMatrix[i + 1][j + 1])) {
                    pretty = false;
                    break outerLoop;
                }
            }
        }

        System.out.print(pretty ? "YES" : "NO");
    }
}
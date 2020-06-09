import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        String[][] array = new String[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                array[i][j] = i == j || i == n / 2 || n - i - 1 == j || j == n / 2 ? "*" : ".";
                if (j != n - 1) {
                    array[i][j] += " ";
                }
                System.out.print(array[i][j]);
            }
            System.out.println();
        }
    }
}
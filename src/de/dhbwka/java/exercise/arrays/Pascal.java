package de.dhbwka.java.exercise.arrays;

/**
 * @author DHBW lecturer
 * @version 1.0
 * <p>
 * Part of lectures on 'Programming in Java'.
 * Baden-Wuerttemberg Cooperative State University.
 * <p>
 * (C) 2015 by J. Sidler, T. Schlachter, C. Schmitt, W. Süß
 */
public class Pascal {

    public static void main(String[] args) {
        int maxRows = 9;
        int[][] pascal = new int[maxRows][];
        for (int i = 0; i < pascal.length; i++) {
            pascal[i] = new int[i + 1];
            pascal[i][0] = 1;
            pascal[i][pascal[i].length - 1] = 1;
            if (i > 1) {
                for (int j = 1; j < pascal[i].length - 1; j++) {
                    pascal[i][j] = pascal[i - 1][j - 1]
                            + pascal[i - 1][j];
                }
            }
        }
        for (int i = 0; i < pascal.length; i++) {
            // insert blanks for centered output
            for (int j = 1; j < maxRows - i; j++)
                System.out.print("  ");
            // output one row
            for (int j : pascal[i])
                System.out.printf(" %2d ", j);
            System.out.println();
        }
    }
}
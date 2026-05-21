package de.dhbwka.java.exercise.arrays;

/**
 * @author DHBW lecturer
 * @version 1.0
 * <p>
 * Part of lectures on 'Programming in Java'.
 * Baden-Wuerttemberg Cooperative State University.
 * <p>
 * (C) 2015 by W. Geiger, T. Schlachter, C. Schmitt, W. Süß
 */
public class Fibonacci {

    public static void main(String[] args) {
        int n = 20; // or 50
        int[] fib = new int[n];
        fib[0] = fib[1] = 1;
        for (int k = 2; k < fib.length; k++) {
            fib[k] = fib[k - 1] + fib[k - 2];
        }
        // output
        for (int i = 0; i < fib.length; i++) {
            System.out.printf("%2d.: %d", i, fib[i]);
            System.out.println();
        }
    }
}
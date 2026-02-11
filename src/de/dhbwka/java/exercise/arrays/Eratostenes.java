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
public class Eratostenes {

    public static void main(String[] args) {
        int max = 100;
        boolean[] prim = new boolean[max + 1];
        // initialize array with true
        for (int i = 2; i < prim.length; i++)
            prim[i] = true;
        // eratostenes' sieve
        for (int i = 2; i < prim.length; i++)
            if (prim[i])
                for (int j = i * 2; j < prim.length; j += i)
                    prim[j] = false;
        // output
        for (int i = 0; i < prim.length; i++)
            if (prim[i])
                System.out.println(i);
    }

}
package de.dhbwka.java.exercise.arrays;

import java.util.Random;

/**
 * @author DHBW lecturer
 * @version 1.0
 * <p>
 * Part of lectures on 'Programming in Java'.
 * Baden-Wuerttemberg Cooperative State University.
 * <p>
 * (C) 2015 by W. Geiger, T. Schlachter, C. Schmitt, W. Süß
 */
public class StandardDeviation {

    public static void main(String[] args) {
        int n = 33;
        Random rnd = new Random();
        int[] x = new int[n];

        // generate random numbers and calculate average
        int sum = 0;
        for (int i = 0; i < x.length; i++) {
            x[i] = rnd.nextInt(11); // 0..10
            sum += x[i]; // sum up the x[i] gleich bedeutend wie sum = sum + x[i]
        }
        double average = ((double) sum / n);
        System.out.printf("Mittelwert: %.8f\n", average);

        // calculate standard deviation
        double devSum = 0;
        for (int i = 0; i < x.length; i++) {
            devSum += Math.pow(x[i] - average, 2);
        }
        double deviation = Math.sqrt(devSum / (n - 1));
        System.out.println("Standardabweichung: " + deviation);
    }

}
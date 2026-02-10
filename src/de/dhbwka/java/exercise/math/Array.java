package de.dhbwka.java.exercise.math;

public class Array {
    public static void main(String[] args) {
        int size = 10;
        double[] array = new double[size];
        for (int i = 0; i < array.length; i++) {
            array[i] = Math.random() * 100;
        }
        for (double v : array) {
            System.out.println(v);
        }
        double sum = 0;
        double max = 0;
        int maxIndex = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] > max) {
                maxIndex = i;
                max = array[i];
            }
            sum += array[i];
        }
        System.out.println("Maximums index " + maxIndex);
        System.out.println("maximum " + max);
        double avg = (double) 1 / size;
        System.out.println("Average " + (avg * sum));
    }
}

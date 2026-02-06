package de.dhbwka.java.exercise.operators;

public class Quadratics {
    public static void main(String[] args) {
        int a = 2;
        int b = -3;
        int c = 1;

        calculate(a, b, c);
    }

    public static void calculate(int a, int b, int c) {
        double[] x = new double[2];

        if (a == 0) {
            if (b == 0) {
                x[0] = (c * (-1));
                System.out.println("Lösung: " + x[0]);
            } else {
                x[0] = (double) (c * (-1)) / (b);
                System.out.println("Lösung: " + x[0]);
            }
        } else {
            int d = b * b - 4 * a * c;
            if (d >= 0) {
                x[0] = (-b + Math.sqrt(d)) / (2 * a);
                x[1] = (-b - Math.sqrt(d)) / (2 * a);

                System.out.println("1. Lösung: " + x[0]);
                System.out.println("2. Lösung: " + x[1]);
            } else
                System.out.println("Keine Lösung");
        }
    }
}

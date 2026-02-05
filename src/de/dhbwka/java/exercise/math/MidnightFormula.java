package de.dhbwka.java.exercise;

public class MidnightFormula {
    public static void main(String[] args) {
        // f(x) = 12x² + 3x - 10
        midnightFormula(12, 3, -10);
    }


    public static void midnightFormula(double a, double b, double c) {
        if (a == 0) {
            if (b == 0) {
                System.out.println("Degenerierte Gleichung");
            } else {
                double x = -c / b;
                System.out.println("Eine Nullstelle: " + x);
            }
        } else {
            double discriminant = b * b - 4 * a * c;
            System.out.println(discriminant);

            if (discriminant == 0) {
                double x = (-b ) / (2*a);
                System.out.println("Eine Nullstelle: " + x);
            }
            else if (discriminant > 0) {
                double x1 = (-b - Math.sqrt(discriminant)) / (2*a);
                double x2 = (-b + Math.sqrt(discriminant)) / (2*a);
                System.out.printf("Zwei Nullstellen: %f und %f", x1, x2);
            } else {
                System.out.println("Keine Nullstelle");
            }
        }
    }
}

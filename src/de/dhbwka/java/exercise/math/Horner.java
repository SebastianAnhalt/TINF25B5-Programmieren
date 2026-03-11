package de.dhbwka.java.exercise.math;


public class Horner {
    /**
     * This function evaluates a polynomial as per Horner's method
     *
     * @param polynomialFactors A polynomial of the form
     *                          a_0 + a_1 * x + a_2 * x^2 + ... + a_n * x^n uniquely described by its
     *                          factors [a_0, a_1, ... , a_n]
     * @param x                 The value for which the polynomial is to be evaluated
     * @return The result of the polynomial evaluated at point x
     */
    public static double evaluateHorner(double[] polynomialFactors, double x) {


        double solution = x * polynomialFactors[polynomialFactors.length - 1];

        for (int i = 0; i < polynomialFactors.length - 1; i++) {
            double summand = polynomialFactors[i];
            solution = summand + x * solution;
        }

        return solution;
    }
}

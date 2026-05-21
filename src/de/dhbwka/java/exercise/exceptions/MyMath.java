package de.dhbwka.java.exercise.exceptions;

public class MyMath {

    public static int divide(int a, int b) throws DivZeroException {
        if (b == 0) {
            throw new DivZeroException("Division by zero");
        }

        return a / b;
    }
}

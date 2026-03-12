package de.dhbwka.java.course.exceptions;

public class MyMath {
    public static int divide(int a, int b) throws DivZeroException {
        if (b == 0) {
            throw new DivZeroException("Division by zero!");
        } else {
            return a / b; // Ganzzahldivision!
        }
    }
}


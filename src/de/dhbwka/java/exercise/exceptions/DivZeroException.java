package de.dhbwka.java.exercise.exceptions;

public class DivZeroException extends Exception{
    public DivZeroException() {
    }

    public DivZeroException(String message) {
        super(message);
    }
}

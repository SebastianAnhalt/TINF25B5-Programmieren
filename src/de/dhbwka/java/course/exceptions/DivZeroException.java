package de.dhbwka.java.course.exceptions;

public class DivZeroException extends Exception {
    public DivZeroException(){
    }
    public DivZeroException(String message){
        super(message);
    }
}

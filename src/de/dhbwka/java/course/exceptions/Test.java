package de.dhbwka.java.course.exceptions;

public class Test {
    public static void main(String[] args) {
        int res;
        try {
            res = MyMath.divide(8, 0);
            System.out.println("Result: " + res);
        } catch (DivZeroException e) {
            System.err.println(e.getMessage());
        }
    }
}

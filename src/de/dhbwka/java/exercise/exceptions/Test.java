package de.dhbwka.java.exercise.exceptions;

public class Test {

    public static void main(String[] args) {
        try {
            int result = MyMath.divide(4, 2);
            System.out.println(result);

            int result2 = MyMath.divide(4, 0);
            System.out.println(result2);

        } catch (DivZeroException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
}

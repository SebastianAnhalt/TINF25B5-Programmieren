package de.dhbwka.java.exercise.wrapper;

public class SpeedTest {
    public static void main(String[] args) {
        long iterations = 1_000_000_000L; // 1 Billion iterations

        // 1. Testing primitive 'int'
        long startTimeInt = System.currentTimeMillis();
        int primitiveCount = 0;
        for (long i = 0; i < iterations; i++) {
            primitiveCount++;
        }
        long endTimeInt = System.currentTimeMillis();
        System.out.println("Time taken by int: " + (endTimeInt - startTimeInt) + "ms");

        // 2. Testing wrapper 'Integer'
        long startTimeInteger = System.currentTimeMillis();
        Integer wrapperCount = 0;
        for (long i = 0; i < iterations; i++) {
            wrapperCount++;
        }
        long endTimeInteger = System.currentTimeMillis();
        System.out.println("Time taken by Integer: " + (endTimeInteger - startTimeInteger) + "ms");

        // Prevent JVM from optimising away unused variables
        System.out.println("Final counts: " + primitiveCount + " / " + wrapperCount);
    }
}

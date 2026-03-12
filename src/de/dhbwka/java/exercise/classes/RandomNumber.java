package de.dhbwka.java.exercise.classes;

import java.util.Arrays;
import java.util.Random;

class RandomNumbers {
    private static final int MAX = 20;
    static int[] randomNumbers;

    static {
        RandomNumbers.randomNumbers = new int[RandomNumbers.MAX];
        // Lokale Variable rand ist nicht static!
        Random rand = new Random();
        for (int i = 0; i < RandomNumbers.MAX; i++) {
            RandomNumbers.randomNumbers[i] = rand.nextInt(50);
        }
        System.out.println(Arrays.toString(RandomNumbers.randomNumbers));
    }

    void main() {
    }
}

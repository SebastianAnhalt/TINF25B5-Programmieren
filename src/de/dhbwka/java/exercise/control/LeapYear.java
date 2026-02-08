package de.dhbwka.java.exercise.control;

import java.util.Scanner;

public class LeapYear {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Welches Jahr soll auf Schaltjahr geprüft werden? ");
        int jahr = scanner.nextInt();

        boolean istSchaltjahr;

        if (jahr % 400 == 0) {
            istSchaltjahr = true;
        } else if (jahr % 100 == 0) {
            istSchaltjahr = false;
        } else if (jahr % 4 == 0) {
            istSchaltjahr = true;
        } else {
            istSchaltjahr = false;
        }

        if (istSchaltjahr) {
            System.out.println(jahr + " ist ein Schaltjahr");
        } else {
            System.out.println(jahr + " ist kein Schaltjahr");
        }

        scanner.close();
    }
}


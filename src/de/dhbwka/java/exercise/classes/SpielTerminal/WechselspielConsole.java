package de.dhbwka.java.exercise.classes.SpielTerminal;

import java.util.Random;
import java.util.Scanner;

public class WechselspielConsole {

    public static void main(String[] args) {

        Spielfeld spielfeld = new Spielfeld(9, 9, 7);
        Scanner scanner = new Scanner(System.in);

        while (true) {

            // Spielfeld ausgeben
            spielfeld.ausgeben();
            System.out.println("Punkte: " + spielfeld.getPunkte());
            System.out.println("Zug eingeben (z.B. B2B3, q zum Beenden):");

            String eingabe = scanner.nextLine();

            if (eingabe.equalsIgnoreCase("q")) {
                break;
            }

            if (eingabe.length() != 4) {
                System.out.println("Ungültige Eingabe. Bitte erneut.");
                continue;
            }

            try {
                int c1 = eingabe.charAt(0) - 'A';
                int r1 = eingabe.charAt(1) - '1';
                int c2 = eingabe.charAt(2) - 'A';
                int r2 = eingabe.charAt(3) - '1';

                boolean erfolgreich = spielfeld.tausche(r1, c1, r2, c2);
                if (!erfolgreich) {
                    System.out.println("Ungültiger Zug. Bitte erneut.");
                }
            } catch (Exception e) {
                System.out.println("Fehlerhafte Eingabe. Bitte erneut.");
            }
        }

        System.out.println("Spiel beendet. Endpunktzahl: " + spielfeld.getPunkte());
        scanner.close();
    }
}

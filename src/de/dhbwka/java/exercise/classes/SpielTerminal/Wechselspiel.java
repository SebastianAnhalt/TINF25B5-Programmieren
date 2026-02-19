package de.dhbwka.java.exercise.classes.SpielTerminal;
import de.dhbwka.java.exercise.classes.SpielGUI.Spielfeld;

import java.util.Random;
import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

public class Wechselspiel {
    static Random random = new Random();

    private static void ErstellungFeld() {
        String[] Farben = new String[] {
                "#FF0000", // Rot
                "#00FF00", // Grün
                "#0000FF", // Blau
                "#FFFF00", // Gelb
                "#FF00FF", // Magenta
                "#00FFFF", // Cyan
                "#FFA500"  // Orange
        };

        // JFrame erstellen
        JFrame frame = new JFrame("Buntes Spielfeld");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);

        // Panel für das Spielfeld mit 10x10 Grid (inkl. Kopf- und Zeilenbeschriftung)
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(10, 10)); // 10x10: 1 extra für Kopf- und Zeilenbeschriftung

        // Aufbau des Spielfeldes
        for (int row = 0; row <= 9; row++) {
            for (int col = 0; col <= 9; col++) {
                JPanel feld = new JPanel();
                feld.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                // Kopf- und Zeilenbeschriftung
                if (row == 0 && col == 0) {
                    // linke obere Ecke frei
                    feld.add(new JLabel(" "));
                } else if (row == 0) {
                    // obere Reihe: Spaltenüberschrift A-I
                    feld.add(new JLabel("" + (char) ('A' + col - 1), SwingConstants.CENTER));
                } else if (col == 0) {
                    // linke Spalte: Zeilenüberschrift 1-9
                    feld.add(new JLabel("" + row, SwingConstants.CENTER));
                } else {
                    // normale Spielfeld-Zelle mit zufälliger Farbe
                    String hex = Farben[random.nextInt(Farben.length)];
                    feld.setBackground(Color.decode(hex));
                }

                panel.add(feld);
            }
        }
        frame.add(panel);
        frame.setVisible(true);
    }

    static void main(){
        ErstellungFeld();

        de.dhbwka.java.exercise.classes.SpielGUI.Spielfeld spielfeld = new Spielfeld(9, 9, 7);
        Scanner scanner = new Scanner(System.in);

        while (true) {

            spielfeld.ausgeben();
            System.out.println("Punkte: " + spielfeld.getPunkte());
            System.out.println("Zug eingeben (z.B. B2B3, q zum Beenden):");

            String eingabe = scanner.nextLine();

            if (eingabe.equalsIgnoreCase("q")) {
                break;
            }

            if (eingabe.length() != 4) {
                System.out.println("Ungültige Eingabe. Bitte versuchen Sie es erneut.");
                continue;
            }

            try {
                int c1 = eingabe.charAt(0) - 'A';
                int r1 = eingabe.charAt(1) - '1';
                int c2 = eingabe.charAt(2) - 'A';
                int r2 = eingabe.charAt(3) - '1';

                boolean erfolgreich = spielfeld.tausche(r1, c1, r2, c2);

                if (!erfolgreich) {
                    System.out.println("Ungültiger Zug. Bitte versuchen Sie es erneut.");
                }
            } catch (Exception e) {
                System.out.println("Fehlerhafte Eingabe. Bitte versuchen Sie es erneut.");
            }
        }

        System.out.println("Spiel beendet. Endpunktzahl: " + spielfeld.getPunkte());
        scanner.close();
    }
}
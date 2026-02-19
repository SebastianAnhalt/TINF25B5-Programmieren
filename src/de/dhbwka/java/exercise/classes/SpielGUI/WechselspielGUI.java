package de.dhbwka.java.exercise.classes.SpielGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class WechselspielGUI {

    private static Spielfeld spielfeld;
    private static JPanel[][] felderGUI;
    private static int[] auswahl = new int[4]; // r1, c1, r2, c2
    private static int klickCount = 0;
    private static Color[] farben; // Mapping: Spielfeld-Index -> Farbe

    public static void main(String[] args) {
        spielfeld = new Spielfeld(9, 9, 7);

        // Farben generieren
        farben = new Color[] {
                Color.RED,
                Color.GREEN,
                Color.BLUE,
                Color.YELLOW,
                Color.MAGENTA,
                Color.CYAN,
                Color.ORANGE
        };


        JFrame frame = new JFrame("Wechselspiel GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 650);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(10, 10)); // inkl. Kopf- und Zeilenbeschriftung

        felderGUI = new JPanel[9][9];

        for (int row = 0; row <= 9; row++) {
            for (int col = 0; col <= 9; col++) {
                JPanel zelle = new JPanel();
                zelle.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                if (row == 0 && col == 0) {
                    zelle.add(new JLabel(" "));
                } else if (row == 0) {
                    zelle.add(new JLabel("" + (char) ('A' + col - 1), SwingConstants.CENTER));
                } else if (col == 0) {
                    zelle.add(new JLabel("" + row, SwingConstants.CENTER));
                } else {
                    int r = row - 1;
                    int c = col - 1;
                    zelle.setBackground(farben[spielfeld.getFarbe(r, c)]);

                    final int fr = r, fc = c;
                    zelle.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            auswahl[klickCount++] = fr;
                            auswahl[klickCount++] = fc;

                            if (klickCount == 4) {
                                boolean ok = spielfeld.tausche(auswahl[0], auswahl[1], auswahl[2], auswahl[3]);
                                klickCount = 0;

                                // GUI aktualisieren
                                aktualisiereFelder();
                            }
                        }
                    });

                    felderGUI[r][c] = zelle;
                }

                panel.add(zelle);
            }
        }

        // Punktestand anzeigen
        JLabel punkteLabel = new JLabel("Punkte: 0");
        punkteLabel.setHorizontalAlignment(SwingConstants.CENTER);

        Timer timer = new Timer(100, e -> punkteLabel.setText("Punkte: " + spielfeld.getPunkte()));
        timer.start();

        frame.setLayout(new BorderLayout());
        frame.add(punkteLabel, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private static void aktualisiereFelder() {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                felderGUI[r][c].setBackground(farben[spielfeld.getFarbe(r, c)]);
            }
        }
    }
}

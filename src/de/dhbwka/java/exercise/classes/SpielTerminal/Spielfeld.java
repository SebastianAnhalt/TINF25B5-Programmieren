package de.dhbwka.java.exercise.classes.SpielTerminal;

import java.util.Random;

public class Spielfeld {

    private int[][] feld;
    private int zeilen;
    private int spalten;
    private int farben;
    private int punkte;

    private Random random = new Random();

    public Spielfeld(int zeilen, int spalten, int farben) {
        this.zeilen = zeilen;
        this.spalten = spalten;
        this.farben = farben;
        this.feld = new int[zeilen][spalten];
        initialisiere();
    }

    private void initialisiere() {
        for (int r = 0; r < zeilen; r++) {
            for (int c = 0; c < spalten; c++) {

                int farbe;
                do {
                    farbe = random.nextInt(farben);
                    feld[r][c] = farbe;
                } while (entstehtReihe(r, c));
            }
        }
    }

    private boolean entstehtReihe(int r, int c) {

        // Überprüfen der horizontalen Reihe
        if (c >= 2 &&
                feld[r][c] == feld[r][c - 1] &&
                feld[r][c] == feld[r][c - 2]) {
            return true;
        }

        // Überprüfen der vertikalen Reihe
        if (r >= 2 &&
                feld[r][c] == feld[r - 1][c] &&
                feld[r][c] == feld[r - 2][c]) {
            return true;
        }

        return false;
    }

    public boolean tausche(int r1, int c1, int r2, int c2) {

        if (!sindNachbarn(r1, c1, r2, c2)) {
            return false;
        }

        swap(r1, c1, r2, c2);

        if (!existiertReihe()) {
            swap(r1, c1, r2, c2);
            return false;
        }

        verarbeiteReihen();
        return true;
    }

    private boolean sindNachbarn(int r1, int c1, int r2, int c2) {
        return Math.abs(c2 - c1) + Math.abs(r2 - r1) == 1;
    }

    private void swap(int r1, int c1, int r2, int c2) {
        int temp = feld[r1][c1];
        feld[r1][c1] = feld[r2][c2];
        feld[r2][c2] = temp;
    }

    private boolean existiertReihe() {

        // horizontal
        for (int r = 0; r < zeilen; r++) {
            for (int c = 0; c < spalten - 2; c++) {
                if (feld[r][c] == feld[r][c + 1] &&
                        feld[r][c] == feld[r][c + 2]) {
                    return true;
                }
            }
        }

        // vertikal
        for (int c = 0; c < spalten; c++) {
            for (int r = 0; r < zeilen - 2; r++) {
                if (feld[r][c] == feld[r + 1][c] &&
                        feld[r][c] == feld[r + 2][c]) {
                    return true;
                }
            }
        }

        return false;
    }

    private void verarbeiteReihen() {
        boolean nochmal;

        do {
            nochmal = false;
            boolean[][] markiert = new boolean[zeilen][spalten];

            // horizontal markieren
            for (int r = 0; r < zeilen; r++) {
                for (int c = 0; c < spalten - 2; c++) {

                    int farbe = feld[r][c];
                    int laenge = 1;

                    while (c + laenge < spalten &&
                            feld[r][c + laenge] == farbe) {
                        laenge++;
                    }

                    if (laenge >= 3) {
                        nochmal = true;
                        punkte += laenge * 10;

                        for (int k = 0; k < laenge; k++)
                            markiert[r][c + k] = true;
                    }
                }
            }

            // vertikal markieren
            for (int c = 0; c < spalten; c++) {
                for (int r = 0; r < zeilen - 2; r++) {

                    int farbe = feld[r][c];
                    int laenge = 1;

                    while (r + laenge < zeilen &&
                            feld[r + laenge][c] == farbe) {
                        laenge++;
                    }

                    if (laenge >= 3) {
                        nochmal = true;
                        punkte += laenge * 10;

                        for (int k = 0; k < laenge; k++)
                            markiert[r + k][c] = true;
                    }
                }
            }

            // entfernen
            for (int r = 0; r < zeilen; r++) {
                for (int c = 0; c < spalten; c++) {
                    if (markiert[r][c]) {
                        feld[r][c] = -1;
                    }
                }
            }

            if (nochmal) {
                lasseFallen();
                fuelleAuf();
            }

        } while (nochmal);
    }

    private void lasseFallen() {

        for (int c = 0; c < spalten; c++) {

            for (int r = zeilen - 1; r >= 0; r--) {

                if (feld[r][c] == -1) {

                    for (int k = r; k > 0; k--) {
                        feld[k][c] = feld[k - 1][c];
                    }
                    feld[0][c] = -1;
                }
            }
        }
    }

    private void fuelleAuf() {
        for (int r = 0; r < zeilen; r++) {
            for (int c = 0; c < spalten; c++) {
                if (feld[r][c] == -1) {
                    feld[r][c] = random.nextInt(farben);
                }
            }
        }
    }

    public int getPunkte() {
        return punkte;
    }

    public int getFarbe(int r, int c) {
        if (r >= 0 && r < zeilen && c >= 0 && c < spalten) {
            return feld[r][c];
        } else {
            throw new IndexOutOfBoundsException("Ungültige Zellkoordinaten");
        }
    }


    public void ausgeben() {

        System.out.println("");
        System.out.print("  ");
        for (int c = 0; c < spalten; c++)
            System.out.print((char)('A' + c) + " ");
        System.out.println();

        for (int r = 0; r < zeilen; r++) {
            System.out.print((r+1) + " ");
            for (int c = 0; c < spalten; c++) {
                System.out.print(feld[r][c] + " ");
            }
            System.out.println();
        }
    }
}


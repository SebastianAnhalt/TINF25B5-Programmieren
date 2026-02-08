package de.dhbwka.java.exercise.control;

import java.util.Scanner;

//Ergebnisse nicht ganz gleich wie auf Aufgabenblatt?! Warum?

public class Babylon {
    private static void Calculation(){

        //Ausgabe an Benutzer
        System.out.print("Wurzel aus welcher Zahl ziehen? ");

        //Deklaration Input sowie globale Variablen
        Scanner inputScanner = new Scanner(System.in);
        double a = inputScanner.nextDouble();
        double xOld = a / 2.0;
        double xNew;

        //Einmalige Ausgabe von Ursprungs-xn
        System.out.println("xn: " + xOld);

        //Berechnung mit Zwischenspeicherung in xNew and xOld
        do {
            xNew = 0.5 * (xOld + a / xOld);

            //mehrmalige Ausgabe von xn
            System.out.println("xn: " + xNew);

            if (Math.abs(xNew - xOld) < 1e-6) {
                break;
            }
            xOld = xNew;

            //Schleife stoppt, wenn if zutrifft
        } while (true);

        //Finaler Output
        System.out.printf("Die Wurzel aus " + a + " ist " + xNew);
    }

    //public Main mit Aufruf privater Funktion
    public static void main(String[] args) {
        Calculation();
    }
}
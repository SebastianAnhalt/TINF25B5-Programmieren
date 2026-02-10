package de.dhbwka.java.exercise.arrays;
import java.util.Scanner;

//Mechanismus ähnlich zu Aufgabe auf Folie 4, daher teilweise Übernahme Code

public class DotProduct {
    private static void calculation(){

        //Scanner und globale Variablen
        Scanner inputScanner = new Scanner(System.in);
        double finalResult = 0;
        double multiplication = 0;

        //Out- und Input
        System.out.print("Bitte Anzahl der Elemente n eingeben: ");
        int input = inputScanner.nextInt();

        //Neue Array mit Größe der einzugebenden Zahlen
        double[] arrayForX = new double[input];
        double[] arrayForY = new double[input];

        //Ausgabe und Schreiben in den Array für x
        for (int i = 0; i < input; i++){
            System.out.print("Bitte x_" + i + " eingeben: ");

            //Abspeichern der Eingabe der x-Zahlen
            double inputForX = inputScanner.nextDouble();
            arrayForX[i] = inputForX;
        }

        //Ausgabe und Schreiben in den Array für y
        for (int n = 0; n < input; n++){
            System.out.print("Bitte y_" + n + " eingeben: ");

            //Abspeichern der Eingabe der y-Zahlen
            double inputForY = inputScanner.nextDouble();
            arrayForY[n] = inputForY;
        }

        //Berechnung mittels Multiplikation der Werte x und y und Addition auf das Gesamtergebnis
        //Bedingung arrayForX: egal ob Array für X oder Y, sind beide immer gleich lang
        for (int m = 0; m < arrayForX.length; m++){
            multiplication = arrayForX[m] * arrayForY[m];
            finalResult += multiplication;
        }

        //Finaler Output
        System.out.println("Der Betrag von x ist " + finalResult);
    }

    public static void main(String[] args) {
        calculation();
    }
}

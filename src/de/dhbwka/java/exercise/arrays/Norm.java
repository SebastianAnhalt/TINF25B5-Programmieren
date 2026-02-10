package de.dhbwka.java.exercise.arrays;
import java.util.Scanner;

public class Norm
{
    private static void calculation(){

        //Scanner und globale Variablen
        Scanner inputScanner = new Scanner(System.in);
        double finalResult = 0;
        double multiplication = 0;
        double cache = 0;

        //Out- und Input
        System.out.print("Bitte Anzahl der Elemente n eingeben: ");
        int input = inputScanner.nextInt();
        
        //Neuer Array mit Größe der einzugebenden Zahlen
        double[] vectorArray = new double[input];

        //Ausgabe und Schreiben in den Array
        for (int i = 0; i < input; i++){
            System.out.print("Bitte x_" + i + " eingeben: ");
            
            //Abspeichern der Eingabe der x-Zahlen
            double inputForX = inputScanner.nextDouble();
            vectorArray[i] = inputForX;
        }
        
        //Berechnung des Zwischenstandes jedes X-Wertes mittels Iteration durch den Array, am Ende Wurzelziehen
        for (int m = 0; m < vectorArray.length; m++){
            multiplication = vectorArray[m] * vectorArray[m];
            cache += multiplication;
        }
        finalResult = Math.sqrt(cache);

        //Finaler Output
        System.out.println("Der Betrag von x ist " + finalResult);
    }

    public static void main(String[] args) {
        calculation();
    }
}
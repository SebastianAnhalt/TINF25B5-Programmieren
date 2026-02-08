package de.dhbwka.java.exercise.control;

public class ShoeSize {
    static void output(){

        //Ausgabe von Tabellen-Head: nur einmal
        System.out.println("Zentimeter      |  Größe");
        System.out.println("----------------+--------");

        //20 Durchläufe der Schleife mit "wachsenden" Parametern und Berechnung/Rundung der Größe
        for (int size = 30; size <= 49; size++) {

            //Berechnung der beiden Größen
            double firstCentimeter = (size - 1) / 1.5;
            double secondCentimeter = size / 1.5;

            //Rundung
            firstCentimeter = Math.round(firstCentimeter * 100.0) / 100.0;
            secondCentimeter = Math.round(secondCentimeter * 100.0) / 100.0;

            //Ausgabe mit zwei Nachkommastellen sowie entsprechender Formatierung/Leerzeichen
            System.out.printf(" %.2f - %.2f  |  %d%n", firstCentimeter, secondCentimeter, size);
        }
    }

    public static void main(){
        output();
    }
}
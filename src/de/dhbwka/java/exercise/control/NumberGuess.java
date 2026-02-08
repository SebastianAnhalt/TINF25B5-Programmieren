package de.dhbwka.java.exercise.control;

import java.util.Scanner;

public class NumberGuess {

    //private Funktion mit Aufruf in Main
    private static void RandomGeneratorAndOutput() {

        //Globale Variablen
        String playerName;
        String status;
        int randomNumberToGuess;
        int guessedNumber = 0;
        int tryXY;
        boolean startGameWithNameAgain;

        //Globaler Scanner
        Scanner inputScanner = new Scanner(System.in);

        do {
            //Variablen wC$hrend des Durchlaufs
            tryXY = 1;
            startGameWithNameAgain = false;

            //Eingabe des Namens
            System.out.print("Wie ist Dein Name? ");
            playerName = inputScanner.nextLine();

            randomNumberToGuess = (int)(Math.random() * 100) + 1;
            boolean gameRunning = true;

            while (gameRunning) {

                //Sich wiederholende Ausgabe fC<r das Raten
                System.out.print(playerName + ", rate eine Zahl [1-100]: ");
                guessedNumber = inputScanner.nextInt();

                //Ausgabe des Status, je nachde opb zu hoch/niedrig oder korrekt
                if (guessedNumber < randomNumberToGuess) {
                    status = "zu niedrig.";
                }
                else if (guessedNumber > randomNumberToGuess) {
                    status = "zu hoch.";
                }
                else {
                    status = "korrekt.";
                    gameRunning = false;
                }
                System.out.println("Versuch " + tryXY++ + ": " + guessedNumber + " ist " + status);

                //Soll Spiel wiederholt werden?
                if (!gameRunning) {
                    System.out.print("Was möchtest Du tun?\n0 - Das Spiel beenden\n1 - Das Spiel fortsetzen ");
                    int choice = inputScanner.nextInt();

                    if (choice == 1) {
                        startGameWithNameAgain = true;
                        inputScanner.nextLine(); //Cleant Variable, damit Spielername erneut abgefragt werden kann!
                    }
                }
            }
        } while (startGameWithNameAgain);
    }

    public static void main(String[] args) {
        RandomGeneratorAndOutput();
    }
}

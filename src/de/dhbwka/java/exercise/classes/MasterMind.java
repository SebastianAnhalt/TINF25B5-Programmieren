package de.dhbwka.java.exercise.classes;

/**
 * @author DHBW lecturer
 * @version 1.0
 * <p>
 * Part of lectures on 'Programming in Java'. Baden-Wuerttemberg
 * Cooperative State University.
 * <p>
 * (C) 2015 by J. Sidler, T. Schlachter, C. Schmitt, W. Suess
 */
public class MasterMind {

    char[] computerKombination;
    char[][] spielerKombination;
    int[] richtigeStellen;
    int[] falscheStellen;
    java.util.Scanner scan;
    int versuch;

    MasterMind() {
        computerKombination = new char[5];
        spielerKombination = new char[50][5];
        richtigeStellen = new int[50];
        falscheStellen = new int[50];
        scan = new java.util.Scanner(System.in);
    }

    void spiel() {
        bestimmeComputerKombination();
        rateSchleife();
    }

    void bestimmeComputerKombination() {
        for (int i = 0; i < 5; i++) {
            computerKombination[i] = (char) ('A' + (int) (Math.random() * 8));
        }
        // Ausgabe fuer das Testen
        System.out.print("Computer-Kombination: ");
        for (int i = 0; i < 5; i++) {
            System.out.print(computerKombination[i]);
        }
        System.out.println();
    }


    void rateSchleife() {
        for (versuch = 0; versuch < 50; versuch++) {
            tippEingabe();
            bewertung();
            if (richtigeStellen[versuch] == 5) {
                System.out.println("Mit " + (versuch + 1)
                        + " Versuchen gewonnen!");
                break;
            }
        }
        if (versuch == 50) {
            System.out.println("Spielende. Sie haben verloren.");
        }
    }

    void tippEingabe() {
        boolean korrekt;
        do {
            System.out.print("Geben Sie Ihren Tipp ab: ");
            String str = scan.next();
            korrekt = true;
            if (str.length() == 5) {
                for (int i = 0; i < 5; i++) {
                    spielerKombination[versuch][i] = str.charAt(i);
                    if ((spielerKombination[versuch][i] >= 'a')
                            && (spielerKombination[versuch][i] <= 'h')) {
                        spielerKombination[versuch][i] += ('A' - 'a');
                    }
                    if ((spielerKombination[versuch][i] < 'A')
                            || (spielerKombination[versuch][i] > 'H')) {
                        korrekt = false;
                        System.out.println("Falscher Buchstabe enthalten.");
                        break;
                    }
                }
            } else {
                korrekt = false;
                System.out.println("Zu viele oder zu wenige Buchstaben.");
            }
        } while (!korrekt);
    }

    void bewertung() {
        System.out.println((versuch + 1) + " bisherige Versuche:");
        for (int k = 0; k <= versuch; k++) {
            for (int i = 0; i < 5; i++) {
                System.out.print(spielerKombination[k][i]);
            }
            richtigeBuchstaben();
            System.out.println(" " + richtigeStellen[k] + " "
                    + falscheStellen[k]);
        }
    }


    void richtigeBuchstaben() {
        richtigeStellen[versuch] = 0;
        falscheStellen[versuch] = 0;
        for (char ch = 'A'; ch <= 'H'; ch++) {
            int buchstabeInComputerKombination = 0;
            int buchstabeInSpielerKombination = 0;
            int buchstabeUebereinstimmend = 0;
            for (int i = 0; i < 5; i++) {
                if (computerKombination[i] == ch) {
                    buchstabeInComputerKombination++;
                    if (spielerKombination[versuch][i] == ch) {
                        buchstabeUebereinstimmend++;
                    }
                }
            }
            for (int i = 0; i < 5; i++) {
                if (spielerKombination[versuch][i] == ch) {
                    buchstabeInSpielerKombination++;
                }
            }
            richtigeStellen[versuch] += buchstabeUebereinstimmend;
            falscheStellen[versuch]
                    += Math.min(buchstabeInSpielerKombination,
                    buchstabeInComputerKombination)
                    - buchstabeUebereinstimmend;
        }
    }

    public static void main(String[] args) {
        MasterMind mm = new MasterMind();
        mm.spiel();
    }

}

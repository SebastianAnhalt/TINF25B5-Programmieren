package de.dhbwka.java.exercise.math;

import java.util.Scanner;

public class Babylon {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.print("Wurzel aus welcher Zahl ziehen? ");

        float a = scan.nextFloat();
        scan.close();

        if(a>=0){
            double xn;
            double xn1 = 1;
            do {
                xn = xn1;
                xn1 = (xn + a/xn)/2;
                System.out.println("xn:" + xn);
            } while(Math.abs(xn1-xn)>0.000001);
            System.out.println("Die Wurzel aus " + a + " ist " + xn1);
        }
    }
}

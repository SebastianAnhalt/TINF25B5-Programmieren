package de.dhbwka.java.exercise.collections;

import javax.print.attribute.standard.PrinterMakeAndModel;
import java.util.Set;
import java.util.TreeSet;

public class lotto {

    public static void main(String[] args) {
        Set<Integer> mySet = new TreeSet<>();
        while(mySet.size() < 49) {
            int i = randomNumber();
            System.out.println(mySet.add(i));
            System.out.println(i);
        }
        System.out.println(mySet);
    }

    public static int randomNumber(){
        return (int) Math.floor(Math.random()*49+1);
    }

}

package de.dhbwka.java.exercise;

import java.util.ArrayList;
import java.util.List;

public class Main {
    static void main() {
        List<Integer> list = new ArrayList<>();
        Integer ichentfernemich = new Integer(3);
        list.add(5);
        list.add(2);
        list.add(3);
        list.add(4);
        list.remove(3);
        list.remove((Integer) 2);
        list.remove(ichentfernemich);
        for(Integer i : list) {
            System.out.println(i);
        }
    }
}

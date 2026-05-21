package de.dhbwka.java.exercise.classes;

public class TestComplex {
    public static void main(String arg[]){
        Complex C1, C2;
        C1 = new Complex(1, 2);
        C2 = new Complex(2, 1);
        System.out.println(C1);
        System.out.println(C2);
        System.out.println("Das Ergebnis der Addition von C1 u. C2 ist " + C1.add(C2));

        Complex my_array[];
        my_array = new Complex[10];

        for(int i=0; i<my_array.length; i++){
            my_array[i] = new Complex(Math.random(), Math.random());
            System.out.println(my_array[i]);
        }

        System.out.println("Starting sorting ..");

        for(int epoch=0; epoch < my_array.length; epoch++){
            for(int index=0; index < my_array.length-1; index++){
                if(my_array[index].isLess(my_array[index+1])) {
                    Complex temp = new Complex();
                    temp = my_array[index];
                    my_array[index] = my_array[index+1];
                    my_array[index+1] = temp;
                }
                }
            }
        for(int i=0; i<my_array.length; i++){
            System.out.println(my_array[i]);
        }

        }
}

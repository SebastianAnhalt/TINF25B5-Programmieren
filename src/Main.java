import static de.dhbwka.java.exercise.math.Horner.evaluateHorner;


public class Main {

    public static void main(String[] args) {
        double[] polynomial = {5, 4, 3, 2, 1};

        System.out.println(evaluateHorner(polynomial, -2));
    }
}

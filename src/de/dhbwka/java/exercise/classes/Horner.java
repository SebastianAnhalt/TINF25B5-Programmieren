package de.dhbwka.java.exercise.classes;

/**
 * @author DHBW lecturer
 * @version 1.0
 * 
 * Part of lectures on 'Programming in Java'. Baden-Wuerttemberg
 * Cooperative State University.
 * 
 * (C) 2015 by J. Sidler, T. Schlachter, C. Schmitt, W. Suess
 */
public class Horner {
	
    // coefficients coeff[0] for x^0, coeff[1] for x^1, etc.
    double[] coeff;

    Horner() {
        this(new double[0]);
    }

    Horner(double[] coeff) {
        this.coeff = coeff;
    }

    /** calculate value of Polynomial for a given x
     *  using Horner scheme
     *  @param x
     *  @return value of Polynomial for a given x
     */
    public double getValue(double x) {
        if (coeff.length>0) {
            double sum = coeff[coeff.length-1];
            for (int i = coeff.length-2; i >= 0; i--) 
                sum = sum * x + coeff[i];
            return sum;
        } else
            return 0;
    }

    @Override
    public String toString() {
        StringBuffer s = new StringBuffer("");
        for (int i = coeff.length-1; i >= 0; i--) {
            s.append(coeff[i]+"*x^"+i+" ");
            if (i>0 && coeff[i-1] >= 0) 
                s.append("+");
        }
        return s.toString();
    }

    public static void main(String[] args) {
        double[] k = {1,-2,3,4.5,8,-10,3,4,2,-3,0.5};
        Horner p = new Horner(k);
        double x = 1.5;
        System.out.println("Polynomial f: " + p.toString());
        System.out.println("f(" + x + ") = " + p.getValue(x));
    }

}

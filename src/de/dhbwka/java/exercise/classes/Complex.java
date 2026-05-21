package de.dhbwka.java.exercise.classes;

public class Complex {
    private double real, imag;

    public Complex(){
        real = 0;
        imag = 0;
    }
    public Complex(double real, double imag){
        this.real = real;
        this.imag = imag;
    }
    public double getReal(){
        return real;
    }
    public double getImag(){
        return imag;
    }

    public Complex add(Complex to_be_operated){
        // return new Complex(this.real + to_be_operated.real, this.imag + to_be_operated.imag)
        Complex temp = new Complex(0, 0);
        temp.real = this.real + to_be_operated.real;
        temp.imag = this.imag + to_be_operated.imag;
        return temp;
    }

    public Complex sub(Complex to_be_operated){
        return new Complex(this.real - to_be_operated.real,
                this.imag - to_be_operated.imag);
    }

    public Complex mult(Complex comp){
        double res_real = this.real * comp.real - this.imag * comp.imag;
        double res_imag = this.real * comp.imag + this.imag * comp.real;
        return new Complex(res_real, res_imag);
    }

    public Complex div(Complex comp){
        double num, den_real, den_imag;
        num = comp.real*comp.real + comp.imag*comp.imag;
        den_real = this.real*comp.real + this.imag*comp.imag;
        den_imag = this.imag*comp.real - this.real*comp.imag;
        return new Complex(den_real/num, den_imag/num);
    }

    public double getMagnitude(){
        return Math.sqrt(real*real+imag*imag);
    }

    public boolean isLess(Complex to_be_compared){
        return this.getMagnitude() < to_be_compared.getMagnitude();
    }

    @Override
    public String toString(){
        return "Hi, I am a complex number having " + this.real + " as a real part, and "
                + this.imag + " as imaginary part. my magnitude is!"+getMagnitude();
    }


}

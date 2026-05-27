package de.dhbwka.java.exercise.ui.currency;

public class TestLocal {
    public static void main(String[] args) {
        ExchangeRateSourceLocal exchangeRateSourceLocal = new ExchangeRateSourceLocal();
        System.out.println(exchangeRateSourceLocal.eur());
    }
}

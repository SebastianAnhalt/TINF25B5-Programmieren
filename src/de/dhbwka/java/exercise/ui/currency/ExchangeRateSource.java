package de.dhbwka.java.exercise.ui.currency;

public interface ExchangeRateSource {
    public default double eur() {
        return 0.2;
    }
}

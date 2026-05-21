package de.dhbwka.java.exercise.classes;

/**
 * Abstract base class for all currencies.
 * Demonstrates the principle of polymorphism:
 * The specific conversion logic is delegated to subclasses.
 */
public abstract class Currency {

    // Protected so that subclasses can access the value directly if needed
    protected double amount;

    /**
     * Constructor for a currency amount.
     * @param amount The amount in the local currency unit.
     */
    public Currency(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Abstract method to calculate the value in US Dollars.
     * Every subclass must provide its own implementation based on current rates.
     * @return The value of the local currency amount in USD.
     */
    public abstract double getDollarValue();

    /**
     * Helper class to simulate a central exchange rate service.
     * In a real system, this might fetch data from an external API.
     */
    public static class ExchangeRateManager {
        // Current exchange rates relative to 1 USD
        // (Values are examples: 1 EUR = 1.08 USD, 1 JPY = 0.0067 USD)
        public static double EUR_TO_USD = 1.08;
        public static double JPY_TO_USD = 0.0067;
    }
}

/**
 * Implementation for Euro.
 */
class Euro extends Currency {
    public Euro(double amount) {
        super(amount);
    }

    @Override
    public double getDollarValue() {
        return this.amount * ExchangeRateManager.EUR_TO_USD;
    }

    @Override
    public String toString() {
        return String.format("%.2f EUR", amount);
    }
}

/**
 * Implementation for Japanese Yen.
 */
class Yen extends Currency {
    public Yen(double amount) {
        super(amount);
    }

    @Override
    public double getDollarValue() {
        return this.amount * ExchangeRateManager.JPY_TO_USD;
    }

    @Override
    public String toString() {
        return String.format("%.0f JPY", amount);
    }
}

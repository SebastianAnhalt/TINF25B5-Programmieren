package de.dhbwka.java.exercise.classes;

import java.util.ArrayList;
import java.util.List;

public class GlobalBankingDemo {
    public static void main(String[] args) {
        Account myAccount = new Account("KIT Researcher");

        // Polymorphism in action: Adding different subclasses to the same list
        myAccount.addHolding(new Euro(100.0)); // 100 Euro
        myAccount.addHolding(new Yen(25000.0)); // 25,000 Yen

        // The account calculates the total without knowing the specific types
        System.out.println("Total Balance in USD: $" + myAccount.getTotalBalanceInUSD());
    }
}

class Account {
    private String owner;
    private List<Currency> holdings = new ArrayList<>();

    public Account(String owner) { this.owner = owner; }

    public void addHolding(Currency c) {
        holdings.add(c);
    }

    public double getTotalBalanceInUSD() {
        double total = 0;
        for (Currency c : holdings) {
            // POLYMORPHIC CALL:
            // Java decides at runtime whether to call Euro.getDollarValue()
            // or Yen.getDollarValue().
            total += c.getDollarValue();
        }
        return total;
    }
}

package de.dhbwka.java.exams.jaime.src.anis;

public class BillingException extends Exception {
    public BillingException(String budgetExceeded) {
        super(budgetExceeded);
    }
}

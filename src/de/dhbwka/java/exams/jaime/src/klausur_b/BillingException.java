/*
 * File converted to course project at Tue Dec 09 08:16:18 CET 2025
 */

package de.dhbwka.java.exams.jaime.src.klausur_b; // automatically inserted

/**
 * Exception thrown when billing operations exceed the budget limit
 */
@SuppressWarnings("serial")
public class BillingException extends Exception {

	/**
	 * Constructor for BillingException
	 *
	 * @param message the error message
	 */
	public BillingException(String message) {
		super(message);
	}

	/**
	 * Constructor for BillingException with cause
	 *
	 * @param message the error message
	 * @param cause the underlying cause
	 */
	public BillingException(String message, Throwable cause) {
		super(message, cause);
	}
}

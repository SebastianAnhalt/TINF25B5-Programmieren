/*
 * File converted to course project at Tue Dec 09 08:16:18 CET 2025
 */

package de.dhbwka.java.exams.jaime.src.klausur_b; // automatically inserted

/**
 * Represents an AI service with name, slogan, type and price per unit
 */
public class AIService {

	/**
	 * Name of the AI service
	 */
	private String name;

	/**
	 * Slogan of the AI service
	 */
	private String slogan;

	/**
	 * Type of AI service (TEXT or IMG)
	 */
	private AIType type;

	/**
	 * Price per unit for using this service
	 */
	private float pricePerUnit;

	/**
	 * Constructor for AIService
	 *
	 * @param name name of the service
	 * @param slogan slogan of the service
	 * @param type type of AI service
	 * @param pricePerUnit price per unit
	 */
	public AIService(String name, String slogan, AIType type, float pricePerUnit) {
		this.name = name;
		this.slogan = slogan;
		this.type = type;
		this.pricePerUnit = pricePerUnit;
	}

	/**
	 * Get the name of the service
	 *
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get the slogan of the service
	 *
	 * @return slogan
	 */
	public String getSlogan() {
		return slogan;
	}

	/**
	 * Get the type of the service
	 *
	 * @return type
	 */
	public AIType getType() {
		return type;
	}

	/**
	 * Get the price per unit
	 *
	 * @return price per unit
	 */
	public float getPricePerUnit() {
		return pricePerUnit;
	}

	/**
	 * String representation of the AI service
	 */
	@Override
	public String toString() {
		return name + " (" + type + ") - " + slogan + " - $" + String.format("%.2f", pricePerUnit) + "/unit";
	}
}

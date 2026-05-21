/*
 * File converted to course project at Tue Dec 09 08:16:18 CET 2025
 */

package de.dhbwka.java.exams.jaime.src.klausur_b; // automatically inserted

/**
 * Represents a text response from an AI service
 */
public class TextAnswer implements Answer {

	/**
	 * The generated text content
	 */
	private String text;

	/**
	 * Number of tokens used for this generation
	 */
	private int tokens;

	/**
	 * Constructor for TextAnswer
	 *
	 * @param text the generated text
	 * @param tokens number of tokens used
	 */
	public TextAnswer(String text, int tokens) {
		this.text = text;
		this.tokens = tokens;
	}

	/**
	 * Get the generated text
	 *
	 * @return generated text
	 */
	public String getText() {
		return text;
	}

	/**
	 * Get the number of tokens used
	 *
	 * @return number of tokens
	 */
	public int getTokens() {
		return tokens;
	}

	/**
	 * String representation of the text answer
	 */
	@Override
	public String toString() {
		return text;
	}
}

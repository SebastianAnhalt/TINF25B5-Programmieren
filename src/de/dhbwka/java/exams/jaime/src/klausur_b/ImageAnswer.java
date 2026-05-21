/*
 * File converted to course project at Tue Dec 09 08:16:18 CET 2025
 */

package de.dhbwka.java.exams.jaime.src.klausur_b; // automatically inserted

import javax.swing.*;

/**
 * Represents an image response from an AI service
 */
public class ImageAnswer implements Answer {

	/**
	 * The generated image
	 */
	private ImageIcon image;

	/**
	 * Number of tokens used for this generation
	 */
	private int tokens;

	/**
	 * Constructor for ImageAnswer
	 *
	 * @param image the generated image
	 * @param tokens number of tokens used
	 */
	public ImageAnswer(ImageIcon image, int tokens) {
		this.image = image;
		this.tokens = tokens;
	}

	/**
	 * Get the generated image
	 *
	 * @return generated image
	 */
	public ImageIcon getImage() {
		return image;
	}

	/**
	 * Get the number of tokens used
	 *
	 * @return number of tokens
	 */
	public int getTokens() {
		return tokens;
	}
}

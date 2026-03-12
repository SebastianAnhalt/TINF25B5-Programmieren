/*
 * File converted to course project at Tue Dec 09 08:16:18 CET 2025
 */

package de.dhbwka.java.exams.jaime.src.klausur_a; /* automatically updated */

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Java Exam 'JAIME' (Java AI Management Engine)
 *
 * 2154031 (TINF24B5)
 *
 * Correction code: 77B6313E
 */
public class JAIME {

	/**
	 * Literal regular expression pattern to split the AI services data in {@link #parseAIService(String)}
	 */
	private final static Pattern PATTERN_SEMICOLON = Pattern.compile(";", Pattern.LITERAL);

	/**
	 * Application entry point
	 *
	 * @param args CLI args (ignored in this program :-)
	 */
	public static void main(String[] args) {
		try {
			// Enforce cross-platform look & feel to ensure colors are working on MacOS as well
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (final Exception e) {
		}

		List<AIService> services = JAIME.loadAIServices();

		new JAIMETerm( services, new BillingTerm(services) );
	}

	/**
	 * Load the AI services
	 *
	 * @return list of AI services
	 */
	private static List<AIService> loadAIServices() {
		/*final String[] dummyData = new String[]{
			"ByteMe;Let’s get digital!;TEXT;0.05",
			"LoremAIpsum;Where filler meets functionality;TEXT;0.01",
			"Glowworm;Lighting up your ideas, one pixel at a time;IMG;0.18"
		};

		List<AIService> services = new LinkedList<>();
		for ( String dummyLine : dummyData ) {
			services.add(JAIME.parseAIService(dummyLine));
		}*/
		List<AIService> services = new LinkedList<>();
		try(BufferedReader br = new BufferedReader(new FileReader("aiservices.txt"))) {
			while(br.ready()){
				String line = br.readLine();
				services.add(JAIME.parseAIService(line));
			}
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error reading questions", JOptionPane.ERROR_MESSAGE);
		}
		Collections.shuffle(services);
		return services;
	}

	/**
	 * Parse a line into an AI service object
	 *
	 * @param line line to parse
	 * @return parsed object
	 */
	private static AIService parseAIService(String line) {
		if (line != null) {
			String[] parts = JAIME.PATTERN_SEMICOLON.split(line);
			try {
				return new AIService(parts[0], parts[1], AIType.valueOf(parts[2]), Float.parseFloat(parts[3]));
			} catch (NumberFormatException e) {
				throw new IllegalStateException("This is not a number: " + parts[3] + ". Sure you read the correct input?",e);
			} catch (IllegalArgumentException e) {
				throw new IllegalStateException("Are you sure, your AIType has IMG and TEXT?",e);
			}
		}
		return null;
   }
}

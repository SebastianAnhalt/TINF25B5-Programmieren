package de.dhbwka.java.exercise.classes.vehicles;

/**
 * @author DHBW lecturer
 * @version 1.01
 * 
 * Part of lectures on 'Programming in Java'. Baden-Wuerttemberg
 * Cooperative State University.
 * 
 * (C) 2016 by J. Sidler, T. Schlachter, C. Schmitt, W. Suess
 */
public class Bicycle extends Vehicle {

	public Bicycle() {
		this(0.0);
	}

	public Bicycle(double speed) {
		super(2, speed, 30.0, 0.0);
	}
	
}

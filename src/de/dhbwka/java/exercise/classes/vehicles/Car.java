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
public class Car extends Vehicle {

	public Car() {
		this(0.0);
	}
	
	public Car(double speed) {
		this(speed, 140.0);
	}	
	
	protected Car(double speed, double maxSpeed) {
		super(4, speed, maxSpeed, 0.0);
	}	
	
}

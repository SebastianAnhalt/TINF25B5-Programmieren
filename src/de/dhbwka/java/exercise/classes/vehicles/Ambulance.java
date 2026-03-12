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
public class Ambulance extends Car {
	
	private boolean signal;
	
	public Ambulance() {
		this(0.0, false);
	}
	
	public Ambulance(double speed) {
		this(speed, false);
	}
	
	public Ambulance(double speed, boolean signal) {
		super(speed);
		setSignal(signal);
	}

	@Override
	public String toString() {		
		return super.toString()+
			" Signal " + (signal ? "on":"off") + ".";
	}
	
	public boolean hasSignal() {
		return signal;
	}

	public void setSignal(boolean signal) {
		this.signal = signal;
	}		
	
}

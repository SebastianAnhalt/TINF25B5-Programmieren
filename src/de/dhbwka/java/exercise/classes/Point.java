package de.dhbwka.java.exercise.classes;

/**
 * @author DHBW lecturer
 * @version 1.0
 * 
 * Part of lectures on 'Programming in Java'. Baden-Wuerttemberg
 * Cooperative State University.
 * 
 * (C) 2015 by J. Sidler, T. Schlachter, C. Schmitt, W. Suess
 */
public class Point {

	private double x, y;
	
	public static final Point ORIGIN = new Point(0.0, 0.0);
	
	public Point() {
		this(0.0, 0.0);
	}

	public Point(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public Point mirrorXAxis() {
		return new Point(x, -y);
	}	

	public Point mirrorYAxis() {
		return new Point(-x, y);
	}	

	public Point mirrorOrigin() {
		return new Point(-x, -y);
	}	
	
	public double getDistance(Point p) {
		double dx = x - p.x;
		double dy = y - p.y;
		return Math.sqrt(dx*dx + dy*dy);
	}

	public double getMagnitude() {
		return getDistance(ORIGIN);	
	}
	
	@Override
	public String toString() {
		return "Punkt (" + x + "," + y +")";
	}
	
	public static void main(String[] args) {
		Point pointA = new Point(4.0, 2.0);
		System.out.println("A: " + pointA);
		System.out.println("Betrag: " + pointA.getMagnitude());
		Point pointB = new Point(-1.0, -1.0);
		System.out.println("B: " + pointB);
		System.out.println("Abstand: " 
				+ pointA.getDistance(pointB));
		pointA = pointA.mirrorOrigin();
		System.out.println("A': " + pointA);
		System.out.println("Abstand: " 
				+ pointA.getDistance(pointB));
	}
	
}

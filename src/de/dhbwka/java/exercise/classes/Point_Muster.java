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
public class Point_Muster {

	private double x, y;

	public static final Point_Muster ORIGIN = new Point_Muster(0.0, 0.0);

	public Point_Muster() {
		this(0.0, 0.0);
	}

	public Point_Muster(double x, double y) {
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

	public Point_Muster mirrorXAxis() {
		return new Point_Muster(x, -y);
	}

	public Point_Muster mirrorYAxis() {
		return new Point_Muster(-x, y);
	}

	public Point_Muster mirrorOrigin() {
		return new Point_Muster(-x, -y);
	}

	public double getDistance(Point_Muster p) {
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
		Point_Muster Point_MusterA = new Point_Muster(4.0, 2.0);
		System.out.println("A: " + Point_MusterA);
		System.out.println("Betrag: " + Point_MusterA.getMagnitude());
		Point_Muster Point_MusterB = new Point_Muster(-1.0, -1.0);
		System.out.println("B: " + Point_MusterB);
		System.out.println("Abstand: "
				+ Point_MusterA.getDistance(Point_MusterB));
		Point_MusterA = Point_MusterA.mirrorOrigin();
		System.out.println("A': " + Point_MusterA);
		System.out.println("Abstand: "
				+ Point_MusterA.getDistance(Point_MusterB));
	}

}

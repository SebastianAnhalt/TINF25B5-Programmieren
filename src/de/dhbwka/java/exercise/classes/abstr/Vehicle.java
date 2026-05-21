package de.dhbwka.java.exercise.classes.abstr;

/**
 * Part of lectures on 'Programming in Java'. Baden-Wuerttemberg
 * Cooperative State University.
 * 
 * (C) 2016-2018 by J. Sidler, T. Schlachter, C. Schmitt, W. Suess
 * 
 * @author DHBW lecturer
 * @version 1.2
 */
public abstract class Vehicle {

   private int wheels;
   private double speed;
   private double maxSpeed;
   private double position;

   public Vehicle() {
      this( 0.0 );
   }

   public Vehicle( double speed ) {
      this( 0, speed, 0.0, 0.0 );
   }

   protected Vehicle( int wheels, double speed, double maxSpeed,
         double position ) {
      this.setWheels( wheels );
      this.setMaxSpeed( maxSpeed );
      this.setSpeed( speed ); // after setMaxSpeed!
      this.setPosition( position );
   }

   public abstract void info();

   public void drive( double minutes ) {
      this.position += this.speed / 60.0 * minutes;
   }

   @Override
   public String toString() {
      return this.getClass().getSimpleName() + " at " + this.position +
            " km with " + this.wheels + " wheels at speed " + this.speed +
            " km/h of max. " + this.maxSpeed + " km/h.";
   }

   public int getWheels() {
      return this.wheels;
   }

   public void setWheels( int wheels ) {
      this.wheels = wheels;
   }

   public double getSpeed() {
      return this.speed;
   }

   public void setSpeed( double speed ) {
      this.speed = Math.min( this.maxSpeed, speed );
   }

   public double getMaxSpeed() {
      return this.maxSpeed;
   }

   public void setMaxSpeed( double maxSpeed ) {
      this.maxSpeed = maxSpeed;
   }

   public double getPosition() {
      return this.position;
   }

   public void setPosition( double position ) {
      this.position = position;
   }

}

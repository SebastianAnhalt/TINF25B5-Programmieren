package de.dhbwka.java.exercise.classes.abstr;

/**
 * Part of lectures on 'Programming in Java'. Baden-Wuerttemberg
 * Cooperative State University.
 * 
 * (C) 2016 by J. Sidler, T. Schlachter, C. Schmitt, W. Suess
 * 
 * @author DHBW lecturer
 * @version 1.2
 */
public class Car extends Vehicle {

   public Car() {
      this( 0.0 );
   }

   public Car( double speed ) {
      this( speed, 140.0 );
   }

   protected Car( double speed, double maxSpeed ) {
      super( 4, speed, maxSpeed, 0.0 );
   }

   @Override
   public void info() {
      System.out.println( this.toString() );
   }

}

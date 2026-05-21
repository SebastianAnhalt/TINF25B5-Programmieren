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
public class Bicycle extends Vehicle {

   public Bicycle() {
      this( 0.0 );
   }

   public Bicycle( double speed ) {
      super( 2, speed, 30.0, 0.0 );
   }

   @Override
   public void info() {
      System.out.println( this.toString() );
   }

}

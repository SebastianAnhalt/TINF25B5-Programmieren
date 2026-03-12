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
public class RacingCar extends Car {

   public RacingCar() {
      this( 0.0 );
   }

   public RacingCar( double speed ) {
      this( speed, 220.0 );
   }

   public RacingCar( double speed, double maxSpeed ) {
      super( speed, maxSpeed );
   }

}

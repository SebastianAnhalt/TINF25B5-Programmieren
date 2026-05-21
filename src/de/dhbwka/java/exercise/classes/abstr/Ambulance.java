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
public class Ambulance extends Car {

   private boolean signal;

   public Ambulance() {
      this( 0.0, false );
   }

   public Ambulance( double speed ) {
      this( speed, false );
   }

   public Ambulance( double speed, boolean signal ) {
      super( speed );
      this.setSignal( signal );
   }

   @Override
   public String toString() {
      return super.toString() +
            " Signal " + (this.signal ? "on" : "off") + ".";
   }

   public boolean hasSignal() {
      return this.signal;
   }

   public void setSignal( boolean signal ) {
      this.signal = signal;
   }

}

package de.dhbwka.java.exercise.classes;

/**
 * Part of lectures on 'Programming in Java'. Baden-Wuerttemberg Cooperative
 * State University.
 *
 * (C) 2019 by J. Sidler, T. Schlachter, C. Schmitt, W. Suess
 * 
 * @author DHBW lecturer
 * @version 1.0
 *
 */
public class Polynomial {

   /* polynomial 2nd degree like ax^2 + bx + c */
   double a, b, c;

   public Polynomial() {
      this( 0.0, 0.0, 0.0 );
   }

   public Polynomial( double a, double b, double c ) {
      // super();
      this.a = a;
      this.b = b;
      this.c = c;
   }

   public double f( double x ) {
      return this.a * x * x + this.b * x + this.c;
   }

   public Polynomial sub( Polynomial p ) {
      return new Polynomial( this.a - p.a, this.b - p.b, this.c - p.c );
   }

   public Polynomial add( Polynomial p ) {
      return new Polynomial( this.a + p.a, this.b + p.b, this.c + p.c );
   }

   public Polynomial scale( double factor ) {
      return new Polynomial( factor * this.a, factor * this.b,
            factor * this.c );
   }

   /**
    * get zeros using the 'midnight formula' /* x1/2 = ( -b +/- sqrt(b*b-4*a*c)
    * ) / (2*a)
    */
   public double[] getZeros() {
      if ( this.a == 0 ) {
         if ( this.b == 0 ) { // no zeros
            return new double[0];
         } else { // 1 zero
            return new double[] { -this.c / this.b };
         }

      } else if ( this.b * this.b < 4 * this.a * this.c ) // no zeros
      {
         return new double[0];
      } else if ( this.b * this.b == 4 * this.a * this.c ) // 1 zero
      {
         return new double[] { -this.b / (2 * this.a) };
      } else // 2 zeros
      {
         return new double[] {
               (-this.b + Math.sqrt( this.b * this.b - 4 * this.a * this.c ))
                     / (2 * this.a),
               (-this.b - Math.sqrt( this.b * this.b - 4 * this.a * this.c ))
                     / (2 * this.a)
         };
      }
   }

   @Override
   public String toString() {
      return this.a + "x^2 " + (this.b >= 0 ? "+" : "") + this.b + "x "
            + (this.c >= 0 ? "+" : "") + this.c;
   }

   public static void main( String[] args ) {
      Polynomial pol1 = new Polynomial( 2, 0, 0 );
      System.out.println( "P1: " + pol1 );
      Polynomial pol2 = new Polynomial( 0, -4, 1 );
      System.out.println( "P2: " + pol2 );
      Polynomial pol3 = pol1.add( pol2 );
      System.out.println( "P3 = P1 + P2: " + pol3 );
      pol3 = pol3.scale( 2.0 );
      System.out.println( "P3 = 2.0 * P3: " + pol3 );
      double[] zeros = pol3.getZeros();
      System.out.println( "Nullstellen von P3 (" + pol3 + "): " );
      for ( double zero : zeros ) {
         System.out.print( zero + " " );
      }
   }

}

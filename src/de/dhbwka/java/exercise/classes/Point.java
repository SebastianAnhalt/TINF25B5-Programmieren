package de.dhbwka.java.exercise.classes;

public class Point {
    double my_X;
    double my_Y;
    Point(){
    }
    public Point(double p_X, double p_Y){
        // this.my_X = X;
        my_X = p_X;
        my_Y = p_Y;
    }

    public double get_my_X(){
        return my_X;
    }
    public double get_my_Y(){
        return my_Y;
    }

    public void set_my_X(double p_my_X){
        my_X = p_my_X;
    }
    public void set_my_Y(double p_my_Y){
        my_Y = p_my_Y;
    }
    public double distToOrigin(){
        return Math.sqrt( my_X * my_X + my_Y * my_Y);
    }
    public void mirrorX(){
        my_X = - my_X;
    }
    public void mirrorY(){
        my_Y = - my_Y;
    }
    public void mirrorOrigin(){
        mirrorX();
        mirrorY();
    }
    public double distToPoint(Point their_Point){
        double my_x_distance, my_y_distance;
        my_x_distance = Math.pow((my_X - their_Point.my_X), 2);
        my_y_distance = Math.pow((my_Y - their_Point.my_Y), 2);
        return Math.sqrt(my_x_distance + my_y_distance);
    }

    public String toString(){
        return "Punkt(" + my_X + ", " + my_Y + ")";
    }

    public static void main(String[] args) {
        Point pointA = new Point(4.0, 2.0);
        System.out.println("A: " + pointA);
        Point pointB = new Point(-1.0, -1.0);
        System.out.println("B: " + pointB);
        System.out.println("Abstand A-B: "
                + pointA.distToPoint(pointB));
        // pointA = pointA.mirrorOrigin();
        pointA.mirrorOrigin();
        System.out.println("A (mirrored): " + pointA);
        System.out.println("Abstand A (mirrored)-B: "
                + pointA.distToPoint(pointB));
    }
}

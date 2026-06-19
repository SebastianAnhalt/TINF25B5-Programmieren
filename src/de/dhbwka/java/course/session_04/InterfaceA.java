package de.dhbwka.java.course.session_04;
public interface InterfaceA {
    default void methodA(){
        System.out.println("Invoked default <InterfaceA>.methodA()");
    }
}

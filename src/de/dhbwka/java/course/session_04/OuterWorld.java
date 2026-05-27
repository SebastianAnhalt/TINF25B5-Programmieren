package de.dhbwka.java.course.session_04;

public class OuterWorld {
    private String secretMessage = "Hello from the Outer World!";

    // This is the Internal Class
    class InnerAssistant {
        void displaySecret() {
            // It can see 'secretMessage' directly!
            System.out.println(secretMessage);
        }
    }

    public void start() {
        InnerAssistant assistant = new InnerAssistant();
        assistant.displaySecret();
    }
}

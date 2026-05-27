package de.dhbwka.java.exercise.ui.joption;
import javax.swing.*;
public class JOptionPaneDialog {
    public static void main(String[] args) {
        // Message dialog
        JOptionPane.showMessageDialog(null, "May the force be with you!");

        // Input dialog
        String input = (String)JOptionPane.showInputDialog("Please enter a number");
        System.out.println(input);

        // Confirm dialog
        JOptionPane.showConfirmDialog(null, "Are you OK?");

        // Select dialog
        String[] options = { "to be", "not to be", "don't know" };
        String selection = (String) JOptionPane.showInputDialog(null, "Hamlet",
                "To be or not to be?", JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
        System.out.println("Chosen: " + selection);

        // Customized option dialog
        String[] opts = { "Yes", "No", "Cancel" };
        int n = JOptionPane.showOptionDialog(null, "Yes or no?","Yes/No/Canel",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, opts, opts[0]);
        if ( n == JOptionPane.YES_OPTION ) {
            System.out.println("Yes!");
        }
    }
}

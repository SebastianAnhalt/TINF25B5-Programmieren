package de.dhbwka.java.exercise.libary;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static final String[] inputNames = {"author", "title", "year", "publisher"};

    public static void main() throws Exception {
        JTextField[] textFields = new JTextField[inputNames.length];
        JButton[] buttons = new JButton[inputNames.length];
        Libary libary = new Libary();
        JFrame frame = new JFrame();
        frame.setSize(400, 250);
        frame.setTitle("Libary");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        JPanel panel = new JPanel(new GridLayout(inputNames.length+1, 1, 5, 5));
        for (int i = 0; i < inputNames.length; i++) {
            JTextField textField = new JTextField();
            JLabel label = new JLabel(inputNames[i]);
            textFields[i] = textField;
            panel.add(label);
            panel.add(textField);
        }
        JLabel placeholder = new JLabel("");
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            Book book = new Book(textFields[0].getText(), textFields[1].getText(), textFields[2].getText(), textFields[3].getText());
            try {
                libary.addBook(book);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        panel.add(placeholder);
        panel.add(saveButton);
        JPanel buttonBar = new JPanel(new GridLayout(1, 4, 5, 5));
        for (int i = 0; i < inputNames.length; i++) {
            JButton button = new JButton(inputNames[i]);
            button.addActionListener(e -> {
                try {
                    JOptionPane.showMessageDialog(frame, libary.readAllBooks());
                } catch (Exception err) {
                    err.printStackTrace();
                }
            });
            buttonBar.add(button);
            buttons[i] = button;
        }
        frame.add(buttonBar, BorderLayout.SOUTH);
        frame.add(panel, BorderLayout.NORTH);


        frame.setVisible(true);
    }


}



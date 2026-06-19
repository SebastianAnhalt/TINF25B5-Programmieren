package de.dhbwka.java.course.session_05;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class EventsWithAnonymousClasses extends JFrame {
    JTextField text = new JTextField("Whatever");
    JButton button = new JButton("Button");
    EventsWithAnonymousClasses() {
        this.setLayout(new FlowLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(this.text);
        this.add(this.button);
        this.button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                button.setText(text.getText());
            }
        });
        this.setSize(400, 300);
        this.setVisible(true);
    }
    public static void main(String[] args) {
        new EventsWithAnonymousClasses();
    }
}

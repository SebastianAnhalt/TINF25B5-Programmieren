package de.dhbwka.java.exercise.waehrungsrechner;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    public Main() {
        super("Währungrechner");
        initialize();
    }

    private void initialize() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 200);
        setLayout(new BorderLayout(10,10));

        JTextField top =  new JTextField(10);
        top.setText("0");
        top.setToolTipText("Enter the amound");
        JPanel bottom = new JPanel();
        JButton bottomleft = new JButton("EUR -> USD");
        JButton bottommiddle = new JButton("EUR -> USD");
        JButton bottomright = new JButton("cancel");
        bottom.add(bottomleft);
        bottom.add(bottommiddle);
        bottom.add(bottomright);

        add(top, BorderLayout.NORTH);
        add(bottom, BorderLayout.SOUTH);
        setLocationRelativeTo(null);

    }

    static void main() {
        Main frame = new Main();
        frame.setVisible(true);
    }
}

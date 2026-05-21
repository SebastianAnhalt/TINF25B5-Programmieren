package de.dhbwka.java.exercise.ui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.event.*;

public class CurrencyCalculator {

    public static void main(String[] args) {
        JFrame f = new JFrame("Currency Calculator");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(new BorderLayout(5, 5));
        f.add(new JTextField("Please enter an amount to convert!"), BorderLayout.PAGE_START);
        f.add(new JButton("EUR -> USD"), BorderLayout.LINE_START);
        f.add(new JButton("USD -> EUR"), BorderLayout.CENTER);
        f.add(new JButton("Cancel"), BorderLayout.LINE_END);
        f.pack();
        f.setVisible(true);
    }
}

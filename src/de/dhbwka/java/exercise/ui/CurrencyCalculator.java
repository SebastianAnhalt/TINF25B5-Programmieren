package de.dhbwka.java.exercise.ui;

import javax.swing.*;
import java.awt.*;

public class CurrencyCalculator extends JFrame {

    private JTextField inputField;
    private JButton eurToUsdButton;
    private JButton usdToEurButton;
    private JButton cancelButton;

    public CurrencyCalculator() {
        setTitle("Currency converter");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        inputField = new JTextField("Please enter amount to convert!");
        add(inputField, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 3, 10, 0));

        eurToUsdButton = new JButton("EUR -> USD");
        usdToEurButton = new JButton("USD -> EUR");
        cancelButton = new JButton("Cancel");

        buttonPanel.add(eurToUsdButton);
        buttonPanel.add(usdToEurButton);
        buttonPanel.add(cancelButton);

        add(buttonPanel, BorderLayout.CENTER);

        setSize(350, 100);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CurrencyCalculator());
    }
}

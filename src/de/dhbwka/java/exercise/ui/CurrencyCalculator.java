package de.dhbwka.java.exercise.ui;

import java.awt.GridLayout;
import javax.swing.*;

public class CurrencyCalculator {
    public static void main(String[] args) {
        final double EUR_TO_USD = 1.1;
        JFrame frame = new JFrame("Currency Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(2,1,6,3));

        JTextField inputField = new JTextField(10);

        JPanel buttonPanel = new JPanel();
        JButton eurToUsdBtn = new JButton("EUR->USD");
        JButton usdToEurBtn = new JButton("USD->EUR");
        JButton cancelBtn = new JButton("Cancel");

        buttonPanel.add(eurToUsdBtn);
        buttonPanel.add(usdToEurBtn);
        buttonPanel.add(cancelBtn);

        frame.add(inputField);
        frame.add(buttonPanel);
        frame.setVisible(true);
    }
}

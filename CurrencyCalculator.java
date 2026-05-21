package de.dhbwka.java.exercise.ui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.GridLayout;

public class CurrencyCalculator extends JFrame
{

    public CurrencyCalculator()
    {
        super("Currency converter");

        JTextField inputField = new JTextField("Please enter amount to convert!");

        JButton eurToUsdButton = new JButton("EUR -> USD");
        JButton usdToEurButton = new JButton("USD -> EUR");
        JButton cancelButton = new JButton("Cancel");

        setLayout(new BorderLayout());

        add(inputField, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 3));
        buttonPanel.add(eurToUsdButton);
        buttonPanel.add(usdToEurButton);
        buttonPanel.add(cancelButton);

        add(buttonPanel, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(560, 120);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args)
    {
        new CurrencyCalculator();
    }
}
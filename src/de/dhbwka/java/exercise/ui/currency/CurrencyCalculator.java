package de.dhbwka.java.exercise.ui.currency;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * CurrencyCalculator - Demonstration application for Programming II.
 * * Structural Form: This is a "Top-Level-Klasse".
 * Layout Framework: Uses BorderLayout to structure the main interface.
 */
public class CurrencyCalculator extends JFrame {

    // Attribute (Objekt Variablen) of the Top-Level Class [cite: 82]
    private JTextField inputField;
    private final ExchangeRateSource exchangeRateSource;

    public CurrencyCalculator(ExchangeRateSource exchangeRateSource) {
        this.exchangeRateSource = exchangeRateSource;
        setTitle("Currency Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(360, 80);

        // Setting up the explicit BorderLayout space
        setLayout(new BorderLayout(5, 5));

        // Placement of text input component
        inputField = new JTextField();
        inputField.setHorizontalAlignment(JTextField.RIGHT);
        add(inputField, BorderLayout.NORTH);

        // A sub-panel utilizing a structural Grid Layout
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 5, 5));

        JButton eurToUsdButton = new JButton("EUR → USD");
        JButton usdToEurButton = new JButton("USD → EUR");
        JButton cancelButton = new JButton("Cancel");

        buttonPanel.add(eurToUsdButton);
        buttonPanel.add(usdToEurButton);
        buttonPanel.add(cancelButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // --- Event-Handling & Inner Class Paradigm Transformations ---

        /*
         * STUFE I & III: Lambda Expressions
         * Metaphorically building upon Functional Interfaces.
         * Behind the scenes, these compress the structural boilerplate of
         * anonymous inner classes down to the pure logic layer.
         */
        eurToUsdButton.addActionListener(e -> {
            try {
                double eur = Double.parseDouble(inputField.getText());
                double usd = eur * exchangeRateSource.eur();
                inputField.setText(String.format("%.2f", usd));
            } catch (NumberFormatException ex) {
                showError();
            }
        });

        usdToEurButton.addActionListener(e -> {
            try {
                double usd = Double.parseDouble(inputField.getText());
                double eur = usd / exchangeRateSource.eur();
                inputField.setText(String.format("%.2f", eur));
            } catch (NumberFormatException ex) {
                showError();
            }
        });

        /*
         * STUFE II: Anonyme innere Klasse (Ref: Slides Page 15-17) [cite: 44, 53]
         * * Syntax Definition & Target Match:
         * - "Klassendefinition und Objekterzeugung sind zu einem Sprachkonstrukt verbunden"[cite: 45].
         * - "Hinter new steht der Name einer Schnittstelle (interface)" -> here, ActionListener[cite: 47, 48].
         * - This represents a prime use-case: "Ein Haupteinsatzgebiet für anonyme
         * innere Klassen ist die Ereignisbehandlung (Event-Handling), z.B. bei Swing."
         */
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*
                 * Accessing Outer State (Ref: Slides Page 6, 9 & 10) [cite: 10, 24, 25]
                 * * - As a non-static entity, this inner context has access to all fields
                 * of the outer container implicitly[cite: 11].
                 * - Explicit Outer Reference Resolution: If field shadowing were present,
                 * we would reach the instance variable using 'Classname.this':
                 * e.g., CurrencyCalculator.this.inputField.setText("");
                 */
                inputField.setText("");
                CurrencyCalculator.this.inputField.requestFocus(); // Explicit reference illustration
            }
        });

        setLocationRelativeTo(null);
    }

    private void showError() {
        JOptionPane.showMessageDialog(this,
                "Bitte geben Sie eine gültige Zahl ein.",
                "Fehler",
                JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        ExchangeRateSource source;
        if (args.length == 0) {
            source = new ExchangeRateSourceLocal();
        } else {
            source = new ExchangeRateSourceService();
        }

        ExchangeRateSource finalSource = source;
        SwingUtilities.invokeLater(() -> {
            CurrencyCalculator calculator = new CurrencyCalculator(finalSource);
            calculator.setVisible(true);
        });
    }
}

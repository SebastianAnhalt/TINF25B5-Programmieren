package de.dhbwka.java.exercise.classes;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class Pigeon extends JFrame {
    private JTextField userField;
    private JPasswordField passwortField;
    private JButton loginButton;

    // "Gehashtes" Passwort (simuliert). In echt stünde hier ein Hash-Wert aus der DB.
    private final String VALID_USER = "Admin";
    private final char[] VALID_PASSWORD_HASH = "geheim123".toCharArray();

    public Pigeon() {
        setTitle("Pigeon System Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        userField = new JTextField();
        passwortField = new JPasswordField();
        loginButton = new JButton("Login");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validateLogin();
            }
        });

        add(new JLabel("Benutzername:"));
        add(userField);
        add(new JLabel("Passwort:"));
        add(passwortField);
        add(loginButton);

        setLocationRelativeTo(null); // Zentriert das Fenster
        setVisible(true);
    }

    private void validateLogin() {
        String inputUser = userField.getText();
        char[] inputPass = passwortField.getPassword();

        // Passwort-Vergleich: Arrays.equals wird genutzt, da == bei Arrays nur die Referenz prüft!
        if (inputUser.equals(VALID_USER) && Arrays.equals(inputPass, VALID_PASSWORD_HASH)) {
            JOptionPane.showMessageDialog(this, "Login erfolgreich! Willkommen, " + inputUser);
        } else {
            JOptionPane.showMessageDialog(this, "Falscher Nutzername oder Passwort!", "Fehler", JOptionPane.ERROR_MESSAGE);
        }

        // Sicherheit: Passwort-Array im Speicher sofort mit Nullen überschreiben
        Arrays.fill(inputPass, '0');
    }

    public static void main(String[] args) {
        // Look & Feel an das Betriebssystem anpassen (optional)
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception e) {}

        new Pigeon();
    }
}
package de.dhbwka.java.exercise.ui;

import javax.swing.*;
import java.awt.*;

public class CurrencyCalculator extends JFrame{
    JFrame frame;
    Panel panel;
    JButton eurToUsd = new JButton("EUR -> USD");
    JButton usdToEur = new JButton("USD -> EUR");
    JButton cancel = new JButton("Cancel");

    public void main(){
        frame = new JFrame("Currency Calculator");
        panel = new Panel(new BorderLayout());
        panel.add(new JTextField(), BorderLayout.NORTH);
        panel.add(eurToUsd, BorderLayout.WEST);
        panel.add(usdToEur, BorderLayout.CENTER);
        panel.add(cancel, BorderLayout.EAST);
        frame.add(panel);
        frame.setSize(400,200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

package de.dhbwka.java.course.session_05;

import java.awt.FlowLayout; import java.awt.event.*; import javax.swing.*;
public class ButtonEventDemo implements ActionListener {
    JFrame f = new JFrame("Event Frame");
    JTextField text_field = new JTextField("enter text...");
    JButton button1 = new JButton("Button 1");
    JButton button2 = new JButton("Button 2");
    public ButtonEventDemo() {
        this.f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.f.getContentPane().setLayout(new FlowLayout());
        this.f.add(this.text_field);
        this.text_field.addActionListener(this);
        this.f.add(this.button1);
        this.button1.addActionListener(this);
        this.f.add(this.button2);
        this.button2.addActionListener(this);
        this.f.setSize(400, 100);
        this.f.setVisible(true);
    }
    public static void main(String[] args) {
        ButtonEventDemo demo = new ButtonEventDemo();
    }
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.text_field) System.out.println("Text changed");
        else System.out.println(((JButton) e.getSource()).getText() + " pressed.");
    }
}

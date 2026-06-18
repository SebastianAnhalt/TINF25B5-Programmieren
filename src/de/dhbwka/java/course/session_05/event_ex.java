package de.dhbwka.java.course.session_05;
import java.awt.Toolkit;
import java.awt.event.*;
import javax.swing.*;
public class event_ex extends JFrame implements ActionListener {
    JButton button = new JButton("Click me!");
    public event_ex() {
        this.add(this.button);
        this.button.addActionListener(this);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(200, 150);
        this.setVisible(true);
}
public void actionPerformed(ActionEvent e) {
    Toolkit.getDefaultToolkit().beep();
}
    public static void main(String[] args) {
        new event_ex();
    }
}

/*
 * File converted to course project at Tue Dec 09 08:16:18 CET 2025
 */

package de.dhbwka.java.exams.jaime.src.klausur_a; /* automatically updated */

import javax.swing.*;

public class ImageAnswer implements Answer {
    private int token;
    private ImageIcon image;

    public ImageAnswer(ImageIcon imageIcon, int tokens) {
        setImage(imageIcon);
        setToken(tokens);
    }

    public int getTokens() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }

    public ImageIcon getImage() {
        return image;
    }

    public void setImage(ImageIcon image) {
        this.image = image;
    }
}

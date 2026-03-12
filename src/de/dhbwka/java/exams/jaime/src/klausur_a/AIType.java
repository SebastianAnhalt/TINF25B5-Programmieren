/*
 * File converted to course project at Tue Dec 09 08:16:18 CET 2025
 */

package de.dhbwka.java.exams.jaime.src.klausur_a; /* automatically updated */

import java.awt.*;

public enum AIType {
    TEXT(Color.BLUE),
    IMG(Color.RED);
    private Color color;
    private AIType(Color color) {
        setColor(color);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}

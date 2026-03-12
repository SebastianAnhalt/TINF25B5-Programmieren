package de.dhbwka.java.exams.jaime.src.tinfb5;

import java.awt.*;

public enum AIType {
    // allowed instances
    ;
    // attributs
    private Color color;

    // constructor
    private AIType(Color color) {
        setColor(color);
    }

    // getter and setter
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}

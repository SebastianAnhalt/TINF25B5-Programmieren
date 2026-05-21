package de.dhbwka.java.exams.jaime.src.anis;

import java.awt.*;

public enum AIType {
    // allowed values
    TEXT(Color.BLUE),
    IMG(Color.RED);

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    // attributes
    private Color color;

    // constructors
    private AIType(Color color){
        this.color = color;
    }
}

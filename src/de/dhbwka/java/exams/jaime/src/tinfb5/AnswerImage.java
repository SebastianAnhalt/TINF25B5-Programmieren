package de.dhbwka.java.exams.jaime.src.tinfb5;

import javax.swing.*;

public class AnswerImage implements Answer{
    // attributes
    ImageIcon imageIcon;
    int tokens;

    // constructor
    public AnswerImage(ImageIcon image, int tokens){
        this.imageIcon = image;
        this.tokens = tokens;
    }

    @Override
    public int getTokens() {
        return tokens;
    }
}

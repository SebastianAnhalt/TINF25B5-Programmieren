package de.dhbwka.java.exams.jaime.src.anis;

import javax.swing.*;

public class ImageAnswer implements Answer{
    // attributes
    private ImageIcon answer;
    private int tokenCount;

    // constructor
    public ImageAnswer(ImageIcon answer, int tokenCount){
        this.answer = answer;
        this.tokenCount = tokenCount;
    }

    // methods

    @Override
    public int getTokens() {
        return tokenCount;
    }
}

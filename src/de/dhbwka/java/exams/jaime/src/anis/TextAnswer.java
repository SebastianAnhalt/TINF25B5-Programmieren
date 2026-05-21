package de.dhbwka.java.exams.jaime.src.anis;

public class TextAnswer implements Answer{
    // attributes
    private String answer;
    private int tokenCount;

    // constructor
    public TextAnswer(String answer, int tokenCount){
        this.answer = answer;
        this.tokenCount = tokenCount;
    }

    // methods

    @Override
    public int getTokens() {
        return tokenCount;
    }
}

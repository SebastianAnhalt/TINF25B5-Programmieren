package de.dhbwka.java.exams.jaime.src.tinfb5;

public class AnswerText implements Answer{
    // attributes
    private String response;
    private int tokens;

    // constructors
    public AnswerText(String response, int tokens){
        this.response = response;
        this.tokens = tokens;
    }

    // getters and setters
    public int getTokens(){
        return tokens;
    }
}

/*
 * File converted to course project at Tue Dec 09 08:16:18 CET 2025
 */

package de.dhbwka.java.exams.jaime.src.klausur_a; /* automatically updated */

public class AIService {
    private String Name;
    private String Slogan;
    private AIType type;
    private float cost;
    private int usedTokens;
    private float usedCost;


    public AIService(String part, String part1, AIType aiType, float v) {
        setName(part);
        setSlogan(part1);
        setType(aiType);
        setCost(v);
    }

    public int getUsedTokens() {
        return usedTokens;
    }

    public void setUsedTokens(int newTokens) {
        usedTokens += newTokens;
    }

    public float getUsedCost() {
        return usedCost;
    }

    public void setUsedCost(float newCost) {
        usedCost += newCost;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSlogan() {
        return Slogan;
    }

    public void setSlogan(String slogan) {
        Slogan = slogan;
    }

    public AIType getType() {
        return type;
    }

    public void setType(AIType type) {
        this.type = type;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return  Name + " - "+ Slogan;
    }
}

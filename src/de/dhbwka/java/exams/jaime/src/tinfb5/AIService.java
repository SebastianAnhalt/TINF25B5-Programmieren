package de.dhbwka.java.exams.jaime.src.tinfb5;

import de.dhbwka.java.exams.jaime.src.klausur_b.AIType;

public class AIService {
    // attributes
    String name;
    String slogan;
    AIType type;
    double price_per_unit;

    // constructors
    public AIService(String name, String slogan, AIType type, double price_per_unit){
        this.name = name;
        this.slogan = slogan;
        this.type = type;
        this.price_per_unit = price_per_unit;
    }

    // getters and setters
    public String getName() {
        return name;
    }
    public String getSlogan(){
        return slogan;
    }
    public AIType getType(){
        return type;
    }
    public double getPrice_per_unit(){
        return price_per_unit;
    }

    // toString
    public String toString(){
        return "Hi, ..";
    }
}

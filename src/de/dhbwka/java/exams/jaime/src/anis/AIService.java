package de.dhbwka.java.exams.jaime.src.anis;

public class AIService {
    private String name;
    private String slogan;
    private AIType type;

    private double price_per_unit;

    // constructors
    public AIService(String name, String slogan, AIType type, double price){
        this.name = name;
        this.slogan = slogan;
        this.type = type;
        this.price_per_unit = price;
    }

    // getters and setters
    public String getName(){
        return name;
    }
    public String getSlogan() {
        return slogan;
    }
    public AIType getType() {
        return type;
    }
    public double getPrice_per_unit() {
        return price_per_unit;
    }


    // toString
    @Override
    public String toString(){
        return "Hi, I am " + name + ", My slogan is: " + slogan;
    }
}

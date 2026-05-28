package de.dhbwka.java.exercise.ui.currency;

public class TestExternal {
    static void main() {
        FromServer service = new FromServer();
        System.out.println(service.eur());
    }
}

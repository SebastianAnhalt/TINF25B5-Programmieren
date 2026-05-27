package de.dhbwka.java.exercise.ui.currency;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

public class ExchangeRateSourceLocal implements ExchangeRateSource {
    public ExchangeRateSourceLocal() {
    }

    public double eur() {
        String dir = new File("src/de/dhbwka/java/exercise/ui/currency/exchangeRateSourceLocal.json").getAbsolutePath();
        try {
            String json = Files.readString(Paths.get(dir));
            String exchangeRate = json.split("\"ETB\":")[1].split("\"FJD\":")[0].split(":")[1].split(",")[0];
            return Double.parseDouble(exchangeRate);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}

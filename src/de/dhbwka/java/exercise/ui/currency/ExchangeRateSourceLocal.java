package de.dhbwka.java.exercise.ui.currency;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ExchangeRateSourceLocal implements ExchangeRateSource {

    private static final String RATES_FILE = "src/de/dhbwka/java/exercise/ui/currency/rates.json";

    @Override
    public double eur() {
        try {
            String json = Files.readString(Paths.get(RATES_FILE));
            // JSON is base USD: rates.EUR = (EUR per 1 USD) → invert to get USD per 1 EUR
            int keyIdx = json.indexOf("\"EUR\":");
            if (keyIdx == -1) return 1.10;
            int valueStart = keyIdx + 6;
            int end = json.length();
            for (int i = valueStart; i < json.length(); i++) {
                char c = json.charAt(i);
                if (c == ',' || c == '}') { end = i; break; }
            }
            double eurPerUsd = Double.parseDouble(json.substring(valueStart, end).trim());
            return 1.0 / eurPerUsd;
        } catch (IOException | NumberFormatException e) {
            return 1.10;
        }
    }
}

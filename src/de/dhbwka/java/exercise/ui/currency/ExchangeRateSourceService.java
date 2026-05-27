package de.dhbwka.java.exercise.ui.currency;

import java.io.IOException;
import java.net.URI;
import java.net.URLConnection;

public class ExchangeRateSourceService implements ExchangeRateSource {

    private static final String API_URL = "https://api.frankfurter.app/latest?from=EUR&to=USD";

    @Override
    public double eur() {
        try {
            URLConnection conn = URI.create(API_URL).toURL().openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            String json = new String(conn.getInputStream().readAllBytes());
            // response: {"amount":1.0,"base":"EUR","date":"...","rates":{"USD":1.0802}}
            int keyIdx = json.indexOf("\"USD\"");
            int colonIdx = json.indexOf(":", keyIdx);
            int end = json.length();
            for (int i = colonIdx + 1; i < json.length(); i++) {
                char c = json.charAt(i);
                if (c == ',' || c == '}') { end = i; break; }
            }
            return Double.parseDouble(json.substring(colonIdx + 1, end).trim());
        } catch (IOException | NumberFormatException e) {
            return 1.10;
        }
    }
}

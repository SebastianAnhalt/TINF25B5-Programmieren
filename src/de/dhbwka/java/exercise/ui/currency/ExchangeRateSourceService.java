package de.dhbwka.java.exercise.ui.currency;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

public class ExchangeRateSourceService implements ExchangeRateSource{
    private final URI url;
    private HashMap<String,Double> map = new HashMap<>();
    private final HttpClient client;


    public FromServer() {
        try {
            url = URI.create("https://open.er-api.com/v6/latest/USD");
            this.client = HttpClient.newHttpClient();
            fetchRates();
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid URL", e);
        }
    }

    private String fetchRates() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IOException("HTTP-Fehler: Statuscode " + response.statusCode());
        }
        formatHashmap(response.body());
        return response.body();
    }

    private void formatHashmap(String string) {
        int start = string.indexOf("\"rates\":") ;
        start = string.indexOf("{", start) + 1;      // erste { nach "rates":
        int end = string.indexOf("}", start);         // schließende } des rates-Blocks
        String ratesBlock = string.substring(start, end);

        for (String pair : ratesBlock.split(",")) {
            String[] data = pair.split(":");
            String key = data[0].replace("\"", "").trim();   // trim() entfernt \n und Leerzeichen
            double value = Double.parseDouble(data[1].trim());
            map.put(key, value);
        }
    }

    @Override
    public  double eur() {
        return map.get("EUR");
    }

    public double getValue(String currency) {
        return map.get(currency);
    }
}

package ua.example;

import lombok.SneakyThrows;
import ua.example.entities.Entities;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

public class Util {
    private static final Logger LOG = Logger.getLogger(Util.class.getName());

    public Map<Double, Double> check(Map<Double, Double> firstMap, Map<Double, Double> secondMap
            , boolean isBids) {

        String name = isBids
                ? "bids"
                : "asks";

        Set<Double> firstKey = firstMap.keySet();
        Set<Double> secondKey = secondMap.keySet();
        for (Double key : secondKey) {
            if (firstMap.containsKey(key)) {
                double firstSize = firstMap.get(key);
                double secondSize = secondMap.get(key);
                if (secondSize != firstSize) {
                    firstMap.put(key, secondSize);
                    LOG.info(String.format("Update [%s] : price : %f , size: %f ", name, key, firstMap.get(key)));
                }
            } else {
                firstMap.put(key, secondMap.get(key));
                LOG.info(String.format("New [%s] : price : %f , size: %f ", name, key, firstMap.get(key)));
            }
        }

        for (Iterator<Double> iterator = firstKey.iterator(); iterator.hasNext(); ) {
            Double key = iterator.next();
            if (!secondMap.containsKey(key)) {
                LOG.info(String.format("Delete [%s] : price : %f , size: %f ", name, key, firstMap.get(key)));
                iterator.remove();
            }
        }

        return firstMap;
    }

    @SneakyThrows
    public Entities jsonFromURL() {
        String url = "https://api.binance.com/api/v3/depth?symbol=LINKUSDT&limit=5000";
        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

        connection.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }

        in.close();

        JsonSimpleParser jsonSimpleParser = new JsonSimpleParser();

        return jsonSimpleParser.parser(response.toString());
    }
}

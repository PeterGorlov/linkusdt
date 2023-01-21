package ua.example;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import ua.example.entities.Asks;
import ua.example.entities.Bids;
import ua.example.entities.Entities;

import java.util.HashMap;
import java.util.Map;

public class JsonSimpleParser {
    public Entities parser(String str) {
        Entities entities = new Entities();

        JsonParser jsonParser = new JsonParser();

        JsonObject entitiesJsonObject = (JsonObject) jsonParser.parse(str);
        Long id = entitiesJsonObject.get("lastUpdateId").getAsLong();

        JsonArray jsonArrayBirds = (JsonArray) entitiesJsonObject.get("bids");
        JsonArray jsonArrayAsks = (JsonArray) entitiesJsonObject.get("asks");

        Map<Double, Double> bidsMap = new HashMap<>();
        Map<Double, Double> asksMap = new HashMap<>();

        for (int i = 0; i < jsonArrayBirds.size(); i++) {
            Bids bids = new Bids();
            bids.setPrice(jsonArrayBirds.get(i).getAsJsonArray().get(0).getAsDouble());
            bids.setSize(jsonArrayBirds.get(i).getAsJsonArray().get(1).getAsDouble());
            bidsMap.put(bids.getPrice(), bids.getSize());
        }

        for (int i = 0; i < jsonArrayAsks.size(); i++) {
            Asks asks = new Asks();
            asks.setPrice(jsonArrayAsks.get(i).getAsJsonArray().get(0).getAsDouble());
            asks.setSize(jsonArrayAsks.get(i).getAsJsonArray().get(1).getAsDouble());
            asksMap.put(asks.getPrice(), asks.getSize());
        }

        entities.setBids(bidsMap);
        entities.setAsks(asksMap);

        entities.setLastUpdateId(id);

        return entities;
    }
}

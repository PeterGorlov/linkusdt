package ua.example;

import lombok.SneakyThrows;
import ua.example.entities.Entities;

import java.util.Map;

public class Main {
    @SneakyThrows
    public static void main(String[] args) {
        Util util = new Util();

        Entities firstEntities = util.jsonFromURL();

        while (firstEntities != null) {

            Thread.sleep(10000);

            Entities secondEntities = util.jsonFromURL();

            Map<Double, Double> bidsMapActual = util.check(firstEntities.getBids(), secondEntities.getBids(),
                    true);
            Map<Double, Double> asksMapActual = util.check(firstEntities.getAsks(), secondEntities.getAsks(),
                    false);

            firstEntities.setBids(bidsMapActual);
            firstEntities.setAsks(asksMapActual);
        }
    }
}

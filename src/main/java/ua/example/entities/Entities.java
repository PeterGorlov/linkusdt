package ua.example.entities;


import lombok.Data;

import java.util.Map;

@Data
public class Entities {
    private Long lastUpdateId;
    Map<Double,Double> bids;
    Map<Double,Double> asks;

}

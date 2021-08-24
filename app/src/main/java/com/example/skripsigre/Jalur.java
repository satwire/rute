package com.example.skripsigre;

import java.util.List;

public class Jalur {
    private List<String> nodes;
    private List<List<Double>> coordinates;
    private List<Double> distance_metres;

    public List<String> getNodes() {
        return nodes;
    }

    public List<List<Double>> getCoordinates() {
        return coordinates;
    }

    public List<Double> getDistance_metres() {
        return distance_metres;
    }
}

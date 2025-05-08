package com.littlepay.tripfarecalculator.repository;

import com.littlepay.tripfarecalculator.model.StopPair;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FareRepository {
    private final Map<StopPair, Double> fareMap = new HashMap<>();

    public FareRepository(String configFilePath) throws IOException {
        // Example hardcoded for now. Replace with JSON load if needed.
        fareMap.put(new StopPair("Stop1", "Stop2"), 3.25);
        fareMap.put(new StopPair("Stop2", "Stop3"), 5.50);
        fareMap.put(new StopPair("Stop1", "Stop3"), 7.30);
    }

    public double getFare(String stopA, String stopB) {
        return fareMap.getOrDefault(new StopPair(stopA, stopB), 0.0);
    }

    public double getMaxFareFrom(String stop) {
        return fareMap.entrySet().stream()
                .filter(e -> stop.endsWith(String.valueOf(e.getKey().stop1)) || stop.endsWith(String.valueOf(e.getKey().stop2)))
                .mapToDouble(Map.Entry::getValue)
                .max().orElse(0.0);
    }
}

package com.littlepay.tripfarecalculator.repository;

import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.littlepay.tripfarecalculator.processor.model.StopPair;
import com.littlepay.tripfarecalculator.processor.model.TripFare;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FareRepository {
    private final Map<StopPair, Double> fareMap = new HashMap<>();

    public FareRepository(InputStream configInputStream) throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
         Map<String, List<TripFare>> yaml = mapper.readValue(configInputStream, Map.class);
        List<TripFare> fares = mapper.convertValue(yaml.get("fares"), mapper.getTypeFactory().constructCollectionType(List.class, TripFare.class));

        for (TripFare entry : fares) {
            fareMap.put(new StopPair(entry.from(), entry.to()), entry.fare());
        }
    }

    public double getFare(String stopA, String stopB) {
        return fareMap.getOrDefault(new StopPair(stopA, stopB), 0.0);
    }

    public double getMaxFareFrom(String stop) {
        return fareMap.entrySet().stream()
                .filter(e -> stop.endsWith(String.valueOf(e.getKey().stop1())) || stop.endsWith(String.valueOf(e.getKey().stop2())))
                .mapToDouble(Map.Entry::getValue)
                .max().orElse(0.0);
    }
}

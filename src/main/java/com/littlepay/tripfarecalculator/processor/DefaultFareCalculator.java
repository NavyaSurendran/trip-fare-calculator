package com.littlepay.tripfarecalculator.processor;

import com.littlepay.tripfarecalculator.repository.FareRepository;

public class DefaultFareCalculator implements FareCalculator{

    private final FareRepository repository;
    @Override
    public double calculateFare(String fromStop, String toStop) {
        return 0;
    }

    @Override
    public double calculateMaxFare(String fromStop) {
        return 0;
    }
}

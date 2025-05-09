package com.littlepay.tripfarecalculator.processor;

import com.littlepay.tripfarecalculator.repository.FareRepository;

public class DefaultFareCalculator implements FareCalculator{

    private final FareRepository repository;

    public DefaultFareCalculator(FareRepository repository) {
        this.repository = repository;
    }

    @Override
    public double calculateFare(String fromStop, String toStop) {
        return repository.getFare(fromStop, toStop);
    }

    @Override
    public double calculateMaxFare(String fromStop) {
        return repository.getMaxFareFrom(fromStop);
    }
}

package com.littlepay.tripfarecalculator.processor;

public interface FareCalculator {
    double calculateFare(String fromStop, String toStop);
    double calculateMaxFare(String fromStop);
}

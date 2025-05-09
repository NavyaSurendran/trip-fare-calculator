package com.littlepay.tripfarecalculator.processor.strategy;

import com.littlepay.tripfarecalculator.processor.model.Tap;
import com.littlepay.tripfarecalculator.processor.model.Trip;
import com.littlepay.tripfarecalculator.processor.FareCalculator;

public class CompletedTripStrategy implements TripStatusStrategy {
    @Override
    public Trip createTrip(Tap tapOn, Tap tapOff, FareCalculator fareCalculator) {
        double fare = fareCalculator.calculateFare(tapOn.stopId(), tapOff.stopId());
        return Trip.completed(tapOn, tapOff, fare);
    }
}

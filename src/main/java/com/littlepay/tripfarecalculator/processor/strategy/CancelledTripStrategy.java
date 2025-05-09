package com.littlepay.tripfarecalculator.processor.strategy;

import com.littlepay.tripfarecalculator.processor.model.Tap;
import com.littlepay.tripfarecalculator.processor.model.Trip;
import com.littlepay.tripfarecalculator.processor.FareCalculator;

public class CancelledTripStrategy implements TripStatusStrategy {
    @Override
    public Trip createTrip(Tap onTap, Tap offTap, FareCalculator fareCalculator) {
        return Trip.cancelled(onTap);
    }
}

package com.littlepay.tripfarecalculator.processor.strategy;

import com.littlepay.tripfarecalculator.processor.model.Tap;
import com.littlepay.tripfarecalculator.processor.model.Trip;
import com.littlepay.tripfarecalculator.processor.FareCalculator;

public interface TripStatusStrategy {
    Trip createTrip(Tap tapOn, Tap tapOff, FareCalculator fareCalculator);
}

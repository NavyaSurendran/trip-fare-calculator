package com.littlepay.tripfarecalculator.processor.builder;

import com.littlepay.tripfarecalculator.processor.model.Tap;
import com.littlepay.tripfarecalculator.processor.model.Trip;

import java.util.List;

public interface TripBuilder {
    List<Trip> buildTrips(List<Tap> taps);
}

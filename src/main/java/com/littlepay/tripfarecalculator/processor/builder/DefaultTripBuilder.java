package com.littlepay.tripfarecalculator.processor.builder;

import com.littlepay.tripfarecalculator.processor.model.Tap;
import com.littlepay.tripfarecalculator.processor.model.Trip;
import com.littlepay.tripfarecalculator.processor.FareCalculator;
import com.littlepay.tripfarecalculator.processor.strategy.CancelledTripStrategy;
import com.littlepay.tripfarecalculator.processor.strategy.CompletedTripStrategy;
import com.littlepay.tripfarecalculator.processor.strategy.TripStatusStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultTripBuilder implements TripBuilder {

    private final FareCalculator fareCalculator;

    public DefaultTripBuilder(FareCalculator fareCalculator) {
        this.fareCalculator = fareCalculator;
    }
    @Override
    public List<Trip> buildTrips(List<Tap> taps) {
        List<Trip> trips = new ArrayList<>();
        Map<String, Tap> pendingTaps = new HashMap<>();

        for (Tap tap : taps) {
            String key = tap.busID() + ":" + tap.PAN();

            if (tap.tapType().equals("ON")) {
                pendingTaps.put(key, tap);
            } else if (tap.tapType().equals("OFF") && pendingTaps.containsKey(key)) {
                Tap onTap = pendingTaps.remove(key);
                TripStatusStrategy strategy = onTap.stopId().equals(tap.stopId())
                        ? new CancelledTripStrategy()
                        : new CompletedTripStrategy();
                trips.add(strategy.createTrip(onTap, tap, fareCalculator));
            }
        }

        for (Tap incomplete : pendingTaps.values()) {
            double fare = fareCalculator.calculateMaxFare(incomplete.stopId());
            trips.add(Trip.incomplete(incomplete, fare));
        }

        return trips;
    }
}

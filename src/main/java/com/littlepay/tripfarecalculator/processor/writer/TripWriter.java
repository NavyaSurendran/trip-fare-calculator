package com.littlepay.tripfarecalculator.processor.writer;

import com.littlepay.tripfarecalculator.processor.model.Trip;

import java.io.IOException;
import java.util.List;

public interface TripWriter {
    void writeTrips(List<Trip> trips) throws IOException;
}

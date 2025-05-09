package com.littlepay.tripfarecalculator.processor;

import com.littlepay.tripfarecalculator.processor.model.Tap;
import com.littlepay.tripfarecalculator.processor.model.Trip;
import com.littlepay.tripfarecalculator.processor.builder.DefaultTripBuilder;
import com.littlepay.tripfarecalculator.processor.builder.TripBuilder;
import com.littlepay.tripfarecalculator.processor.model.TripStatus;
import com.littlepay.tripfarecalculator.processor.reader.CsvTapReader;
import com.littlepay.tripfarecalculator.processor.reader.TapReader;
import com.littlepay.tripfarecalculator.processor.writer.CsvTripWriter;
import com.littlepay.tripfarecalculator.processor.writer.TripWriter;
import com.littlepay.tripfarecalculator.repository.FareRepository;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class TripFareProcessor {
    private final TapReader tapReader;
    private final TripBuilder tripBuilder;
    private final TripWriter tripWriter;
    private Logger logger = Logger.getLogger(TripFareProcessor.class.getName());

    public TripFareProcessor(TapReader tapReader,
                             TripBuilder tripBuilder, TripWriter tripWriter) {
        this.tapReader = tapReader;
        this.tripBuilder = tripBuilder;
        this.tripWriter = tripWriter;
    }
    public void run() throws Exception {
        List<Tap> taps = tapReader.readTaps();
        List<Trip> trips = tripBuilder.buildTrips(taps);
        tripWriter.writeTrips(trips);
        logSummary(trips);
    }
    private void logSummary(List<Trip> trips) {
        Map<TripStatus, Long> summary = trips.stream()
                .collect(Collectors.groupingBy(Trip::status, Collectors.counting()));

        logger.info("Summary Report:");
        summary.forEach((status, count) -> logger.info(status.toString() + ": " + count));

    }


}

package com.littlepay.tripfarecalculator.processor;

import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class TripFareProcessor {
    private static final Logger logger = Logger.getLogger(TripFareProcessor.class.getName());

    public static void main(String[] args) throws Exception {
        FareRepository fareRepository = new FareRepository("fares.json");
        TapReader reader = new CsvTapReader("taps.csv");
        List<Tap> taps = reader.readTaps();

        TripBuilder builder = new DefaultTripBuilder(new DefaultFareCalculator(fareRepository));
        List<Trip> trips = builder.buildTrips(taps);

        TripWriter writer = new CsvTripWriter("trips.csv");
        writer.writeTrips(trips);

        Map<String, Long> summary = trips.stream()
                .collect(Collectors.groupingBy(Trip::status, Collectors.counting()));
        double totalRevenue = trips.stream().mapToDouble(Trip::chargeAmount).sum();

        logger.info("Summary Report:");
        summary.forEach((status, count) -> logger.info(status + ": " + count));
        logger.info("Total Revenue: $" + totalRevenue);
    }
}

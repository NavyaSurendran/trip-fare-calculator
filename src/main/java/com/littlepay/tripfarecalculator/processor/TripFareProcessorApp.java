package com.littlepay.tripfarecalculator.processor;

import com.littlepay.tripfarecalculator.processor.builder.DefaultTripBuilder;
import com.littlepay.tripfarecalculator.processor.builder.TripBuilder;
import com.littlepay.tripfarecalculator.processor.reader.CsvTapReader;
import com.littlepay.tripfarecalculator.processor.reader.TapReader;
import com.littlepay.tripfarecalculator.processor.writer.CsvTripWriter;
import com.littlepay.tripfarecalculator.processor.writer.TripWriter;
import com.littlepay.tripfarecalculator.repository.FareRepository;

import java.io.InputStream;
import java.util.logging.Logger;

public class TripFareProcessorApp {
    private static Logger logger = Logger.getLogger(TripFareProcessor.class.getName());
    public static void main(String[] args) throws Exception {
        InputStream input = TripFareProcessor.class.getClassLoader().getResourceAsStream("fares.yaml");
        TapReader tapReader = new CsvTapReader("taps.csv");
        FareRepository fareRepository = new FareRepository(input);
        TripBuilder tripBuilder = new DefaultTripBuilder(new DefaultFareCalculator(fareRepository));
        TripWriter tripWriter = new CsvTripWriter("trips.csv");

        TripFareProcessor processor = new TripFareProcessor(tapReader, tripBuilder, tripWriter);
        double totalrevenue = processor.run();
        logger.info("Total Revenue: " + totalrevenue);
    }
}

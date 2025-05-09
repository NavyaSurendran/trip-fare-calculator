package com.littlepay.tripfarecalculator.processor.writer;

import com.littlepay.tripfarecalculator.processor.model.Trip;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CsvTripWriter implements TripWriter{
    private final String fileName;

    public CsvTripWriter(String fileName) {
        this.fileName = fileName;
    }
    @Override
    public void writeTrips(List<Trip> trips) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(Paths.get(fileName)))) {
            writer.println("Started,Finished,DurationSecs,FromStopId,ToStopId,ChargeAmount,CompanyId,BusID,PAN,Status");
            for (Trip trip : trips) {
                writer.printf("%s,%s,%d,%s,%s,$%.2f,%s,%s,%s,%s\n",
                        trip.started() != null ? trip.started().format(formatter) : "",
                        trip.finished() != null ? trip.finished().format(formatter) : "",
                        trip.durationSecs(),
                        trip.fromStopId(),
                        trip.toStopId() != null ? trip.toStopId() : "",
                        trip.chargeAmount(),
                        trip.companyId(),
                        trip.busID(),
                        trip.PAN(),
                        trip.status());
            }
        }
    }
}

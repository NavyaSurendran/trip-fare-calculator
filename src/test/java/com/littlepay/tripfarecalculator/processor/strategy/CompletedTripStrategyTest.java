package com.littlepay.tripfarecalculator.processor.strategy;

import com.littlepay.tripfarecalculator.processor.FareCalculator;
import com.littlepay.tripfarecalculator.processor.model.Tap;
import com.littlepay.tripfarecalculator.processor.model.Trip;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.littlepay.tripfarecalculator.processor.model.TripStatus.COMPLETED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

public class CompletedTripStrategyTest {
    @Test
    void shouldReturnCompletedTrip() {

        CompletedTripStrategy strategy = new CompletedTripStrategy();

        Tap tapOn =   buildTap(1, "22-01-2023 13:00:00",  "ON", "Stop1", "Company1", "Bus37", "5500005555555559");
        Tap tapOff = buildTap(2, "21-01-2023 13:10:00",  "OFF", "Stop2", "Company1", "Bus37", "5500005555555559");

        FareCalculator fareCalculator = mock(FareCalculator.class);
        Trip trip = strategy.createTrip(tapOn, tapOff, fareCalculator);
        assertNotNull(trip);
        assertEquals(COMPLETED, trip.status());
    }

    private Tap buildTap(int id,
                         String time,
                         String tapType,
                         String stopId,
                         String companyId,
                         String busID,
                         String PAN){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime dateTimeUTC = LocalDateTime.parse(time, formatter);
        return new Tap(id, dateTimeUTC, tapType, stopId, companyId, busID, PAN);

    }
}

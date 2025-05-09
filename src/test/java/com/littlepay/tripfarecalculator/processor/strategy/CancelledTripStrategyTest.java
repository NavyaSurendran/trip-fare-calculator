package com.littlepay.tripfarecalculator.processor.strategy;

import com.littlepay.tripfarecalculator.processor.FareCalculator;
import com.littlepay.tripfarecalculator.processor.model.Tap;
import com.littlepay.tripfarecalculator.processor.model.Trip;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static com.littlepay.tripfarecalculator.processor.model.TripStatus.CANCELLED;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class CancelledTripStrategyTest {

    @Test
    void shouldReturnCancelledTrip() {

        CancelledTripStrategy strategy = new CancelledTripStrategy();

              Tap tapOn =   buildTap(1, "22-01-2023 13:00:00",  "ON", "Stop1", "Company1", "Bus37", "5500005555555559");
              Tap tapOff = buildTap(2, "21-01-2023 13:10:00",  "OFF", "Stop1", "Company1", "Bus37", "5500005555555559");

        FareCalculator fareCalculator = mock(FareCalculator.class);
        Trip trip = strategy.createTrip(tapOn, tapOff, fareCalculator);
        assertNotNull(trip);
        assertEquals(CANCELLED, trip.status());
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

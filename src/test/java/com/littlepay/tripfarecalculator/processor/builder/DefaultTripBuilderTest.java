package com.littlepay.tripfarecalculator.processor.builder;

import com.littlepay.tripfarecalculator.processor.FareCalculator;
import com.littlepay.tripfarecalculator.processor.model.Tap;
import com.littlepay.tripfarecalculator.processor.model.Trip;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static com.littlepay.tripfarecalculator.processor.model.TripStatus.CANCELLED;
import static com.littlepay.tripfarecalculator.processor.model.TripStatus.INCOMPLETE;
import static com.littlepay.tripfarecalculator.processor.model.TripStatus.COMPLETED;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DefaultTripBuilderTest {
    private FareCalculator fareCalculator;
    private DefaultTripBuilder tripBuilder;

    @BeforeEach
    void setUp() {
        fareCalculator = mock(FareCalculator.class);
        tripBuilder = new DefaultTripBuilder(fareCalculator);
    }

    @Test
    void testBuildTrips_withCompletedTrip() {
        List<Tap> taps = Arrays.asList(
                buildTap(1, "22-01-2023 13:00:00",  "ON", "Stop1", "Company1", "Bus37", "5500005555555559"),
                buildTap(2, "21-01-2023 13:10:00",  "OFF", "Stop2", "Company1", "Bus37", "5500005555555559")
        );
        when(fareCalculator.calculateFare("Stop1", "Stop2")).thenReturn(3.25);
        List<Trip> trips = tripBuilder.buildTrips(taps);
        assertEquals(1, trips.size());
        Trip trip = trips.get(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime startedTime = LocalDateTime.parse("22-01-2023 13:00:00", formatter);
        LocalDateTime endedTime = LocalDateTime.parse("21-01-2023 13:10:00", formatter);
        assertNotNull(trip);
        assertEquals(3.25, trip.chargeAmount());
        assertEquals("Company1", trip.companyId());
        assertEquals("Bus37", trip.busID());
        assertEquals(COMPLETED, trip.status());
    }

    @Test
    void testBuildTrips_withCancelledTrip() {
        List<Tap> taps = Arrays.asList(
                buildTap(1, "22-01-2023 13:00:00",  "ON", "Stop1", "Company1", "Bus37", "5500005555555559"),
                buildTap(2, "21-01-2023 13:10:00",  "OFF", "Stop1", "Company1", "Bus37", "5500005555555559")
        );
        when(fareCalculator.calculateFare("Stop1", "Stop2")).thenReturn(3.25);
        when(fareCalculator.calculateMaxFare("Stop1")).thenReturn(10.0);
        when(fareCalculator.calculateMaxFare("Stop2")).thenReturn(5.0);

        // Execute the method
        List<Trip> trips = tripBuilder.buildTrips(taps);

        // Assert that one trip is created with cancelled status
        assertEquals(1, trips.size());
        Trip trip = trips.get(0);
        assertNotNull(trip);
        assertEquals(0, trip.chargeAmount());
        assertEquals(CANCELLED, trip.status());
    }

    @Test
    void testBuildTrips_withIncompleteTrip() {
        List<Tap> taps = Arrays.asList(
                buildTap(1, "22-01-2023 13:00:00",  "ON", "Stop1", "Company1", "Bus37", "5500005555555559")
        );
        when(fareCalculator.calculateFare("Stop1", "Stop2")).thenReturn(3.25);
        when(fareCalculator.calculateMaxFare("Stop1")).thenReturn(10.0);
        // Execute the method
        List<Trip> trips = tripBuilder.buildTrips(taps);

        assertEquals(1, trips.size());
        Trip trip = trips.get(0);
        assertNotNull(trip);
        assertEquals(10, trip.chargeAmount());
        assertEquals(INCOMPLETE, trip.status());
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

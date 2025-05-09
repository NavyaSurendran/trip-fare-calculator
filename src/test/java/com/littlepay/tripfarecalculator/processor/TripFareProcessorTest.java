package com.littlepay.tripfarecalculator.processor;

import com.littlepay.tripfarecalculator.processor.model.Tap;
import com.littlepay.tripfarecalculator.processor.model.Trip;
import com.littlepay.tripfarecalculator.processor.builder.TripBuilder;
import com.littlepay.tripfarecalculator.processor.model.TripStatus;
import com.littlepay.tripfarecalculator.processor.reader.TapReader;
import com.littlepay.tripfarecalculator.processor.writer.TripWriter;
import com.littlepay.tripfarecalculator.repository.FareRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static com.littlepay.tripfarecalculator.processor.model.TripStatus.COMPLETED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TripFareProcessorTest {

    @Mock
    private FareRepository fareRepository;

    @Mock
    private TapReader tapReader;

    @Mock
    private TripBuilder tripBuilder;

    @Mock
    private TripWriter tripWriter;

    @InjectMocks
    private TripFareProcessor tripFareProcessor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testMainProcessingFlow() throws Exception {
        List<Tap> taps = Arrays.asList(
                buildTap(1, "22-01-2023 13:00:00",  "ON", "Stop1", "Company1", "Bus37", "5500005555555559"),
                buildTap(2, "21-01-2023 13:10:00",  "OFF", "Stop2", "Company1", "Bus37", "5500005555555559")
        );
        when(fareRepository.getFare("stop1", "stop2")).thenReturn(3.25);
        when(tapReader.readTaps()).thenReturn(taps);

        List<Trip> trips = Arrays.asList(buildTrip("22-01-2023 13:00:00", "21-01-2023 13:10:00", 600, "Stop1",
                "Stop2", 3.25, "Company1", "Bus37", "5500005555555559", COMPLETED));
        when(tripBuilder.buildTrips(taps)).thenReturn(trips);

        doNothing().when(tripWriter).writeTrips(trips);
        double totalRevenue = tripFareProcessor.run();

        verify(tapReader, times(1)).readTaps();
        verify(tripBuilder, times(1)).buildTrips(taps);
        verify(tripWriter, times(1)).writeTrips(trips);
        assertEquals(totalRevenue, 3.25);
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

    private Trip buildTrip(String started,
                           String finished,
                           long durationSecs,
                           String fromStopId,
                           String toStopId,
                           double chargeAmount,
                           String companyId,
                           String busID,
                           String PAN,
                           TripStatus status) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime startedUTC = LocalDateTime.parse(started, formatter);
        LocalDateTime finishedUTC = LocalDateTime.parse(finished, formatter);
        return new Trip(startedUTC, finishedUTC, durationSecs, fromStopId, toStopId, chargeAmount, companyId, busID, PAN, status);
    }
}

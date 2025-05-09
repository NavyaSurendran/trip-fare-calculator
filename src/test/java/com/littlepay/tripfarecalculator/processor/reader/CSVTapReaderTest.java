package com.littlepay.tripfarecalculator.processor.reader;

import com.littlepay.tripfarecalculator.processor.model.Tap;
import com.littlepay.tripfarecalculator.processor.reader.CsvTapReader;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CSVTapReaderTest {
    private String getFilePath(String resourceName) throws URISyntaxException {
        URL resource = getClass().getClassLoader().getResource(resourceName);
        if (resource == null) {
            throw new IllegalArgumentException("Resource not found: " + resourceName);
        }
        return Paths.get(resource.toURI()).toString();
    }
    @Test
    void testReadTaps_validAndInvalidRows_shouldParseValidAndIgnoreInvalid() throws Exception {
        CsvTapReader reader = new CsvTapReader("valid_and_invalid_rows.csv");
        List<Tap> actualTaps = reader.readTaps();

        /*Tap tap1 = buildTap(1, "22-01-2023 13:00:00",  "ON", "Stop1", "Company1", "Bus37", "5500005555555559");
        Tap tap2 = buildTap(2, "21-01-2023 12:00:00",  "OFF", "Stop2", "Company1", "Bus37", "5500005555555559");
        List<Tap> expectedTaps = Arrays.asList(tap1, tap2);*/
        assertEquals(2, actualTaps.size());
        // assertEquals(actualTaps, expectedTaps);
        assertTrue(actualTaps.get(0).dateTimeUTC().isBefore(actualTaps.get(1).dateTimeUTC()));
    }

    @Test
    void testReadTaps_emptyFile_shouldReturnEmptyList() throws Exception {
        CsvTapReader reader = new CsvTapReader("empty.csv");
        List<Tap> taps = reader.readTaps();
        assertTrue(taps.isEmpty());
    }

    @Test
    void testReadTaps_malformedTimestamp_shouldSkipRow() throws Exception {
        CsvTapReader reader = new CsvTapReader("malformed_timestamp.csv");
        List<Tap> taps = reader.readTaps();

        assertEquals(1, taps.size());
        assertEquals(2, taps.get(0).id());
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

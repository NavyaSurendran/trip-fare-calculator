package com.littlepay.tripfarecalculator.processor.reader;

import com.littlepay.tripfarecalculator.processor.model.Tap;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class CsvTapReader implements TapReader{
    private final String fileName;
    private static final Logger logger = Logger.getLogger(CsvTapReader.class.getName());

    public CsvTapReader(String fileName) {
        this.fileName = fileName;
    }
    @Override
    public List<Tap> readTaps() throws IOException {
        //List<String> lines = Files.readAllLines(Paths.get(fileName));
        InputStream is = getClass().getClassLoader().getResourceAsStream(fileName);
        if (is == null) throw new FileNotFoundException("File not found: " + fileName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        List<String> lines = reader.lines().collect(Collectors.toList());
        lines.remove(0); // remove header
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return lines.stream().map(line -> {
            String[] parts = line.split(",");
            try {
                return new Tap(
                        Integer.parseInt(parts[0].trim()),
                        LocalDateTime.parse(parts[1].trim(), formatter),
                        parts[2].trim(),
                        parts[3].trim(),
                        parts[4].trim(),
                        parts[5].trim(),
                        parts[6].trim()
                );
            } catch (Exception e) {
                logger.warning("Skipping invalid line: " + line);
                return null;
            }
        }).filter(Objects::nonNull).sorted(Comparator.comparing(Tap::dateTimeUTC)).collect(Collectors.toList());
    }
}

package com.littlepay.tripfarecalculator.processor.reader;

import com.littlepay.tripfarecalculator.processor.model.Tap;

import java.io.IOException;
import java.util.List;

public interface TapReader {
    List<Tap> readTaps() throws IOException;
}

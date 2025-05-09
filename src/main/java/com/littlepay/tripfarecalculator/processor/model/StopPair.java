package com.littlepay.tripfarecalculator.processor.model;

import java.util.Objects;

public record StopPair(int stop1, int stop2) {
    public StopPair(String s1, String s2) {
        this(
                Math.min(
                        Integer.parseInt(s1.substring(s1.length() - 1)),
                        Integer.parseInt(s2.substring(s2.length() - 1))
                ),
                Math.max(
                        Integer.parseInt(s1.substring(s1.length() - 1)),
                        Integer.parseInt(s2.substring(s2.length() - 1))
                )
        );
    }
}

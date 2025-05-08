package com.littlepay.tripfarecalculator.model;

import java.util.Objects;

public record StopPair(int stop1, int stop2) {
    public StopPair(String s1, String s2) {
        int a = Integer.parseInt(s1.substring(s1.length() - 1));
        int b = Integer.parseInt(s2.substring(s2.length() - 1));
        this.stop1 = Math.min(a, b);
        this.stop2 = Math.max(a, b);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StopPair)) return false;
        StopPair that = (StopPair) o;
        return stop1 == that.stop1 && stop2 == that.stop2;
    }

    @Override
    public int hashCode() {
        return Objects.hash(stop1, stop2);
    }
}

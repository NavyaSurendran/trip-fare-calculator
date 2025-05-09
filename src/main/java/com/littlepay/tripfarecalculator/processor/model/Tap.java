package com.littlepay.tripfarecalculator.processor.model;

import java.time.LocalDateTime;

public record Tap(int id,
        LocalDateTime dateTimeUTC,
        String tapType,
        String stopId,
        String companyId,
        String busID,
        String PAN) {
    @Override
    public String toString() {
        return "Tap{" +
                "id=" + id +
                ", dateTimeUTC=" + dateTimeUTC +
                ", tapType='" + tapType + '\'' +
                ", stopId='" + stopId + '\'' +
                ", companyId='" + companyId + '\'' +
                ", busID='" + busID + '\'' +
                ", PAN='" + PAN + '\'' +
                '}';
    }
}


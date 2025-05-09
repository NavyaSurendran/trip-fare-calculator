package com.littlepay.tripfarecalculator.processor.model;

import com.littlepay.tripfarecalculator.processor.FareCalculator;

import java.time.Duration;
import java.time.LocalDateTime;

import static com.littlepay.tripfarecalculator.processor.model.TripStatus.CANCELLED;
import static com.littlepay.tripfarecalculator.processor.model.TripStatus.COMPLETED;
import static com.littlepay.tripfarecalculator.processor.model.TripStatus.INCOMPLETE;

public record Trip(LocalDateTime started,
                   LocalDateTime finished,
                   long durationSecs,
                   String fromStopId,
                   String toStopId,
                   double chargeAmount,
                   String companyId,
                   String busID,
                   String PAN,
                   TripStatus status) {

    public static Trip completed(Tap onTap, Tap offTap, double chargeAmount) {
        long duration = Duration.between(onTap.dateTimeUTC(), offTap.dateTimeUTC()).getSeconds();
        return new Trip(
                onTap.dateTimeUTC(),
                offTap.dateTimeUTC(),
                duration,
                onTap.stopId(),
                offTap.stopId(),
                chargeAmount,
                onTap.companyId(),
                onTap.busID(),
                onTap.PAN(),
                COMPLETED
        );
    }

    public static Trip cancelled(Tap tap) {
        return new Trip(
                tap.dateTimeUTC(),
                tap.dateTimeUTC(),
                0,
                tap.stopId(),
                tap.stopId(),
                0.0,
                tap.companyId(),
                tap.busID(),
                tap.PAN(),
                CANCELLED
        );
    }


    public static Trip incomplete(Tap tap, double chargeAmount) {
        return new Trip(
                tap.dateTimeUTC(),
                null,
                0,
                tap.stopId(),
                null,
                chargeAmount,
                tap.companyId(),
                tap.busID(),
                tap.PAN(),
                INCOMPLETE
        );
    }
}

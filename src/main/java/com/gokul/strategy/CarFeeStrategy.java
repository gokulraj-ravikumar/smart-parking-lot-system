package com.gokul.strategy;

import java.time.Duration;
import java.time.LocalDateTime;

public class CarFeeStrategy implements FeeCalculationStrategy {
    @Override
    public double calculateFee(LocalDateTime entryTime, LocalDateTime exitTime) {
        long hours = Math.max(1, Duration.between(entryTime, exitTime).toHours());
        return hours * 20.0;
    }
}
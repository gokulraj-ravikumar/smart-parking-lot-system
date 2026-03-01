package com.gokul.strategy;

import java.time.Duration;
import java.time.LocalDateTime;

public class BusFeeStrategy implements FeeCalculationStrategy {
    @Override
    public double calculateFee(LocalDateTime entryTime, LocalDateTime exitTime) {
        long hours = Math.max(1, Duration.between(entryTime, exitTime).toHours());
        return hours * 50.0;
    }
}
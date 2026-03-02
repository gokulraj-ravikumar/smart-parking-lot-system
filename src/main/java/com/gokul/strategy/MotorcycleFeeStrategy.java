package com.gokul.strategy;


import com.gokul.config.ConfigLoader;

import java.time.Duration;
import java.time.LocalDateTime;

public class MotorcycleFeeStrategy implements FeeCalculationStrategy {
    @Override
    public double calculateFee(LocalDateTime entryTime, LocalDateTime exitTime) {
        long hours = Math.max(1, Duration.between(entryTime, exitTime).toHours());
        return hours * ConfigLoader.getDoubleProperty("fee.hourly.motorcycle");
    }
}
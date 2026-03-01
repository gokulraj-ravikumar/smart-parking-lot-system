package com.gokul.strategy;

import java.time.LocalDateTime;

public interface FeeCalculationStrategy {
    double calculateFee(LocalDateTime entryTime, LocalDateTime exitTime);
}
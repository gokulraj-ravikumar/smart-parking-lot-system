package com.gokul.factory;

import com.gokul.model.Vehicle;
import com.gokul.model.enums.VehicleType;

public class VehicleFactory {
    public static Vehicle createVehicle(String licensePlate, VehicleType type) {
        return new Vehicle(licensePlate, type);
    }
}
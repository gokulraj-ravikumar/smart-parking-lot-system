package com.gokul.model;

import com.gokul.model.enums.SpotStatus;
import com.gokul.model.enums.VehicleType;

public class ParkingSpot {
    private int spotId;
    private int floorNumber;
    private VehicleType spotType;
    private SpotStatus status;

    public ParkingSpot(int spotId, int floorNumber, VehicleType spotType, SpotStatus status) {
        this.spotId = spotId;
        this.floorNumber = floorNumber;
        this.spotType = spotType;
        this.status = status;
    }

    public int getSpotId() {
        return spotId;
    }

    public void setSpotId(int spotId) {
        this.spotId = spotId;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    public VehicleType getSpotType() {
        return spotType;
    }

    public void setSpotType(VehicleType spotType) {
        this.spotType = spotType;
    }

    public SpotStatus getStatus() {
        return status;
    }

    public void setStatus(SpotStatus status) {
        this.status = status;
    }
}
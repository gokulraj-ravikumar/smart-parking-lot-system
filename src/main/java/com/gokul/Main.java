package com.gokul;

import com.gokul.factory.VehicleFactory;
import com.gokul.model.Ticket;
import com.gokul.model.Vehicle;
import com.gokul.model.enums.VehicleType;
import com.gokul.service.ParkingLotService;

public class Main {
    public static void main(String[] args) {
        ParkingLotService service = new ParkingLotService();

        System.out.println("=== INITIALIZING SMART PARKING LOT SYSTEM ===");

        // Initialize vehicles
        Vehicle car1 = VehicleFactory.createVehicle("TN-30-AA-1111", VehicleType.CAR);
        Vehicle car2 = VehicleFactory.createVehicle("TN-30-BB-2222", VehicleType.CAR);
        Vehicle bike1 = VehicleFactory.createVehicle("TN-30-CC-3333", VehicleType.MOTORCYCLE);
        Vehicle bus1 = VehicleFactory.createVehicle("TN-30-DD-4444", VehicleType.BUS);

        System.out.println("\n--- PROCESSING INCOMING VEHICLES ---");
        Ticket carTicket1 = service.parkVehicle(car1);
        Ticket carTicket2 = service.parkVehicle(car2);
        Ticket bikeTicket = service.parkVehicle(bike1);
        Ticket busTicket = service.parkVehicle(bus1);

        System.out.println("\n--- SIMULATING CHECK-OUT & FEE CALCULATION ---");
        if (carTicket1 != null) {
            service.unparkVehicle(carTicket1.getTicketId(), car1);
        }
        if (bikeTicket != null) {
            service.unparkVehicle(bikeTicket.getTicketId(), bike1);
        }
        if (busTicket != null) {
            service.unparkVehicle(busTicket.getTicketId(), bus1);
        }

        System.out.println("\n=== SYSTEM SHUTDOWN ===");
    }
}
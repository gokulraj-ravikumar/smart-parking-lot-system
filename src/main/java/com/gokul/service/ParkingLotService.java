package com.gokul.service;

import com.gokul.dao.ParkingSpotDAO;
import com.gokul.dao.TicketDAO;
import com.gokul.model.ParkingSpot;
import com.gokul.model.Ticket;
import com.gokul.model.Vehicle;
import com.gokul.model.enums.SpotStatus;
import com.gokul.strategy.BusFeeStrategy;
import com.gokul.strategy.CarFeeStrategy;
import com.gokul.strategy.FeeCalculationStrategy;
import com.gokul.strategy.MotorcycleFeeStrategy;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.UUID;

public class ParkingLotService {
    private final ParkingSpotDAO spotDAO = new ParkingSpotDAO();
    private final TicketDAO ticketDAO = new TicketDAO();

    public Ticket parkVehicle(Vehicle vehicle) {
        try {
            ParkingSpot spot = spotDAO.findAvailableSpot(vehicle.getType());
            if (spot == null) {
                System.out.println("ALERT: Parking lot is full for vehicle type - " + vehicle.getType());
                return null;
            }

            String ticketId = UUID.randomUUID().toString();
            Ticket ticket = new Ticket(ticketId, vehicle.getLicensePlate(), spot.getSpotId(), LocalDateTime.now());

            spotDAO.updateSpotStatus(spot.getSpotId(), SpotStatus.OCCUPIED);
            ticketDAO.saveTicket(ticket);

            System.out.println("SUCCESS: " + vehicle.getType() + " parked. Ticket ID: " + ticketId + " at Floor: " + spot.getFloorNumber() + ", Spot ID: " + spot.getSpotId());
            return ticket;

        } catch (SQLException e) {
            System.err.println("Database error during check-in: " + e.getMessage());
            return null;
        }
    }

    public void unparkVehicle(String ticketId, Vehicle vehicle) {
        try {
            Ticket ticket = ticketDAO.getTicket(ticketId);
            if (ticket == null) {
                System.out.println("ERROR: Invalid ticket ID.");
                return;
            }

            // Simulating an exit time 3 hours into the future to demonstrate fee calculation
            ticket.setExitTime(LocalDateTime.now().plusHours(3));

            FeeCalculationStrategy strategy = switch (vehicle.getType()) {
                case CAR -> new CarFeeStrategy();
                case MOTORCYCLE -> new MotorcycleFeeStrategy();
                case BUS -> new BusFeeStrategy();
            };

            double fee = strategy.calculateFee(ticket.getEntryTime(), ticket.getExitTime());
            ticket.setTotalFee(fee);

            ticketDAO.updateTicket(ticket);
            spotDAO.updateSpotStatus(ticket.getSpotId(), SpotStatus.AVAILABLE);

            System.out.println("SUCCESS: Vehicle " + vehicle.getLicensePlate() + " exited. Total Fee: $" + fee);

        } catch (SQLException e) {
            System.err.println("Database error during check-out: " + e.getMessage());
        }
    }
}
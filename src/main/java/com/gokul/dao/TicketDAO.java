package com.gokul.dao;

import com.gokul.config.DatabaseConnectionManager;
import com.gokul.model.Ticket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class TicketDAO {

    public void saveTicket(Ticket ticket) throws SQLException {
        String vehicleQuery = "INSERT IGNORE INTO vehicles (license_plate, vehicle_type) VALUES (?, ?)";
        String ticketQuery = "INSERT INTO parking_transactions (ticket_id, license_plate, spot_id, entry_time) VALUES (?, ?, ?, ?)";
        Connection conn = DatabaseConnectionManager.getInstance().getConnection();

        // Ensure vehicle exists before creating ticket to satisfy foreign key
        try (PreparedStatement vehicleStmt = conn.prepareStatement(vehicleQuery)) {
            vehicleStmt.setString(1, ticket.getLicensePlate());
            vehicleStmt.setString(2, "UNKNOWN"); // Simplified for persistence layer separation
            vehicleStmt.executeUpdate();
        }

        try (PreparedStatement stmt = conn.prepareStatement(ticketQuery)) {
            stmt.setString(1, ticket.getTicketId());
            stmt.setString(2, ticket.getLicensePlate());
            stmt.setInt(3, ticket.getSpotId());
            stmt.setTimestamp(4, Timestamp.valueOf(ticket.getEntryTime()));
            stmt.executeUpdate();
        }
    }

    public Ticket getTicket(String ticketId) throws SQLException {
        String query = "SELECT * FROM parking_transactions WHERE ticket_id = ?";
        Connection conn = DatabaseConnectionManager.getInstance().getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, ticketId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Ticket ticket = new Ticket(
                        rs.getString("ticket_id"),
                        rs.getString("license_plate"),
                        rs.getInt("spot_id"),
                        rs.getTimestamp("entry_time").toLocalDateTime()
                );

                Timestamp exitTimestamp = rs.getTimestamp("exit_time");
                if (exitTimestamp != null) {
                    ticket.setExitTime(exitTimestamp.toLocalDateTime());
                }
                ticket.setTotalFee(rs.getDouble("total_fee"));

                return ticket;
            }
        }
        return null;
    }

    public void updateTicket(Ticket ticket) throws SQLException {
        String query = "UPDATE parking_transactions SET exit_time = ?, total_fee = ? WHERE ticket_id = ?";
        Connection conn = DatabaseConnectionManager.getInstance().getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setTimestamp(1, Timestamp.valueOf(ticket.getExitTime()));
            stmt.setDouble(2, ticket.getTotalFee());
            stmt.setString(3, ticket.getTicketId());
            stmt.executeUpdate();
        }
    }
}
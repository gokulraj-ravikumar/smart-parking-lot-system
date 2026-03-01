package com.gokul.dao;

import com.gokul.config.DatabaseConnectionManager;
import com.gokul.model.ParkingSpot;
import com.gokul.model.enums.SpotStatus;
import com.gokul.model.enums.VehicleType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ParkingSpotDAO {

    public ParkingSpot findAvailableSpot(VehicleType type) throws SQLException {
        String query = "SELECT * FROM parking_spots WHERE spot_type = ? AND status = 'AVAILABLE' LIMIT 1";
        Connection conn = DatabaseConnectionManager.getInstance().getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, type.name());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new ParkingSpot(
                        rs.getInt("spot_id"),
                        rs.getInt("floor_number"),
                        VehicleType.valueOf(rs.getString("spot_type")),
                        SpotStatus.valueOf(rs.getString("status"))
                );
            }
        }
        return null;
    }

    public void updateSpotStatus(int spotId, SpotStatus status) throws SQLException {
        String query = "UPDATE parking_spots SET status = ? WHERE spot_id = ?";
        Connection conn = DatabaseConnectionManager.getInstance().getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, status.name());
            stmt.setInt(2, spotId);
            stmt.executeUpdate();
        }
    }
}
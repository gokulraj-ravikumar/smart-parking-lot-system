CREATE DATABASE IF NOT EXISTS parking_lot_db;
USE parking_lot_db;

CREATE TABLE parking_spots (
    spot_id INT AUTO_INCREMENT PRIMARY KEY,
    floor_number INT NOT NULL,
    spot_type VARCHAR(20) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'AVAILABLE'
);

CREATE TABLE vehicles (
    license_plate VARCHAR(20) PRIMARY KEY,
    vehicle_type VARCHAR(20) NOT NULL
);

CREATE TABLE parking_transactions (
    ticket_id VARCHAR(50) PRIMARY KEY,
    license_plate VARCHAR(20) NOT NULL,
    spot_id INT NOT NULL,
    entry_time DATETIME NOT NULL,
    exit_time DATETIME,
    total_fee DECIMAL(10, 2),
    FOREIGN KEY (license_plate) REFERENCES vehicles(license_plate),
    FOREIGN KEY (spot_id) REFERENCES parking_spots(spot_id)
);

-- Floor 1: Buses and motorcycles
INSERT INTO parking_spots (floor_number, spot_type, status) VALUES 
(1, 'BUS', 'AVAILABLE'), (1, 'BUS', 'AVAILABLE'), (1, 'BUS', 'AVAILABLE'), (1, 'BUS', 'AVAILABLE'), (1, 'BUS', 'AVAILABLE'),
(1, 'MOTORCYCLE', 'AVAILABLE'), (1, 'MOTORCYCLE', 'AVAILABLE'), (1, 'MOTORCYCLE', 'AVAILABLE'), (1, 'MOTORCYCLE', 'AVAILABLE'), (1, 'MOTORCYCLE', 'AVAILABLE'),
(1, 'MOTORCYCLE', 'AVAILABLE'), (1, 'MOTORCYCLE', 'AVAILABLE'), (1, 'MOTORCYCLE', 'AVAILABLE'), (1, 'MOTORCYCLE', 'AVAILABLE'), (1, 'MOTORCYCLE', 'AVAILABLE');

-- Floor 2: Cars
INSERT INTO parking_spots (floor_number, spot_type, status) VALUES 
(2, 'CAR', 'AVAILABLE'), (2, 'CAR', 'AVAILABLE'), (2, 'CAR', 'AVAILABLE'), (2, 'CAR', 'AVAILABLE'), (2, 'CAR', 'AVAILABLE'),
(2, 'CAR', 'AVAILABLE'), (2, 'CAR', 'AVAILABLE'), (2, 'CAR', 'AVAILABLE'), (2, 'CAR', 'AVAILABLE'), (2, 'CAR', 'AVAILABLE');

-- Floor 3: Cars
INSERT INTO parking_spots (floor_number, spot_type, status) VALUES 
(3, 'CAR', 'AVAILABLE'), (3, 'CAR', 'AVAILABLE'), (3, 'CAR', 'AVAILABLE'), (3, 'CAR', 'AVAILABLE'), (3, 'CAR', 'AVAILABLE'),
(3, 'CAR', 'AVAILABLE'), (3, 'CAR', 'AVAILABLE'), (3, 'CAR', 'AVAILABLE'), (3, 'CAR', 'AVAILABLE'), (3, 'CAR', 'AVAILABLE');

-- Seeding Data: Historical Vehicles
INSERT INTO vehicles (license_plate, vehicle_type) VALUES 
('TN-30-XY-9876', 'CAR'),
('TN-30-ZZ-5432', 'MOTORCYCLE');

-- Seeding Data: Historical Transactions fir completed parking sessions
INSERT INTO parking_transactions (ticket_id, license_plate, spot_id, entry_time, exit_time, total_fee) VALUES 
('hist-ticket-001', 'TN-30-XY-9876', 16, DATE_SUB(NOW(), INTERVAL 5 HOUR), DATE_SUB(NOW(), INTERVAL 2 HOUR), 60.00),
('hist-ticket-002', 'TN-30-ZZ-5432', 6, DATE_SUB(NOW(), INTERVAL 10 HOUR), DATE_SUB(NOW(), INTERVAL 9 HOUR), 10.00);
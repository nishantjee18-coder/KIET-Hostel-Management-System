-- KIET Hostel Management System
-- MySQL setup script for the Java JDBC assignment

CREATE DATABASE IF NOT EXISTS hostel_db;
USE hostel_db;

-- Remove the old table if you want a clean setup.
-- Uncomment the next line only if you want to delete existing records:
-- DROP TABLE IF EXISTS HostelStudent;

CREATE TABLE IF NOT EXISTS HostelStudent (
                                             student_id INT PRIMARY KEY,
                                             student_name VARCHAR(100) NOT NULL,
    roll_number VARCHAR(20) NOT NULL,
    hostel_name VARCHAR(50) NOT NULL,
    room_number VARCHAR(20) NOT NULL,
    fee DOUBLE NOT NULL DEFAULT 0
    )
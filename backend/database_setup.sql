-- Run this script in MySQL Workbench or Command Line to fix setup
-- 1. Create Database
CREATE DATABASE IF NOT EXISTS padel_reservation;
USE padel_reservation;

-- 2. Create a dedicated user (bypasses root issues)
CREATE USER IF NOT EXISTS 'padel_user'@'%' IDENTIFIED BY 'padel_password';
-- Also allow for localhost specifically just in case
CREATE USER IF NOT EXISTS 'padel_user'@'localhost' IDENTIFIED BY 'padel_password';

-- 3. Grant Permissions
GRANT ALL PRIVILEGES ON padel_reservation.* TO 'padel_user'@'%';
GRANT ALL PRIVILEGES ON padel_reservation.* TO 'padel_user'@'localhost';
FLUSH PRIVILEGES;

-- 4. (Optional) Manual Data Seeding if Java DDL fails
-- Users (Pass: 123456)
-- INSERT INTO users (email, password, first_name, last_name, role) VALUES 
-- ('admin@test.com', '$2a$10$YourHashedPasswordHere', 'Admin', 'User', 'ADMIN');

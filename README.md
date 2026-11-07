# KIET-Hostel-Management-System
Hostel Management System using Java JDBC and MySQL
# KIET Hostel Management System

## Project Description
A menu-driven Hostel Management System developed using Java, JDBC and MySQL.

This project manages hostel student records and performs various database operations.

## Technologies Used
- Java
- JDBC
- MySQL
- PreparedStatement
- MySQL Connector/J

## Database Details
Database Name: `hostel_db`

Table Name: `HostelStudent`

### Table Attributes
- student_id (INT, Primary Key)
- student_name (VARCHAR)
- roll_number (VARCHAR)
- hostel_name (VARCHAR)
- room_number (VARCHAR)
- fee (DOUBLE)

## Features
1. Insert three hostel student records.
2. Retrieve students based on hostel name and fee greater than ₹50,000.
3. Update hostel fees.
4. Search students and modify room numbers.
5. Calculate total hostel fees using SUM().
6. Conditionally delete student records.

## How to Run
1. Create the MySQL database using `hostel_management.sql`.
2. Add MySQL Connector/J to your Java project.
3. Configure your MySQL username and password in the Java file.
4. Compile and run `HostelManagementSystem.java`.

## Author
KIET Student

## Project Type
Java JDBC Database Management Project

package org.kiet;

import java.sql.*;
import java.util.Scanner;

public class Main {

    static final String URL =
            "jdbc:mysql://localhost:3306/hostel_db";

    static final String USER = "root";
    static final String PASSWORD = "root";

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                    URL, USER, PASSWORD);

            System.out.println("Database Connected Successfully!");

            int choice;

            do {
                System.out.println("\n===== KIET HOSTEL MANAGEMENT =====");
                System.out.println("1. Insert Student Records");
                System.out.println("2. Retrieve Students by Hostel");
                System.out.println("3. Update Hostel Fee");
                System.out.println("4. Search and Update Room Number");
                System.out.println("5. Display Total Fee and Delete Student");
                System.out.println("6. Exit");

                System.out.print("Enter your choice: ");
                choice = sc.nextInt();

                switch (choice) {

                    case 1:
                        insertStudents(con);
                        break;

                    case 2:
                        retrieveStudents(con);
                        break;

                    case 3:
                        updateFee(con);
                        break;

                    case 4:
                        updateRoom(con);
                        break;

                    case 5:
                        deleteStudent(con);
                        break;

                    case 6:
                        System.out.println("Thank You!");
                        break;

                    default:
                        System.out.println("Invalid Choice!");
                }

            } while (choice != 6);

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // PART A: INSERT RECORDS

    static void insertStudents(Connection con) {

        String sql = "INSERT INTO HostelStudent VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            for (int i = 1; i <= 3; i++) {

                System.out.println("\nEnter Student " + i + " Details");

                System.out.print("Student ID: ");
                int id = sc.nextInt();
                sc.nextLine();

                System.out.print("Student Name: ");
                String name = sc.nextLine();

                System.out.print("Roll Number: ");
                String roll = sc.nextLine();

                System.out.print("Hostel Name: ");
                String hostel = sc.nextLine();

                System.out.print("Room Number: ");
                String room = sc.nextLine();

                System.out.print("Fee: ");
                double fee = sc.nextDouble();

                ps.setInt(1, id);
                ps.setString(2, name);
                ps.setString(3, roll);
                ps.setString(4, hostel);
                ps.setString(5, room);
                ps.setDouble(6, fee);

                try {
                    ps.executeUpdate();
                    System.out.println("Record Inserted Successfully!");
                } catch (SQLIntegrityConstraintViolationException e) {
                    System.out.println("Duplicate Student ID!");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // PART B: RETRIEVE AND SORT

    static void retrieveStudents(Connection con) {

        String sql = "SELECT * FROM HostelStudent " +
                "WHERE hostel_name = ? AND fee > 50000 " +
                "ORDER BY fee DESC";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            sc.nextLine();

            System.out.print("Enter Hostel Name: ");
            String hostel = sc.nextLine();

            ps.setString(1, hostel);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                System.out.println("\nID: " + rs.getInt("student_id"));
                System.out.println("Name: " + rs.getString("student_name"));
                System.out.println("Roll: " + rs.getString("roll_number"));
                System.out.println("Hostel: " + rs.getString("hostel_name"));
                System.out.println("Room: " + rs.getString("room_number"));
                System.out.println("Fee: " + rs.getDouble("fee"));
            }

            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // PART C: UPDATE FEE

    static void updateFee(Connection con) {

        String sql = "UPDATE HostelStudent SET fee = ? WHERE student_id = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            System.out.print("Enter Student ID: ");
            int id = sc.nextInt();

            System.out.print("Enter New Fee: ");
            double fee = sc.nextDouble();

            ps.setDouble(1, fee);
            ps.setInt(2, id);

            int rows = ps.executeUpdate();

            if (rows > 0)
                System.out.println("Fee Updated Successfully!");
            else
                System.out.println("Student Not Found!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // PART D: SEARCH AND UPDATE ROOM

    static void updateRoom(Connection con) {

        try {

            System.out.print("Enter Student ID: ");
            int id = sc.nextInt();
            sc.nextLine();

            String search =
                    "SELECT * FROM HostelStudent WHERE student_id = ?";

            try (PreparedStatement ps = con.prepareStatement(search)) {

                ps.setInt(1, id);

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {

                    System.out.println("Student Found!");
                    System.out.println("Name: " +
                            rs.getString("student_name"));

                    System.out.println("Old Room: " +
                            rs.getString("room_number"));

                    System.out.print("Enter New Room Number: ");
                    String room = sc.nextLine();

                    String update =
                            "UPDATE HostelStudent SET room_number = ? " +
                                    "WHERE student_id = ?";

                    try (PreparedStatement ps2 =
                                 con.prepareStatement(update)) {

                        ps2.setString(1, room);
                        ps2.setInt(2, id);

                        ps2.executeUpdate();
                    }

                    System.out.println("Room Updated Successfully!");

                    String display =
                            "SELECT * FROM HostelStudent WHERE student_id = ?";

                    try (PreparedStatement ps3 =
                                 con.prepareStatement(display)) {

                        ps3.setInt(1, id);

                        ResultSet rs2 = ps3.executeQuery();

                        if (rs2.next()) {
                            System.out.println("ID: " +
                                    rs2.getInt("student_id"));
                            System.out.println("Name: " +
                                    rs2.getString("student_name"));
                            System.out.println("Hostel: " +
                                    rs2.getString("hostel_name"));
                            System.out.println("New Room: " +
                                    rs2.getString("room_number"));
                            System.out.println("Fee: " +
                                    rs2.getDouble("fee"));
                        }

                        rs2.close();
                    }

                } else {
                    System.out.println("Student Not Found!");
                }

                rs.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // PART E: SUM AND DELETE

    static void deleteStudent(Connection con) {

        try {

            String sum = "SELECT SUM(fee) FROM HostelStudent";

            try (Statement st = con.createStatement();
                 ResultSet rs = st.executeQuery(sum)) {

                if (rs.next()) {
                    System.out.println("Total Hostel Fee: " +
                            rs.getDouble(1));
                }
            }

            System.out.print("Enter Student ID to Delete: ");
            int id = sc.nextInt();

            String sql =
                    "DELETE FROM HostelStudent " +
                            "WHERE student_id = ? AND fee > 0";

            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, id);

                int rows = ps.executeUpdate();

                if (rows > 0)
                    System.out.println("Student Deleted Successfully!");
                else
                    System.out.println("Student Not Found or Fee Not Paid!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

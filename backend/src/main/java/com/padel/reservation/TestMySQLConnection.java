package com.padel.reservation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestMySQLConnection {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/padel_reservation?allowPublicKeyRetrieval=true&useSSL=false";
        String user = "root";
        String password = "Root";

        System.out.println("Testing MySQL Connection...");
        System.out.println("URL: " + url);
        System.out.println("User: " + user);
        System.out.println("Password: " + password);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("✅ SUCCESS! Connected to MySQL database.");
            System.out.println("Database: " + conn.getCatalog());
            conn.close();
        } catch (ClassNotFoundException e) {
            System.out.println("❌ MySQL Driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("❌ Connection FAILED!");
            System.out.println("Error: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("Error Code: " + e.getErrorCode());
            e.printStackTrace();
        }
    }
}

package com.autocare.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestMySQLConnection {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/CarService?useSSL=false";
        String user = "root";
        String password = "imane";

        try {
            // Optionnel si JDBC 4.0+
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            Connection connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connexion réussie !");
            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Erreur de connexion : " + e.getMessage());
        }
    }
}

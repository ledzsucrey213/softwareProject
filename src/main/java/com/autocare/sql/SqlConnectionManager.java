package com.autocare.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

public class SqlConnectionManager {
    private static final String JDBC_URL    = System.getenv("JDBC_URL");
    private static final String DB_USER     = System.getenv("DB_USER");
    private static final String DB_PASSWORD = System.getenv("DB_PASSWORD");

    private static Optional<Connection> conn = Optional.empty();

    public static void loadConnection() throws SQLException {
        if (conn.isEmpty()) {
            conn = Optional.of(DriverManager.getConnection(JDBC_URL,
                                                           DB_USER,
                                                           DB_PASSWORD
            ));
        }
    }

    public static Connection getConnection() throws SQLException {
        if (conn.isEmpty()) {
            loadConnection();
        }

        return conn.get();
    }

    public static void closeConnection() throws SQLException {
        if (conn.isPresent()) {
            conn.get().close();
        }
    }
}

package com.autocare.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

/**
 * The SqlConnectionManager class is a utility for managing a database connection
 * using JDBC. It provides methods to load, retrieve, and close the connection
 * while ensuring that a single connection instance is used throughout the application.
 */

public class SqlConnectionManager {
    public static final String JDBC_URL    = System.getenv("JDBC_URL");
    public static final String DB_USER     = System.getenv("DB_USER");
    public static final String DB_PASSWORD = System.getenv("DB_PASSWORD");

    private static Optional<Connection> conn = Optional.empty();

    /**
     * Establishes a connection to the database if it has not already been created.
     * This method uses the JDBC URL, username, and password provided as environment variables.
     *
     * @throws SQLException if a database access error occurs or the connection cannot be established.
     */

    public static void loadConnection() throws SQLException {
        if (conn.isEmpty()) {
            conn = Optional.of(DriverManager.getConnection(JDBC_URL,
                                                           DB_USER,
                                                           DB_PASSWORD
            ));
        }
    }

    /**
     * Retrieves the current database connection. If the connection is not yet established,
     * this method will attempt to initialize it using {@link #loadConnection()}.
     *
     * @return the established database connection.
     * @throws SQLException if the connection cannot be established.
     */

    public static Connection getConnection() throws SQLException {
        if (conn.isEmpty()) {
            loadConnection();
        }

        return conn.get();
    }

    /**
     * Closes the current database connection if it exists. After closing, the connection
     * is no longer available until reinitialized.
     *
     * @throws SQLException if a database access error occurs during connection closure.
     */

    public static void closeConnection() throws SQLException {
        if (conn.isPresent()) {
            conn.get().close();
        }
    }
}

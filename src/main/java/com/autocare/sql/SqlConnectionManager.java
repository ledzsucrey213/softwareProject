package com.autocare.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * The SqlConnectionManager class is a utility for managing a database connection
 * using JDBC. It ensures safe creation, retrieval, and closure of database connections.
 */
public class SqlConnectionManager {

    private static final Logger logger = Logger.getLogger(SqlConnectionManager.class.getName());

    public static final String JDBC_URL = System.getenv("JDBC_URL");
    public static final String DB_USER = System.getenv("DB_USER");
    public static final String DB_PASSWORD = System.getenv("DB_PASSWORD");

    private static Optional<Connection> conn = Optional.empty();

    /**
     * Establishes a connection to the database if it has not already been created.
     * Uses synchronized block for thread safety.
     *
     * @throws SQLException if a database access error occurs or the connection cannot be established.
     */
    public static synchronized void loadConnection() throws SQLException {
        if (conn.isEmpty() || conn.get().isClosed()) {
            try {
                conn = Optional.of(DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD));
                logger.info("Connection to the database established successfully.");
            } catch (SQLException e) {
                logger.severe("Error while establishing database connection: " + e.getMessage());
                throw e;
            }
        }
    }

    /**
     * Retrieves the current database connection. If the connection is not yet established
     * or is closed, it will attempt to initialize it.
     *
     * @return the established database connection.
     * @throws SQLException if the connection cannot be established.
     */
    public static synchronized Connection getConnection() throws SQLException {
        if (conn.isEmpty() || conn.get().isClosed()) {
            loadConnection();
        }
        return conn.get();
    }

    /**
     * Closes the current database connection if it exists and is open.
     * Ensures that the connection is properly cleaned up.
     *
     * @throws SQLException if a database access error occurs during connection closure.
     */
    public static synchronized void closeConnection() throws SQLException {
        if (conn.isPresent() && !conn.get().isClosed()) {
            try {
                conn.get().close();
                logger.info("Database connection closed successfully.");
            } catch (SQLException e) {
                logger.severe("Error while closing database connection: " + e.getMessage());
                throw e;
            } finally {
                conn = Optional.empty();
            }
        }
    }
}


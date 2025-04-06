package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgreSQL implements IDatabase {
    private static final String URL = "jdbc:postgresql://localhost:5432/hastane_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "1234";
    
    private Connection connection;

    @Override
    public Connection connect() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Connected to PostgreSQL database.");
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Failed to connect to PostgreSQL database.");
            e.printStackTrace();
        }
        return connection;
    }

    @Override
    public void disconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Disconnected from PostgreSQL database.");
            }
        } catch (SQLException e) {
            System.err.println("Failed to disconnect from PostgreSQL database.");
            e.printStackTrace();
        }
    }


    public Connection getConnection() {
        if (connection == null || isConnectionClosed()) {
            connect();
        }
        return connection;
    }

    private boolean isConnectionClosed() {
        try {
            return connection == null || connection.isClosed();
        } catch (SQLException e) {
            System.err.println("Error checking connection status.");
            e.printStackTrace();
            return true;
        }
    }
}

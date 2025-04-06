package db;
import java.sql.Connection;

public class DatabaseConnection {
    private IDatabase database;

    public DatabaseConnection(IDatabase database) {
        this.database = database;
    }

    private Connection connection;

    public Connection connect() {
        return database.connect();
    }

    public void disconnect() {
        database.disconnect();
    }
}
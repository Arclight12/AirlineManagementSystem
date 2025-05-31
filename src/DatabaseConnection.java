// DatabaseConnection.java
import java.sql.*;

public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/airline_db";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "Nooriluthra11@";
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
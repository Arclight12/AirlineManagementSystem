// TestConnection.java
import java.sql.Connection;
import java.sql.SQLException;

public class TestConnection {
    public static void main(String[] args) {
        System.out.println("Testing database connection...");
        
        try {
            Connection conn = DatabaseConnection.getConnection();
            if (conn != null) {
                System.out.println("✅ Database connection successful!");
                System.out.println("Connected to: " + conn.getMetaData().getDatabaseProductName());
                System.out.println("Database URL: " + conn.getMetaData().getURL());
                System.out.println("Username: " + conn.getMetaData().getUserName());
                conn.close();
                System.out.println("Connection closed successfully.");
                System.out.println("\n🎉 You're ready to run the main application!");
            }
        } catch (SQLException e) {
            System.out.println("❌ Database connection failed!");
            System.out.println("Error: " + e.getMessage());
            
            // Common solutions
            System.out.println("\n🔧 Troubleshooting:");
            System.out.println("1. Make sure PostgreSQL service is running");
            System.out.println("2. Check your username and password in DatabaseConnection.java");
            System.out.println("3. Verify the database 'airline_db' exists");
            System.out.println("4. Ensure JDBC driver is in classpath");
            System.out.println("5. Check if PostgreSQL is running on port 5432");
        }
    }
}

// PassengerDAO.java
// kd

import java.sql.*;
import java.util.*;

public class PassengerDAO {
    
    public int addPassenger(Passenger passenger) {
        String sql = "INSERT INTO passengers (first_name, last_name, email, phone, address) VALUES (?, ?, ?, ?, ?) RETURNING passenger_id";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, passenger.getFirstName());
            pstmt.setString(2, passenger.getLastName());
            pstmt.setString(3, passenger.getEmail());
            pstmt.setString(4, passenger.getPhone());
            pstmt.setString(5, passenger.getAddress());
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return -1;
    }
    
    public List<Passenger> getAllPassengers() {
        List<Passenger> passengers = new ArrayList<>();
        String sql = "SELECT * FROM passengers";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Passenger p = new Passenger();
                p.setId(rs.getInt("passenger_id"));
                p.setFirstName(rs.getString("first_name"));
                p.setLastName(rs.getString("last_name"));
                p.setEmail(rs.getString("email"));
                p.setPhone(rs.getString("phone"));
                p.setAddress(rs.getString("address"));
                passengers.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return passengers;
    }
    
    public boolean deletePassenger(int passengerId) {
        String sql = "DELETE FROM passengers WHERE passenger_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, passengerId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
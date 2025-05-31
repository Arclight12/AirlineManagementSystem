// FlightDAO.java
import java.sql.*;
import java.util.*;

public class FlightDAO {
    
    public boolean addFlight(Flight flight) {
        String sql = "INSERT INTO flights (flight_number, source, destination, departure_time, arrival_time, price, available_seats) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, flight.getFlightNumber());
            pstmt.setString(2, flight.getSource());
            pstmt.setString(3, flight.getDestination());
            pstmt.setString(4, flight.getDepartureTime());
            pstmt.setString(5, flight.getArrivalTime());
            pstmt.setDouble(6, flight.getPrice());
            pstmt.setInt(7, flight.getAvailableSeats());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Flight> getAllFlights() {
        List<Flight> flights = new ArrayList<>();
        String sql = "SELECT * FROM flights";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Flight flight = new Flight(
                    rs.getInt("flight_id"),
                    rs.getString("flight_number"),
                    rs.getString("source"),
                    rs.getString("destination"),
                    rs.getString("departure_time"),
                    rs.getString("arrival_time"),
                    rs.getDouble("price"),
                    rs.getInt("available_seats")
                );
                flights.add(flight);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return flights;
    }
    
    public List<Flight> searchFlights(String query1, String query2) {
        List<Flight> flights = new ArrayList<>();
        String sql = "SELECT * FROM flights WHERE flight_number ILIKE ? OR source ILIKE ? OR destination ILIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + query1 + "%");
            pstmt.setString(2, "%" + query1 + "%");
            pstmt.setString(3, "%" + query2 + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Flight flight = new Flight();
                    flight.setFlightId(rs.getInt("flight_id"));
                    flight.setFlightNumber(rs.getString("flight_number"));
                    flight.setSource(rs.getString("source"));
                    flight.setDestination(rs.getString("destination"));
                    flight.setDepartureTime(rs.getString("departure_time"));
                    flight.setArrivalTime(rs.getString("arrival_time"));
                    flight.setPrice(rs.getDouble("price"));
                    flight.setAvailableSeats(rs.getInt("available_seats"));
                    flights.add(flight);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flights;
    }
    
    public void updateAvailableSeats(int flightId, int seatsBooked) {
        String sql = "UPDATE flights SET available_seats = available_seats - ? WHERE flight_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, seatsBooked);
            pstmt.setInt(2, flightId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public Flight getFlightById(int flightId) {
        String sql = "SELECT * FROM flights WHERE flight_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, flightId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Flight flight = new Flight();
                    flight.setFlightId(rs.getInt("flight_id"));
                    flight.setFlightNumber(rs.getString("flight_number"));
                    flight.setSource(rs.getString("source"));
                    flight.setDestination(rs.getString("destination"));
                    flight.setDepartureTime(rs.getString("departure_time"));
                    flight.setArrivalTime(rs.getString("arrival_time"));
                    flight.setPrice(rs.getDouble("price"));
                    flight.setAvailableSeats(rs.getInt("available_seats"));
                    // Set other fields if needed
                    return flight;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean deleteFlight(int flightId) {
        String sql = "DELETE FROM flights WHERE flight_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, flightId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
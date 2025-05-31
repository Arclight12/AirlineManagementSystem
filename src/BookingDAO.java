// BookingDAO.java
import java.sql.*;
import java.util.*;

public class BookingDAO {

    public boolean addBooking(Booking booking) {
        String sql = "INSERT INTO bookings (passenger_id, flight_id, booking_date, status, total_price, num_people) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, booking.getPassengerId());
            pstmt.setInt(2, booking.getFlightId());
            pstmt.setTimestamp(3, booking.getBookingDate());
            pstmt.setString(4, booking.getStatus() != null ? booking.getStatus() : "CONFIRMED");
            pstmt.setDouble(5, booking.getTotalPrice());
            pstmt.setInt(6, booking.getNumPeople());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Booking> getAllBookings() {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Booking booking = new Booking();
                booking.setBookingId(rs.getInt("booking_id"));
                booking.setPassengerId(rs.getInt("passenger_id"));
                booking.setFlightId(rs.getInt("flight_id"));
                booking.setBookingDate(rs.getTimestamp("booking_date"));
                booking.setStatus(rs.getString("status"));
                booking.setTotalPrice(rs.getDouble("total_price"));
                booking.setNumPeople(rs.getInt("num_people"));
                bookings.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }
}
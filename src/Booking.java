// Booking.java
import java.sql.Timestamp;

public class Booking {
    private int bookingId;
    private int passengerId;
    private int flightId;
    private Timestamp bookingDate;
    private String status;
    private double totalPrice;

    private int numPeople;

    // Constructors
    public Booking() {}

    public Booking(int bookingId, int passengerId, int flightId, 
                   Timestamp bookingDate, String status, double totalPrice, int numPeople) {
        this.bookingId = bookingId;
        this.passengerId = passengerId;
        this.flightId = flightId;
        this.bookingDate = bookingDate;
        this.status = status;
        this.totalPrice = totalPrice;
        this.numPeople = numPeople;
    }
    
    // Getters and Setters
    public int getBookingId() { return bookingId; }
    public void setBookingId(int bookingId) { this.bookingId = bookingId; }
    
    public int getPassengerId() { return passengerId; }
    public void setPassengerId(int passengerId) { this.passengerId = passengerId; }
    
    public int getFlightId() { return flightId; }
    public void setFlightId(int flightId) { this.flightId = flightId; }
    
    public Timestamp getBookingDate() { return bookingDate; }
    public void setBookingDate(Timestamp bookingDate) { this.bookingDate = bookingDate; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
    
    public int getNumPeople() { return numPeople; }
    public void setNumPeople(int numPeople) { this.numPeople = numPeople; }

    public int getId() {
        return bookingId;
    }

    public Timestamp getBookingTime() {
        return bookingDate;
    }
}
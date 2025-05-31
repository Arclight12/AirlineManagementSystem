// Flight.java
public class Flight {
    private int flightId;
    private String flightNumber;
    private String source;
    private String destination;
    private String departureTime;
    private String arrivalTime;
    private double price;
    private int availableSeats;
    private int id; // Make sure this field exists
    
    // Constructors
    public Flight() {}
    
    public Flight(int flightId, String flightNumber, String source, String destination,
                  String departureTime, String arrivalTime, double price, int availableSeats) {
        this.flightId = flightId;
        this.flightNumber = flightNumber;
        this.source = source;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.price = price;
        this.availableSeats = availableSeats;
    }
    
    // Getters and Setters
    public int getFlightId() { return flightId; }
    public void setFlightId(int flightId) { this.flightId = flightId; }
    
    public String getFlightNumber() { return flightNumber; }
    public void setFlightNumber(String flightNumber) { this.flightNumber = flightNumber; }
    
    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
    
    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }
    
    public String getDepartureTime() { return departureTime; }
    public void setDepartureTime(String departureTime) { this.departureTime = departureTime; }
    
    public String getArrivalTime() { return arrivalTime; }
    public void setArrivalTime(String arrivalTime) { this.arrivalTime = arrivalTime; }
    
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    
    public int getAvailableSeats() { return availableSeats; }
    public void setAvailableSeats(int availableSeats) { this.availableSeats = availableSeats; }
    
    public int getId() {
        return flightId;
    }
}
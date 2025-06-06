// Passenger.java
public class Passenger {
    private int passengerId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private int id;
    
    // Constructors
    public Passenger() {}
    
    public Passenger(int passengerId, String firstName, String lastName, 
                    String email, String phone, String address) {
        this.passengerId = passengerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }
    
    // Getters and Setters
    public int getPassengerId() { return passengerId; }
    public void setPassengerId(int passengerId) { this.passengerId = passengerId; }
    
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
}
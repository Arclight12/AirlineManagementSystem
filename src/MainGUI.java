// MainGUI.java
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.List;

public class MainGUI extends JFrame {
    private FlightDAO flightDAO;
    private PassengerDAO passengerDAO;
    private BookingDAO bookingDAO;
    
    private JTabbedPane tabbedPane;
    private JTable flightTable, passengerTable, bookingTable;
    private DefaultTableModel flightModel, passengerModel, bookingModel;
    private JComboBox<String> passengerCombo;
    
    public MainGUI() {
        flightDAO = new FlightDAO();
        passengerDAO = new PassengerDAO();
        bookingDAO = new BookingDAO();
        
        initComponents();
        loadData();
    }
    
    private void initComponents() {
        setTitle("Airline Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        tabbedPane = new JTabbedPane();
        
        // Flight Management Tab
        JPanel flightPanel = createFlightPanel();
        tabbedPane.addTab("Flight Management", flightPanel);
        
        // Passenger Management Tab
        JPanel passengerPanel = createPassengerPanel();
        tabbedPane.addTab("Passenger Management", passengerPanel);
        
        // Booking Management Tab
        JPanel bookingPanel = createBookingPanel();
        tabbedPane.addTab("Booking Management", bookingPanel);
        
        // Flight Search Tab
        JPanel searchPanel = createSearchPanel();
        tabbedPane.addTab("Search Flights", searchPanel);
        
        add(tabbedPane, BorderLayout.CENTER);
        
        setSize(1200, 800);
        setLocationRelativeTo(null);
    }
    
    private JPanel createFlightPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Flight form
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Add New Flight"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        JTextField flightNumberField = new JTextField(15);
        JTextField sourceField = new JTextField(15);
        JTextField destinationField = new JTextField(15);
        JTextField departureField = new JTextField(15);
        JTextField arrivalField = new JTextField(15);
        JTextField priceField = new JTextField(15);
        JTextField seatsField = new JTextField(15);
        
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Flight Number:"), gbc);
        gbc.gridx = 1;
        formPanel.add(flightNumberField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Source:"), gbc);
        gbc.gridx = 1;
        formPanel.add(sourceField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Destination:"), gbc);
        gbc.gridx = 1;
        formPanel.add(destinationField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Departure Time:"), gbc);
        gbc.gridx = 1;
        formPanel.add(departureField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Arrival Time:"), gbc);
        gbc.gridx = 1;
        formPanel.add(arrivalField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(new JLabel("Price:"), gbc);
        gbc.gridx = 1;
        formPanel.add(priceField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 6;
        formPanel.add(new JLabel("Available Seats:"), gbc);
        gbc.gridx = 1;
        formPanel.add(seatsField, gbc);
        
        JButton addFlightBtn = new JButton("Add Flight");
        addFlightBtn.setBackground(new Color(0, 123, 255));
        addFlightBtn.setForeground(Color.WHITE);
        gbc.gridx = 1; gbc.gridy = 7;
        formPanel.add(addFlightBtn, gbc);
        
        addFlightBtn.addActionListener(e -> {
            try {
                Flight flight = new Flight();
                flight.setFlightNumber(flightNumberField.getText());
                flight.setSource(sourceField.getText());
                flight.setDestination(destinationField.getText());
                flight.setDepartureTime(departureField.getText());
                flight.setArrivalTime(arrivalField.getText());
                flight.setPrice(Double.parseDouble(priceField.getText()));
                flight.setAvailableSeats(Integer.parseInt(seatsField.getText()));
                
                if (flightDAO.addFlight(flight)) {
                    JOptionPane.showMessageDialog(this, "Flight added successfully!");
                    clearFlightForm(flightNumberField, sourceField, destinationField, 
                                  departureField, arrivalField, priceField, seatsField);
                    loadFlightData();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add flight!");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numeric values!");
            }
        });
        
        // Flight table
        String[] flightColumns = {"ID", "Flight Number", "Source", "Destination", 
                                "Departure", "Arrival", "Price", "Available Seats"};
        flightModel = new DefaultTableModel(flightColumns, 0);
        flightTable = new JTable(flightModel);
        flightTable.setSelectionBackground(new Color(184, 207, 229));
        JScrollPane flightScrollPane = new JScrollPane(flightTable);
        flightScrollPane.setBorder(BorderFactory.createTitledBorder("All Flights"));
        
        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(flightScrollPane, BorderLayout.CENTER);
        
        JButton deleteFlightBtn = new JButton("Delete Flight");
        deleteFlightBtn.setBackground(new Color(220, 53, 69));
        deleteFlightBtn.setForeground(Color.WHITE);
        gbc.gridx = 1; gbc.gridy = 8;
        formPanel.add(deleteFlightBtn, gbc);

        deleteFlightBtn.addActionListener(e -> {
            int selectedRow = flightTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a flight to delete.");
                return;
            }
            int flightId = (int) flightModel.getValueAt(selectedRow, 0);
            if (flightDAO.deleteFlight(flightId)) {
                JOptionPane.showMessageDialog(this, "Flight deleted successfully!");
                loadFlightData();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete flight.");
            }
        });
        
        return panel;
    }
    
    private JPanel createPassengerPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Passenger form
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Add New Passenger"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        JTextField firstNameField = new JTextField(15);
        JTextField lastNameField = new JTextField(15);
        JTextField emailField = new JTextField(15);
        JTextField phoneField = new JTextField(15);
        JTextArea addressArea = new JTextArea(3, 15);
        
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("First Name:"), gbc);
        gbc.gridx = 1;
        formPanel.add(firstNameField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Last Name:"), gbc);
        gbc.gridx = 1;
        formPanel.add(lastNameField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        formPanel.add(emailField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Phone:"), gbc);
        gbc.gridx = 1;
        formPanel.add(phoneField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Address:"), gbc);
        gbc.gridx = 1;
        formPanel.add(new JScrollPane(addressArea), gbc);
        
        JButton addPassengerBtn = new JButton("Add Passenger");
        addPassengerBtn.setBackground(new Color(40, 167, 69));
        addPassengerBtn.setForeground(Color.WHITE);
        gbc.gridx = 1; gbc.gridy = 5;
        formPanel.add(addPassengerBtn, gbc);
        
        addPassengerBtn.addActionListener(e -> {
            Passenger passenger = new Passenger();
            passenger.setFirstName(firstNameField.getText());
            passenger.setLastName(lastNameField.getText());
            passenger.setEmail(emailField.getText());
            passenger.setPhone(phoneField.getText());
            passenger.setAddress(addressArea.getText());
            
            int passengerId = passengerDAO.addPassenger(passenger);
            if (passengerId > 0) {
                JOptionPane.showMessageDialog(this, "Passenger added successfully! ID: " + passengerId);
                clearPassengerForm(firstNameField, lastNameField, emailField, phoneField, addressArea);
                loadPassengerData();
                reloadPassengerCombo(passengerCombo); // Reload passenger combo in booking panel
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add passenger!");
            }
        });
        
        // Passenger table
        String[] passengerColumns = {"ID", "First Name", "Last Name", "Email", "Phone", "Address"};
        passengerModel = new DefaultTableModel(passengerColumns, 0);
        passengerTable = new JTable(passengerModel);
        passengerTable.setSelectionBackground(new Color(184, 207, 229));
        JScrollPane passengerScrollPane = new JScrollPane(passengerTable);
        passengerScrollPane.setBorder(BorderFactory.createTitledBorder("All Passengers"));
        
        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(passengerScrollPane, BorderLayout.CENTER);
        
        JButton deletePassengerBtn = new JButton("Delete Passenger");
        deletePassengerBtn.setBackground(new Color(220, 53, 69));
        deletePassengerBtn.setForeground(Color.WHITE);
        gbc.gridx = 1; gbc.gridy = 6;
        formPanel.add(deletePassengerBtn, gbc);

        deletePassengerBtn.addActionListener(e -> {
            int selectedRow = passengerTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a passenger to delete.");
                return;
            }
            int passengerId = (int) passengerModel.getValueAt(selectedRow, 0);
            if (passengerDAO.deletePassenger(passengerId)) {
                JOptionPane.showMessageDialog(this, "Passenger deleted successfully!");
                loadPassengerData();
                reloadPassengerCombo(passengerCombo);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete passenger.");
            }
        });
        
        return panel;
    }
    
    private JPanel createBookingPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Booking form
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Create New Booking"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // ComboBoxes for Passenger and Flight selection
        JComboBox<String> flightCombo = new JComboBox<>();
        passengerCombo = new JComboBox<>();
        // Populate passengerCombo
        List<Passenger> passengers = passengerDAO.getAllPassengers();
        for (Passenger p : passengers) {
            passengerCombo.addItem(p.getId() + " - " + p.getFirstName() + " " + p.getLastName());
        }

        // Populate flightCombo
        List<Flight> flights = flightDAO.getAllFlights();
        for (Flight f : flights) {
            flightCombo.addItem(f.getId() + " - " + f.getFlightNumber() + " (" + f.getSource() + " → " + f.getDestination() + ")");
        }

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Passenger:"), gbc);
        gbc.gridx = 1;
        formPanel.add(passengerCombo, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Flight:"), gbc);
        gbc.gridx = 1;
        formPanel.add(flightCombo, gbc);

        JSpinner numPeopleSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1)); // 1 to 10 people

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Number of People:"), gbc);
        gbc.gridx = 1;
        formPanel.add(numPeopleSpinner, gbc);

        JButton addBookingBtn = new JButton("Add Booking");
        addBookingBtn.setBackground(new Color(255, 193, 7));
        addBookingBtn.setForeground(Color.BLACK);
        gbc.gridx = 1; gbc.gridy = 3;
        formPanel.add(addBookingBtn, gbc);

        addBookingBtn.addActionListener(e -> {
            if (passengerCombo.getSelectedIndex() == -1 || flightCombo.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(this, "Please select both a passenger and a flight.");
                return;
            }
            try {
                String passengerStr = (String) passengerCombo.getSelectedItem();
                String flightStr = (String) flightCombo.getSelectedItem();
                int passengerId = Integer.parseInt(passengerStr.split(" - ")[0]);
                int flightId = Integer.parseInt(flightStr.split(" - ")[0]);
                int numPeople = (Integer) numPeopleSpinner.getValue();

                Flight selectedFlight = flightDAO.getFlightById(flightId);
                if (selectedFlight == null) {
                    JOptionPane.showMessageDialog(this, "Flight not found. Please refresh the flight list.");
                    return;
                }
                if (selectedFlight.getAvailableSeats() < numPeople) {
                    JOptionPane.showMessageDialog(this, "Not enough available seats for this flight.");
                    return;
                }

                Booking booking = new Booking();
                booking.setPassengerId(passengerId);
                booking.setFlightId(flightId);
                booking.setBookingDate(new Timestamp(System.currentTimeMillis()));
                booking.setStatus("CONFIRMED");
                booking.setTotalPrice(selectedFlight.getPrice() * numPeople);
                booking.setNumPeople(numPeople);

                if (bookingDAO.addBooking(booking)) {
                    flightDAO.updateAvailableSeats(flightId, numPeople);
                    JOptionPane.showMessageDialog(this, "Booking added successfully!");
                    loadBookingData();
                    loadFlightData();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add booking!");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        // Booking table
        String[] bookingColumns = {"ID", "Passenger", "Flight", "Booking Time"};
        bookingModel = new DefaultTableModel(bookingColumns, 0);
        bookingTable = new JTable(bookingModel);
        bookingTable.setSelectionBackground(new Color(184, 207, 229));
        JScrollPane bookingScrollPane = new JScrollPane(bookingTable);
        bookingScrollPane.setBorder(BorderFactory.createTitledBorder("All Bookings"));

        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(bookingScrollPane, BorderLayout.CENTER);

        return panel;
    }
    
    private JPanel createSearchPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Search form
        JPanel formPanel = new JPanel(new FlowLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Search Flights"));
        JTextField searchField = new JTextField(20);
        JButton searchBtn = new JButton("Search");
        formPanel.add(new JLabel("Flight Number or Route:"));
        formPanel.add(searchField);
        formPanel.add(searchBtn);
        
        // Search results table
        String[] columns = {"ID", "Flight Number", "Source", "Destination", "Departure", "Arrival", "Price", "Available Seats"};
        DefaultTableModel searchModel = new DefaultTableModel(columns, 0);
        JTable searchTable = new JTable(searchModel);
        searchTable.setSelectionBackground(new Color(184, 207, 229));
        JScrollPane searchScrollPane = new JScrollPane(searchTable);
        searchScrollPane.setBorder(BorderFactory.createTitledBorder("Search Results"));
        
        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(searchScrollPane, BorderLayout.CENTER);
        
        // Search button action
        searchBtn.addActionListener(e -> {
            String query = searchField.getText().trim();
            searchModel.setRowCount(0); // Clear existing rows
            if (query.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a flight number or route to search.");
                return;
            }
            try {
                List<Flight> results = flightDAO.searchFlights(query, query);
                for (Flight f : results) {
                    Object[] row = {f.getId(), f.getFlightNumber(), f.getSource(), f.getDestination(), 
                                    f.getDepartureTime(), f.getArrivalTime(), f.getPrice(), f.getAvailableSeats()};
                    searchModel.addRow(row);
                }
                if (results.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "No flights found matching your query.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });
        
        return panel;
    }
    
    private void loadData() {
        loadFlightData();
        loadPassengerData();
        loadBookingData();
    }
    
    private void loadFlightData() {
        flightModel.setRowCount(0);
        List<Flight> flights = flightDAO.getAllFlights();
        for (Flight f : flights) {
            Object[] row = {f.getId(), f.getFlightNumber(), f.getSource(), f.getDestination(), 
                            f.getDepartureTime(), f.getArrivalTime(), f.getPrice(), f.getAvailableSeats()};
            flightModel.addRow(row);
        }
    }
    
    private void loadPassengerData() {
        passengerModel.setRowCount(0);
        List<Passenger> passengers = passengerDAO.getAllPassengers();
        for (Passenger p : passengers) {
            Object[] row = {p.getId(), p.getFirstName(), p.getLastName(), p.getEmail(), p.getPhone(), p.getAddress()};
            passengerModel.addRow(row);
        }
    }
    
    private void loadBookingData() {
        bookingModel.setRowCount(0);
        List<Booking> bookings = bookingDAO.getAllBookings();
        for (Booking b : bookings) {
            Object[] row = {b.getId(), b.getPassengerId(), b.getFlightId(), b.getBookingTime()};
            bookingModel.addRow(row);
        }
    }
    
    private void clearFlightForm(JTextField... fields) {
        for (JTextField field : fields) {
            field.setText("");
        }
    }
    
    private void clearPassengerForm(JTextField firstNameField, JTextField lastNameField, JTextField emailField, 
                                    JTextField phoneField, JTextArea addressArea) {
        firstNameField.setText("");
        lastNameField.setText("");
        emailField.setText("");
        phoneField.setText("");
        addressArea.setText("");
    }
    
    private void reloadPassengerCombo(JComboBox<String> passengerCombo) {
        passengerCombo.removeAllItems();
        List<Passenger> passengers = passengerDAO.getAllPassengers();
        for (Passenger p : passengers) {
            passengerCombo.addItem(p.getId() + " - " + p.getFirstName() + " " + p.getLastName());
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainGUI gui = new MainGUI();
            gui.setVisible(true);
        });
    }
}

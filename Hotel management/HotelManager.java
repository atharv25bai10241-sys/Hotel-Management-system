import java.io.*;
import java.util.*;

/**
 * HotelManager Class - Main business logic
 * Topics: Collections (ArrayList, HashMap), Exception Handling, File I/O
 */
public class HotelManager {
    private ArrayList<Room> rooms;
    private ArrayList<Customer> customers;
    private ArrayList<Booking> bookings;
    
    private static final String ROOMS_FILE = "rooms.dat";
    private static final String CUSTOMERS_FILE = "customers.dat";
    private static final String BOOKINGS_FILE = "bookings.dat";
    
    // Hotel information
    private String hotelName;
    private String hotelLocation;
    private double taxRate;  // GST/Tax percentage
    
    // Constructor
    public HotelManager(String hotelName, String hotelLocation, double taxRate) {
        this.hotelName = hotelName;
        this.hotelLocation = hotelLocation;
        this.taxRate = taxRate;
        this.rooms = new ArrayList<>();
        this.customers = new ArrayList<>();
        this.bookings = new ArrayList<>();
        loadData();
    }
    
    // ============= ROOM OPERATIONS =============
    
    /**
     * Add a new room to hotel
     */
    public void addRoom(Room room) throws HotelException {
        for (Room r : rooms) {
            if (r.getRoomNumber().equals(room.getRoomNumber())) {
                throw new HotelException("Room " + room.getRoomNumber() + " already exists!");
            }
        }
        rooms.add(room);
        saveRooms();
        System.out.println("✓ Room added successfully!");
    }
    
    /**
     * Search room by number
     */
    public Room searchRoomByNumber(String roomNumber) {
        for (Room room : rooms) {
            if (room.getRoomNumber().equals(roomNumber)) {
                return room;
            }
        }
        return null;
    }
    
    /**
     * Get rooms by type
     */
    public ArrayList<Room> getRoomsByType(String roomType) {
        ArrayList<Room> results = new ArrayList<>();
        for (Room room : rooms) {
            if (room.getRoomType().equalsIgnoreCase(roomType)) {
                results.add(room);
            }
        }
        return results;
    }
    
    /**
     * Get available rooms of type
     */
    public ArrayList<Room> getAvailableRoomsByType(String roomType) {
        ArrayList<Room> results = new ArrayList<>();
        for (Room room : rooms) {
            if (room.getRoomType().equalsIgnoreCase(roomType) && room.isAvailable()) {
                results.add(room);
            }
        }
        return results;
    }
    
    /**
     * View all rooms
     */
    public void viewAllRooms() {
        if (rooms.isEmpty()) {
            System.out.println("\nNo rooms in hotel!");
            return;
        }
        System.out.println("\n" + "=".repeat(80));
        System.out.println("ALL ROOMS");
        System.out.println("=".repeat(80));
        for (Room room : rooms) {
            System.out.println(room);
            System.out.println("-".repeat(80));
        }
    }
    
    // ============= CUSTOMER OPERATIONS =============
    
    /**
     * Register a new customer
     */
    public void registerCustomer(Customer customer) throws HotelException {
        for (Customer c : customers) {
            if (c.getCustomerID().equals(customer.getCustomerID())) {
                throw new HotelException("Customer ID " + customer.getCustomerID() + " already exists!");
            }
        }
        customers.add(customer);
        saveCustomers();
        System.out.println("✓ Customer registered successfully!");
    }
    
    /**
     * Search customer by ID
     */
    public Customer searchCustomerByID(String customerID) {
        for (Customer customer : customers) {
            if (customer.getCustomerID().equals(customerID)) {
                return customer;
            }
        }
        return null;
    }
    
    /**
     * View all customers
     */
    public void viewAllCustomers() {
        if (customers.isEmpty()) {
            System.out.println("\nNo customers registered!");
            return;
        }
        System.out.println("\n" + "=".repeat(80));
        System.out.println("ALL CUSTOMERS");
        System.out.println("=".repeat(80));
        for (Customer customer : customers) {
            System.out.println(customer);
            System.out.println("-".repeat(80));
        }
    }
    
    // ============= BOOKING OPERATIONS =============
    
    /**
     * Create a new booking
     * Topics: Exception Handling, Validation
     */
    public void createBooking(String bookingID, String customerID, String roomNumber,
                              Date checkInDate, Date checkOutDate) throws HotelException {
        // Validate customer
        Customer customer = searchCustomerByID(customerID);
        if (customer == null) {
            throw new HotelException("Customer not found!");
        }
        
        // Validate room
        Room room = searchRoomByNumber(roomNumber);
        if (room == null) {
            throw new HotelException("Room not found!");
        }
        
        if (!room.isAvailable()) {
            throw new HotelException("Room is not available!");
        }
        
        // Check if booking ID already exists
        for (Booking b : bookings) {
            if (b.getBookingID().equals(bookingID)) {
                throw new HotelException("Booking ID already exists!");
            }
        }
        
        // Create booking
        Booking booking = new Booking(bookingID, customerID, roomNumber, 
                                      checkInDate, checkOutDate, room.getPrice());
        bookings.add(booking);
        room.bookRoom();
        customer.addBooking(bookingID);
        
        saveBookings();
        saveRooms();
        saveCustomers();
        
        System.out.println("✓ Booking created successfully!");
        System.out.println("Booking ID: " + bookingID);
        System.out.println("Total Cost: Rs. " + booking.getTotalCost());
    }
    
    /**
     * Search booking by ID
     */
    public Booking searchBookingByID(String bookingID) {
        for (Booking booking : bookings) {
            if (booking.getBookingID().equals(bookingID)) {
                return booking;
            }
        }
        return null;
    }
    
    /**
     * Get bookings for a customer
     */
    public ArrayList<Booking> getCustomerBookings(String customerID) throws HotelException {
        Customer customer = searchCustomerByID(customerID);
        if (customer == null) {
            throw new HotelException("Customer not found!");
        }
        
        ArrayList<Booking> results = new ArrayList<>();
        for (String bookingID : customer.getBookingHistory()) {
            Booking booking = searchBookingByID(bookingID);
            if (booking != null) {
                results.add(booking);
            }
        }
        return results;
    }
    
    /**
     * Cancel a booking
     * Topics: Exception Handling, Refund calculation
     */
    public void cancelBooking(String bookingID) throws HotelException {
        Booking booking = searchBookingByID(bookingID);
        if (booking == null) {
            throw new HotelException("Booking not found!");
        }
        
        // Cancel booking and calculate refund
        booking.cancelBooking();
        double refund = booking.getRefundAmount();
        
        // Free the room
        Room room = searchRoomByNumber(booking.getRoomNumber());
        if (room != null) {
            room.freeRoom();
        }
        
        // Update customer
        Customer customer = searchCustomerByID(booking.getCustomerID());
        if (customer != null) {
            customer.deductFromTotal(booking.getTotalCost());
            customer.addToTotalSpent(refund);
        }
        
        saveBookings();
        saveRooms();
        saveCustomers();
        
        System.out.println("✓ Booking cancelled!");
        System.out.println("Refund Amount: Rs. " + refund);
    }
    
    /**
     * Check in for a booking
     */
    public void checkInBooking(String bookingID) throws HotelException {
        Booking booking = searchBookingByID(bookingID);
        if (booking == null) {
            throw new HotelException("Booking not found!");
        }
        
        if (!booking.getStatus().equals("Confirmed")) {
            throw new HotelException("Booking is not confirmed!");
        }
        
        booking.checkIn();
        saveBookings();
        System.out.println("✓ Checked in successfully!");
    }
    
    /**
     * Check out for a booking
     */
    public void checkOutBooking(String bookingID) throws HotelException {
        Booking booking = searchBookingByID(bookingID);
        if (booking == null) {
            throw new HotelException("Booking not found!");
        }
        
        if (!booking.getStatus().equals("Checked-In")) {
            throw new HotelException("Booking must be checked-in first!");
        }
        
        booking.checkOut();
        Room room = searchRoomByNumber(booking.getRoomNumber());
        if (room != null) {
            room.freeRoom();
        }
        
        saveBookings();
        saveRooms();
        System.out.println("✓ Checked out successfully!");
    }
    
    /**
     * Generate bill for a booking
     */
    public void generateBill(String bookingID) throws HotelException {
        Booking booking = searchBookingByID(bookingID);
        if (booking == null) {
            throw new HotelException("Booking not found!");
        }
        
        System.out.println("\n" + "=".repeat(80));
        System.out.println("HOTEL BILL");
        System.out.println(hotelName + " - " + hotelLocation);
        System.out.println("=".repeat(80));
        System.out.println(booking);
        
        double subtotal = booking.getTotalCost();
        double tax = (subtotal * taxRate) / 100.0;
        double grandTotal = subtotal + tax;
        
        System.out.println("\n" + "-".repeat(80));
        System.out.printf("Subtotal: Rs. %.2f\n", subtotal);
        System.out.printf("Tax (%d%%): Rs. %.2f\n", (int)taxRate, tax);
        System.out.printf("Grand Total: Rs. %.2f\n", grandTotal);
        System.out.println("-".repeat(80));
    }
    
    /**
     * Apply discount to booking
     */
    public void applyDiscountToBooking(String bookingID, double discountPercent) throws HotelException {
        Booking booking = searchBookingByID(bookingID);
        if (booking == null) {
            throw new HotelException("Booking not found!");
        }
        
        booking.applyDiscount(discountPercent);
        saveBookings();
        System.out.println("✓ Discount applied successfully!");
        System.out.println("New Total: Rs. " + booking.getTotalCost());
    }
    
    /**
     * View all bookings
     */
    public void viewAllBookings() {
        if (bookings.isEmpty()) {
            System.out.println("\nNo bookings found!");
            return;
        }
        System.out.println("\n" + "=".repeat(80));
        System.out.println("ALL BOOKINGS");
        System.out.println("=".repeat(80));
        for (Booking booking : bookings) {
            System.out.println(booking);
            System.out.println("-".repeat(80));
        }
    }
    
    /**
     * Get upcoming bookings
     */
    public ArrayList<Booking> getUpcomingBookings() {
        ArrayList<Booking> upcoming = new ArrayList<>();
        for (Booking booking : bookings) {
            if (booking.isUpcoming()) {
                upcoming.add(booking);
            }
        }
        return upcoming;
    }
    
    // ============= FILE I/O OPERATIONS (Serialization) =============
    
    /**
     * Save rooms to file
     */
    private void saveRooms() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ROOMS_FILE))) {
            oos.writeObject(rooms);
        } catch (IOException e) {
            System.err.println("Error saving rooms: " + e.getMessage());
        }
    }
    
    /**
     * Save customers to file
     */
    private void saveCustomers() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CUSTOMERS_FILE))) {
            oos.writeObject(customers);
        } catch (IOException e) {
            System.err.println("Error saving customers: " + e.getMessage());
        }
    }
    
    /**
     * Save bookings to file
     */
    private void saveBookings() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(BOOKINGS_FILE))) {
            oos.writeObject(bookings);
        } catch (IOException e) {
            System.err.println("Error saving bookings: " + e.getMessage());
        }
    }
    
    /**
     * Load all data from files
     */
    @SuppressWarnings("unchecked")
    private void loadData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ROOMS_FILE))) {
            rooms = (ArrayList<Room>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            rooms = new ArrayList<>();
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(CUSTOMERS_FILE))) {
            customers = (ArrayList<Customer>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            customers = new ArrayList<>();
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(BOOKINGS_FILE))) {
            bookings = (ArrayList<Booking>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            bookings = new ArrayList<>();
        }
    }
    
    /**
     * Display hotel statistics
     */
    public void displayStatistics() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("HOTEL STATISTICS - " + hotelName);
        System.out.println("=".repeat(80));
        System.out.println("Total Rooms: " + rooms.size());
        System.out.println("Available Rooms: " + getAvailableRoomsByType("Single").size() + 
                          getAvailableRoomsByType("Double").size() + 
                          getAvailableRoomsByType("Suite").size() + 
                          getAvailableRoomsByType("Deluxe").size());
        System.out.println("Total Customers: " + customers.size());
        System.out.println("Total Bookings: " + bookings.size());
        System.out.println("Upcoming Bookings: " + getUpcomingBookings().size());
        
        double totalRevenue = 0;
        for (Customer customer : customers) {
            totalRevenue += customer.getTotalAmountSpent();
        }
        System.out.println("Total Revenue: Rs. " + totalRevenue);
        System.out.println("=".repeat(80));
    }
    
    // Getters for hotel info
    public String getHotelName() { return hotelName; }
    public String getHotelLocation() { return hotelLocation; }
    public double getTaxRate() { return taxRate; }
}

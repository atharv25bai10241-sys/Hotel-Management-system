import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Customer Class - Represents a hotel customer
 * Topics: Collections (ArrayList), Encapsulation
 */
public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String customerID;
    private String name;
    private String email;
    private String phone;
    private String address;
    private Date registrationDate;
    private ArrayList<String> bookingHistory;   // List of booking IDs
    private double totalAmountSpent;
    
    // Constructor
    public Customer(String customerID, String name, String email, 
                    String phone, String address) {
        this.customerID = customerID;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.registrationDate = new Date();
        this.bookingHistory = new ArrayList<>();
        this.totalAmountSpent = 0.0;
    }
    
    // Getters and Setters
    public String getCustomerID() { return customerID; }
    public void setCustomerID(String customerID) { this.customerID = customerID; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    
    public Date getRegistrationDate() { return registrationDate; }
    
    public ArrayList<String> getBookingHistory() { return bookingHistory; }
    
    public double getTotalAmountSpent() { return totalAmountSpent; }
    public void addToTotalSpent(double amount) { this.totalAmountSpent += amount; }
    public void deductFromTotal(double amount) { this.totalAmountSpent -= amount; }
    
    // Add booking to history
    public void addBooking(String bookingID) {
        bookingHistory.add(bookingID);
    }
    
    // Remove booking from history
    public boolean removeBooking(String bookingID) {
        return bookingHistory.remove(bookingID);
    }
    
    // Get booking count
    public int getBookingCount() {
        return bookingHistory.size();
    }
    
    // Check if customer has booking
    public boolean hasBooking(String bookingID) {
        return bookingHistory.contains(bookingID);
    }
    
    @Override
    public String toString() {
        return String.format(
            "\nCustomer ID: %s\n" +
            "Name: %s\n" +
            "Email: %s\n" +
            "Phone: %s\n" +
            "Address: %s\n" +
            "Registration Date: %s\n" +
            "Total Bookings: %d\n" +
            "Total Amount Spent: Rs. %.2f",
            customerID, name, email, phone, address, 
            registrationDate, bookingHistory.size(), totalAmountSpent
        );
    }
}

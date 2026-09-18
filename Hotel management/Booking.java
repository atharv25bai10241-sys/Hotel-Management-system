import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Booking Class - Represents a hotel booking
 * Topics: Date handling, Exception handling, toString()
 */
public class Booking implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String bookingID;
    private String customerID;
    private String roomNumber;
    private Date checkInDate;
    private Date checkOutDate;
    private int numberOfNights;
    private double roomPrice;
    private double totalCost;
    private String status;          // "Confirmed", "Checked-In", "Cancelled", "Completed"
    private Date bookingDate;
    private double refundAmount;
    private double discountApplied;
    
    // Cancellation policy: Full refund if cancelled 48 hours before check-in
    private static final long CANCELLATION_WINDOW = 48 * 60 * 60 * 1000L;  // 48 hours in milliseconds
    
    // Constructor
    public Booking(String bookingID, String customerID, String roomNumber,
                   Date checkInDate, Date checkOutDate, double roomPrice) throws HotelException {
        
        // Validate dates
        Date today = new Date();
        if (checkInDate.before(today)) {
            throw new HotelException("Check-in date cannot be in the past!");
        }
        
        if (checkOutDate.before(checkInDate)) {
            throw new HotelException("Check-out date must be after check-in date!");
        }
        
        this.bookingID = bookingID;
        this.customerID = customerID;
        this.roomNumber = roomNumber;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.roomPrice = roomPrice;
        this.status = "Confirmed";
        this.bookingDate = new Date();
        this.refundAmount = 0.0;
        this.discountApplied = 0.0;
        
        // Calculate number of nights
        this.numberOfNights = (int) ((checkOutDate.getTime() - checkInDate.getTime()) 
                              / (24 * 60 * 60 * 1000L));
        
        // Calculate total cost
        this.totalCost = numberOfNights * roomPrice;
    }
    
    // Getters and Setters
    public String getBookingID() { return bookingID; }
    public void setBookingID(String bookingID) { this.bookingID = bookingID; }
    
    public String getCustomerID() { return customerID; }
    public void setCustomerID(String customerID) { this.customerID = customerID; }
    
    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }
    
    public Date getCheckInDate() { return checkInDate; }
    public void setCheckInDate(Date checkInDate) { this.checkInDate = checkInDate; }
    
    public Date getCheckOutDate() { return checkOutDate; }
    public void setCheckOutDate(Date checkOutDate) { this.checkOutDate = checkOutDate; }
    
    public int getNumberOfNights() { return numberOfNights; }
    public void setNumberOfNights(int numberOfNights) { this.numberOfNights = numberOfNights; }
    
    public double getRoomPrice() { return roomPrice; }
    public void setRoomPrice(double roomPrice) { this.roomPrice = roomPrice; }
    
    public double getTotalCost() { return totalCost; }
    public void setTotalCost(double totalCost) { this.totalCost = totalCost; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public Date getBookingDate() { return bookingDate; }
    
    public double getRefundAmount() { return refundAmount; }
    public void setRefundAmount(double refundAmount) { this.refundAmount = refundAmount; }
    
    public double getDiscountApplied() { return discountApplied; }
    public void setDiscountApplied(double discountApplied) { 
        this.discountApplied = discountApplied;
        this.totalCost = (numberOfNights * roomPrice) - discountApplied;
    }
    
    // Check if booking is confirmed
    public boolean isConfirmed() {
        return status.equals("Confirmed");
    }
    
    // Mark as checked in
    public void checkIn() {
        this.status = "Checked-In";
    }
    
    // Mark as checked out
    public void checkOut() {
        this.status = "Completed";
    }
    
    // Calculate cancellation refund
    public double calculateRefund() throws HotelException {
        if (!status.equals("Confirmed")) {
            throw new HotelException("Can only cancel confirmed bookings!");
        }
        
        Date today = new Date();
        long timeDifference = checkInDate.getTime() - today.getTime();
        
        // Full refund if cancelled more than 48 hours before check-in
        if (timeDifference > CANCELLATION_WINDOW) {
            return totalCost;
        }
        // 50% refund if cancelled within 48 hours
        else if (timeDifference > 0) {
            return totalCost * 0.5;
        }
        // No refund if check-in date has passed
        else {
            return 0.0;
        }
    }
    
    // Cancel booking
    public void cancelBooking() throws HotelException {
        if (!status.equals("Confirmed")) {
            throw new HotelException("Can only cancel confirmed bookings!");
        }
        
        this.refundAmount = calculateRefund();
        this.status = "Cancelled";
    }
    
    // Check if booking is upcoming (within next 7 days)
    public boolean isUpcoming() {
        Date today = new Date();
        long daysUntilCheckIn = (checkInDate.getTime() - today.getTime()) / (24 * 60 * 60 * 1000L);
        return daysUntilCheckIn >= 0 && daysUntilCheckIn <= 7;
    }
    
    // Apply discount
    public void applyDiscount(double discountPercent) throws HotelException {
        if (discountPercent < 0 || discountPercent > 100) {
            throw new HotelException("Discount must be between 0 and 100%!");
        }
        
        double discountAmount = (numberOfNights * roomPrice) * (discountPercent / 100.0);
        setDiscountApplied(discountAmount);
    }
    
    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return String.format(
            "\nBooking ID: %s\n" +
            "Customer ID: %s\n" +
            "Room Number: %s\n" +
            "Check-In: %s\n" +
            "Check-Out: %s\n" +
            "Nights: %d\n" +
            "Price/Night: Rs. %.2f\n" +
            "Discount Applied: Rs. %.2f\n" +
            "Total Cost: Rs. %.2f\n" +
            "Status: %s\n" +
            "Refund Amount: Rs. %.2f",
            bookingID, customerID, roomNumber, sdf.format(checkInDate),
            sdf.format(checkOutDate), numberOfNights, roomPrice,
            discountApplied, totalCost, status, refundAmount
        );
    }
}

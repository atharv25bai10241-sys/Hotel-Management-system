import java.io.Serializable;

/**
 * Room Class - Represents a hotel room
 * Topics: Encapsulation, Access Modifiers, Constructor
 */
public class Room implements Serializable {
    private static final long serialVersionUID = 1L;
    
    // Room types
    public static final String SINGLE = "Single";
    public static final String DOUBLE = "Double";
    public static final String SUITE = "Suite";
    public static final String DELUXE = "Deluxe";
    
    private String roomNumber;      // Unique room ID
    private String roomType;        // Type of room
    private double price;           // Price per night
    private String status;          // Available or Occupied
    private String amenities;       // Room features
    private int capacity;           // Max persons
    
    // Constructor
    public Room(String roomNumber, String roomType, double price, 
                String amenities, int capacity) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.price = price;
        this.status = "Available";
        this.amenities = amenities;
        this.capacity = capacity;
    }
    
    // Getters and Setters
    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }
    
    public String getRoomType() { return roomType; }
    public void setRoomType(String roomType) { this.roomType = roomType; }
    
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public String getAmenities() { return amenities; }
    public void setAmenities(String amenities) { this.amenities = amenities; }
    
    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    
    // Check if room is available
    public boolean isAvailable() {
        return status.equals("Available");
    }
    
    // Book the room
    public void bookRoom() {
        this.status = "Occupied";
    }
    
    // Free the room
    public void freeRoom() {
        this.status = "Available";
    }
    
    @Override
    public String toString() {
        return String.format(
            "Room #%s | Type: %s | Status: %s | Price: Rs. %.2f/night\n" +
            "Capacity: %d persons | Amenities: %s",
            roomNumber, roomType, status, price, capacity, amenities
        );
    }
}

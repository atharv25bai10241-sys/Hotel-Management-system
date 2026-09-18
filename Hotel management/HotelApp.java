import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

/**
 * HotelApp Class - Main CLI Application
 * Topics: User Input/Output, Exception Handling, Control Flow
 */
public class HotelApp {
    private HotelManager manager;
    private Scanner scanner;
    private SimpleDateFormat dateFormat;
    
    public HotelApp() {
        // Initialize with sample hotel
        this.manager = new HotelManager("Grand Hotel India", "Mumbai", 18.0);  // 18% GST
        this.scanner = new Scanner(System.in);
        this.dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        
        // Initialize with some sample rooms if starting fresh
        initializeSampleRooms();
    }
    
    /**
     * Initialize sample rooms
     */
    private void initializeSampleRooms() {
        try {
            // Check if rooms already exist
            if (manager.getRoomsByType("Single").isEmpty()) {
                // Add sample rooms
                manager.addRoom(new Room("101", Room.SINGLE, 2000, "AC, WiFi, TV, Bathroom", 1));
                manager.addRoom(new Room("102", Room.SINGLE, 2000, "AC, WiFi, TV, Bathroom", 1));
                manager.addRoom(new Room("201", Room.DOUBLE, 3500, "AC, WiFi, TV, Bathroom, Mini Fridge", 2));
                manager.addRoom(new Room("202", Room.DOUBLE, 3500, "AC, WiFi, TV, Bathroom, Mini Fridge", 2));
                manager.addRoom(new Room("301", Room.SUITE, 5000, "AC, WiFi, TV, Bathroom, Kitchen, Sofa", 4));
                manager.addRoom(new Room("302", Room.DELUXE, 4000, "AC, WiFi, TV, Bathroom, Gym Access", 2));
                System.out.println("✓ Sample rooms initialized!");
            }
        } catch (HotelException e) {
            System.out.println("Rooms already loaded from previous session.");
        }
    }
    
    /**
     * Main menu
     */
    public void showMainMenu() {
        while (true) {
            System.out.println("\n" + "=".repeat(80));
            System.out.println("HOTEL RESERVATION SYSTEM");
            System.out.println("Welcome to " + manager.getHotelName());
            System.out.println("=".repeat(80));
            System.out.println("1. Room Operations");
            System.out.println("2. Customer Operations");
            System.out.println("3. Booking Operations");
            System.out.println("4. View Statistics");
            System.out.println("5. Exit");
            System.out.println("=".repeat(80));
            System.out.print("Enter your choice: ");
            
            try {
                int choice = scanner.nextInt();
                scanner.nextLine();
                
                switch (choice) {
                    case 1:
                        showRoomMenu();
                        break;
                    case 2:
                        showCustomerMenu();
                        break;
                    case 3:
                        showBookingMenu();
                        break;
                    case 4:
                        manager.displayStatistics();
                        break;
                    case 5:
                        System.out.println("\nThank you for using Hotel Reservation System!");
                        System.out.println("Goodbye! 👋");
                        return;
                    default:
                        System.out.println("❌ Invalid choice!");
                }
            } catch (Exception e) {
                System.out.println("❌ Invalid input!");
                scanner.nextLine();
            }
        }
    }
    
    /**
     * Room operations menu
     */
    private void showRoomMenu() {
        while (true) {
            System.out.println("\n" + "-".repeat(80));
            System.out.println("ROOM OPERATIONS");
            System.out.println("-".repeat(80));
            System.out.println("1. Add Room");
            System.out.println("2. View All Rooms");
            System.out.println("3. View Available Rooms");
            System.out.println("4. Search Room by Number");
            System.out.println("5. View Rooms by Type");
            System.out.println("6. Back to Main Menu");
            System.out.println("-".repeat(80));
            System.out.print("Enter your choice: ");
            
            try {
                int choice = scanner.nextInt();
                scanner.nextLine();
                
                switch (choice) {
                    case 1:
                        addRoom();
                        break;
                    case 2:
                        manager.viewAllRooms();
                        break;
                    case 3:
                        viewAvailableRooms();
                        break;
                    case 4:
                        searchRoomByNumber();
                        break;
                    case 5:
                        viewRoomsByType();
                        break;
                    case 6:
                        return;
                    default:
                        System.out.println("❌ Invalid choice!");
                }
            } catch (Exception e) {
                System.out.println("❌ Invalid input!");
                scanner.nextLine();
            }
        }
    }
    
    /**
     * Add new room
     */
    private void addRoom() {
        try {
            System.out.println("\n--- ADD NEW ROOM ---");
            System.out.print("Enter Room Number: ");
            String roomNumber = scanner.nextLine().trim();
            
            System.out.println("Room Types: Single, Double, Suite, Deluxe");
            System.out.print("Enter Room Type: ");
            String roomType = scanner.nextLine().trim();
            
            System.out.print("Enter Price (per night in Rs.): ");
            double price = scanner.nextDouble();
            scanner.nextLine();
            
            System.out.print("Enter Amenities (comma separated): ");
            String amenities = scanner.nextLine().trim();
            
            System.out.print("Enter Capacity (number of persons): ");
            int capacity = scanner.nextInt();
            scanner.nextLine();
            
            if (price < 0 || capacity <= 0) {
                System.out.println("❌ Invalid price or capacity!");
                return;
            }
            
            Room room = new Room(roomNumber, roomType, price, amenities, capacity);
            manager.addRoom(room);
        } catch (HotelException e) {
            System.out.println("❌ Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("❌ Invalid input!");
            scanner.nextLine();
        }
    }
    
    /**
     * View available rooms
     */
    private void viewAvailableRooms() {
        System.out.print("Enter Room Type (or leave empty for all): ");
        String roomType = scanner.nextLine().trim();
        
        ArrayList<Room> available;
        if (roomType.isEmpty()) {
            available = new ArrayList<>();
            available.addAll(manager.getAvailableRoomsByType("Single"));
            available.addAll(manager.getAvailableRoomsByType("Double"));
            available.addAll(manager.getAvailableRoomsByType("Suite"));
            available.addAll(manager.getAvailableRoomsByType("Deluxe"));
        } else {
            available = manager.getAvailableRoomsByType(roomType);
        }
        
        if (available.isEmpty()) {
            System.out.println("❌ No available rooms found!");
        } else {
            System.out.println("\n" + "=".repeat(80));
            System.out.println("AVAILABLE ROOMS - Total: " + available.size());
            System.out.println("=".repeat(80));
            for (Room room : available) {
                System.out.println(room);
                System.out.println("-".repeat(80));
            }
        }
    }
    
    /**
     * Search room by number
     */
    private void searchRoomByNumber() {
        System.out.print("Enter Room Number: ");
        String roomNumber = scanner.nextLine().trim();
        
        Room room = manager.searchRoomByNumber(roomNumber);
        if (room != null) {
            System.out.println("\n" + room);
        } else {
            System.out.println("❌ Room not found!");
        }
    }
    
    /**
     * View rooms by type
     */
    private void viewRoomsByType() {
        System.out.println("Room Types: Single, Double, Suite, Deluxe");
        System.out.print("Enter Room Type: ");
        String roomType = scanner.nextLine().trim();
        
        ArrayList<Room> rooms = manager.getRoomsByType(roomType);
        if (rooms.isEmpty()) {
            System.out.println("❌ No rooms of this type found!");
        } else {
            System.out.println("\n" + "=".repeat(80));
            System.out.println("ROOMS - " + roomType + " (Total: " + rooms.size() + ")");
            System.out.println("=".repeat(80));
            for (Room room : rooms) {
                System.out.println(room);
                System.out.println("-".repeat(80));
            }
        }
    }
    
    /**
     * Customer operations menu
     */
    private void showCustomerMenu() {
        while (true) {
            System.out.println("\n" + "-".repeat(80));
            System.out.println("CUSTOMER OPERATIONS");
            System.out.println("-".repeat(80));
            System.out.println("1. Register New Customer");
            System.out.println("2. Search Customer by ID");
            System.out.println("3. View Customer Details");
            System.out.println("4. View All Customers");
            System.out.println("5. Back to Main Menu");
            System.out.println("-".repeat(80));
            System.out.print("Enter your choice: ");
            
            try {
                int choice = scanner.nextInt();
                scanner.nextLine();
                
                switch (choice) {
                    case 1:
                        registerCustomer();
                        break;
                    case 2:
                        searchCustomer();
                        break;
                    case 3:
                        viewCustomerDetails();
                        break;
                    case 4:
                        manager.viewAllCustomers();
                        break;
                    case 5:
                        return;
                    default:
                        System.out.println("❌ Invalid choice!");
                }
            } catch (Exception e) {
                System.out.println("❌ Invalid input!");
                scanner.nextLine();
            }
        }
    }
    
    /**
     * Register new customer
     */
    private void registerCustomer() {
        try {
            System.out.println("\n--- REGISTER NEW CUSTOMER ---");
            System.out.print("Enter Customer ID: ");
            String customerID = scanner.nextLine().trim();
            
            System.out.print("Enter Name: ");
            String name = scanner.nextLine().trim();
            
            System.out.print("Enter Email: ");
            String email = scanner.nextLine().trim();
            
            System.out.print("Enter Phone: ");
            String phone = scanner.nextLine().trim();
            
            System.out.print("Enter Address: ");
            String address = scanner.nextLine().trim();
            
            Customer customer = new Customer(customerID, name, email, phone, address);
            manager.registerCustomer(customer);
        } catch (HotelException e) {
            System.out.println("❌ Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("❌ Invalid input!");
            scanner.nextLine();
        }
    }
    
    /**
     * Search customer by ID
     */
    private void searchCustomer() {
        System.out.print("Enter Customer ID: ");
        String customerID = scanner.nextLine().trim();
        
        Customer customer = manager.searchCustomerByID(customerID);
        if (customer != null) {
            System.out.println(customer);
        } else {
            System.out.println("❌ Customer not found!");
        }
    }
    
    /**
     * View customer details
     */
    private void viewCustomerDetails() {
        System.out.print("Enter Customer ID: ");
        String customerID = scanner.nextLine().trim();
        
        Customer customer = manager.searchCustomerByID(customerID);
        if (customer == null) {
            System.out.println("❌ Customer not found!");
            return;
        }
        
        System.out.println(customer);
        
        System.out.print("View booking history? (yes/no): ");
        if (scanner.nextLine().trim().equalsIgnoreCase("yes")) {
            try {
                ArrayList<Booking> bookings = manager.getCustomerBookings(customerID);
                if (bookings.isEmpty()) {
                    System.out.println("\nNo bookings found!");
                } else {
                    System.out.println("\n" + "=".repeat(80));
                    System.out.println("BOOKING HISTORY");
                    System.out.println("=".repeat(80));
                    for (Booking booking : bookings) {
                        System.out.println(booking);
                        System.out.println("-".repeat(80));
                    }
                }
            } catch (HotelException e) {
                System.out.println("❌ Error: " + e.getMessage());
            }
        }
    }
    
    /**
     * Booking operations menu
     */
    private void showBookingMenu() {
        while (true) {
            System.out.println("\n" + "-".repeat(80));
            System.out.println("BOOKING OPERATIONS");
            System.out.println("-".repeat(80));
            System.out.println("1. Create Booking");
            System.out.println("2. Search Booking by ID");
            System.out.println("3. Check-In");
            System.out.println("4. Check-Out");
            System.out.println("5. Cancel Booking");
            System.out.println("6. Apply Discount");
            System.out.println("7. Generate Bill");
            System.out.println("8. View All Bookings");
            System.out.println("9. View Upcoming Bookings");
            System.out.println("10. Back to Main Menu");
            System.out.println("-".repeat(80));
            System.out.print("Enter your choice: ");
            
            try {
                int choice = scanner.nextInt();
                scanner.nextLine();
                
                switch (choice) {
                    case 1:
                        createBooking();
                        break;
                    case 2:
                        searchBooking();
                        break;
                    case 3:
                        checkInBooking();
                        break;
                    case 4:
                        checkOutBooking();
                        break;
                    case 5:
                        cancelBooking();
                        break;
                    case 6:
                        applyDiscount();
                        break;
                    case 7:
                        generateBill();
                        break;
                    case 8:
                        manager.viewAllBookings();
                        break;
                    case 9:
                        viewUpcomingBookings();
                        break;
                    case 10:
                        return;
                    default:
                        System.out.println("❌ Invalid choice!");
                }
            } catch (Exception e) {
                System.out.println("❌ Invalid input!");
                scanner.nextLine();
            }
        }
    }
    
    /**
     * Create new booking
     */
    private void createBooking() {
        try {
            System.out.println("\n--- CREATE BOOKING ---");
            System.out.print("Enter Booking ID: ");
            String bookingID = scanner.nextLine().trim();
            
            System.out.print("Enter Customer ID: ");
            String customerID = scanner.nextLine().trim();
            
            System.out.print("Enter Room Number: ");
            String roomNumber = scanner.nextLine().trim();
            
            System.out.print("Enter Check-In Date (dd-MM-yyyy): ");
            Date checkInDate = dateFormat.parse(scanner.nextLine().trim());
            
            System.out.print("Enter Check-Out Date (dd-MM-yyyy): ");
            Date checkOutDate = dateFormat.parse(scanner.nextLine().trim());
            
            manager.createBooking(bookingID, customerID, roomNumber, checkInDate, checkOutDate);
        } catch (HotelException e) {
            System.out.println("❌ Error: " + e.getMessage());
        } catch (ParseException e) {
            System.out.println("❌ Invalid date format! Use dd-MM-yyyy");
        } catch (Exception e) {
            System.out.println("❌ Invalid input!");
            scanner.nextLine();
        }
    }
    
    /**
     * Search booking by ID
     */
    private void searchBooking() {
        System.out.print("Enter Booking ID: ");
        String bookingID = scanner.nextLine().trim();
        
        Booking booking = manager.searchBookingByID(bookingID);
        if (booking != null) {
            System.out.println(booking);
        } else {
            System.out.println("❌ Booking not found!");
        }
    }
    
    /**
     * Check in booking
     */
    private void checkInBooking() {
        try {
            System.out.print("Enter Booking ID: ");
            String bookingID = scanner.nextLine().trim();
            manager.checkInBooking(bookingID);
        } catch (HotelException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }
    
    /**
     * Check out booking
     */
    private void checkOutBooking() {
        try {
            System.out.print("Enter Booking ID: ");
            String bookingID = scanner.nextLine().trim();
            manager.checkOutBooking(bookingID);
        } catch (HotelException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }
    
    /**
     * Cancel booking
     */
    private void cancelBooking() {
        try {
            System.out.print("Enter Booking ID: ");
            String bookingID = scanner.nextLine().trim();
            manager.cancelBooking(bookingID);
        } catch (HotelException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }
    
    /**
     * Apply discount to booking
     */
    private void applyDiscount() {
        try {
            System.out.print("Enter Booking ID: ");
            String bookingID = scanner.nextLine().trim();
            
            System.out.print("Enter Discount Percentage (0-100): ");
            double discountPercent = scanner.nextDouble();
            scanner.nextLine();
            
            manager.applyDiscountToBooking(bookingID, discountPercent);
        } catch (HotelException e) {
            System.out.println("❌ Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("❌ Invalid input!");
            scanner.nextLine();
        }
    }
    
    /**
     * Generate bill
     */
    private void generateBill() {
        try {
            System.out.print("Enter Booking ID: ");
            String bookingID = scanner.nextLine().trim();
            manager.generateBill(bookingID);
        } catch (HotelException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }
    
    /**
     * View upcoming bookings
     */
    private void viewUpcomingBookings() {
        ArrayList<Booking> upcoming = manager.getUpcomingBookings();
        if (upcoming.isEmpty()) {
            System.out.println("\nNo upcoming bookings!");
        } else {
            System.out.println("\n" + "=".repeat(80));
            System.out.println("UPCOMING BOOKINGS (Next 7 Days) - Total: " + upcoming.size());
            System.out.println("=".repeat(80));
            for (Booking booking : upcoming) {
                System.out.println(booking);
                System.out.println("-".repeat(80));
            }
        }
    }
    
    /**
     * Main entry point
     */
    public static void main(String[] args) {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("Welcome to Hotel Reservation System");
        System.out.println("=".repeat(80));
        System.out.println("Loading data...");
        
        HotelApp app = new HotelApp();
        app.showMainMenu();
    }
}

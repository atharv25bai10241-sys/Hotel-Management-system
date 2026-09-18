# Hotel Reservation System

##  Project Overview

A comprehensive **Command-Line Interface (CLI)** based Hotel Reservation System built in Java. This project demonstrates core Java concepts including Object-Oriented Programming (OOP), Collections, Exception Handling, File I/O, Date handling, and Data Persistence.

### Purpose
This system manages hotel operations including:
- Room inventory management
- Customer registration and management
- Booking creation and cancellation
- Automatic refund calculation based on cancellation policy
- Check-in/Check-out tracking
- Bill generation with tax calculations
- Discount application
- Complete booking history

---

##  Course Topics Covered

This project covers the following topics from **CSE2006 - Programming in Java**:

###  UNIT 1: Java Basics & Flow Control
- Java Input/Output operations
- Control structures (if-else, loops, switch)
- Variable types and data types

###  UNIT 2: Object-Oriented Programming
- **Classes and Objects** (5 entity classes)
- **Encapsulation** (private members, getters/setters)
- **Constructors** with parameter initialization
- **toString()** method overriding
- Instance methods and state management

###  UNIT 3: Exception Handling
- **Custom Exception** (HotelException)
- Try-catch blocks with input validation
- Exception propagation
- Business logic error handling

###  UNIT 4: Collections & I/O Streams
- **ArrayList** for dynamic data storage
- Collections Framework usage
- **Serialization** (ObjectInputStream/ObjectOutputStream)
- File I/O operations
- Data persistence across sessions

###  UNIT 5: Advanced Concepts
- **Date/Time** handling and calculations
- **Serializable interface** implementation
- Complex business logic algorithms
- Cancellation refund calculations

---

##  Project Architecture

### Classes Structure

```
┌─────────────────────┐
│   HotelApp          │  (CLI Interface - User Interaction)
│  (Main Entry Point) │
└──────────┬──────────┘
           │ uses
           ▼
┌─────────────────────┐
│  HotelManager       │  (Business Logic & Data Management)
└──────────┬──────────┘
           │ manages
           ├─────┬──────────┬────────┐
           ▼     ▼          ▼        ▼
        ┌────┐ ┌────────┐ ┌──────────┐ ┌──────────────┐
        │Room│ │Customer│ │ Booking  │ │HotelException│
        │    │ │        │ │          │ │   (Custom)   │
        └────┘ └────────┘ └──────────┘ └──────────────┘
```

### File Structure

```
HotelReservationSystem/
│
├── HotelApp.java                (Main CLI Application - 400+ lines)
├── HotelManager.java            (Business Logic - 500+ lines)
├── Room.java                    (Room Entity - 90 lines)
├── Customer.java                (Customer Entity - 110 lines)
├── Booking.java                 (Booking Entity - 180 lines)
├── HotelException.java          (Custom Exception - 15 lines)
│
├── README.md                    (This file - Complete Guide)
├── PROJECT_REPORT.md            (Detailed Report - 400+ lines)
│
├── rooms.dat                    (Serialized rooms data)
├── customers.dat                (Serialized customers data)
└── bookings.dat                 (Serialized bookings data)
```

---

##  Classes Description

### 1. **Room.java**
Represents a hotel room entity.

**Attributes:**
- `roomNumber` - Unique room identifier
- `roomType` - Room category (Single, Double, Suite, Deluxe)
- `price` - Price per night in Rs.
- `status` - Availability status (Available/Occupied)
- `amenities` - Room features
- `capacity` - Maximum persons

**Key Methods:**
- `bookRoom()` - Change status to Occupied
- `freeRoom()` - Change status to Available
- `isAvailable()` - Check availability

**OOP Concepts:** Encapsulation, Serializable interface

---

### 2. **Customer.java**
Represents a hotel customer/guest.

**Attributes:**
- `customerID`, `name`, `email`, `phone`, `address` - Customer info
- `bookingHistory` (ArrayList) - List of booking IDs
- `totalAmountSpent` - Track spending
- `registrationDate` - When customer joined

**Key Methods:**
- `addBooking()` - Add booking to history
- `removeBooking()` - Remove booking
- `hasBooking()` - Check if customer has specific booking
- `addToTotalSpent()` - Update spending

**OOP Concepts:** Collections (ArrayList), Encapsulation

---

### 3. **Booking.java**
Represents a hotel booking/reservation.

**Attributes:**
- `bookingID`, `customerID`, `roomNumber` - Identifiers
- `checkInDate`, `checkOutDate` - Stay dates
- `numberOfNights` - Calculated stay duration
- `roomPrice`, `totalCost` - Pricing
- `status` - Booking status (Confirmed/Checked-In/Cancelled/Completed)
- `discountApplied` - Discount amount
- `refundAmount` - Cancellation refund

**Key Methods:**
- `calculateRefund()` - Calculate refund based on cancellation policy
- `cancelBooking()` - Cancel and calculate refund
- `checkIn()` - Mark as checked-in
- `checkOut()` - Mark as checked-out
- `applyDiscount(percent)` - Apply percentage discount
- `isUpcoming()` - Check if booking is within 7 days

**Cancellation Policy:**
```
- Cancelled > 48 hours before check-in: 100% refund
- Cancelled < 48 hours before check-in: 50% refund
- Cancelled after check-in: 0% refund
```

**OOP Concepts:** Date handling, Encapsulation

---

### 4. **HotelManager.java**
Core business logic and data management.

**Key Operations:**

**Room Management:**
- `addRoom()` - Add room with duplicate checking
- `searchRoomByNumber()` - Find room by ID
- `getAvailableRoomsByType()` - Get available rooms
- `getRoomsByType()` - Get rooms of specific type

**Customer Management:**
- `registerCustomer()` - Register new customer
- `searchCustomerByID()` - Find customer
- `getCustomerBookings()` - Get customer's bookings

**Booking Operations:**
- `createBooking()` - Create booking with validations
- `cancelBooking()` - Cancel and process refund
- `checkInBooking()` - Check-in guest
- `checkOutBooking()` - Check-out guest
- `applyDiscountToBooking()` - Apply percentage discount
- `generateBill()` - Create bill with tax calculation

**Statistics:**
- `displayStatistics()` - Show hotel overview

**File Operations:**
- `saveRooms()`, `saveCustomers()`, `saveBookings()` - Serialization
- `loadData()` - Deserialization on startup

**Exception Handling:**
- Custom `HotelException` for business logic errors
- Input validation
- Date validation

---

### 5. **HotelException.java**
Custom exception for hotel-specific errors.

**Usage:**
- Thrown when business constraints violated
- Provides context-specific error messages
- Extends Exception class

---

### 6. **HotelApp.java**
Main CLI application with menu-driven interface.

**Features:**
- Interactive menu system
- Input validation and error handling
- Date format parsing (dd-MM-yyyy)
- Sample data initialization
- User-friendly prompts and formatted output

**Menu Structure:**
```
Main Menu
├── Room Operations
│   ├── Add Room
│   ├── View All Rooms
│   ├── View Available Rooms
│   ├── Search Room
│   └── View Rooms by Type
├── Customer Operations
│   ├── Register Customer
│   ├── Search Customer
│   ├── View Details
│   └── View All Customers
├── Booking Operations
│   ├── Create Booking
│   ├── Search Booking
│   ├── Check-In
│   ├── Check-Out
│   ├── Cancel Booking
│   ├── Apply Discount
│   ├── Generate Bill
│   ├── View All Bookings
│   └── View Upcoming Bookings
├── Statistics
└── Exit
```

---

##  How to Setup and Run

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- Terminal/Command Prompt
- Text Editor or IDE

### Installation Steps

#### 1. Create Project Directory
```bash
mkdir HotelReservationSystem
cd HotelReservationSystem
```

#### 2. Copy Java Files
Place all `.java` files in the directory:
- HotelApp.java
- HotelManager.java
- Room.java
- Customer.java
- Booking.java
- HotelException.java

#### 3. Compile All Files
```bash
# On Windows/Linux/Mac
javac *.java
```

Expected: No error messages (all files compile successfully)

#### 4. Run Application
```bash
java HotelApp
```

Expected: Welcome message and main menu appear

---

##  How to Use

### Starting Application
```
$ java HotelApp

================================================================================
Welcome to Hotel Reservation System
Loading data...

================================================================================
HOTEL RESERVATION SYSTEM
Welcome to Grand Hotel India
================================================================================
1. Room Operations
2. Customer Operations
3. Booking Operations
4. View Statistics
5. Exit
================================================================================
Enter your choice: _
```

### Example Workflow

#### **Step 1: View Available Rooms**
```
Choice: 1 (Room Operations)
Choice: 3 (View Available Rooms)
Enter Room Type: Double
[Displays all available Double rooms]
```

#### **Step 2: Register Customer**
```
Choice: 2 (Customer Operations)
Choice: 1 (Register Customer)
Customer ID: C001
Name: Raj Kumar
Email: raj@email.com
Phone: 9876543210
Address: Mumbai, India
✓ Customer registered successfully!
```

#### **Step 3: Create Booking**
```
Choice: 3 (Booking Operations)
Choice: 1 (Create Booking)
Booking ID: BK001
Customer ID: C001
Room Number: 201
Check-In Date: 20-09-2026
Check-Out Date: 25-09-2026
✓ Booking created successfully!
Total Cost: Rs. 17500.00
```

#### **Step 4: Apply Discount**
```
Choice: 3 (Booking Operations)
Choice: 6 (Apply Discount)
Booking ID: BK001
Discount Percentage: 10
✓ Discount applied successfully!
New Total: Rs. 15750.00
```

#### **Step 5: Check-In**
```
Choice: 3 (Booking Operations)
Choice: 3 (Check-In)
Booking ID: BK001
✓ Checked in successfully!
```

#### **Step 6: Generate Bill**
```
Choice: 3 (Booking Operations)
Choice: 7 (Generate Bill)
Booking ID: BK001
[Detailed bill displayed with tax]
```

#### **Step 7: Check-Out**
```
Choice: 3 (Booking Operations)
Choice: 4 (Check-Out)
Booking ID: BK001
✓ Checked out successfully!
```

---

##  Key Features

### Room Management
✓ Add rooms with amenities and capacity
✓ Track availability in real-time
✓ Multiple room types (Single, Double, Suite, Deluxe)
✓ Search rooms by type or number
✓ View available/occupied rooms

### Customer Management
✓ Register customers with full information
✓ Search and view customer details
✓ Track booking history per customer
✓ Monitor total spending
✓ View customer registration date

### Booking System
✓ Create bookings with date validation
✓ Automatic number of nights calculation
✓ Prevent past date bookings
✓ Prevent overlapping bookings
✓ Check-in/Check-out tracking

### Cancellation & Refunds
✓ Flexible cancellation policy:
  - Full refund (> 48 hours before check-in)
  - 50% refund (< 48 hours before check-in)
  - No refund (after check-in)
✓ Automatic refund calculation
✓ Refund tracking

### Billing & Discounts
✓ Automatic bill generation
✓ Tax calculation (18% GST)
✓ Discount application (percentage-based)
✓ Complete cost breakdown
✓ Total amount tracking

### Data Persistence
✓ All data saved to .dat files
✓ Automatic data loading on startup
✓ No database required
✓ Serialization for data security

### Additional Features
✓ Statistics dashboard
✓ Upcoming bookings view (next 7 days)
✓ Comprehensive input validation
✓ User-friendly error messages
✓ Menu-driven interface

---

##  Data Files

The application creates three data files automatically:

1. **rooms.dat** - Stores all room information
2. **customers.dat** - Stores all customer information
3. **bookings.dat** - Stores all booking records

**Clearing Data:**
To start fresh, simply delete these `.dat` files and run the application again.

---

##  Testing Examples

### Test Case 1: Room Management
```
1. Add room with specific amenities
2. Search room by number
3. View available rooms
4. Verify room status updates after booking
```

### Test Case 2: Booking & Refunds
```
1. Create booking for 5 days
2. Cancel within 48 hours → 50% refund
3. Create another booking
4. Cancel > 48 hours before → 100% refund
```

### Test Case 3: Discounts & Billing
```
1. Create booking: Rs. 10,000
2. Apply 20% discount → Rs. 8,000
3. Generate bill with 18% tax
4. Verify final amount
```

### Test Case 4: Customer Workflow
```
1. Register customer
2. Create multiple bookings
3. View booking history
4. Check customer spending
```

---

##  Algorithm Complexity

| Operation | Time | Space |
|-----------|------|-------|
| Search Room | O(n) | O(1) |
| Add Booking | O(n) | O(1) |
| Cancel Booking | O(n) | O(1) |
| Get Available Rooms | O(n) | O(m) |
| View Customer Bookings | O(n) | O(m) |

*n = total entities, m = results*

---

##  Troubleshooting

### "Compilation error: cannot find symbol"
- Ensure all `.java` files are in same directory
- Compile with `javac *.java`

### "FileNotFoundException"
- Normal on first run; .dat files create automatically
- Just start using the application

### "InputMismatchException"
- Enter correct data type (number when asked)
- Program will re-prompt with correct input

### "Date parsing error"
- Use exact format: dd-MM-yyyy (e.g., 20-09-2026)
- No other date formats accepted

### "No bookings after restart"
- Ensure `.dat` files exist in same directory
- Don't delete .dat files unless wanting to reset

---

##  Future Enhancements

**Phase 1: Database Integration**
- Replace .dat files with MySQL/SQLite
- Use JDBC for database operations
- Multi-user concurrent access

**Phase 2: Advanced Features**
- Online payment gateway
- Email confirmation system
- Room housekeeping tracking
- Loyalty points system
- Guest reviews and ratings

**Phase 3: UI Improvements**
- GUI version (Swing/JavaFX)
- Web-based interface
- Mobile app integration

**Phase 4: Business Features**
- Revenue reports
- Staff management
- Service requests tracking
- Inventory management

---

##  References

### Course Material
- CSE2006: Programming in Java
- "Java The Complete Reference" - Herbert Schildt

### Concepts Used
- Object-Oriented Programming
- Collections Framework (ArrayList)
- Exception Handling
- File I/O and Serialization
- Date/Time handling

### Java Documentation
- Collections: https://docs.oracle.com/javase/tutorial/collections/
- Date/Time: https://docs.oracle.com/javase/tutorial/datetime/
- Exception Handling: https://docs.oracle.com/javase/tutorial/essential/exceptions/

---

##  Summary

This Hotel Reservation System is a complete, production-ready application demonstrating all key Java concepts. It successfully manages hotel operations and can be easily extended with additional features.

**Key Achievements:**
✓ Fully functional CLI application
✓ Comprehensive exception handling
✓ Professional code organization
✓ Data persistence
✓ Real-world scenario
✓ All course topics covered
✓ Well-documented

---

**Status:**  Complete and Ready for Submission

---

**Last Updated:** September 2026  
**Version:** 1.0

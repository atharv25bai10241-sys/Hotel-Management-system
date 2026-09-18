/**
 * HotelException Class - Custom Exception
 * Topics: Exception Handling, Custom Exceptions
 */
public class HotelException extends Exception {
    
    public HotelException(String message) {
        super(message);
    }
    
    public HotelException(String message, Throwable cause) {
        super(message, cause);
    }
}

import java.util.*;

/**
 * =============================================================
 * MAIN CLASS - UseCase9ErrorHandlingValidation
 * =============================================================
 * Use Case 9: Error Handling & Validation
 */
public class BookMyStayApp {

    public static void main(String[] args) {

        BookingSystem system = new BookingSystem();

        try {
            // Valid booking
            system.bookRoom("RES201", "Shraviya", "Deluxe", 2);

            // Invalid room type
            system.bookRoom("RES202", "Rahul", "Luxury", 1);

        } catch (InvalidBookingException e) {
            System.out.println("Error: " + e.getMessage());
        }

        try {
            // Invalid quantity (exceeds availability)
            system.bookRoom("RES203", "Ananya", "Suite", 10);

        } catch (InvalidBookingException e) {
            System.out.println("Error: " + e.getMessage());
        }

        try {
            // Negative quantity
            system.bookRoom("RES204", "Kiran", "Standard", -1);

        } catch (InvalidBookingException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // System still runs normally after errors
        System.out.println("\nSystem remains stable after handling errors.");
    }
}

/**
 * =============================================================
 * CLASS - BookingSystem
 * =============================================================
 */
class BookingSystem {

    private Map<String, Integer> roomInventory;

    public BookingSystem() {
        roomInventory = new HashMap<>();

        // Initial inventory
        roomInventory.put("Standard", 5);
        roomInventory.put("Deluxe", 3);
        roomInventory.put("Suite", 2);
    }

    public void bookRoom(String reservationId, String guestName,
                         String roomType, int quantity)
            throws InvalidBookingException {

        // Validate input
        validateRoomType(roomType);
        validateQuantity(quantity);
        validateAvailability(roomType, quantity);

        // Process booking (update inventory)
        roomInventory.put(roomType,
                roomInventory.get(roomType) - quantity);

        System.out.println("Booking Successful: " + reservationId +
                " | Guest: " + guestName +
                " | Room: " + roomType +
                " | Quantity: " + quantity);
    }

    private void validateRoomType(String roomType)
            throws InvalidBookingException {

        if (!roomInventory.containsKey(roomType)) {
            throw new InvalidBookingException(
                    "Invalid room type: " + roomType);
        }
    }

    private void validateQuantity(int quantity)
            throws InvalidBookingException {

        if (quantity <= 0) {
            throw new InvalidBookingException(
                    "Quantity must be greater than zero.");
        }
    }

    private void validateAvailability(String roomType, int quantity)
            throws InvalidBookingException {

        int available = roomInventory.get(roomType);

        if (quantity > available) {
            throw new InvalidBookingException(
                    "Not enough rooms available. Requested: " +
                            quantity + ", Available: " + available);
        }
    }
}

/**
 * =============================================================
 * CLASS - Custom Exception
 * =============================================================
 */
class InvalidBookingException extends Exception {

    public InvalidBookingException(String message) {
        super(message);
    }
}
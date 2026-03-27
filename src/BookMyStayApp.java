import java.util.*;

/**
 * =============================================================
 * MAIN CLASS - UseCase10BookingCancellation
 * =============================================================
 * Use Case 10: Booking Cancellation & Inventory Rollback
 */
public class BookMyStayApp {

    public static void main(String[] args) {

        BookingSystem system = new BookingSystem();

        try {
            // Create bookings
            system.bookRoom("RES301", "Shraviya", "Deluxe");
            system.bookRoom("RES302", "Rahul", "Suite");

            // Cancel a booking
            system.cancelBooking("RES301");

            // Try cancelling same booking again (error)
            system.cancelBooking("RES301");

        } catch (BookingException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Display remaining inventory
        system.displayInventory();
    }
}

/**
 * =============================================================
 * CLASS - BookingSystem
 * =============================================================
 */
class BookingSystem {

    // Room inventory
    private Map<String, Integer> inventory;

    // Reservation records
    private Map<String, Reservation> reservations;

    // Stack for rollback (released room IDs)
    private Stack<String> rollbackStack;

    public BookingSystem() {
        inventory = new HashMap<>();
        reservations = new HashMap<>();
        rollbackStack = new Stack<>();

        // Initial inventory
        inventory.put("Standard", 2);
        inventory.put("Deluxe", 2);
        inventory.put("Suite", 1);
    }

    // Book a room
    public void bookRoom(String reservationId, String guestName, String roomType)
            throws BookingException {

        if (!inventory.containsKey(roomType)) {
            throw new BookingException("Invalid room type.");
        }

        if (inventory.get(roomType) <= 0) {
            throw new BookingException("No rooms available for " + roomType);
        }

        // Allocate room (reduce inventory)
        inventory.put(roomType, inventory.get(roomType) - 1);

        // Create reservation
        Reservation res = new Reservation(reservationId, guestName, roomType);
        reservations.put(reservationId, res);

        System.out.println("Booking Confirmed: " + reservationId +
                " | " + guestName + " | " + roomType);
    }

    // Cancel booking (rollback)
    public void cancelBooking(String reservationId)
            throws BookingException {

        // Validate existence
        if (!reservations.containsKey(reservationId)) {
            throw new BookingException("Reservation not found or already cancelled.");
        }

        Reservation res = reservations.get(reservationId);
        String roomType = res.getRoomType();

        // Push to rollback stack
        rollbackStack.push(reservationId);

        // Restore inventory
        inventory.put(roomType, inventory.get(roomType) + 1);

        // Remove reservation
        reservations.remove(reservationId);

        System.out.println("Booking Cancelled: " + reservationId +
                " | Room Restored: " + roomType);
    }

    // Display inventory
    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " : " + inventory.get(type));
        }
    }
}

/**
 * =============================================================
 * CLASS - Reservation
 * =============================================================
 */
class Reservation {

    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getRoomType() {
        return roomType;
    }
}

/**
 * =============================================================
 * CLASS - Custom Exception
 * =============================================================
 */
class BookingException extends Exception {

    public BookingException(String message) {
        super(message);
    }
}
import java.io.*;
import java.util.*;

/**
 * =============================================================
 * MAIN CLASS - UseCase12DataPersistenceRecovery
 * =============================================================
 * Use Case 12: Data Persistence & System Recovery
 */
public class BookMyStayApp {

    public static void main(String[] args) {

        BookingSystem system = new BookingSystem();
        PersistenceService persistence = new PersistenceService();

        // Try loading previous state (on startup)
        system = persistence.loadState();

        // Perform operations
        system.bookRoom("RES501", "Shraviya", "Deluxe");
        system.bookRoom("RES502", "Rahul", "Suite");

        // Display current state
        System.out.println("\nCurrent System State:");
        system.displayState();

        // Save state before shutdown
        persistence.saveState(system);

        System.out.println("\nSystem state saved successfully.");
    }
}

/**
 * =============================================================
 * CLASS - BookingSystem (Serializable)
 * =============================================================
 */
class BookingSystem implements Serializable {

    private static final long serialVersionUID = 1L;

    private Map<String, Integer> inventory;
    private List<Reservation> bookingHistory;

    public BookingSystem() {
        inventory = new HashMap<>();
        bookingHistory = new ArrayList<>();

        // Default inventory
        inventory.put("Standard", 2);
        inventory.put("Deluxe", 2);
        inventory.put("Suite", 1);
    }

    public void bookRoom(String reservationId, String guestName, String roomType) {

        if (!inventory.containsKey(roomType)) {
            System.out.println("Invalid room type.");
            return;
        }

        int available = inventory.get(roomType);

        if (available > 0) {
            inventory.put(roomType, available - 1);

            Reservation res = new Reservation(reservationId, guestName, roomType);
            bookingHistory.add(res);

            System.out.println("Booking Confirmed: " + reservationId +
                    " | " + guestName + " | " + roomType);
        } else {
            System.out.println("Booking Failed (No Rooms): " + reservationId);
        }
    }

    public void displayState() {

        System.out.println("\nInventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " : " + inventory.get(type));
        }

        System.out.println("\nBooking History:");
        for (Reservation r : bookingHistory) {
            System.out.println(r);
        }
    }
}

/**
 * =============================================================
 * CLASS - Reservation (Serializable)
 * =============================================================
 */
class Reservation implements Serializable {

    private static final long serialVersionUID = 1L;

    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    @Override
    public String toString() {
        return reservationId + " | " + guestName + " | " + roomType;
    }
}

/**
 * =============================================================
 * CLASS - PersistenceService
 * =============================================================
 */
class PersistenceService {

    private static final String FILE_NAME = "booking_data.ser";

    // Save system state
    public void saveState(BookingSystem system) {

        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            oos.writeObject(system);

        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    // Load system state
    public BookingSystem loadState() {

        File file = new File(FILE_NAME);

        // If file doesn't exist, return fresh system
        if (!file.exists()) {
            System.out.println("No previous data found. Starting fresh.");
            return new BookingSystem();
        }

        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            System.out.println("Previous data loaded successfully.");
            return (BookingSystem) ois.readObject();

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading data. Starting fresh.");
            return new BookingSystem();
        }
    }
}
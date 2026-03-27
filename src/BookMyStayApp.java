import java.util.*;

/**
 * =============================================================
 * MAIN CLASS - UseCase11ConcurrentBookingSimulation
 * =============================================================
 * Use Case 11: Concurrent Booking Simulation (Thread Safety)
 */
public class BookMyStayApp {

    public static void main(String[] args) {

        BookingSystem system = new BookingSystem();

        // Create multiple booking requests (threads)
        Thread t1 = new Thread(new BookingTask(system, "RES401", "Shraviya", "Deluxe"));
        Thread t2 = new Thread(new BookingTask(system, "RES402", "Rahul", "Deluxe"));
        Thread t3 = new Thread(new BookingTask(system, "RES403", "Ananya", "Deluxe"));
        Thread t4 = new Thread(new BookingTask(system, "RES404", "Kiran", "Suite"));
        Thread t5 = new Thread(new BookingTask(system, "RES405", "Meena", "Suite"));

        // Start threads (simulate concurrent requests)
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();

        // Wait for all threads to finish
        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
            t5.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Final inventory state
        system.displayInventory();
    }
}

/**
 * =============================================================
 * CLASS - BookingTask (Thread Task)
 * =============================================================
 */
class BookingTask implements Runnable {

    private BookingSystem system;
    private String reservationId;
    private String guestName;
    private String roomType;

    public BookingTask(BookingSystem system, String reservationId,
                       String guestName, String roomType) {
        this.system = system;
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    @Override
    public void run() {
        system.bookRoom(reservationId, guestName, roomType);
    }
}

/**
 * =============================================================
 * CLASS - BookingSystem (Thread-Safe)
 * =============================================================
 */
class BookingSystem {

    private Map<String, Integer> inventory;

    public BookingSystem() {
        inventory = new HashMap<>();

        // Initial inventory
        inventory.put("Standard", 2);
        inventory.put("Deluxe", 2);
        inventory.put("Suite", 1);
    }

    // Synchronized method ensures thread safety
    public synchronized void bookRoom(String reservationId,
                                      String guestName,
                                      String roomType) {

        System.out.println(Thread.currentThread().getName() +
                " processing " + reservationId);

        if (!inventory.containsKey(roomType)) {
            System.out.println("Invalid room type for " + reservationId);
            return;
        }

        int available = inventory.get(roomType);

        if (available > 0) {

            // Simulate delay to expose race conditions if not synchronized
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Allocate room
            inventory.put(roomType, available - 1);

            System.out.println("Booking Confirmed: " + reservationId +
                    " | " + guestName +
                    " | " + roomType);
        } else {
            System.out.println("Booking Failed (No Rooms): " + reservationId +
                    " | " + roomType);
        }
    }

    public void displayInventory() {
        System.out.println("\nFinal Inventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " : " + inventory.get(type));
        }
    }
}
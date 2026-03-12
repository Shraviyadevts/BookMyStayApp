import java.util.LinkedList;
import java.util.Queue;

/**
 * ============================================================
 * MAIN CLASS - UseCase5BookingRequestQueue
 * ============================================================
 *
 * Use Case 5: Booking Request (First-Come-First-Served)
 *
 * Description:
 * Demonstrates handling of booking requests using
 * a FIFO queue structure to preserve request order.
 *
 * @author Developer
 * @version 5.1
 */

public class BookMyStayApp {

    public static void main(String[] args) {

        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        System.out.println("===== Booking Request Queue =====\n");

        // Guests submitting booking requests
        bookingQueue.addRequest(new Reservation("Alice", "Single Room"));
        bookingQueue.addRequest(new Reservation("Bob", "Double Room"));
        bookingQueue.addRequest(new Reservation("Charlie", "Suite Room"));

        // Display queued requests
        bookingQueue.displayRequests();
    }
}


/**
 * Reservation Class
 * Represents a guest booking request.
 *
 * @version 5.0
 */
class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void displayReservation() {
        System.out.println("Guest Name : " + guestName);
        System.out.println("Room Type  : " + roomType);
    }
}


/**
 * BookingRequestQueue Class
 * Manages booking requests using FIFO principle.
 *
 * @version 5.0
 */
class BookingRequestQueue {

    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    // Add request to queue
    public void addRequest(Reservation reservation) {

        requestQueue.offer(reservation);

        System.out.println("Booking request added for "
                + reservation.getGuestName()
                + " (" + reservation.getRoomType() + ")");
    }

    // Display queued requests
    public void displayRequests() {

        System.out.println("\nRequests in Queue (FIFO Order):\n");

        for (Reservation r : requestQueue) {

            r.displayReservation();
            System.out.println();
        }
    }
}
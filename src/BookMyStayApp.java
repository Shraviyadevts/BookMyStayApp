import java.util.*;

/**
 * ============================================================
 * MAIN CLASS - UseCase6RoomAllocationService
 * ============================================================
 *
 * Use Case 6: Reservation Confirmation & Room Allocation
 *
 * Description:
 * Processes booking requests from the queue, allocates rooms,
 * generates unique room IDs, and updates inventory safely.
 *
 * @version 6.1
 */

public class BookMyStayApp {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Add booking requests
        bookingQueue.addRequest(new Reservation("Alice", "Single Room"));
        bookingQueue.addRequest(new Reservation("Bob", "Double Room"));
        bookingQueue.addRequest(new Reservation("Charlie", "Single Room"));

        // Allocation service
        RoomAllocationService allocationService =
                new RoomAllocationService(inventory, bookingQueue);

        System.out.println("===== Processing Reservations =====\n");

        allocationService.processReservations();
    }
}


/**
 * Reservation Class
 * Represents a booking request.
 * @version 6.0
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
}


/**
 * BookingRequestQueue Class
 * Maintains booking requests using FIFO queue.
 * @version 6.0
 */
class BookingRequestQueue {

    private Queue<Reservation> queue = new LinkedList<>();

    public void addRequest(Reservation reservation) {
        queue.offer(reservation);
    }

    public Reservation getNextRequest() {
        return queue.poll();
    }

    public boolean hasRequests() {
        return !queue.isEmpty();
    }
}


/**
 * RoomInventory Class
 * Maintains available room counts.
 * @version 6.0
 */
class RoomInventory {

    private HashMap<String, Integer> availability = new HashMap<>();

    public RoomInventory() {

        availability.put("Single Room", 2);
        availability.put("Double Room", 1);
        availability.put("Suite Room", 1);
    }

    public int getAvailability(String roomType) {
        return availability.getOrDefault(roomType, 0);
    }

    public void decreaseAvailability(String roomType) {

        int count = availability.get(roomType);
        availability.put(roomType, count - 1);
    }
}


/**
 * RoomAllocationService Class
 * Handles reservation confirmation and room allocation.
 * @version 6.0
 */
class RoomAllocationService {

    private RoomInventory inventory;
    private BookingRequestQueue queue;

    private Set<String> allocatedRoomIds = new HashSet<>();
    private HashMap<String, Set<String>> roomAllocations = new HashMap<>();

    public RoomAllocationService(RoomInventory inventory,
                                 BookingRequestQueue queue) {

        this.inventory = inventory;
        this.queue = queue;
    }

    public void processReservations() {

        while (queue.hasRequests()) {

            Reservation reservation = queue.getNextRequest();
            String roomType = reservation.getRoomType();

            int available = inventory.getAvailability(roomType);

            if (available > 0) {

                String roomId = generateRoomId(roomType);

                // ensure uniqueness
                while (allocatedRoomIds.contains(roomId)) {
                    roomId = generateRoomId(roomType);
                }

                allocatedRoomIds.add(roomId);

                roomAllocations
                        .computeIfAbsent(roomType, k -> new HashSet<>())
                        .add(roomId);

                inventory.decreaseAvailability(roomType);

                System.out.println("Reservation Confirmed");
                System.out.println("Guest: " + reservation.getGuestName());
                System.out.println("Room Type: " + roomType);
                System.out.println("Assigned Room ID: " + roomId);
                System.out.println();

            } else {

                System.out.println("Reservation Failed for "
                        + reservation.getGuestName()
                        + " - No " + roomType + " available.\n");
            }
        }
    }

    private String generateRoomId(String roomType) {

        String prefix = roomType.replace(" Room", "").substring(0, 1);
        int number = new Random().nextInt(900) + 100;

        return prefix + number;
    }
}

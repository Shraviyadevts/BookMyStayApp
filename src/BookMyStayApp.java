import java.util.HashMap;
import java.util.Map;

/**
 * ============================================================
 * MAIN CLASS - UseCase4RoomSearch
 * ============================================================
 *
 * Use Case 4: Room Search & Availability Check
 *
 * Description:
 * Demonstrates read-only search of available rooms
 * using centralized inventory and room domain objects.
 *
 * @author Developer
 * @version 4.1
 */

public class BookMyStayApp {

    public static void main(String[] args) {

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Initialize search service
        RoomSearchService searchService = new RoomSearchService(inventory);

        System.out.println("===== Available Rooms =====\n");

        // Perform room search
        searchService.searchAvailableRooms();
    }
}


/**
 * Room Inventory Class
 * Maintains centralized availability.
 *
 * @version 4.0
 */
class RoomInventory {

    private HashMap<String, Integer> availability;

    public RoomInventory() {

        availability = new HashMap<>();

        availability.put("Single Room", 5);
        availability.put("Double Room", 3);
        availability.put("Suite Room", 0); // Example unavailable room
    }

    public int getAvailability(String roomType) {
        return availability.getOrDefault(roomType, 0);
    }

    public Map<String, Integer> getAllAvailability() {
        return availability;
    }
}


/**
 * Search Service Class
 * Performs read-only room searches.
 *
 * @version 4.0
 */
class RoomSearchService {

    private RoomInventory inventory;

    public RoomSearchService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void searchAvailableRooms() {

        // Create room objects
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        Room[] rooms = {single, doubleRoom, suite};

        for (Room room : rooms) {

            int available = inventory.getAvailability(room.getRoomType());

            // Defensive check: only show available rooms
            if (available > 0) {

                room.displayDetails();
                System.out.println("Available Rooms: " + available);
                System.out.println();
            }
        }
    }
}


/**
 * Abstract Room Class
 *
 * @version 4.0
 */
abstract class Room {

    protected String roomType;
    protected double price;

    public Room(String roomType, double price) {
        this.roomType = roomType;
        this.price = price;
    }

    public String getRoomType() {
        return roomType;
    }

    public void displayDetails() {
        System.out.println("Room Type : " + roomType);
        System.out.println("Price     : $" + price);
    }
}


/**
 * Single Room Class
 */
class SingleRoom extends Room {

    public SingleRoom() {
        super("Single Room", 100);
    }
}


/**
 * Double Room Class
 */
class DoubleRoom extends Room {

    public DoubleRoom() {
        super("Double Room", 180);
    }
}


/**
 * Suite Room Class
 */
class SuiteRoom extends Room {

    public SuiteRoom() {
        super("Suite Room", 300);
    }
}
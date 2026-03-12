import java.util.HashMap;
import java.util.Map;

/**
 * ============================================================
 * MAIN CLASS - UseCase3InventorySetup
 * ============================================================
 *
 * Use Case 3: Centralized Room Inventory Management
 *
 * Description:
 * Demonstrates centralized inventory management using
 * a HashMap to maintain room availability.
 *
 * @author Developer
 * @version 3.1
 */

public class BookMyStayApp {

    public static void main(String[] args) {

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        System.out.println("===== Book My Stay - Room Inventory =====\n");

        // Display current inventory
        inventory.displayInventory();

        // Example update
        System.out.println("\nUpdating Double Room availability...\n");
        inventory.updateAvailability("Double Room", 4);

        // Display updated inventory
        inventory.displayInventory();
    }
}


/**
 * RoomInventory class manages room availability.
 *
 * @version 3.0
 */
class RoomInventory {

    private HashMap<String, Integer> roomAvailability;

    // Constructor to initialize inventory
    public RoomInventory() {

        roomAvailability = new HashMap<>();

        roomAvailability.put("Single Room", 5);
        roomAvailability.put("Double Room", 3);
        roomAvailability.put("Suite Room", 2);
    }

    // Method to get availability
    public int getAvailability(String roomType) {
        return roomAvailability.getOrDefault(roomType, 0);
    }

    // Method to update availability
    public void updateAvailability(String roomType, int count) {
        roomAvailability.put(roomType, count);
    }

    // Display inventory
    public void displayInventory() {

        for (Map.Entry<String, Integer> entry : roomAvailability.entrySet()) {

            System.out.println("Room Type : " + entry.getKey());
            System.out.println("Available : " + entry.getValue());
            System.out.println();
        }
    }
}
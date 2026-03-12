/**
 * ============================================================
 * MAIN CLASS - UseCase2RoomInitialization
 * ============================================================
 *
 * Use Case 2: Basic Room Types & Static Availability
 *
 * Description:
 * Demonstrates object modeling using abstraction,
 * inheritance, and polymorphism for hotel room types.
 *
 * Room types:
 * - Single Room
 * - Double Room
 * - Suite Room
 *
 * Availability is stored using simple variables.
 *
 * @author Developer
 * @version 2.1
 */

public class BookMyStayApp {

    public static void main(String[] args) {

        // Create room objects
        Room singleRoom = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suiteRoom = new SuiteRoom();

        // Static availability variables
        int singleAvailable = 5;
        int doubleAvailable = 3;
        int suiteAvailable = 2;

        System.out.println("===== Book My Stay - Room Availability =====\n");

        singleRoom.displayRoomDetails();
        System.out.println("Available Rooms: " + singleAvailable);
        System.out.println();

        doubleRoom.displayRoomDetails();
        System.out.println("Available Rooms: " + doubleAvailable);
        System.out.println();

        suiteRoom.displayRoomDetails();
        System.out.println("Available Rooms: " + suiteAvailable);
    }
}


/**
 * Abstract Room Class
 * Defines common properties of all room types.
 */
abstract class Room {

    protected String roomType;
    protected int beds;
    protected int size;
    protected double price;

    public Room(String roomType, int beds, int size, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    public void displayRoomDetails() {
        System.out.println("Room Type : " + roomType);
        System.out.println("Beds      : " + beds);
        System.out.println("Size      : " + size + " sq ft");
        System.out.println("Price     : $" + price);
    }
}


/**
 * Single Room Class
 */
class SingleRoom extends Room {

    public SingleRoom() {
        super("Single Room", 1, 200, 100.0);
    }
}


/**
 * Double Room Class
 */
class DoubleRoom extends Room {

    public DoubleRoom() {
        super("Double Room", 2, 350, 180.0);
    }
}


/**
 * Suite Room Class
 */
class SuiteRoom extends Room {

    public SuiteRoom() {
        super("Suite Room", 3, 500, 300.0);
    }
}
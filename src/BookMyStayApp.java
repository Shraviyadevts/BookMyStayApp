import java.util.*;

/**
 * =============================================================
 * MAIN CLASS - UseCase8BookingHistoryReport
 * =============================================================
 * Use Case 8: Booking History & Reporting
 */
public class BookMyStayApp {

    public static void main(String[] args) {

        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        // Create sample reservations
        Reservation r1 = new Reservation("RES101", "Shraviya", "Deluxe Room", 3000);
        Reservation r2 = new Reservation("RES102", "Rahul", "Suite Room", 5000);
        Reservation r3 = new Reservation("RES103", "Ananya", "Standard Room", 2000);

        // Add to booking history (confirmed bookings)
        history.addReservation(r1);
        history.addReservation(r2);
        history.addReservation(r3);

        // Display booking history
        System.out.println("=== Booking History ===");
        history.displayReservations();

        // Generate report
        System.out.println("\n=== Booking Report ===");
        reportService.generateReport(history.getAllReservations());
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
    private double cost;

    public Reservation(String reservationId, String guestName, String roomType, double cost) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.cost = cost;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public double getCost() {
        return cost;
    }

    public void display() {
        System.out.println("Reservation ID: " + reservationId +
                ", Guest: " + guestName +
                ", Room: " + roomType +
                ", Cost: ₹" + cost);
    }
}

/**
 * =============================================================
 * CLASS - BookingHistory
 * =============================================================
 */
class BookingHistory {

    // List to store reservations in insertion order
    private List<Reservation> reservations;

    public BookingHistory() {
        reservations = new ArrayList<>();
    }

    // Add confirmed reservation
    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    // Get all reservations
    public List<Reservation> getAllReservations() {
        return reservations;
    }

    // Display all reservations
    public void displayReservations() {
        if (reservations.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        for (Reservation r : reservations) {
            r.display();
        }
    }
}

/**
 * =============================================================
 * CLASS - BookingReportService
 * =============================================================
 */
class BookingReportService {

    // Generate summary report
    public void generateReport(List<Reservation> reservations) {

        if (reservations == null || reservations.isEmpty()) {
            System.out.println("No data available for report.");
            return;
        }

        double totalRevenue = 0;
        int totalBookings = reservations.size();

        for (Reservation r : reservations) {
            totalRevenue += r.getCost();
        }

        System.out.println("Total Bookings: " + totalBookings);
        System.out.println("Total Revenue: ₹" + totalRevenue);
        System.out.println("Average Booking Value: ₹" + (totalRevenue / totalBookings));
    }
}
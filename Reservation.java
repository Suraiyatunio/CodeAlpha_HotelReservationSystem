import java.io.Serializable;

public class Reservation implements Serializable {

    private static final long serialVersionUID = 1L;

    private int reservationId;
    private String guestName;
    private int roomNumber;
    private String roomCategory;
    private int nights;
    private double totalCost;
    private Payment payment;

    public Reservation(int reservationId, String guestName, Room room,
                       int nights, Payment payment) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomNumber = room.getRoomNumber();
        this.roomCategory = room.getCategory();
        this.nights = nights;
        this.totalCost = room.getPricePerNight() * nights;
        this.payment = payment;
    }

    public int getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public double getTotalCost() {
        return totalCost;
    }
    public void printDetails() {
        System.out.println("+------------------------------------------------+");
        System.out.println("|             BOOKING DETAILS                    |");
        System.out.println("+------------------------------------------------+");
        System.out.printf( "  Reservation ID : %d%n", reservationId);
        System.out.printf( "  Guest Name     : %s%n", guestName);
        System.out.printf( "  Room Number    : %d (%s)%n", roomNumber, roomCategory);
        System.out.printf( "  Nights         : %d%n", nights);
        System.out.printf( "  Total Cost     : $%.2f%n", totalCost);
        System.out.println("  " + payment);
        System.out.println("+------------------------------------------------+");
    }

    @Override
    public String toString() {
        return String.format(
            "ID:%d | %s | Room #%d (%s) | %d night(s) | $%.2f | %s",
            reservationId, guestName, roomNumber, roomCategory,
            nights, totalCost, (payment.isPaid() ? "PAID" : "UNPAID"));
    }
}

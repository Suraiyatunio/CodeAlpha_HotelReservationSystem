import java.io.Serializable;

public abstract class Room implements Serializable {

    private static final long serialVersionUID = 1L;
    private int roomNumber;
    private String category;
    private double pricePerNight;
    private boolean available;

    public Room(int roomNumber, String category, double pricePerNight) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.pricePerNight = pricePerNight;
        this.available = true; // every room starts as available
    }

    public abstract String getDescription();

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getCategory() {
        return category;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return String.format(
            "Room #%-3d | %-8s | $%-7.2f/night | %-11s | %s",
            roomNumber,
            category,
            pricePerNight,
            (available ? "AVAILABLE" : "BOOKED"),
            getDescription()
        );
    }
}

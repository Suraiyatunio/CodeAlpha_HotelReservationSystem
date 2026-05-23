public class DeluxeRoom extends Room {
    private static final long serialVersionUID = 1L;
    public DeluxeRoom(int roomNumber) {
        super(roomNumber, "Deluxe", 150.00);
    }

    @Override
    public String getDescription() {
        return "2 beds, city view, breakfast included";
    }
}

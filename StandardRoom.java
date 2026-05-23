public class StandardRoom extends Room {
    private static final long serialVersionUID = 1L;

    public StandardRoom(int roomNumber) {
        super(roomNumber, "Standard", 80.00);
    }

    @Override
    public String getDescription() {
        return "1 bed, free Wi-Fi, basic amenities";
    }
}

public class SuiteRoom extends Room {
    private static final long serialVersionUID = 1L;

    public SuiteRoom(int roomNumber) {
        super(roomNumber, "Suite", 300.00);
    }

    @Override
    public String getDescription() {
        return "Master suite, living area, jacuzzi, premium service";
    }
}

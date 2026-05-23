import java.io.*;
import java.util.ArrayList;
import java.util.List;
public class Hotel {

    private static final String ROOMS_FILE = "rooms.dat";
    private static final String RES_FILE   = "reservations.dat";

    private List<Room> rooms;
    private List<Reservation> reservations;
    private int nextReservationId;

    public Hotel() {
        rooms = new ArrayList<>();
        reservations = new ArrayList<>();
        nextReservationId = 1;
        loadData();
    }
    @SuppressWarnings("unchecked")
    private void loadData() {
        File roomsFile = new File(ROOMS_FILE);
        File resFile = new File(RES_FILE);

        if (roomsFile.exists()) {
            try (ObjectInputStream in =
                     new ObjectInputStream(new FileInputStream(roomsFile))) {
                rooms = (List<Room>) in.readObject();
                System.out.println("[Loaded " + rooms.size() + " rooms from file]");
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("[Could not read rooms file, seeding defaults]");
                seedRooms();
            }
        } else {
            seedRooms();
        }

        if (resFile.exists()) {
            try (ObjectInputStream in =
                     new ObjectInputStream(new FileInputStream(resFile))) {
                reservations = (List<Reservation>) in.readObject();
                // make sure new IDs don't collide with loaded ones
                for (Reservation r : reservations) {
                    if (r.getReservationId() >= nextReservationId) {
                        nextReservationId = r.getReservationId() + 1;
                    }
                }
                System.out.println("[Loaded " + reservations.size()
                        + " reservations from file]");
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("[Could not read reservations file]");
            }
        }
    }

    /** Writes the current rooms and reservations to disk. */
    public void saveData() {
        try (ObjectOutputStream out =
                 new ObjectOutputStream(new FileOutputStream(ROOMS_FILE))) {
            out.writeObject(rooms);
        } catch (IOException e) {
            System.out.println("Error saving rooms: " + e.getMessage());
        }

        try (ObjectOutputStream out =
                 new ObjectOutputStream(new FileOutputStream(RES_FILE))) {
            out.writeObject(reservations);
        } catch (IOException e) {
            System.out.println("Error saving reservations: " + e.getMessage());
        }
    }

    /** Creates a default set of rooms the very first time the app runs. */
    private void seedRooms() {
        // 101-104 Standard, 201-203 Deluxe, 301-302 Suite
        rooms.add(new StandardRoom(101));
        rooms.add(new StandardRoom(102));
        rooms.add(new StandardRoom(103));
        rooms.add(new StandardRoom(104));
        rooms.add(new DeluxeRoom(201));
        rooms.add(new DeluxeRoom(202));
        rooms.add(new DeluxeRoom(203));
        rooms.add(new SuiteRoom(301));
        rooms.add(new SuiteRoom(302));
        System.out.println("[Initialized hotel with " + rooms.size()
                + " default rooms]");
    }

    public List<Room> getAllRooms() {
        return rooms;
    }

    public List<Room> searchAvailableRooms(String category) {
        List<Room> found = new ArrayList<>();
        for (Room r : rooms) {
            if (r.isAvailable()) {
                if (category == null
                        || category.equalsIgnoreCase("all")
                        || r.getCategory().equalsIgnoreCase(category)) {
                    found.add(r);
                }
            }
        }
        return found;
    }

    public Room findRoom(int roomNumber) {
        for (Room r : rooms) {
            if (r.getRoomNumber() == roomNumber) {
                return r;
            }
        }
        return null;
    }
    public Reservation bookRoom(int roomNumber, String guestName,
                                int nights, String paymentMethod) {
        Room room = findRoom(roomNumber);

        if (room == null) {
            System.out.println(">> No room with number " + roomNumber);
            return null;
        }
        if (!room.isAvailable()) {
            System.out.println(">> Room #" + roomNumber + " is already booked.");
            return null;
        }
        if (nights <= 0) {
            System.out.println(">> Nights must be at least 1.");
            return null;
        }

        double amount = room.getPricePerNight() * nights;
        Payment payment = new Payment(amount, paymentMethod);
        payment.process();

        Reservation res = new Reservation(
                nextReservationId++, guestName, room, nights, payment);

        room.setAvailable(false);
        reservations.add(res);
        saveData();

        return res;
    }

    public boolean cancelReservation(int reservationId) {
        Reservation toRemove = null;
        for (Reservation r : reservations) {
            if (r.getReservationId() == reservationId) {
                toRemove = r;
                break;
            }
        }

        if (toRemove == null) {
            System.out.println(">> No reservation with ID " + reservationId);
            return false;
        }

        Room room = findRoom(toRemove.getRoomNumber());
        if (room != null) {
            room.setAvailable(true);
        }

        reservations.remove(toRemove);
        saveData();
        return true;
    }
    public List<Reservation> getAllReservations() {
        return reservations;
    }
    public Reservation findReservation(int reservationId) {
        for (Reservation r : reservations) {
            if (r.getReservationId() == reservationId) {
                return r;
            }
        }
        return null;
    }
}

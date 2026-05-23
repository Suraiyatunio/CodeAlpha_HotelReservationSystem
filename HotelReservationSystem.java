import java.util.List;
import java.util.Scanner;
public class HotelReservationSystem {

    private static Hotel hotel = new Hotel();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("\n========================================");
        System.out.println("    WELCOME TO THE HOTEL RESERVATION SYSTEM");
        System.out.println("========================================");

        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("Choose an option: ");

            switch (choice) {
                case 1: viewAllRooms();        break;
                case 2: searchRooms();         break;
                case 3: bookRoom();            break;
                case 4: cancelReservation();   break;
                case 5: viewAllReservations(); break;
                case 6: viewBookingDetails();  break;
                case 0:
                    running = false;
                    System.out.println("\nData saved. Goodbye!");
                    break;
                default:
                    System.out.println(">> Invalid option, try again.");
            }
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n---------------- MENU ----------------");
        System.out.println("1. View all rooms");
        System.out.println("2. Search available rooms");
        System.out.println("3. Book a room");
        System.out.println("4. Cancel a reservation");
        System.out.println("5. View all reservations");
        System.out.println("6. View a booking's details");
        System.out.println("0. Exit");
        System.out.println("--------------------------------------");
    }
    private static void viewAllRooms() {
        System.out.println("\n--- ALL ROOMS ---");
        for (Room r : hotel.getAllRooms()) {
            System.out.println(r);
        }
    }

    private static void searchRooms() {
        System.out.println("\nCategories: Standard, Deluxe, Suite (or 'all')");
        System.out.print("Enter category to search: ");
        String category = scanner.nextLine().trim();

        List<Room> results = hotel.searchAvailableRooms(category);
        if (results.isEmpty()) {
            System.out.println(">> No available rooms found for: " + category);
        } else {
            System.out.println("\n--- AVAILABLE ROOMS ---");
            for (Room r : results) {
                System.out.println(r);
            }
        }
    }

    private static void bookRoom() {
        System.out.println("\n--- BOOK A ROOM ---");
        List<Room> available = hotel.searchAvailableRooms("all");
        if (available.isEmpty()) {
            System.out.println(">> Sorry, no rooms are available right now.");
            return;
        }
        for (Room r : available) {
            System.out.println(r);
        }

        int roomNumber = readInt("Enter room number to book: ");
        System.out.print("Enter your name: ");
        String name = scanner.nextLine().trim();
        int nights = readInt("Number of nights: ");
        System.out.print("Payment method (e.g. Credit Card / PayPal / Cash): ");
        String method = scanner.nextLine().trim();

        Reservation res = hotel.bookRoom(roomNumber, name, nights, method);
        if (res != null) {
            System.out.println("\n>> Booking successful!");
            res.printDetails();   // booking details view
        }
    }

    private static void cancelReservation() {
        System.out.println("\n--- CANCEL RESERVATION ---");
        if (hotel.getAllReservations().isEmpty()) {
            System.out.println(">> There are no reservations to cancel.");
            return;
        }
        for (Reservation r : hotel.getAllReservations()) {
            System.out.println(r);
        }
        int id = readInt("Enter reservation ID to cancel: ");
        if (hotel.cancelReservation(id)) {
            System.out.println(">> Reservation #" + id + " cancelled. Room is free again.");
        }
    }

    private static void viewAllReservations() {
        System.out.println("\n--- ALL RESERVATIONS ---");
        List<Reservation> all = hotel.getAllReservations();
        if (all.isEmpty()) {
            System.out.println(">> No reservations yet.");
        } else {
            for (Reservation r : all) {
                System.out.println(r);
            }
        }
    }

    private static void viewBookingDetails() {
        System.out.println("\n--- BOOKING DETAILS VIEW ---");
        int id = readInt("Enter reservation ID: ");
        Reservation res = hotel.findReservation(id);
        if (res == null) {
            System.out.println(">> No reservation with ID " + id);
        } else {
            res.printDetails();
        }
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println(">> Please enter a valid number.");
            }
        }
    }
}

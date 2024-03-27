import api.AdminResource;
import model.customer.Customer;
import model.room.IRoom;
import model.room.Room;
import model.room.enums.RoomType;

import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;

/**
 * AdminMenu
 * <p>
 * Manages the admin menu functionality, including displaying the menu, handling user input,
 * adding rooms, showing all customers, showing all rooms, and displaying all reservations.
 * Provides methods for interacting with rooms and customers through the admin resource.
 * Uses a scanner for user input.
 * Implements a singleton design pattern to access the admin resource.
 *
 * @author ahmad deni atmaja saputra
 */
public class AdminMenu {
    private static final AdminResource adminResource = AdminResource.getSingleton();
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Displays the admin menu and handles user input for menu options.
     */
    public static void showAdminMenu() {
        boolean running = true;
        while (running) {
            printAdminMenu();
            String line = scanner.nextLine();
            if (line.length() == 1) {
                char choice = line.charAt(0);
                switch (choice) {
                    case '1' -> showAllCustomers();
                    case '2' -> showAllRooms();
                    case '3' -> adminResource.displayAllReservations();
                    case '4' -> addRoom();
                    case '5' -> {
                        MainMenu.showMainMenu();
                        running = false;
                    }
                    default -> System.out.println("Unknown action");
                }
            } else {
                System.out.println("Error: Invalid action");
            }
        }
    }

    /**
     * Prints the admin menu options.
     */
    private static void printAdminMenu() {
        System.out.print("""
                \nAdmin Menu
                --------------------------------------------
                1. Show all Customers
                2. Show all Rooms
                3. Show all Reservations
                4. Add a Room
                5. Back to Main Menu
                --------------------------------------------
                Please select a number for the menu option:
                """);
    }

    /**
     * Adds a room to the system after getting room details from the user.
     */
    private static void addRoom() {
        System.out.println("Enter room number:");
        String roomNumber = scanner.nextLine();
        try {
            Integer.parseInt(roomNumber);
        } catch (NumberFormatException e) {
            System.out.println("Oops, please input a valid room number. Would you like to continue adding a new room? (Y/N)");
            addAnotherRoom();
            return;
        }

        boolean roomExists = adminResource.getAllRooms().stream()
                .anyMatch(room -> room.getRoomNumber().equals(roomNumber));

        if (roomExists) {
            System.out.println("Oops, room number already added before. Please add another unique number. Would you like to add another room? (Y/N)");
            addAnotherRoom();
            return;
        }

        double roomPrice = getValidDoubleInput();
        RoomType roomType = getValidRoomTypeInput();



        Room room = new Room(roomNumber, roomPrice, roomType);
        adminResource.addRoom(Collections.singletonList(room));
        System.out.println("Room added successfully!");

        System.out.println("Would you like to add another room? (Y/N)");
        addAnotherRoom();
    }

    /**
     * Retrieves a valid double input from the user.
     *
     * @return the valid double input
     */
    private static double getValidDoubleInput() {
        while (true) {
            try {
                System.out.println("Enter room price:");
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException exp) {
                System.out.println("Invalid input! Please enter a valid number.");
            }
        }
    }

    /**
     * Retrieves a valid room type input from the user.
     *
     * @return the valid room type input
     */
    private static RoomType getValidRoomTypeInput() {
        while (true) {
            try {
                System.out.println("Enter room type (1 for single bed, 2 for double bed):");
                return RoomType.valueOfLabel(scanner.nextLine());
            } catch (IllegalArgumentException exp) {
                System.out.println("Invalid input! Please choose 1 for single bed or 2 for double bed.");
            }
        }
    }

    /**
     * Handles the user input for adding another room.
     */
    private static void addAnotherRoom() {
        while (true) {
            String answer = scanner.nextLine().toUpperCase();
            if (answer.equals("Y")) {
                addRoom();
                break;
            } else if (answer.equals("N")) {
                printAdminMenu();
                break;
            } else {
                System.out.println("Add another room, Please enter Y (Yes) or N (No)");
            }
        }
    }

    /**
     * Displays all rooms stored in the system.
     */
    private static void showAllRooms() {
        Collection<IRoom> rooms = adminResource.getAllRooms();
        printItems(rooms, "No rooms found.", "Available rooms:");
    }

    /**
     * Displays all customers stored in the system.
     */
    private static void showAllCustomers() {
        Collection<Customer> customers = adminResource.getAllCustomers();
        printItems(customers, "No customers found.", "All Customers:");
    }

    /**
     * Prints a collection of items with a header and empty message if the collection is empty.
     *
     * @param items the collection of items to print
     * @param emptyMessage the message to print if the collection is empty
     * @param header the header to print before printing the items
     */
    private static <T> void printItems(Collection<T> items, String emptyMessage, String header) {
        System.out.println(header);
        if (items.isEmpty()) {
            System.out.println(emptyMessage);
        } else {
            items.forEach(System.out::println);
        }
    }
}

import api.AdminResource;
import api.HotelResource;
import model.customer.Customer;
import model.reservation.Reservation;
import model.room.IRoom;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Scanner;

/**
 * MainMenu
 *
 * @author ahmad deni atmaja saputra
 */
public class MainMenu {
    private static final String DEFAULT_DATE_FORMAT = "MM/dd/yyyy";
    private static final HotelResource hotelResource = HotelResource.getSingleton();
    private static final AdminResource adminResource = AdminResource.getSingleton();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        showMainMenu();
    }

    /**
     * Displays the main menu options and continuously prompts the user for input until they choose to exit.
     * The user's input is then processed by the handleMainMenuChoice method.
     */
    public static void showMainMenu() {
        boolean running = true;
        while (running) {
            printMainMenu();
            String line = scanner.nextLine();
            if (line.length() == 1) {
                char choice = line.charAt(0);
                running = handleMainMenuChoice(choice);
            } else {
                System.out.println("Invalid input");
            }
        }
    }



    /**
     * Handles the user's choice from the main menu.
     * Performs different actions based on the selected menu option:
     * - If '1' is chosen, invokes the searchAndReserveRoom method to search and reserve a room.
     * - If '2' is chosen, invokes the displayMyReservations method to display the user's reservations.
     * - If '3' is chosen, invokes the createAccount method to create a new customer account.
     * - If '4' is chosen, invokes the showAdminMenu method from the AdminMenu class to display the admin menu.
     * - If '5' is chosen, prints "Exiting..." and returns false, indicating to exit the main loop.
     * - For any other input, prints "Unknown action" and continues the loop.
     *
     * @param choice The user's choice from the main menu.
     * @return True if the program should continue running after handling the choice, false otherwise.
     */
    private static boolean handleMainMenuChoice(char choice) {
        switch (choice) {
            case '1' -> {
                searchAndReserveRoom();
                return true;
            }
            case '2' -> {
                displayMyReservations();
                return true;
            }
            case '3' -> {
                createAccount();
                return true;
            }
            case '4' -> {
                AdminMenu.showAdminMenu();
                return true;
            }
            case '5' -> {
                System.out.println("Exiting...");
                return false;
            }
            default -> {
                System.out.println("Unknown action");
                return true;
            }
        }
    }


    /**
     * Prints the main menu of the hotel reservation application.
     * Displays the following options:
     * 1. Search and reserve a room
     * 2. Display my reservations
     * 3. Create an Account
     * 4. Admin
     * 5. Exit
     * Prompts the user to select a number for the menu option.
     */
    private static void printMainMenu() {
        System.out.println(
                """
                        Welcome to the Hotel Reservation Application
                        --------------------------------------------
                        1. Search and reserve a room
                        2. Display my reservations
                        3. Create an Account
                        4. Admin
                        5. Exit
                        --------------------------------------------
                        Please select a number for the menu option:""");
    }

    /**
     * Allows the user to search for available rooms and reserve a room.
     * Prompts the user to enter the check-in and check-out dates in MM/dd/yyyy format.
     * Validates the entered dates and ensures that the check-out date is after the check-in date.
     * Retrieves available rooms for the specified dates from the hotel resource.
     * If no rooms are available, informs the user. Otherwise, displays available rooms and proceeds to room reservation.
     */
    private static void searchAndReserveRoom() {
        Collection<IRoom> rooms = adminResource.getAllRooms();
        if(rooms.isEmpty()){
            System.out.println("No rooms found, try to insert in admin menu");
            return;
        }

        System.out.println("Enter Check-In Date (MM/dd/yyyy):");
        Date checkIn = getInputDate();

        Date checkOut;
        do {
            System.out.println("Enter Check-Out Date (MM/dd/yyyy):");
            checkOut = getInputDate();

            if (checkOut != null && checkOut.compareTo(checkIn) <= 0) {
                System.out.println("Check-Out date must be after Check-In date.");
            }
        } while (checkOut != null && checkOut.compareTo(checkIn) <= 0);

        if (checkIn != null && checkOut != null) {
            Collection<IRoom> availableRooms = hotelResource.findARoom(checkIn, checkOut);
            if (availableRooms.isEmpty()) {
                System.out.println("No rooms available for selected dates. Searching for recommended rooms...");

                Calendar recommendCheckIn = Calendar.getInstance();
                recommendCheckIn.setTime(checkIn);

                Calendar recommendCheckOut = Calendar.getInstance();
                recommendCheckOut.setTime(checkOut);

                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

                Collection<IRoom> recommendedRooms;
                int daysSettings = 7;
                int daysIncrement = 7;
                while (true) {
                    recommendCheckIn.add(Calendar.DATE, daysSettings);
                    recommendCheckOut.add(Calendar.DATE, daysSettings);

                    recommendedRooms = hotelResource.findARoom(recommendCheckIn.getTime(), recommendCheckOut.getTime());

                    if (!recommendedRooms.isEmpty()) {
                        System.out.println("Recommended rooms for alternative dates: checkin " + sdf.format(recommendCheckIn.getTime()) + " and checkout " + sdf.format(recommendCheckOut.getTime()));
                        printAvailableRooms(recommendedRooms);
                        reserveRoom(recommendCheckIn.getTime(), recommendCheckOut.getTime(), recommendedRooms);
                        break;
                    }

                    System.out.println("No recommended rooms available for alternative dates. Searching for rooms " + daysIncrement + " days later...");

                    daysIncrement += 7;
                }
            } else {
                printAvailableRooms(availableRooms);
                reserveRoom(checkIn, checkOut, availableRooms);
            }
        }
    }





    /**
     * Prompts the user to enter a date in MM/dd/yyyy format and retrieves the input from the scanner.
     * Parses the input string to a Date object using the SimpleDateFormat with the default date format.
     * Ensures that the entered date format is valid and matches the expected format.
     * If the entered date format is invalid, catches the ParseException and displays an error message.
     * Continues prompting the user until a valid date is entered.
     * Returns the parsed Date object representing the input date.
     */
    private static Date getInputDate() {
        while (true) {
            try {
                String input = scanner.nextLine();
                SimpleDateFormat dateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
                dateFormat.setLenient(false);

                Date parsedDate = dateFormat.parse(input);

                if (!dateFormat.format(parsedDate).equals(input)) {
                    throw new ParseException("Invalid date format. Please enter date in MM/dd/yyyy format.", 0);
                }

                return parsedDate;
            } catch (ParseException ex) {
                System.out.println("Invalid date format. Please enter date in MM/dd/yyyy format.");
            }
        }
    }

    /**
     * Prompts the user to confirm whether they would like to book a room.
     * Retrieves user input from the scanner to determine their choice.
     * If the user confirms (input 'y' or 'Y'), calls the handleRoomReservation method to proceed with the reservation process.
     * If the user declines (input 'n' or 'N'), displays a message indicating the return to the main menu.
     */
    private static void reserveRoom(Date checkInDate, Date checkOutDate, Collection<IRoom> rooms) {
        System.out.println("Would you like to book a room? (y/n)");
        String bookRoom = scanner.nextLine();

        if ("y".equalsIgnoreCase(bookRoom)) {
            handleRoomReservation(checkInDate, checkOutDate, rooms);
        } else {
            System.out.println("Returning to main menu.");
        }
    }

    /**
     * Prompts the user to confirm whether they have an account with the hotel.
     * Retrieves user input from the scanner to determine their choice.
     * If the user confirms (input 'y' or 'Y'), calls the processExistingCustomerReservation method to proceed with the reservation process.
     * If the user declines (input 'n' or 'N'), displays a message indicating the need to create an account.
     */
    private static void handleRoomReservation(Date checkInDate, Date checkOutDate, Collection<IRoom> rooms) {
        System.out.println("Do you have an account with us? (y/n)");
        String haveAccount = scanner.nextLine();
        if ("y".equalsIgnoreCase(haveAccount)) {
            processExistingCustomerReservation(checkInDate, checkOutDate, rooms);
        } else {
            System.out.println("Please create an account first, in menu 3. Create an Account.");
        }
    }

    /**
     * Prompts the user to enter their email address.
     * Validates the email format using the Customer.isValidEmail method.
     * If the email format is invalid, displays a message indicating the requirement for a valid email address.
     * Checks if the customer exists in the hotel resource database.
     * If the customer does not exist, prompts the user to create a new account.
     * If the customer exists, proceeds to complete the reservation process by calling the completeReservation method.
     */
    private static void processExistingCustomerReservation(Date checkInDate, Date checkOutDate, Collection<IRoom> rooms) {
        System.out.println("Enter your email: eg. name@domain.com");
        String customerEmail = scanner.nextLine();
        if (!Customer.isValidEmail(customerEmail)) {
            System.out.println("Invalid email format. Please enter a valid email address.");
            return;
        }

        if (hotelResource.getCustomer(customerEmail) == null) {
            System.out.println("Customer not found. Please create a new account, choose menu 3.");
        } else {
            completeReservation(customerEmail, checkInDate, checkOutDate, rooms);
        }
    }

    /**
     * Prompts the user to enter the room number they wish to reserve.
     * Retrieves the room object corresponding to the entered room number from the hotel resource.
     * Checks if the entered room is among the available rooms for the specified dates.
     * If the room is valid and available, completes the reservation process by calling the bookARoom method from the hotel resource.
     * Displays a success message along with the reservation details upon successful reservation.
     * If the entered room number is invalid or not available, displays a message indicating an invalid room number.
     */
    private static void completeReservation(String customerEmail, Date checkInDate, Date checkOutDate, Collection<IRoom> rooms) {
        System.out.println("Enter room number:");
        String roomNumber = scanner.nextLine();
        IRoom room = hotelResource.getRoom(roomNumber);
        if (room != null && rooms.contains(room)) {
            Reservation reservation = hotelResource.bookARoom(customerEmail, room, checkInDate, checkOutDate);
            System.out.println("Reservation successful:");
            System.out.println(reservation);
        } else {
            System.out.println("Invalid room number.");
        }
    }

    /**
     * Prompts the user to enter their email address.
     * Validates the entered email address format using the isValidEmail method of the Customer class.
     * If the email address is invalid, displays a message indicating an invalid email format and returns.
     * Retrieves the reservations associated with the entered email address from the hotel resource.
     * Displays the reservations if found, otherwise, prints a message indicating no reservations found.
     */
    private static void displayMyReservations() {
        System.out.println("Enter your email: eg. name@domain.com");
        String customerEmail = scanner.nextLine();
        if (!Customer.isValidEmail(customerEmail)) {
            System.out.println("Invalid email format. Please enter a valid email address.");
            return;
        }
        printReservations(hotelResource.getCustomersReservations(customerEmail));
    }

    /**
     * Prints the reservations associated with a customer.
     * Checks if the reservations collection is null or empty.
     * If no reservations are found, prints a message indicating no reservations found.
     * If reservations are found, prints a message indicating "Your reservations:" and then prints each reservation.
     *
     * @param reservations A collection of Reservation objects associated with a customer.
     */
    private static void printReservations(Collection<Reservation> reservations) {
        if (reservations == null || reservations.isEmpty()) {
            System.out.println("No reservations found.");
        } else {
            System.out.println("Your reservations:");
            reservations.forEach(System.out::println);
        }
    }


    /**
     * Allows a user to create a new customer account.
     * Prompts the user to enter their email, first name, and last name.
     * Attempts to create a new customer account with the provided information using the HotelResource.
     * If the account creation is successful, prints a message indicating "Account created successfully!"
     * If an IllegalArgumentException occurs during account creation, prints the error message.
     */
    private static void createAccount() {
        System.out.println("Enter Email (name@domain.com):");
        String email = scanner.nextLine();

        System.out.println("Enter First Name:");
        String firstName = scanner.nextLine();

        System.out.println("Enter Last Name:");
        String lastName = scanner.nextLine();

        try {
            hotelResource.createACustomer(email, firstName, lastName);
            System.out.println("Account created successfully!");
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Prints the available rooms to the console.
     * If the provided collection of rooms is empty, prints "No rooms found."
     * Otherwise, prints "Available rooms:" followed by the details of each room in the collection.
     */
    private static void printAvailableRooms(Collection<IRoom> rooms) {
        if (rooms.isEmpty()) {
            System.out.println("No rooms found.");
        } else {
            System.out.println("Available rooms:");
            rooms.forEach(System.out::println);
        }
    }
}

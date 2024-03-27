package service.reservation;

import model.customer.Customer;
import model.reservation.Reservation;
import model.room.IRoom;

import java.util.*;
import java.util.stream.Collectors;

/**
 * ReservationService
 * <p>
 * Manages reservations, including adding rooms, reserving rooms for customers,
 * finding available rooms, and printing all reservations.
 * Implements a singleton design pattern.
 * Uses maps to store rooms and reservations, where the keys are room numbers and customer emails, respectively.
 * Provides methods to interact with reservations, such as adding, retrieving, and printing.
 * Provides methods to find available rooms for a given date range.
 * Provides methods to check for overlapping reservations.
 *
 * @author ahmad deni atmaja saputra
 */
public class ReservationService {

    private static final ReservationService SINGLETON = new ReservationService();

    private final Map<String, IRoom> rooms = new HashMap<>();
    private final Map<String, Collection<Reservation>> reservations = new HashMap<>();

    private ReservationService() {}

    /**
     * Returns the singleton instance of ReservationService.
     *
     * @return the singleton instance of ReservationService
     */
    public static ReservationService getSingleton() {
        return SINGLETON;
    }

    /**
     * Adds a room to the reservation service.
     *
     * @param room the room to add
     */
    public void addRoom(final IRoom room) {
        rooms.put(room.getRoomNumber(), room);
    }

    /**
     * Retrieves a room by its room number.
     *
     * @param roomNumber the room number
     * @return the room with the specified room number, or null if not found
     */
    public IRoom getARoom(final String roomNumber) {
        return rooms.get(roomNumber);
    }

    /**
     * Retrieves all rooms stored in the service.
     *
     * @return a collection containing all rooms
     */
    public Collection<IRoom> getAllRooms() {
        return rooms.values();
    }

    /**
     * Reserves a room for a customer for the specified dates.
     *
     * @param customer the customer reserving the room
     * @param room the room to reserve
     * @param checkInDate the check-in date
     * @param checkOutDate the check-out date
     * @return the reservation object representing the reservation
     */
    public Reservation reserveARoom(final Customer customer, final IRoom room,
                                    final Date checkInDate, final Date checkOutDate) {
        final Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);

        Collection<Reservation> customerReservations = getCustomersReservation(customer);

        if (customerReservations == null) {
            customerReservations = new LinkedList<>();
        }

        customerReservations.add(reservation);
        reservations.put(customer.getEmail(), customerReservations);

        return reservation;
    }

    /**
     * Finds available rooms for the specified date range.
     *
     * @param checkInDate the check-in date
     * @param checkOutDate the check-out date
     * @return a collection of available rooms for the given date range
     */
    public Collection<IRoom> findRooms(final Date checkInDate, final Date checkOutDate) {
        return findAvailableRooms(checkInDate, checkOutDate);
    }

    /**
     * Finds available rooms for the specified date range.
     *
     * @param checkInDate the check-in date
     * @param checkOutDate the check-out date
     * @return a collection of available rooms for the given date range
     */
    private Collection<IRoom> findAvailableRooms(final Date checkInDate, final Date checkOutDate) {
        final Collection<Reservation> allReservations = getAllReservations();
        final Collection<IRoom> notAvailableRooms = new LinkedList<>();

        for (Reservation reservation : allReservations) {
            if (reservationOverlaps(reservation, checkInDate, checkOutDate)) {
                notAvailableRooms.add(reservation.getRoom());
            }
        }

        return rooms.values().stream().filter(room -> notAvailableRooms.stream()
                        .noneMatch(notAvailableRoom -> notAvailableRoom.equals(room)))
                .collect(Collectors.toList());
    }

    /**
     * Checks if a reservation overlaps with the specified date range.
     *
     * @param reservation the reservation to check
     * @param checkInDate the check-in date
     * @param checkOutDate the check-out date
     * @return true if the reservation overlaps with the date range, false otherwise
     */
    private boolean reservationOverlaps(final Reservation reservation, final Date checkInDate,
                                        final Date checkOutDate){
        return checkInDate.before(reservation.getCheckOutDate())
                && checkOutDate.after(reservation.getCheckInDate());
    }

    /**
     * Retrieves all reservations for a given customer.
     *
     * @param customer the customer to retrieve reservations for
     * @return a collection of reservations for the specified customer
     */
    public Collection<Reservation> getCustomersReservation(final Customer customer) {
        return reservations.get(customer.getEmail());
    }

    /**
     * Prints all reservations.
     */
    public void printAllReservation() {
        final Collection<Reservation> reservations = getAllReservations();

        if (reservations.isEmpty()) {
            System.out.println("No reservations found.");
        } else {
            for (Reservation reservation : reservations) {
                System.out.println(reservation + "\n");
            }
        }
    }

    /**
     * Retrieves all reservations.
     *
     * @return a collection containing all reservations
     */
    private Collection<Reservation> getAllReservations() {
        final Collection<Reservation> allReservations = new LinkedList<>();

        for(Collection<Reservation> reservations : reservations.values()) {
            allReservations.addAll(reservations);
        }

        return allReservations;
    }
}

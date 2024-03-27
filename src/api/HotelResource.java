package api;

import model.customer.Customer;
import model.reservation.Reservation;
import model.room.IRoom;
import service.customer.CustomerService;
import service.reservation.ReservationService;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

/**
 * HotelResource
 * <p>
 * Provides access to hotel-related operations such as managing customers, rooms, and reservations.
 * Singleton pattern is used to ensure only one instance of this class exists.
 *
 * @author ahmad deni atmaja saputra
 */
public class HotelResource {

    // Singleton instance
    private static final HotelResource SINGLETON = new HotelResource();

    // Services
    private final CustomerService customerService = CustomerService.getSingleton();
    public final ReservationService reservationService = ReservationService.getSingleton();

    // Private constructor to prevent instantiation
    private HotelResource() {}

    /**
     * Retrieves the singleton instance of HotelResource.
     *
     * @return the singleton instance
     */
    public static HotelResource getSingleton() {
        return SINGLETON;
    }

    /**
     * Retrieves a customer by email.
     *
     * @param email the email of the customer to retrieve
     * @return the customer with the specified email, or null if not found
     */
    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    /**
     * Creates a new customer.
     *
     * @param email the email of the new customer
     * @param firstName the first name of the new customer
     * @param lastName the last name of the new customer
     */
    public void createACustomer(String email, String firstName, String lastName) {
        customerService.addCustomer(email, firstName, lastName);
    }

    /**
     * Retrieves a room by room number.
     *
     * @param roomNumber the number of the room to retrieve
     * @return the room with the specified number
     */
    public IRoom getRoom(String roomNumber) {
        return reservationService.getARoom(roomNumber);
    }

    /**
     * Books a room for a customer.
     *
     * @param customerEmail the email of the customer booking the room
     * @param room the room to be booked
     * @param checkInDate the check-in date for the reservation
     * @param checkOutDate the check-out date for the reservation
     * @return the reservation for the booked room
     */
    public Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate) {
        return reservationService.reserveARoom(getCustomer(customerEmail), room, checkInDate, checkOutDate);
    }

    /**
     * Retrieves reservations made by a customer.
     *
     * @param customerEmail the email of the customer
     * @return a collection of reservations made by the customer
     */
    public Collection<Reservation> getCustomersReservations(String customerEmail) {
        final Customer customer = getCustomer(customerEmail);

        if (customer == null) {
            return Collections.emptyList();
        }

        return reservationService.getCustomersReservation(getCustomer(customerEmail));
    }

    /**
     * Finds available rooms for a given date range.
     *
     * @param checkIn the check-in date
     * @param checkOut the check-out date
     * @return a collection of available rooms
     */
    public Collection<IRoom> findARoom(final Date checkIn, final Date checkOut) {
        return reservationService.findRooms(checkIn, checkOut);
    }

}

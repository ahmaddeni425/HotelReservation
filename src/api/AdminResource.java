package api;

import model.customer.Customer;
import model.room.IRoom;
import service.customer.CustomerService;
import service.reservation.ReservationService;

import java.util.Collection;
import java.util.List;

/**
 * AdminResource
 * <p>
 * Provides access to administrative operations such as managing rooms, retrieving customer information,
 * and displaying reservations.
 * Singleton pattern is used to ensure only one instance of this class exists.
 *
 * @author ahmad deni atmaja saputra
 */
public class AdminResource {

    // Singleton instance
    private static final AdminResource SINGLETON = new AdminResource();

    // Services
    private final CustomerService customerService = CustomerService.getSingleton();
    private final ReservationService reservationService = ReservationService.getSingleton();

    // Private constructor to prevent instantiation
    private AdminResource() {}

    /**
     * Retrieves the singleton instance of AdminResource.
     *
     * @return the singleton instance
     */
    public static AdminResource getSingleton() {
        return SINGLETON;
    }

    /**
     * Adds rooms to the system.
     *
     * @param rooms the list of rooms to add
     */
    public void addRoom(List<IRoom> rooms) {
        rooms.forEach(reservationService::addRoom);
    }

    /**
     * Retrieves all rooms in the system.
     *
     * @return a collection of all rooms
     */
    public Collection<IRoom> getAllRooms() {
        return reservationService.getAllRooms();
    }

    /**
     * Retrieves all customers in the system.
     *
     * @return a collection of all customers
     */
    public Collection<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    /**
     * Displays all reservations made in the system.
     */
    public void displayAllReservations() {
        reservationService.printAllReservation();
    }
}

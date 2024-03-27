package model.reservation;

import model.customer.Customer;
import model.room.IRoom;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Reservation
 * <p>
 * Represents a reservation made by a customer for a room.
 * Stores information about the customer, room, check-in date, and check-out date.
 * Provides methods to access reservation details.
 *
 * @author ahmad deni atmaja saputra
 */
public class Reservation {

    private final Customer customer;
    private final IRoom room;
    private final Date checkInDate;
    private final Date checkOutDate;

    /**
     * Constructs a reservation with the specified customer, room, check-in date, and check-out date.
     *
     * @param customer the customer making the reservation
     * @param room the room reserved by the customer
     * @param checkInDate the check-in date of the reservation
     * @param checkOutDate the check-out date of the reservation
     */
    public Reservation(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    /**
     * Gets the room reserved by the customer.
     *
     * @return the reserved room
     */
    public IRoom getRoom() {
        return room;
    }

    /**
     * Gets the check-in date of the reservation.
     *
     * @return the check-in date
     */
    public Date getCheckInDate() {
        return checkInDate;
    }

    /**
     * Gets the check-out date of the reservation.
     *
     * @return the check-out date
     */
    public Date getCheckOutDate() {
        return checkOutDate;
    }

    /**
     * Returns a string representation of the reservation.
     *
     * @return a string containing details of the reservation
     */
    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        return "Reservation{" +
                "Customer: " + customer +
                ", Room: " + room +
                ", Check-In Date: " + sdf.format(checkInDate) +
                ", Check-Out Date: " + sdf.format(checkOutDate) +
                '}';
    }
}

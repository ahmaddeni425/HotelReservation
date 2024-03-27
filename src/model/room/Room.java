package model.room;

import model.room.enums.RoomType;

import java.util.Objects;

/**
 * Room
 * <p>
 * Represents a room in a hotel.
 * <p>
 * Stores information such as room number, price, and room type.
 *
 * @author ahmad deni atmaja saputra
 */
public class Room implements IRoom {

    private final String roomNumber;
    private final Double price;
    private final RoomType enumeration;

    /**
     * Constructs a room with the given room number, price, and room type.
     *
     * @param roomNumber the room number
     * @param price the price of the room
     * @param enumeration the room type enumeration
     */
    public Room(final String roomNumber, final Double price, final RoomType enumeration) {
        this.roomNumber = roomNumber;
        this.price = price;
        this.enumeration = enumeration;
    }

    /**
     * Returns the room number.
     *
     * @return the room number
     */
    public String getRoomNumber() {
        return this.roomNumber;
    }

    /**
     * Returns a string representation of the room.
     *
     * @return a string representation of the room
     */
    @Override
    public String toString() {
        return "Room Number: " + this.roomNumber
                + " Price: $" + this.price
                + " Enumeration: " + this.enumeration;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param obj the reference object with which to compare
     * @return true if this object is the same as the obj argument; false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }

        if(!(obj instanceof Room room)) {
            return false;
        }

        return Objects.equals(this.roomNumber, room.roomNumber);
    }


}

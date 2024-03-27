package model.room;

import model.room.enums.RoomType;

public class FreeRoom extends Room {
    public FreeRoom(String roomNumber, RoomType roomType) {
        super(roomNumber, 0.0, roomType); // Free rooms have a price of 0.0
    }

}

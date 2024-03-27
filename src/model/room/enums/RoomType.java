package model.room.enums;

import java.util.Arrays;

/**
 * RoomType
 * <p>
 * Enumerates the types of rooms available.
 * Provides a method to obtain a RoomType based on its label.
 * <p>
 * Each RoomType has a corresponding label to uniquely identify it.
 *
 * @author ahmad deni atmaja saputra
 */
public enum RoomType {
    SINGLE("1"),
    DOUBLE("2");

    public final String label;

    /**
     * Constructs a RoomType with the specified label.
     *
     * @param label the label representing the room type
     */
    private RoomType(String label) {
        this.label = label;
    }

    /**
     * Returns the RoomType corresponding to the given label.
     *
     * @param label the label of the room type
     * @return the RoomType with the specified label
     * @throws IllegalArgumentException if the label does not match any RoomType
     */
    public static RoomType valueOfLabel(String label) {
        return Arrays.stream(RoomType.values())
                .filter(roomType -> roomType.label.equals(label))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid room type label: " + label));
    }

}
